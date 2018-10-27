package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gmvn on 8/31/16.
 */
@Entity
@Table(name = "email_master")
public class EmailMaster implements java.io.Serializable {

    private Integer id;
    private String name;
    private String email;

    public EmailMaster() {
    }

    public EmailMaster(Integer id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
