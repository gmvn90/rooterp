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

/**
 * Created by gmvn on 11/25/16.
 */
@Entity
@Table(name = "warehouse_master")
public class WarehouseMaster extends AbstractTimestampEntity {

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
    private Country country;

    private String representative;
    private String role;
    private String email;
    private String fax;
    private String telephone;

    private Byte status;
    private Boolean isSworn;

    public WarehouseMaster() {
    }

    public WarehouseMaster(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public WarehouseMaster setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WarehouseMaster setName(String name) {
        this.name = name;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public WarehouseMaster setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public WarehouseMaster setAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public WarehouseMaster setAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public WarehouseMaster setCountry(Country country) {
        this.country = country;
        return this;
    }

    public String getRepresentative() {
        return representative;
    }

    public WarehouseMaster setRepresentative(String representative) {
        this.representative = representative;
        return this;
    }

    public String getRole() {
        return role;
    }

    public WarehouseMaster setRole(String role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public WarehouseMaster setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public WarehouseMaster setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public WarehouseMaster setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public WarehouseMaster setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Boolean getSworn() {
        return isSworn;
    }

    public void setSworn(Boolean sworn) {
        isSworn = sworn;
    }
}
