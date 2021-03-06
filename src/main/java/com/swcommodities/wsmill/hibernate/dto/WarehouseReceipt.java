package com.swcommodities.wsmill.hibernate.dto;
// Generated Feb 18, 2014 3:38:53 PM by Hibernate Tools 3.2.1.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WarehouseReceipt generated by hbm2java
 */
@Entity
@Table(name = "warehouse_receipt")
public class WarehouseReceipt implements java.io.Serializable {

    private Integer id;
    private User user;
    private CompanyMaster companyMasterByWeightControllerId;
    private QualityReport qualityReport;
    private CompanyMaster companyMasterByQualityControllerId;
    private String refNumber;
    private Date date;
    private Integer instId;
    private String instType;
    private Byte status;
    private String remark;
    private Date lastUpdated;
    private String log;

    public WarehouseReceipt() {
    }

    public WarehouseReceipt(User user, CompanyMaster companyMasterByWeightControllerId, QualityReport qualityReport, CompanyMaster companyMasterByQualityControllerId, String refNumber, Date date, Integer instId, String instType, Byte status, String remark, Date lastUpdated, String log) {
        this.user = user;
        this.companyMasterByWeightControllerId = companyMasterByWeightControllerId;
        this.qualityReport = qualityReport;
        this.companyMasterByQualityControllerId = companyMasterByQualityControllerId;
        this.refNumber = refNumber;
        this.date = date;
        this.instId = instId;
        this.instType = instType;
        this.status = status;
        this.remark = remark;
        this.lastUpdated = lastUpdated;
        this.log = log;
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_controller_id")
    public CompanyMaster getCompanyMasterByWeightControllerId() {
        return this.companyMasterByWeightControllerId;
    }

    public void setCompanyMasterByWeightControllerId(CompanyMaster companyMasterByWeightControllerId) {
        this.companyMasterByWeightControllerId = companyMasterByWeightControllerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_id")
    public QualityReport getQualityReport() {
        return this.qualityReport;
    }

    public void setQualityReport(QualityReport qualityReport) {
        this.qualityReport = qualityReport;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_controller_id")
    public CompanyMaster getCompanyMasterByQualityControllerId() {
        return this.companyMasterByQualityControllerId;
    }

    public void setCompanyMasterByQualityControllerId(CompanyMaster companyMasterByQualityControllerId) {
        this.companyMasterByQualityControllerId = companyMasterByQualityControllerId;
    }

    @Column(name = "ref_number")
    public String getRefNumber() {
        return this.refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", length = 19)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "inst_id")
    public Integer getInstId() {
        return this.instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    @Column(name = "inst_type", length = 50)
    public String getInstType() {
        return this.instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated", length = 19)
    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Column(name = "log", length = 65535)
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
