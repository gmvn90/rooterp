package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.Date;

public class DeliveryInstructionListViewResult {
	private Integer id;
	private String refNumber;
	private String client;
	private String clientRef;
	private String supplier;
	private String supplierRef;
	private String pledge;
	private String origin;
	private String quality;
	private String grade;
	private String packing;
	private String location;
	private Date firstDate;
	private Date lastDate;
	private Double totalTons;
	private Double deliverd;
	private Double balance;
	private Byte requestStatus;
	private Byte status;
	// DI Ref.	Client	Client Ref.	Supplier	Supplier Ref.	Pledge	Origin	Quality	Grade	Packing	Location	First Date	Last Date	Total Tons	Delivered	Balance	Req. Status	Status
	
	//(di.id as id, di.refNumber as refNumber, di.companyMasterByClientId.name as client, di.clientRef as clientRef, di.companyMasterBySupplierId.name as supplier, di.supplierRef as supplierRef, di.companyMasterByPledger.name as pledge, di.originMaster.country.shortName as origin, di.qualityMaster.name as quality, di.gradeMaster.name as grade, di.packingMaster.name as packing, di.locationMaster.name as location, di.firstDate as firstDate, di.lastDate as lastDate, 0 as totalTons, 0 as deliverd, 0 as balance, di.requestStatus as requestStatus, di.status as status
	
	public DeliveryInstructionListViewResult() {}
	
	public DeliveryInstructionListViewResult(Integer id, String refNumber, String client, String clientRef,
			String supplier, String supplierRef, String pledge, String origin, String quality, String grade,
			String packing, String location, Date firstDate, Date lastDate, Double totalTons, Double deliverd,
			Double balance, Byte requestStatus, Byte status) {
		super();
		this.id = id;
		this.refNumber = refNumber;
		this.client = client;
		this.clientRef = clientRef;
		this.supplier = supplier;
		this.supplierRef = supplierRef;
		this.pledge = pledge;
		this.origin = origin;
		this.quality = quality;
		this.grade = grade;
		this.packing = packing;
		this.location = location;
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		this.totalTons = totalTons;
		this.deliverd = deliverd;
		this.balance = balance;
		this.requestStatus = requestStatus;
		this.status = status;
	}
	
	public DeliveryInstructionListViewResult(Integer id, String refNumber) {
		this.id = id;
		this.refNumber = refNumber;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
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

	public String getPledge() {
		return pledge;
	}

	public void setPledge(String pledge) {
		this.pledge = pledge;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Double getTotalTons() {
		return totalTons;
	}

	public void setTotalTons(Double totalTons) {
		this.totalTons = totalTons;
	}

	public Double getDeliverd() {
		return deliverd;
	}

	public void setDeliverd(Double deliverd) {
		this.deliverd = deliverd;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Byte getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Byte requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
	
	
	
	
}
