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
 * Movement generated by hbm2java
 */
@Entity
@Table(name = "movement"
)
public class Movement implements java.io.Serializable {

    private Integer id;
    private CompanyMaster companyMasterByPledgeId;
    private CompanyMaster companyMasterByClientId;
    private WarehouseCell warehouseCell;
    private User user;
    private GradeMaster gradeMaster;
    private PackingMaster packingMaster;
    private String refNumber;
    private Boolean status;
    private String log;
    private String clientRef;
    private Date date;
    private Date fromDate;
    private Integer originId;
    private Integer qualityId;
    private String remark;
    private Date toDate;
    private Double quantity;
    private Byte type;
    private String areaCode;

    public Movement() {
    }

    public Movement(CompanyMaster companyMasterByClientId, WarehouseCell warehouseCell, User user, String refNumber) {
        this.companyMasterByClientId = companyMasterByClientId;
        this.warehouseCell = warehouseCell;
        this.user = user;
        this.refNumber = refNumber;
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
    @JoinColumn(name = "pledge_id")
    public CompanyMaster getCompanyMasterByPledgeId() {
        return this.companyMasterByPledgeId;
    }

    public void setCompanyMasterByPledgeId(CompanyMaster companyMasterByPledgeId) {
        this.companyMasterByPledgeId = companyMasterByPledgeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    public CompanyMaster getCompanyMasterByClientId() {
        return this.companyMasterByClientId;
    }

    public void setCompanyMasterByClientId(CompanyMaster companyMasterByClientId) {
        this.companyMasterByClientId = companyMasterByClientId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    public WarehouseCell getWarehouseCell() {
        return this.warehouseCell;
    }

    public void setWarehouseCell(WarehouseCell warehouseCell) {
        this.warehouseCell = warehouseCell;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    public GradeMaster getGradeMaster() {
        return this.gradeMaster;
    }

    public void setGradeMaster(GradeMaster gradeMaster) {
        this.gradeMaster = gradeMaster;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_id")
    public PackingMaster getPackingMaster() {
        return this.packingMaster;
    }

    public void setPackingMaster(PackingMaster packingMaster) {
        this.packingMaster = packingMaster;
    }

    @Column(name = "ref_number", nullable = false)
    public String getRefNumber() {
        return this.refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Column(name = "status")
    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Column(name = "log", length = 65535)
    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Column(name = "client_ref")
    public String getClientRef() {
        return this.clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date", length = 10)
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date", length = 10)
    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Column(name = "origin_id")
    public Integer getOriginId() {
        return this.originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    @Column(name = "quality_id")
    public Integer getQualityId() {
        return this.qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date", length = 10)
    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Column(name = "quantity", precision = 22, scale = 0)
    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Column(name = "type")
    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Column(name = "areaCode")
    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

}
