package cn.contact.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

/***
 * 人员实体类
 */
@Entity
@Table(name="test_labor")
@Getter
@Setter
public class Labor
{
    /**
     * GenerationType.IDENTITY 自增 mysql
     * GenerationType.SEQUENCE 序列 oracle
     * GenerationType.TABLE JPA提供的一种机制，通过一张数据库表完成主键自增
     * GenerationType.AUTO 由程序根据数据库类型自动选择适合的主键生成策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "age")
    private Integer age;

    /**
     * @Transient 不对该字段持久化
     */
    @Transient
    private List<Role> roleList = new ArrayList<>();

    /**
     * 配置人员和角色的一对多关系
     * 1.声明关系
     *      @OneToMany 一对多
     *          targetEntity：从表对象
     * 2.配置外键
     *      @JoinColumn 配置外键
     *          name：外键字段名称
     *          referencedColumnName：主表主键名称
     * 因为在主表对象中添加了外键配置，所以主表也具备了维护外键的作用
     * 3.放弃外键维护权
     *      mappedBy:从表多对一配置关系的属性名称
     *      cascade:配置级联
     *       fetch:EAGER-立即加载,LAZY-延迟加载
     */
    @OneToMany(mappedBy = "labor",cascade = CascadeType.ALL)
    private Set<Role> roleSet = new HashSet<>();

    @Override
    public String toString() {
        return "Labor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
