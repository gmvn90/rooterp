/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

import java.util.Date;

import com.swcommodities.wsmill.domain.model.ShippingWeightNoteTotalInfo;

/**
 *
 * @author macOS
 */
public class ShippingInstructionSummaryDTO {
    
    private int id;
    private String shippingLineCompanyMaster;
    private String refNumber;
    private String buyer;
    private String buyerRef;
    private String bookingRef;
    private String siNo;
    private Date loadDate;
    private Date date;
    private String packing;
    private String quality;
    private String containerStatus;
    private String fumigation;
    private String weightQualityCertificate;
    private String destination;
    private Date feederEts; //feederEts
    private String fullContReturn;
    private String shippingMark;
    private String grade;
    private ShippingWeightNoteTotalInfo shippingWeightNoteTotalInfo;
    private String client;
    private String clientRef;
    private String supplier;
    private String supplierRef;
    private String shipper;
    private String shipperRef;
    private Date fromDate;
    private Date toDate;
    private String origin;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShippingLineCompanyMaster() {
        return shippingLineCompanyMaster;
    }

    public void setShippingLineCompanyMaster(String shippingLineCompanyMaster) {
        this.shippingLineCompanyMaster = shippingLineCompanyMaster;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
        this.siNo = refNumber;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerRef() {
        return buyerRef;
    }

    public void setBuyerRef(String buyerRef) {
        this.buyerRef = buyerRef;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public void setBookingRef(String bookingRef) {
        this.bookingRef = bookingRef;
    }

    public String getSiNo() {
        return siNo;
    }

    public void setSiNo(String siNo) {
        this.siNo = siNo;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(String containerStatus) {
        this.containerStatus = containerStatus;
    }

    public String getFumigation() {
        return fumigation;
    }

    public void setFumigation(String fumigation) {
        this.fumigation = fumigation;
    }

    public String getWeightQualityCertificate() {
        return weightQualityCertificate;
    }

    public void setWeightQualityCertificate(String weightQualityCertificate) {
        this.weightQualityCertificate = weightQualityCertificate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getFeederEts() {
        return feederEts;
    }

    public void setFeederEts(Date feederEts) {
        this.feederEts = feederEts;
    }
    
    
    
    public String getFullContReturn() {
        return fullContReturn;
    }

    public void setFullContReturn(String fullContReturn) {
        this.fullContReturn = fullContReturn;
    }
    
    public ShippingWeightNoteTotalInfo getShippingWeightNoteTotalInfo() {
        return shippingWeightNoteTotalInfo;
    }

    public void setShippingWeightNoteTotalInfo(ShippingWeightNoteTotalInfo shippingWeightNoteTotalInfo) {
        this.shippingWeightNoteTotalInfo = shippingWeightNoteTotalInfo;
    }

    public String getShippingMark() {
        return shippingMark;
    }

    public void setShippingMark(String shippingMark) {
        this.shippingMark = shippingMark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierRef() {
        return supplierRef;
    }

    public void setSupplierRef(String supplierRef) {
        this.supplierRef = supplierRef;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperRef() {
        return shipperRef;
    }

    public void setShipperRef(String shipperRef) {
        this.shipperRef = shipperRef;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    
    
    
    
    
}
