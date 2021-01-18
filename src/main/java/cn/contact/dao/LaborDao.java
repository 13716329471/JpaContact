package cn.contact.dao;

import cn.contact.domain.Labor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/***
 * JpaRepository<操作的实体类类型,主键类型>,封装了基本的CRUD
 * JpaSpecificationExecutor<操作的实体类类型>,封装了复杂查询
 * @author lmh
 */
public interface LaborDao extends JpaRepository<Labor, Long>, JpaSpecificationExecutor<Labor> {

    /***
     * jpql方式查询
     * @param age-年龄
     * @return 人员集合
     */
    @Query(value = "from Labor t where t.age = ?1")
    List<Labor> findByJpql(Integer age);

    /***
     * sql方式查询
     * @param name-姓名
     * @return 人员集合
     */
    @Query(value = "select * from test_labor t where t.name = ?1", nativeQuery = true)
    List<Labor> findBySql(String name);

    /***
     * 命名规则方式查询
     * @param name-姓名
     * @return 人员集合
     */
    List<Labor> findByName(String name);
    List<Labor> findByNameLike(String name);
    List<Labor> findByNameLikeAndAgeOrderByIdDesc(String name,Integer age);
    /***
     * 更新人员
     * @Query声明此方法执行的是查询操作
     * @Modifying声明此方法执行的是更新操作
     * @param name-姓名
     * @param age-年龄
     * @param id-ID
     */
    @Query("update Labor set age=?2,name=?1 where id=?3")
    @Modifying
    void updateLaborById(String name, Integer age, Long id);
}
