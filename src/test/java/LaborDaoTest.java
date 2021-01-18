import cn.contact.dao.LaborDao;
import cn.contact.dao.RoleDao;
import cn.contact.domain.Labor;
import cn.contact.domain.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

//声明spring提供的单元测试环境
@RunWith(SpringJUnit4ClassRunner.class)
//指定spring容器的配置信息
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LaborDaoTest {

    @Autowired
    private LaborDao laborDao;

    @Autowired
    private RoleDao roleDao;

    @Test
    public void findById() {
        Labor labor = laborDao.getOne(1L);
        System.out.println(labor);
    }

    @Test
    public void saveLabor() {
        /**
         * 若save的对象有id,更新
         * 若没有,新增
         */
        Labor labor = laborDao.getOne(4L);
        labor.setName("马六一");
        laborDao.save(labor);
    }

    @Test
    public void findAll() {
        /**
         * Specification:查询条件
         *      Root:查询的根对象(查询的任何属性都可以从根对象中获取)
         *      CriteriaQuery:顶层查询对象，自定义查询方式
         *      CriteriaBuilder:查询构造器，里面封装了很多的查询条件
         * Predicate toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);
         */
        //匿名内部类
        Specification<Labor> spec = (root, cq, cb) -> {
            //获取属性
            Path<Object> age = root.get("age");
            Path<Object> name = root.get("name");
            //构造查询条件
            Predicate p1 = cb.equal(age,15);
            Predicate p2 = cb.like(name.as(String.class), "%一");
            return cb.and(p1, p2);
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        /**
         *PageRequest是接口Pageable的实现类
         * 创建PageRequest的过程中，需要调用它的有参构造方法
         * 第一个参数:当前查询的页数(第一页是0)
         * 第二个参数:要查询的条数
         */
        Pageable pageable = PageRequest.of(0,2,sort);
        Page<Labor> page = laborDao.findAll(spec, pageable);
        for (Labor labor : page.getContent()) {
            System.out.println(labor.toString());
        }
    }

    @Test
    public void findByJpql() {
        List<Labor> all = laborDao.findByJpql(10);
        for (Labor labor : all) {
            System.out.println(labor.getName());
        }
    }

    @Test
    public void findBySql() {
        List<Labor> all1 = laborDao.findBySql("张三");
        for (Labor labor : all1) {
            System.out.println(labor.getAge());
        }
    }

    @Test
    public void findByNameingRule() {
        //精准查询
        //List<Labor> all1 = laborDao.findByName("张三");
        //模糊查询
        //List<Labor> all = laborDao.findByNameLike("%一");
        //多条件查询并按ID排序
        List<Labor> all = laborDao.findByNameLikeAndAgeOrderByIdDesc("%一", 15);
        for (Labor labor : all) {
            System.out.println(labor.toString());
        }
    }

    /***
     * 通过jpql语句更新人员信息
     * 需要手动添加事务支持
     * 执行结束之后，会默认回滚事务，所以需要指定@Rollback(value = false)
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void updateLabor() {
        laborDao.updateLaborById("张三一", 15, 1L);
    }

    /***
     * 级联添加数据
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void oneToManyAdd(){
        Labor labor = new Labor();
        labor.setName("赵七");
        labor.setAge(15);
        labor.setSex(1);
        labor.setBirthday(new Date());

        Role role = new Role();
        role.setRolename("test");
        //二选一即可
        labor.getRoleSet().add(role);
        role.setLabor(labor);

        laborDao.save(labor);
        roleDao.save(role);
    }

    /***
     * 级联删除数据
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void oneToManyDel(){
        Labor labor = laborDao.getOne(6L);
        laborDao.delete(labor);
    }

    /***
     * 级联查询数据(一对多)
     * 对象导航查询默认使用的是延迟加载的方式进行查询
     * 调用get方法时获取从表对象时并不会立即发起数据库查询,只有在使用时才会发起数据库查询
     * 可通过在配置多表映射关系的注解上添加fetch属性设置是否延迟加载
     * 一对多查询时建议使用延迟加载
     */
    @Test
    @Transactional
    public void oneToManyQuery(){
        Labor one = laborDao.getOne(1L);
        System.out.println(one.toString());
        Set<Role> roleSet = one.getRoleSet();
        for(Role role : roleSet){
            System.out.println(role.toString());
        }
    }

    /***
     * 级联查询数据(多对一)
     * 对象导航查询默认使用的是立即加载的方式进行查询
     * 多对一查询时建议使用立即加载
     */
    @Test
    @Transactional
    public void ManyToOneQuery(){
        Role role = roleDao.getOne(5L);
        Labor labor = role.getLabor();
        System.out.println(role.toString());
        System.out.println(labor.toString());
    }
}
