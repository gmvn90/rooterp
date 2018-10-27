/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swcommodities.wsmill.domain.model.SICustomCost;
import com.swcommodities.wsmill.domain.model.common.BaseIdAndNameImpl;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleSentShortSummaryDTO;

/**
 *
 * @author macOS
 */
public class ShippingInstructionForm {

    private Integer id;
    private Integer client;
    private String clientRef;
    private Integer supplier;
    private String supplierRef;
    private Integer shipper;
    private String shipperRef;
    private Integer buyer;
    private String buyerRef;
    private Integer consignee;
    private Date fromDate;
    private Date toDate;
    private Integer shippingLine;
    private String serviceContractNo;
    private String feederVessel;
    private Date feederEts;
    private Date feederEta;
    private String oceanVessel;
    private Date oceanEts;
    private Date oceanEta;
    private Integer loadingPort;
    private Date loadDate;
    private String bookingRef;
    private Date closingDate;
    private String closingTime;
    private Integer transitPort;
    private String fullContReturn;
    private Integer dischargePort;
    private Integer containerStatus;
    private String blNumber;
    private Date blDate;
    private String icoNumber;
    private Integer origin;
    private Integer quality;
    private Integer allocationGrade;
    private Integer grade;
    private float totalTons;
    private String remark;
    private String fumigation;
    private String weightQualityCertificate;
    private String clientSiCostListJson;
    private String requestUser;
    private String updateUser;
    private Date shipmentStatusUpdateDate;
    private String shipmentStatusUpdateUser;
    private String user;
    private Date updateCompletionDate;
    private String completionStatus;
    private String costNames;
    private Date requestDate;
    private String refNumber;
    private Date date;
    private String userRemark;
    private String shippingMark;
    private String requestStatus;
    private String requestStatusEnum;
    private String status;
    private String completionStatusUser;

    private int packingCostCategory;
    private int loadingAndTransportCategory;
    private int documentCategory;
    private int certificateCategory;
    private int fumigationCategory;
    private int fumigationProviderCategory;
    private int optionalCategory;
    private int allMarkingCategory;
    private int markingCategory;
    private String fumigationDetailCost = "";
    private String fumigationInStore = "";
    private String certificateCost = "";
    private double tonPerContainer;
    private int numberOfContainer;

    private int optionalDocumentNumber;
    List<String> documentCosts = new ArrayList<>();
    List<String> packingItemCosts = new ArrayList<>();
    List<SICustomCost> sICustomCosts = new ArrayList<>();
    Set<SampleSentShortSummaryDTO> sampleSents = new HashSet<>();
    Set<BaseIdAndNameImpl> notifyParties = new HashSet<>();
    
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public String getSupplierRef() {
        return supplierRef;
    }

    public void setSupplierRef(String supplierRef) {
        this.supplierRef = supplierRef;
    }

    public Integer getShipper() {
        return shipper;
    }

    public void setShipper(Integer shipper) {
        this.shipper = shipper;
    }

    public String getShipperRef() {
        return shipperRef;
    }

    public void setShipperRef(String shipperRef) {
        this.shipperRef = shipperRef;
    }

    public Integer getBuyer() {
        return buyer;
    }

    public void setBuyer(Integer buyer) {
        this.buyer = buyer;
    }

    public String getBuyerRef() {
        return buyerRef;
    }

    public void setBuyerRef(String buyerRef) {
        this.buyerRef = buyerRef;
    }

    public Integer getConsignee() {
        return consignee;
    }

    public void setConsignee(Integer consignee) {
        this.consignee = consignee;
    }

    public Set<BaseIdAndNameImpl> getNotifyParties() {
        return notifyParties;
    }

