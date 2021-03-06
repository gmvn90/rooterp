package com.swcommodities.wsmill.hibernate.dto.view;
// Generated Aug 26, 2013 5:19:13 PM by Hibernate Tools 3.6.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CompanyMaster generated by hbm2java
 */
@Entity
@Table(name = "company_master")
public class CompanyView implements java.io.Serializable {

    private Integer id;
    private String fullName;
    private String fullNameVn;
    private String name;
    private String nameVn;
    private String address1;
    private String address1Vn;
    private String address2;
    private String address2Vn;
    private String representative;
    private String representativeRole;
    private String representativeRoleVn;
    private String taxCode;
    private String email;
    private String fax;
    private String telephone;
    private String countryId;
    private Byte active;

    public CompanyView() {
    }

    public CompanyView(Integer id, String fullName, String fullNameVn, String name, String nameVn, String address1, String address1Vn, String address2, String address2Vn, String representative, String representativeRole, String representativeRoleVn, String taxCode, String email, String fax, String telephone, String countryId, Byte active) {
        this.id = id;
        this.fullName = fullName;
        this.fullNameVn = fullNameVn;
        this.name = name;
        this.nameVn = nameVn;
        this.address1 = address1;
        this.address1Vn = address1Vn;
        this.address2 = address2;
        this.address2Vn = address2Vn;
        this.representative = representative;
        this.representativeRole = representativeRole;
        this.representativeRoleVn = representativeRoleVn;
        this.taxCode = taxCode;
        this.email = email;
        this.fax = fax;
        this.telephone = telephone;
        this.countryId = countryId;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "full_name_vn")
    public String getFullNameVn() {
        return this.fullNameVn;
    }

    public void setFullNameVn(String fullNameVn) {
        this.fullNameVn = fullNameVn;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name_vn")
    public String getNameVn() {
        return this.nameVn;
    }

    public void setNameVn(String nameVn) {
        this.nameVn = nameVn;
    }

    @Column(name = "address1")
    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "address1_vn")
    public String getAddress1Vn() {
        return this.address1Vn;
    }

    public void setAddress1Vn(String address1Vn) {
        this.address1Vn = address1Vn;
    }

    @Column(name = "address2")
    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "address2_vn")
    public String getAddress2Vn() {
        return this.address2Vn;
    }

    public void setAddress2Vn(String address2Vn) {
        this.address2Vn = address2Vn;
    }

    @Column(name = "representative")
    public String getRepresentative() {
        return this.representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    @Column(name = "representative_role")
    public String getRepresentativeRole() {
        return this.representativeRole;
    }

    public void setRepresentativeRole(String representativeRole) {
        this.representativeRole = representativeRole;
    }

    @Column(name = "representative_role_vn")
    public String getRepresentativeRoleVn() {
        return this.representativeRoleVn;
    }

    public void setRepresentativeRoleVn(String representativeRoleVn) {
        this.representativeRoleVn = representativeRoleVn;
    }

    @Column(name = "tax_code")
    public String getTaxCode() {
        return this.taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fax")
    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "country_id", length = 50)
    public String getCountryId() {
        return this.countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Column(name = "active")
    public Byte getActive() {
        return this.active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }
}
