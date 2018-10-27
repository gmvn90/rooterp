package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * CompanyRole generated by hbm2java
 */
@Entity
@Table(name = "company_role"
)
public class CompanyRole implements java.io.Serializable {

    private CompanyRoleId id;

    public CompanyRole() {
    }

    public CompanyRole(CompanyRoleId id) {
        this.id = id;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "companyId", column = @Column(name = "company_id", nullable = false))
        , 
        @AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false))})
    public CompanyRoleId getId() {
        return this.id;
    }

    public void setId(CompanyRoleId id) {
        this.id = id;
    }

}
