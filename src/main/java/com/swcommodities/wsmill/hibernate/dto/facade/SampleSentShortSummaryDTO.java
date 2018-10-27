/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

import java.util.Date;

/**
 *
 * @author macOS
 */
public class SampleSentShortSummaryDTO {

    private int id;
    private String refNumber;
    private String courier;
    private String trackingNo;
    private String sendingStatus;
    private String remark;
    private String user;
    private Date updatedDate;
    private String saveRemarkUser;
    private Date saveRemarkDate;
    private double cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(String sendingStatus) {
        this.sendingStatus = sendingStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getSaveRemarkUser() {
        return saveRemarkUser;
    }

    public void setSaveRemarkUser(String saveRemarkUser) {
        this.saveRemarkUser = saveRemarkUser;
    }

    public Date getSaveRemarkDate() {
        return saveRemarkDate;
    }

    public void setSaveRemarkDate(Date saveRemarkDate) {
        this.saveRemarkDate = saveRemarkDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

}
