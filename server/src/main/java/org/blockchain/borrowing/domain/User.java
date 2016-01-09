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

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

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
}
