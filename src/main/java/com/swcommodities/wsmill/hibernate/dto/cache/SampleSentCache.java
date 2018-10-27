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
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import javax.persistence.Transient;

@Entity
@Table(name = "sample_sent_cache")
public class SampleSentCache {
	//Sample Ref.	SI Ref.	Client	Client Ref.	Buyer	First Date	Origin	Quality	Grade	
	//Courier	AWB No.	Sent Date	Sent Status	Approval Status

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	@JsonProperty("id")
	private SampleSent sampleSent;
	
	private String sampleRef;
	private String siRef;
	private String client;
	private String clientRef;
	private String buyer;
	private String buyerRef;
	private Date firstDate;
	private String origin;
	private String quality;
	private String grade;
	private String courier;
	private String aWBNo;
	private Date etaDate;
	private Date sentDate;
	private String sentStatus;
	private String approvalStatus;
	private String type;

	private Integer clientInt;
	private Byte sentStatusInt;
	private Byte approvalStatusInt;
	private Integer buyerInt;
	private Integer typeInt;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SampleSent getSampleSent() {
		return sampleSent;
	}
	public void setSampleSent(SampleSent sampleSent) {
		this.sampleSent = sampleSent;
	}
	public String getSampleRef() {
		return sampleRef;
	}
	public void setSampleRef(String sampleRef) {
		this.sampleRef = sampleRef;
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
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public String getaWBNo() {
		return aWBNo;
	}
	public void setaWBNo(String aWBNo) {
		this.aWBNo = aWBNo;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public String getSentStatus() {
		return sentStatus;
	}
	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getEtaDate() {
		return etaDate;
	}

	public void setEtaDate(Date etaDate) {
		this.etaDate = etaDate;
	}

	public Integer getClientInt() {
		return clientInt;
	}

	public void setClientInt(Integer clientInt) {
		this.clientInt = clientInt;
	}

	public Byte getSentStatusInt() {
		return sentStatusInt;
	}

	public void setSentStatusInt(Byte sentStatusInt) {
		this.sentStatusInt = sentStatusInt;
	}

	public Byte getApprovalStatusInt() {
		return approvalStatusInt;
	}

	public void setApprovalStatusInt(Byte approvalStatusInt) {
		this.approvalStatusInt = approvalStatusInt;
	}

	public Integer getBuyerInt() {
		return buyerInt;
	}

	public void setBuyerInt(Integer buyerInt) {
		this.buyerInt = buyerInt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTypeInt() {
		return typeInt;
	}

	public void setTypeInt(Integer typeInt) {
		this.typeInt = typeInt;
	}
    
    @Transient
    public boolean getIsSampleSent() {
        if(typeInt == null) {
            return true;
        }
        return (int) typeInt == 1;
    }
    
    @Transient
    public boolean getIsSampleType() {
        return (int) typeInt == 2;
    }
}
