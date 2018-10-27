package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * NotifyParty generated by hbm2java
 */

@Entity
@Table(name = "notify_party")
public class NotifyParty implements java.io.Serializable {

    private Integer id;
    private CompanyMaster companyMaster;
    private ShippingInstruction shippingInstruction;

    public NotifyParty() {
    }

    public NotifyParty(Integer id) {
        this.id = id;
    }

    public NotifyParty(CompanyMaster companyMaster, ShippingInstruction shippingInstruction) {
        this.companyMaster = companyMaster;
        this.shippingInstruction = shippingInstruction;
    }
    
    public NotifyParty(int company, ShippingInstruction shippingInstruction) {
        this(new CompanyMaster(company), shippingInstruction);
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    public CompanyMaster getCompanyMaster() {
        return this.companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ins_id")
    public ShippingInstruction getShippingInstruction() {
        return this.shippingInstruction;
    }

    public void setShippingInstruction(ShippingInstruction shippingInstruction) {
        this.shippingInstruction = shippingInstruction;
    }

}
