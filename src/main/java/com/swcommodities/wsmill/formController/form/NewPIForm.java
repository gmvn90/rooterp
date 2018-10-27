package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import java.util.Date;

public class NewPIForm {
	
	private String refNumber;
	private String requestRemark;
	private Date createdDate;
	private Integer clientId;
	private String clientRef;
	private Integer piType;
	private Integer gradeMaster;
	private Float quantity;
	private Double allocatedWeight; // in tons
    private Double inProcessWeight;
    private Double exProcessWeight;
    private Double pendingWeight;
    private Integer packingMaster;
	private Date creditDate;
	private String remark;
	private Date updatedDate;
    private String user;
	
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getRequestRemark() {
		return requestRemark;
	}
	public void setRequestRemark(String requestRemark) {
		this.requestRemark = requestRemark;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getClientRef() {
		return clientRef;
	}
	public void setClientRef(String clientRef) {
		this.clientRef = clientRef;
	}
	public Integer getPiType() {
		return piType;
	}
	public void setPiType(Integer piType) {
		this.piType = piType;
	}
	public Integer getGradeMaster() {
		return gradeMaster;
	}
	public void setGradeMaster(Integer gradeMaster) {
		this.gradeMaster = gradeMaster;
	}
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	public Double getAllocatedWeight() {
		return allocatedWeight;
	}
	public void setAllocatedWeight(Double allocatedWeight) {
		this.allocatedWeight = allocatedWeight;
	}
	public Double getInProcessWeight() {
		return inProcessWeight;
	}
	public void setInProcessWeight(Double inProcessWeight) {
		this.inProcessWeight = inProcessWeight;
	}
	public Double getExProcessWeight() {
		return exProcessWeight;
	}
	public void setExProcessWeight(Double exProcessWeight) {
		this.exProcessWeight = exProcessWeight;
	}
	public Double getPendingWeight() {
		return pendingWeight;
	}
	public void setPendingWeight(Double pendingWeight) {
		this.pendingWeight = pendingWeight;
	}
	public Integer getPackingMaster() {
		return packingMaster;
	}
	public void setPackingMaster(Integer packingMaster) {
		this.packingMaster = packingMaster;
	}
	public Date getCreditDate() {
		return creditDate;
	}
	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
	
	
	
	
}
