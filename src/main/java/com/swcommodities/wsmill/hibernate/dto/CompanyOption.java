/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author trung
 */

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by dunguyen on 8/8/16.
 */

@Entity
@Table(name = "company_options")
public class CompanyOption extends AbstractTimestampEntity implements Comparable, java.io.Serializable {

    public CompanyOption() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = CompanyMaster.class)
    @JsonIgnore
    private CompanyMaster company;

    private String name;

    private String value;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CompanyMaster getCompany() {
        return company;
    }

    public void setCompany(CompanyMaster company) {
        this.company = company;
        company.getCompanyOptions().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object o) {
        CompanyOption cat = (CompanyOption) o;
        if(cat.getId() > id) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
