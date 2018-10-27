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
public class ShippingInstructionAdviceDTO extends ShippingInstructionSummaryDTO {
    private String client;
    private String clientRef;
    private String supplier;
    private String supplierRef;
    private String shipper;
    private String shipperRef;
    private String buyer;
    private String buyerRef;
    private String consignee;
    private String notifyParties;
    
    private String blNumber;
    private Date blDate;
    private String feederVessel;
    private Date feederEta;
    private String loadingPort;
    private String transitPort;
    private String oceanVessel;
    private Date oceanEts;
    private Date oceanEta;
    private String icoNumber;
    
    private String origin;
    private String remark;
    
    private String shippingAdviceRefNumber;
    private Date shippingAdviceDate;
    

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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getNotifyParties() {
        return notifyParties;
    }

    public void setNotifyParties(String notifyParties) {
        this.notifyParties = notifyParties;
    }

    public String getBlNumber() {
        return blNumber;
    }

    public void setBlNumber(String blNumber) {
        this.blNumber = blNumber;
    }

    public Date getBlDate() {
        return blDate;
    }

    public void setBlDate(Date blDate) {
        this.blDate = blDate;
    }

    public String getFeederVessel() {
        return feederVessel;
    }

    public void setFeederVessel(String feederVessel) {
        this.feederVessel = feederVessel;
    }

    public Date getFeederEta() {
        return feederEta;
    }

    public void setFeederEta(Date feederEta) {
        this.feederEta = feederEta;
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getTransitPort() {
        return transitPort;
    }

    public void setTransitPort(String transitPort) {
        this.transitPort = transitPort;
    }

    public String getOceanVessel() {
        return oceanVessel;
    }

    public void setOceanVessel(String oceanVessel) {
        this.oceanVessel = oceanVessel;
    }

    public Date getOceanEts() {
        return oceanEts;
    }

    public void setOceanEts(Date oceanEts) {
        this.oceanEts = oceanEts;
    }

    public Date getOceanEta() {
        return oceanEta;
    }

    public void setOceanEta(Date oceanEta) {
        this.oceanEta = oceanEta;
    }

    public String getIcoNumber() {
        return icoNumber;
    }

    public void setIcoNumber(String icoNumber) {
        this.icoNumber = icoNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShippingAdviceRefNumber() {
        return shippingAdviceRefNumber;
    }

    public void setShippingAdviceRefNumber(String shippingAdviceRefNumber) {
        this.shippingAdviceRefNumber = shippingAdviceRefNumber;
    }

    public Date getShippingAdviceDate() {
        return shippingAdviceDate;
    }

    public void setShippingAdviceDate(Date shippingAdviceDate) {
        this.shippingAdviceDate = shippingAdviceDate;
    }
    
}
