package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by gmvn on 11/25/16.
 */
@Entity
@Table(name = "courier_master")
public class CourierMaster extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String address1;
    @Column(columnDefinition = "TEXT")
    private String address2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Country country;

    private String representative;
    private String role;
    private String email;
    private String fax;
    private String telephone;

    private int accountNo;

    private Byte status;

    private double cost = 0;

    public CourierMaster() {
    }

    public CourierMaster(Integer id) {
        this.id = id;
    }
    
    public CourierMaster(String name) {
    	this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public CourierMaster setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourierMaster setName(String name) {
        this.name = name;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public CourierMaster setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public CourierMaster setAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public CourierMaster setAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public CourierMaster setCountry(Country country) {
        this.country = country;
        return this;
    }

    public String getRepresentative() {
        return representative;
    }

    public CourierMaster setRepresentative(String representative) {
        this.representative = representative;
        return this;
    }

    public String getRole() {
        return role;
    }

    public CourierMaster setRole(String role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CourierMaster setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public CourierMaster setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public CourierMaster setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public CourierMaster setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public CourierMaster setAccountNo(int accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
