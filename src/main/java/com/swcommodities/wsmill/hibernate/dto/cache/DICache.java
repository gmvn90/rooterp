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
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;

@Entity
@Table(name = "delivery_instruction_cache")
public class DICache {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	@JsonProperty("id")
	private DeliveryInstruction deliveryInstruction;
	
	private String diRef;
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
	private String requestStatus;
	private String status;
	
	private Integer supplierInt;
	private Integer clientInt;
	private Byte statusInt;
	private Integer gradeInt;
	
	public DICache() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public DeliveryInstruction getDeliveryInstruction() {
		return deliveryInstruction;
	}
	public void setDeliveryInstruction(DeliveryInstruction deliveryInstruction) {
		this.deliveryInstruction = deliveryInstruction;
	}
	public String getDiRef() {
		return diRef;
	}
	public void setDiRef(String diRef) {
		this.diRef = diRef;
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
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	
	
	
	
	
	
	
}
