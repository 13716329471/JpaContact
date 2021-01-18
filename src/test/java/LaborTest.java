import cn.contact.domain.Labor;
import cn.contact.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class LaborTest {

    @Test
    public void getLabor() {
        //获取EntityManager
        EntityManager em = JpaUtils.getEntityManager();
        //3.获取事务对象,开启事务
        EntityTransaction et = em.getTransaction();
        et.begin();
        //4.完成增删改查操作
        String jpql = " from Labor";
        List<Labor> resultList = em.createQuery(jpql).getResultList();
        System.out.println(resultList.get(0).getName());
        //5.提交事务/回滚事务
        et.commit();
        //6.释放资源
        em.close();
    }

    @Test
    public void addLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Labor labor = new Labor();
        labor.setName("马六");
        labor.setSex(2);
        labor.setBirthday(new Date());
        em.persist(labor);
        et.commit();
        em.close();
    }

    @Test
    public void updateLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Labor labor = em.find(Labor.class,1l);
        labor.setName("张三1");
        em.merge(labor);
        et.commit();
        em.close();
    }

    @Test
    public void delLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Labor labor = em.find(Labor.class,1l);
        em.remove(labor);
        et.commit();
        em.close();
    }

    @Test
    public void orderLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        String jpql = "from Labor order by id desc";
        List resultList = em.createQuery(jpql).getResultList();
        System.out.println(resultList);
        et.commit();
        em.close();
    }

    @Test
    public void countLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        String jpql = "select count(*) from Labor order by id desc";
        Object singleResult = em.createQuery(jpql).getSingleResult();
        System.out.println(singleResult);
        et.commit();
        em.close();
    }

    @Test
    public void pageLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        String jpql = "from Labor";
        Query query = em.createQuery(jpql);
        query.setFirstResult(0);
        query.setMaxResults(2);
        List resultList = query.getResultList();
        System.out.println(resultList.size());
        et.commit();
        em.close();
    }

    @Test
    public void conditionLabor(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        String jpql = "from Labor where name = ?1 and age = ?2";
        Query query = em.createQuery(jpql);
        query.setParameter(1,"张三");
        query.setParameter(2,10);
        List resultList = query.getResultList();
        System.out.println(resultList.size());
        et.commit();
        em.close();
    }
}
