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
@Table(name = "qualification_companies")
public class QualificationCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    private CompanyMaster companyMaster;

    public long getId() {
        return id;
    }

    public QualificationCompany setId(long id) {
        this.id = id;
        return this;
    }

    public CompanyMaster getCompanyMaster() {
        return companyMaster;
    }

    public QualificationCompany setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
        return this;
    }

    public QualificationCompany() {

    }

    public QualificationCompany(Integer id) {
        this.id = Long.valueOf(id);
    }
}
