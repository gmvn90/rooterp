/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.view;

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
 *
 * @author kiendn
 */
@Entity
@Table(name = "movement")
public class MovementView implements java.io.Serializable {

    private Integer id;
    private Integer originId;
    private Integer qualityId;
    private String refNumber;
    private Integer client;
    private String clientRef;
    private Integer grade;
    private Integer packing;
    private Integer pledge;
    private Integer area;
    private Date date;
    private Double tons;
    private Date fromDate;
    private Date toDate;
    private String remark;
    private Integer user;
    private Byte status;
    private String log;
    private Byte type;
    private String areaCode;

    public MovementView() {
    }

    public MovementView(String refNumber, Integer client, Integer pledge, Integer area, Date date, Integer user, Byte status, String areaCode) {
        this.refNumber = refNumber;
        this.client = client;
        this.pledge = pledge;
        this.area = area;
        this.date = date;
        this.user = user;
        this.status = status;
        this.areaCode = areaCode;
    }
    
    public MovementView(Integer id, Integer originId, Integer qualityId, String refNumber, Integer client, String clientRef, Integer grade, Integer packing, Integer pledge, Integer area, Date date, Double tons, Date fromDate, Date toDate, String remark, Integer user, Byte status, String log) {
        this.id = id;
        this.originId = originId;
        this.qualityId = qualityId;
        this.refNumber = refNumber;
        this.client = client;
        this.clientRef = clientRef;
        this.grade = grade;
        this.packing = packing;
        this.pledge = pledge;
        this.area = area;
        this.date = date;
        this.tons = tons;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.remark = remark;
        this.user = user;
        this.status = status;
        this.log = log;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "origin_id")
    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    @Column(name = "quality_id")
    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    @Column(name = "ref_number")
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Column(name = "client_id")
    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    @Column(name = "client_ref")
    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    @Column(name = "grade_id")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Column(name = "packing_id")
    public Integer getPacking() {
        return packing;
    }

    public void setPacking(Integer packing) {
        this.packing = packing;
    }

    @Column(name = "pledge_id")
    public Integer getPledge() {
        return pledge;
    }

    public void setPledge(Integer pledge) {
        this.pledge = pledge;
    }
    
    @Column(name = "area_id")
    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date", length = 10)
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "quantity")
    public Double getTons() {
        return tons;
    }

    public void setTons(Double tons) {
        this.tons = tons;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date", length = 10)
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date", length = 10)
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "user")
    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Column(name = "log")
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Column(name = "type", length = 4)
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Column(name = "areaCode")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
