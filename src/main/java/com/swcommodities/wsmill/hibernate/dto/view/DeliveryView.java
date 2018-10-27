/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.view;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
@Table(name="delivery_instruction")
/*@Subselect("select di.id as id, user.id as userId, client.id as clientId, controller.id as controllerId,"
        + "supplier.id as supplierId, grade.id as gradeId, packing.id as packingId, warehouse.id as warehouseId,"
        + "qualityController.id as qualityControllerId,"
        + "companyMasterByPledger.id as pledgerId,"
        + "di.refNumber as refNumber,"
        + "di.clientRef as clientRef,"
        + "di.date as date,"
        + "di.supplierRef as supplierRef,"
        + "di.tons as tons,"
        + "di.kgPerBag as kgPerBag,"
        + "di.noOfBags as noOfBags,"
        + "di.deliveryDate as deliveryDate,"
        + "di.fromTime as fromTime,"
        + "di.toTime as toTime,"
        + "di.markingOnBags as markingOnBags,"
        + "di.originId as originId,"
        + "di.qualityId as qualityId,"
        + "di.remark as remarks,"
        + "di.status as status,"
        + "di.log as log "
        + "from DeliveryInstruction as di "
        + "left join di.user as user "
        + "left join di.companyMasterByClientId as client "
        + "left join di.companyMasterByWeightControllerId as controller "
        + "left join di.companyMasterBySupplierId as supplier "
        + "left join di.gradeMaster as grade "
        + "left join di.packingMaster as packing "
        + "left join di.companyMasterByQualityControllerId as qualityController "
        + "left join di.companyMasterByPledger as pledger "
        + "left join di.warehouse as warehouse")
@Synchronize({"DeliveryInstruction"})*/
public class DeliveryView implements Serializable{

    private Integer id;
    private Integer userId;
    private Integer clientId;
    private Integer controllerId;
    private Integer supplierId;
    private Integer gradeId;
    private Integer packingId;
    private Integer warehouseId;
    private Integer qualityControllerId;
    private Integer pledgerId;
    private String refNumber;
    private String clientRef;
    private Date date;
    private String supplierRef;
    private Double tons;
    private Float kgPerBag;
    private Integer noOfBags;
    private Date deliveryDate;
    private String fromTime;
    private String toTime;
    private String markingOnBags;
    private Integer originId;
    private Integer qualityId;
    private String remark;
    private Byte status;
    private String log;

    public DeliveryView() {
    }

    public DeliveryView(Integer id, Integer userId, Integer clientId, Integer controllerId, Integer supplierId, Integer gradeId, Integer packingId, Integer warehouseId, Integer qualityControllerId, Integer pledgerId, String refNumber, String clientRef, Date date, String supplierRef, Double tons, Float kgPerBag, Integer noOfBags, Date deliveryDate, String fromTime, String toTime, String markingOnBags, Integer originId, Integer qualityId, String remark, Byte status, String log) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.controllerId = controllerId;
        this.supplierId = supplierId;
        this.gradeId = gradeId;
        this.packingId = packingId;
        this.warehouseId = warehouseId;
        this.qualityControllerId = qualityControllerId;
        this.pledgerId = pledgerId;
        this.refNumber = refNumber;
        this.clientRef = clientRef;
        this.date = date;
        this.supplierRef = supplierRef;
        this.tons = tons;
        this.kgPerBag = kgPerBag;
        this.noOfBags = noOfBags;
        this.deliveryDate = deliveryDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.markingOnBags = markingOnBags;
        this.originId = originId;
        this.qualityId = qualityId;
        this.remark = remark;
        this.status = status;
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

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Column(name = "client_id")
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    @Column(name = "weight_controller_id")
    public Integer getControllerId() {
        return controllerId;
    }

    public void setControllerId(Integer controllerId) {
        this.controllerId = controllerId;
    }
    @Column(name = "supplier_id")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    @Column(name = "grade_id")
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
    @Column(name = "packing_id")
    public Integer getPackingId() {
        return packingId;
    }

    public void setPackingId(Integer packingId) {
        this.packingId = packingId;
    }
    @Column(name = "warehouse_id")
    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
    @Column(name = "quality_controller_id")
    public Integer getQualityControllerId() {
        return qualityControllerId;
    }

    public void setQualityControllerId(Integer qualityControllerId) {
        this.qualityControllerId = qualityControllerId;
    }
    @Column(name = "pledger")
    public Integer getPledgerId() {
        return pledgerId;
    }

    public void setPledgerId(Integer pledgerId) {
        this.pledgerId = pledgerId;
    }
    @Column(name = "ref_number")
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }
    @Column(name = "client_ref")
    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
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
    @Column(name = "supplier_ref")
    public String getSupplierRef() {
        return supplierRef;
    }

    public void setSupplierRef(String supplierRef) {
        this.supplierRef = supplierRef;
    }
    @Column(name = "tons", precision = 22, scale = 0)
    public Double getTons() {
        return tons;
    }

    public void setTons(Double tons) {
        this.tons = tons;
    }
    @Column(name = "kg_per_bag", precision = 12, scale = 0)
    public Float getKgPerBag() {
        return kgPerBag;
    }

    public void setKgPerBag(Float kgPerBag) {
        this.kgPerBag = kgPerBag;
    }
    @Column(name = "no_of_bags")
    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "delivery_date", length = 10)
    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    @Column(name = "from_time")    
    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }
    @Column(name = "to_time")
    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
    @Column(name = "marking_on_bags")
    public String getMarkingOnBags() {
        return markingOnBags;
    }

    public void setMarkingOnBags(String markingOnBags) {
        this.markingOnBags = markingOnBags;
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
    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
    @Column(name = "log", length = 65535)
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
