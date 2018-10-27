/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.json.obj;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author kiendn
 */
public class WarehouseReceipt {

    private Integer id;
    private String refNumber;
    private String instType;
    private Integer instId;
    private Integer qrId;
    private String qrRef;
    private Integer weightControllerId;
    private Integer qualityControllerId;
    private String date;
    private byte status;
    private String remark;
    private String lastUpdated;
    private Integer userId;
    private String supplier;
    private String grade;
    private ArrayList<Integer> wn;

    public WarehouseReceipt(com.swcommodities.wsmill.hibernate.dto.WarehouseReceipt source, ArrayList<WeightNote> wn_source, String grade, String company) {
        this.id = (source.getId() != null) ? source.getId() : -1;
        this.refNumber = source.getRefNumber();
        this.instId = source.getInstId();
        this.instType = source.getInstType();
        this.qrId = source.getQualityReport().getId();
        this.qrRef = source.getQualityReport().getRefNumber();
        this.qualityControllerId = source.getCompanyMasterByQualityControllerId().getId();
        this.weightControllerId = source.getCompanyMasterByWeightControllerId().getId();
        this.date = Common.getDateFromDatabase(source.getDate(), Common.date_format_a);
        this.status = source.getStatus();
        this.remark = source.getRemark();
        this.lastUpdated = Common.getDateFromDatabase(source.getLastUpdated(), Common.date_format_a);
        this.userId = source.getUser().getId();
        this.supplier = company;
        this.grade = grade;
        this.wn = new ArrayList<>();
        for (WeightNote weightNote : wn_source) {
            this.wn.add(weightNote.getId());
        }
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getQrRef() {
        return qrRef;
    }

    public void setQrRef(String qrRef) {
        this.qrRef = qrRef;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(int instId) {
        this.instId = instId;
    }

    public Integer getQrId() {
        return qrId;
    }

    public void setQrId(int qrId) {
        this.qrId = qrId;
    }

    public Integer getWeightControllerId() {
        return weightControllerId;
    }

    public void setWeightControllerId(int weightControllerId) {
        this.weightControllerId = weightControllerId;
    }

    public Integer getQualityControllerId() {
        return qualityControllerId;
    }

    public void setQualityControllerId(int qualityControllerId) {
        this.qualityControllerId = qualityControllerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Integer> getWn() {
        return wn;
    }

    public void setWn(ArrayList<Integer> wn) {
        this.wn = wn;
    }
}
