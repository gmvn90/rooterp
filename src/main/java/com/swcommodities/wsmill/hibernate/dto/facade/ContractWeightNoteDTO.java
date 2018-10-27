/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

/**
 *
 * @author macOS
 */
public class ContractWeightNoteDTO {
    private String sealNo;
    private String containerNo;
    private float contractGrossWeight = 0;
    private float contractTareWeight = 0;
    private float contractNetWeight = 0;
    private int weightNoteId;
    private float emptyContainerWeight;
    private float dryBagWeight;
    private float draftWeight;
    private float cartonWeight;
    private float palletWeight;
    private float vgmWeight;
    private float maxGrossContainerWeight;
    private int tallyQuantity;
    private String kindOfBag;
    private String remark;
    private String packing;
    private String refNumber;
        
    public ContractWeightNoteDTO() {}
    public ContractWeightNoteDTO(float grossWeight, float tareWeight, int weightNoteId) {
        this.contractGrossWeight = grossWeight;
        this.contractTareWeight = tareWeight;
        this.weightNoteId = weightNoteId;
    }
    
    public ContractWeightNoteDTO(float grossWeight, float tareWeight, String refNumber) {
    	this.contractGrossWeight = grossWeight;
        this.contractTareWeight = tareWeight;
        this.refNumber = refNumber;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public float getContractGrossWeight() {
        return contractGrossWeight;
    }

    public void setContractGrossWeight(float contractGrossWeight) {
        this.contractGrossWeight = contractGrossWeight;
    }

    public float getContractTareWeight() {
        return contractTareWeight;
    }

    public void setContractTareWeight(float contractTareWeight) {
        this.contractTareWeight = contractTareWeight;
    }

    public float getContractNetWeight() {
        return contractNetWeight;
    }

    public void setContractNetWeight(float contractNetWeight) {
        this.contractNetWeight = contractNetWeight;
    }

    public int getWeightNoteId() {
        return weightNoteId;
    }

    public void setWeightNoteId(int weightNoteId) {
        this.weightNoteId = weightNoteId;
    }

    public float getEmptyContainerWeight() {
        return emptyContainerWeight;
    }

    public void setEmptyContainerWeight(float emptyContainerWeight) {
        this.emptyContainerWeight = emptyContainerWeight;
    }

    public float getDryBagWeight() {
        return dryBagWeight;
    }

    public void setDryBagWeight(float dryBagWeight) {
        this.dryBagWeight = dryBagWeight;
    }

    public float getDraftWeight() {
        return draftWeight;
    }

    public void setDraftWeight(float draftWeight) {
        this.draftWeight = draftWeight;
    }

    public float getCartonWeight() {
        return cartonWeight;
    }

    public void setCartonWeight(float cartonWeight) {
        this.cartonWeight = cartonWeight;
    }

    public float getPalletWeight() {
        return palletWeight;
    }

    public void setPalletWeight(float palletWeight) {
        this.palletWeight = palletWeight;
    }

    public float getVgmWeight() {
        return vgmWeight;
    }

    public void setVgmWeight(float vgmWeight) {
        this.vgmWeight = vgmWeight;
    }

    public float getMaxGrossContainerWeight() {
        return maxGrossContainerWeight;
    }

    public void setMaxGrossContainerWeight(float maxGrossContainerWeight) {
        this.maxGrossContainerWeight = maxGrossContainerWeight;
    }

    public int getTallyQuantity() {
        return tallyQuantity;
    }

    public void setTallyQuantity(int tallyQuantity) {
        this.tallyQuantity = tallyQuantity;
    }
    
    public String getTallyQuantityIncludingBagInfo() {
        return tallyQuantity + " " + "Bags";
    }

    public void setTallyQuantityIncludingBagInfo(int tallyQuantity) {
    }

    public String getKindOfBag() {
        return kindOfBag;
    }

    public void setKindOfBag(String kindOfBag) {
        this.kindOfBag = kindOfBag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
    
    

    
}
