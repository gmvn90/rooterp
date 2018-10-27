package com.swcommodities.wsmill.hibernate.dto.query.result;

public class ShippingInstructionListViewResult {
	private Integer id;
	private String refNumber;

	public ShippingInstructionListViewResult(Integer id, String refNumber) {
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
	
	
	
}
