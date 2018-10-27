package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dunguyen on 10/11/16.
 */
@Entity
@Table(name = "location_companies")
public class LocationCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    private CompanyMaster companyMaster;

    public long getId() {
        return id;
    }

    public LocationCompany setId(long id) {
        this.id = id;
        return this;
    }

    public CompanyMaster getCompanyMaster() {
        return companyMaster;
    }

    public LocationCompany setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
        return this;
    }
}
