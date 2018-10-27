package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ClientPackingPrice generated by hbm2java
 */
@Entity
@Table(name = "client_packing_price"
)
public class ClientPackingPrice implements java.io.Serializable {

    private ClientPackingPriceId id;
    private CompanyMaster companyMaster;
    private PackingMaster packingMaster;
    private Float cost;

    public ClientPackingPrice() {
    }

    public ClientPackingPrice(ClientPackingPriceId id, CompanyMaster companyMaster, PackingMaster packingMaster) {
        this.id = id;
        this.companyMaster = companyMaster;
        this.packingMaster = packingMaster;
    }

    public ClientPackingPrice(ClientPackingPriceId id, CompanyMaster companyMaster, PackingMaster packingMaster, Float cost) {
        this.id = id;
        this.companyMaster = companyMaster;
        this.packingMaster = packingMaster;
        this.cost = cost;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "clientId", column = @Column(name = "client_id", nullable = false))
        , 
        @AttributeOverride(name = "packingId", column = @Column(name = "packing_id", nullable = false))})
    public ClientPackingPriceId getId() {
        return this.id;
    }

    public void setId(ClientPackingPriceId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, insertable = false, updatable = false)
    public CompanyMaster getCompanyMaster() {
        return this.companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_id", nullable = false, insertable = false, updatable = false)
    public PackingMaster getPackingMaster() {
        return this.packingMaster;
    }

    public void setPackingMaster(PackingMaster packingMaster) {
        this.packingMaster = packingMaster;
    }

    @Column(name = "cost", precision = 9)
    public Float getCost() {
        return this.cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

}