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
 * Created by dunguyen on 10/11/16.
 */
@Entity
@Table(name = "shipping_line_company_master")
public class ShippingLineCompanyMaster extends AbstractTimestampEntity {

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
    private int accountNo;

    private Byte status;

    public ShippingLineCompanyMaster() {
    }

    public ShippingLineCompanyMaster(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ShippingLineCompanyMaster setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShippingLineCompanyMaster setName(String name) {
        this.name = name;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public ShippingLineCompanyMaster setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public ShippingLineCompanyMaster setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public ShippingLineCompanyMaster setAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public ShippingLineCompanyMaster setAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public ShippingLineCompanyMaster setCountry(Country country) {
        this.country = country;
        return this;
    }

    public String getRepresentative() {
        return representative;
    }

    public ShippingLineCompanyMaster setRepresentative(String representative) {
        this.representative = representative;
        return this;
    }

    public String getRole() {
        return role;
    }

    public ShippingLineCompanyMaster setRole(String role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ShippingLineCompanyMaster setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public ShippingLineCompanyMaster setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public ShippingLineCompanyMaster setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public ShippingLineCompanyMaster setAccountNo(int accountNo) {
        this.accountNo = accountNo;
        return this;
    }
}
