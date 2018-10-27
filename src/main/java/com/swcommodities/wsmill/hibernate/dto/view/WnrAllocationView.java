package com.swcommodities.wsmill.hibernate.dto.view;
// Generated Aug 26, 2013 5:19:13 PM by Hibernate Tools 3.6.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.swcommodities.wsmill.json.jackson.JsonDateSerializer;

/**
 * WnrAllocationView generated by hbm2java
 */
@Entity
@Table(name = "wnr_allocation")
public class WnrAllocationView implements java.io.Serializable {

    private Integer id;
    private Integer user;
    private Integer weightOutUser;
    private Integer outWnrId;
    private Integer wnrId;
    private Integer instId;
    private String instType;
    private Integer wnId;
    private Date allocatedDate;
    private Byte status;
    private Date dateOut;
    private Float weightOut;
    private String log;

    public WnrAllocationView() {
    }

    public WnrAllocationView(Integer user, Integer wnrId, Integer instId, String instType, Integer wnId, Date allocatedDate, Byte status) {
        this.user = user;
        this.wnrId = wnrId;
        this.instId = instId;
        this.instType = instType;
        this.wnId = wnId;
        this.allocatedDate = allocatedDate;
        this.status = status;
    }
    
    public WnrAllocationView(Integer id, Integer user, Integer weightOutUser, Integer outWnrId, Integer wnrId, Integer instId, String instType, Integer wnId, Date allocatedDate, Byte status, Date dateOut, Float weightOut, String log) {
        this.id = id;
        this.user = user;
        this.weightOutUser = weightOutUser;
        this.outWnrId = outWnrId;
        this.wnrId = wnrId;
        this.instId = instId;
        this.instType = instType;
        this.wnId = wnId;
        this.allocatedDate = allocatedDate;
        this.status = status;
        this.dateOut = dateOut;
        this.weightOut = weightOut;
        this.log = log;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "allocated_user")
    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    @Column(name = "weight_out_user")
    public Integer getWeightOutUser() {
        return weightOutUser;
    }

    public void setWeightOutUser(Integer weightOutUser) {
        this.weightOutUser = weightOutUser;
    }

    @Column(name = "out_wnr_id")
    public Integer getOutWnrId() {
        return outWnrId;
    }

    public void setOutWnrId(Integer outWnrId) {
        this.outWnrId = outWnrId;
    }

    @Column(name = "wnr_id")
    public Integer getWnrId() {
        return wnrId;
    }

    public void setWnrId(Integer wnrId) {
        this.wnrId = wnrId;
    }

    @Column(name = "inst_id")
    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    @Column(name = "inst_type")
    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    @Column(name = "wn_id")
    public Integer getWnId() {
        return wnId;
    }

    public void setWnId(Integer wnId) {
        this.wnId = wnId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "allocated_date", length = 10)
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getAllocatedDate() {
        return allocatedDate;
    }

    public void setAllocatedDate(Date allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_out", length = 10)
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    @Column(name = "weight_out")
    public Float getWeightOut() {
        return weightOut;
    }

    public void setWeightOut(Float weightOut) {
        this.weightOut = weightOut;
    }

    @Column(name = "log")
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
