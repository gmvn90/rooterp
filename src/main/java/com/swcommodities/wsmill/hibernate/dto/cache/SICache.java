package com.swcommodities.wsmill.hibernate.dto.cache;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;

@Entity
@Table(name = "shipping_instruction_cache")
public class SICache {
	//SI Ref.	Client	Client Ref.	Buyer	Buyer Ref.	First Date	Loading Date	Grade	
	//Packing	Dest.	Total	W/Q Cert.	Sample ETA	Shipping Line	Booking No	
	//Closing Date	Closing Time	Sample Status	Request Status
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	@JsonProperty("id")
	private ShippingInstruction shippingInstruction;
	private String siRef;
	private String client;
	private String clientRef;
	private String buyer;
	private String buyerRef;
	private Date firstDate;
	private Date loadingDate;
	private String grade;
	private String packing;
	private String dest;
	private Double total;
	private double exported = 0;
	private String wQCert;
	private String shippingLine;
	private String bookingNo;
	private Date closingDate;
	private String closingTime;
	private String sampleStatus;
	private String requestStatus;
	private String shipmentStatus;

	private Integer supplierInt;
	private Integer clientInt;
	private Byte statusInt;
	private Integer shipmentStatusInt;
	private Integer gradeInt;
	private Integer buyerInt;

	public SICache() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ShippingInstruction getShippingInstruction() {
		return shippingInstruction;
	}
	public void setShippingInstruction(ShippingInstruction shippingInstruction) {
		this.shippingInstruction = shippingInstruction;
	}
	public String getSiRef() {
		return siRef;
	}
	public void setSiRef(String siRef) {
		this.siRef = siRef;
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
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	public Date getLoadingDate() {
		return loadingDate;
	}
	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
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
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getwQCert() {
		return wQCert;
	}
	public void setwQCert(String wQCert) {
		this.wQCert = wQCert;
	}
	public String getShippingLine() {
		return shippingLine;
	}
	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
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

	public String getSampleStatus() {
		return sampleStatus;
	}
	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Integer getSupplierInt() {
		return supplierInt;
	}

	public void setSupplierInt(Integer supplierInt) {
		this.supplierInt = supplierInt;
	}

	public Integer getClientInt() {
		return clientInt;
	}

	public void setClientInt(Integer clientInt) {
		this.clientInt = clientInt;
	}

	public Byte getStatusInt() {
		return statusInt;
	}

	public void setStatusInt(Byte statusInt) {
		this.statusInt = statusInt;
	}

	public Integer getGradeInt() {
		return gradeInt;
	}

	public void setGradeInt(Integer gradeInt) {
		this.gradeInt = gradeInt;
	}

	public Integer getBuyerInt() {
		return buyerInt;
	}

	public void setBuyerInt(Integer buyerInt) {
		this.buyerInt = buyerInt;
	}

	public double getExported() {
		return exported;
	}

	public void setExported(double exported) {
		this.exported = exported;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public Integer getShipmentStatusInt() {
		return shipmentStatusInt;
	}

	public void setShipmentStatusInt(Integer shipmentStatusInt) {
		this.shipmentStatusInt = shipmentStatusInt;
	}
}
