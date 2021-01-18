package cn.contact.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtils {

    private static EntityManagerFactory factory;

    static {
        //加载配置文件,创建工厂对象(实体管理器工厂)
        factory = Persistence.createEntityManagerFactory("myjpa");
    }

    /***
     * 获取EntityManager对象
     */

    public static EntityManager getEntityManager(){
        EntityManager em = factory.createEntityManager();
        return em;
    }
}
