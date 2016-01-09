package org.blockchain.borrowing.domain;

import javax.persistence.*;

/**
 * User Entity
 *
 * Created by pengchangguo on 16/1/9.
 */

@Entity
@Table(name = "b_user")
public class User {

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 真实姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 身份证号码
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 手机号码
     */
    @Column(name = "phone")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