    public void setNotifyParties(Set<BaseIdAndNameImpl> notifyParties) {
        this.notifyParties = notifyParties;
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

    public Integer getShippingLine() {
        return shippingLine;
    }

    public void setShippingLine(Integer shippingLine) {
        this.shippingLine = shippingLine;
    }

    public String getServiceContractNo() {
        return serviceContractNo;
    }

    public void setServiceContractNo(String serviceContractNo) {
        this.serviceContractNo = serviceContractNo;
    }

    public String getFeederVessel() {
        return feederVessel;
    }

    public void setFeederVessel(String feederVessel) {
        this.feederVessel = feederVessel;
    }

    public Date getFeederEts() {
        return feederEts;
    }

    public void setFeederEts(Date feederEts) {
        this.feederEts = feederEts;
    }

    public Date getFeederEta() {
        return feederEta;
    }

    public void setFeederEta(Date feederEta) {
        this.feederEta = feederEta;
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

    public Integer getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(Integer loadingPort) {
        this.loadingPort = loadingPort;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public String getBookingRef() {
        return bookingRef;
    }

    public void setBookingRef(String bookingRef) {
        this.bookingRef = bookingRef;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public Integer getTransitPort() {
        return transitPort;
    }

    public void setTransitPort(Integer transitPort) {
        this.transitPort = transitPort;
    }

    public String getFullContReturn() {
        return fullContReturn;
    }

    public void setFullContReturn(String fullContReturn) {
        this.fullContReturn = fullContReturn;
    }

    public Integer getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(Integer dischargePort) {
        this.dischargePort = dischargePort;
    }

    public Integer getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(Integer containerStatus) {
        this.containerStatus = containerStatus;
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

    public String getIcoNumber() {
        return icoNumber;
    }

    public void setIcoNumber(String icoNumber) {
        this.icoNumber = icoNumber;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getAllocationGrade() {
        return allocationGrade;
    }

    public void setAllocationGrade(Integer allocationGrade) {
        this.allocationGrade = allocationGrade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public float getTotalTons() {
        return totalTons;
    }

    public void setTotalTons(float totalTons) {
        this.totalTons = totalTons;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getClientSiCostListJson() {
        return clientSiCostListJson;
    }

    public void setClientSiCostListJson(String clientSiCostListJson) {
        this.clientSiCostListJson = clientSiCostListJson;
    }

    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getShipmentStatusUpdateDate() {
        return shipmentStatusUpdateDate;
    }

    public void setShipmentStatusUpdateDate(Date shipmentStatusUpdateDate) {
        this.shipmentStatusUpdateDate = shipmentStatusUpdateDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getUpdateCompletionDate() {
        return updateCompletionDate;
    }

    public void setUpdateCompletionDate(Date updateCompletionDate) {
        this.updateCompletionDate = updateCompletionDate;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getShipmentStatusUpdateUser() {
        return shipmentStatusUpdateUser;
    }

    public void setShipmentStatusUpdateUser(String shipmentStatusUpdateUser) {
        this.shipmentStatusUpdateUser = shipmentStatusUpdateUser;
    }

    public String getCostNames() {
        return costNames;
    }

    public void setCostNames(String costNames) {
        this.costNames = costNames;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getShippingMark() {
        return shippingMark;
    }

    public void setShippingMark(String shippingMark) {
        this.shippingMark = shippingMark;
    }

    public int getPackingCostCategory() {
        return packingCostCategory;
    }

    public void setPackingCostCategory(int packingCostCategory) {
        this.packingCostCategory = packingCostCategory;
    }

    public int getLoadingAndTransportCategory() {
        return loadingAndTransportCategory;
    }

    public void setLoadingAndTransportCategory(int loadingAndTransportCategory) {
        this.loadingAndTransportCategory = loadingAndTransportCategory;
    }

    public int getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(int documentCategory) {
        this.documentCategory = documentCategory;
    }

    public int getCertificateCategory() {
        return certificateCategory;
    }

    public void setCertificateCategory(int certificateCategory) {
        this.certificateCategory = certificateCategory;
    }

    public int getFumigationCategory() {
        return fumigationCategory;
    }

    public void setFumigationCategory(int fumigationCategory) {
        this.fumigationCategory = fumigationCategory;
    }

    public int getFumigationProviderCategory() {
        return fumigationProviderCategory;
    }

    public void setFumigationProviderCategory(int fumigationProviderCategory) {
        this.fumigationProviderCategory = fumigationProviderCategory;
    }

    public int getOptionalCategory() {
        return optionalCategory;
    }

    public void setOptionalCategory(int optionalCategory) {
        this.optionalCategory = optionalCategory;
    }

    public int getAllMarkingCategory() {
        return allMarkingCategory;
    }

    public void setAllMarkingCategory(int allMarkingCategory) {
        this.allMarkingCategory = allMarkingCategory;
    }

    public int getMarkingCategory() {
        return markingCategory;
    }

    public void setMarkingCategory(int markingCategory) {
        this.markingCategory = markingCategory;
    }

    public String getFumigationDetailCost() {
        return fumigationDetailCost;
    }

    public void setFumigationDetailCost(String fumigationDetailCost) {
        this.fumigationDetailCost = fumigationDetailCost;
    }

    public String getFumigationInStore() {
        return fumigationInStore;
    }

    public void setFumigationInStore(String fumigationInStore) {
        this.fumigationInStore = fumigationInStore;
    }

    public String getCertificateCost() {
        return certificateCost;
    }

    public void setCertificateCost(String certificateCost) {
        this.certificateCost = certificateCost;
    }

    public double getTonPerContainer() {
        return tonPerContainer;
    }

    public void setTonPerContainer(double tonPerContainer) {
        this.tonPerContainer = tonPerContainer;
    }

    public int getNumberOfContainer() {
        return numberOfContainer;
    }

    public void setNumberOfContainer(int numberOfContainer) {
        this.numberOfContainer = numberOfContainer;
    }

    public int getOptionalDocumentNumber() {
        return optionalDocumentNumber;
    }

    public void setOptionalDocumentNumber(int optionalDocumentNumber) {
        this.optionalDocumentNumber = optionalDocumentNumber;
    }

    public List<String> getDocumentCosts() {
        return documentCosts;
    }

    public void setDocumentCosts(List<String> documentCosts) {
        this.documentCosts = documentCosts;
    }

    public List<String> getPackingItemCosts() {
        return packingItemCosts;
    }

    public void setPackingItemCosts(List<String> packingItemCosts) {
        this.packingItemCosts = packingItemCosts;
    }

    public List<SICustomCost> getsICustomCosts() {
        return sICustomCosts;
    }

    public void setsICustomCosts(List<SICustomCost> sICustomCosts) {
        this.sICustomCosts = sICustomCosts;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestStatusEnum() {
        return requestStatusEnum;
    }

    public void setRequestStatusEnum(String requestStatusEnum) {
        this.requestStatusEnum = requestStatusEnum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<SampleSentShortSummaryDTO> getSampleSents() {
        return sampleSents;
    }

    public void setSampleSents(Set<SampleSentShortSummaryDTO> sampleSents) {
        this.sampleSents = sampleSents;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public String getCompletionStatusUser() {
		return completionStatusUser;
	}

	public void setCompletionStatusUser(String completionStatusUser) {
		this.completionStatusUser = completionStatusUser;
	}
    
    

}
