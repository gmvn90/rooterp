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
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;

@Entity
@Table(name = "processing_instruction_cache")
public class PICache {
	//PI Ref.	Client	Client Ref.	Type	Debit Grade	Packing	Req. Date	
	//Total Tons	Debit Tons	Credit Tons	Balance	Req. Status	Status
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	@JsonProperty("id")
	private ProcessingInstruction processingInstruction;
	
	private String piRef;
	private String client;
	private String clientRef;
	private String type;
	private String debitGrade;
	private String packing;
	private Date reqDate;
	private Double totalTons;
	private Double debitTons;
	private Double creditTons;
	private Double balance;
	private String requestStatus;
	private String status;

	private Integer clientInt;
	private Byte statusInt;
	private Integer gradeInt;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ProcessingInstruction getProcessingInstruction() {
		return processingInstruction;
	}
	public void setProcessingInstruction(ProcessingInstruction processingInstruction) {
		this.processingInstruction = processingInstruction;
	}
	
	public PICache() {
		
	}
	
	public String getPiRef() {
		return piRef;
	}
	public void setPiRef(String piRef) {
		this.piRef = piRef;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDebitGrade() {
		return debitGrade;
	}
	public void setDebitGrade(String debit) {
		this.debitGrade = debit;
	}
	
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	
	
	public Date getReqDate() {
		return reqDate;
	}
	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}
	
	
	
	public Double getTotalTons() {
		return totalTons;
	}
	public void setTotalTons(Double totalTons) {
		this.totalTons = totalTons;
	}
	public Double getDebitTons() {
		return debitTons;
	}
	public void setDebitTons(Double debitTons) {
		this.debitTons = debitTons;
	}
	public Double getCreditTons() {
		return creditTons;
	}
	public void setCreditTons(Double creditTons) {
		this.creditTons = creditTons;
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
