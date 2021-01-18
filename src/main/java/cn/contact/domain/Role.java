package cn.contact.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/***
 * 人员角色
 */
@Entity
@Table(name="test_role")
@Getter
@Setter
public class Role
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

    @Column(name = "rolename")
    private String rolename;

    @ManyToOne(targetEntity = Labor.class)
    @JoinColumn(name = "sid",referencedColumnName = "id")
    private Labor labor;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                '}';
    }
}
