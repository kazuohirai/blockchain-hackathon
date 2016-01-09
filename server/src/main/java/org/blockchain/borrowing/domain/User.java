package org.blockchain.borrowing.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 账户金额
     */
    @Column(name = "amount")
    private double amount;

    /**
     * 联系人
     */
    @Transient()
    private List<User> contacts;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static User sampleValue() {
        User user = new User();
        user.id = 1L;
        user.name = "pcg";
        user.phone = "15802540615";
        return user;
    }
}
