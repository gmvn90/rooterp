package com.swcommodities.wsmill.hibernate.dto.view;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="processing_invoice")
public class ProcessingInvoice implements java.io.Serializable {
	/**
	 * 
	 */
	private Integer id;
	private Integer processingId;
	private Integer processingType;
	private String processingRef;
	private Integer clientId;
	private String clientName;
	private Date date;
	private Integer dateCode;
	private String inprocess;
	private String exprocess;
	public ProcessingInvoice() {
		super();
	}
	public ProcessingInvoice(Integer id, Integer processingId, Integer processingType,
			String processingRef, Integer clientId, String clientName, Date date, Integer dateCode,
			String inprocess, String exprocess) {
		super();
		this.id = id;
		this.processingId = processingId;
		this.processingType = processingType;
		this.processingRef = processingRef;
		this.clientId = clientId;
		this.clientName = clientName;
		this.date = date;
		this.dateCode = dateCode;
		this.inprocess = inprocess;
		this.exprocess = exprocess;
	}
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="processing_id")
	public Integer getProcessingId() {
		return processingId;
	}
	public void setProcessingId(Integer processingId) {
		this.processingId = processingId;
	}
	@Column(name="processing_type")
	public Integer getProcessingType() {
		return processingType;
	}
	public void setProcessingType(Integer processingType) {
		this.processingType = processingType;
	}
	@Column(name="processing_ref")
	public String getProcessingRef() {
		return processingRef;
	}
	public void setProcessingRef(String processingRef) {
		this.processingRef = processingRef;
	}
	@Column(name="client_id")
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	@Column(name="client_name")
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date",length=19)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column(name="date_code")
	public Integer getDateCode() {
		return dateCode;
	}
	public void setDateCode(Integer dateCode) {
		this.dateCode = dateCode;
	}
	@Column(name="inprocess")
	public String getInprocess() {
		return inprocess;
	}
	public void setInprocess(String inprocess) {
		this.inprocess = inprocess;
	}
	@Column(name="exprocess")
	public String getExprocess() {
		return exprocess;
	}
	public void setExprocess(String exprocess) {
		this.exprocess = exprocess;
	}
	
}
