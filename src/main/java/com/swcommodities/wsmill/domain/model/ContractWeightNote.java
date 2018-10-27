/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 *
 * @author macOS
 */

@Embeddable
public class ContractWeightNote implements WeightNoteBasicFigure {
    
    private Integer weightNoteId;
    private String refNumber;
    private float contractGrossWeight = 0;
    private float contractTareWeight = 0;
    
    private float emptyContainerWeight;
    private float dryBagWeight;
    private float draftWeight;
    private float cartonWeight;
    private float palletWeight;
    private float maxGrossContainerWeight;
    private int tallyQuantity;
    private String kindOfBag = "Bags";
    private String remark;
    protected String packing;
    private String containerNo;
    private String sealNo;

    public ContractWeightNote() {}
    
    public ContractWeightNote(int weightNoteId) {
        this.weightNoteId = weightNoteId;
    }
    
    public ContractWeightNote(float contractGrossWeight, float contractTareWeight, int weightNoteId) {
        this.contractGrossWeight = contractGrossWeight;
        this.contractTareWeight = contractTareWeight;
        this.weightNoteId = weightNoteId;
    }
    
    public ContractWeightNote(float contractGrossWeight, float contractTareWeight, String refNumber) {
        this.contractGrossWeight = contractGrossWeight;
        this.contractTareWeight = contractTareWeight;
        this.refNumber = refNumber;
    }
    
    public ContractWeightNote(float contractGrossWeight, float contractTareWeight, int weightNoteId, String refNumber) {
        this(contractGrossWeight, contractTareWeight, weightNoteId);
        this.refNumber = refNumber;
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
        return contractGrossWeight - contractTareWeight;
    }
    
    // for hibernate
    private void setContractNetWeight(float contractNetWeight) {
        
    }
    
    public float getVgmWeight() {
        return contractGrossWeight + emptyContainerWeight + dryBagWeight + draftWeight + cartonWeight + palletWeight;
    }
    
    // for hibernate
    private void setVgmWeight(float vgmWeight) {}

    @Override
    public Integer getWeightNoteId() {
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

    @Transient
    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    @Transient
    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    @Transient
    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }
    
    
    
    public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.weightNoteId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContractWeightNote other = (ContractWeightNote) obj;
        if (this.weightNoteId != other.weightNoteId) {
            return false;
        }
        return true;
    }

    @Override
    @Transient
    public Integer getId() {
        return getWeightNoteId();
    }

    @Override
    @Transient
    public Float getGrossWeight() {
        return getContractGrossWeight();
    }

    @Override
    @Transient
    public Float getTareWeight() {
        return getContractTareWeight();
    }

    @Override
    @Transient
    public Float getNetWeight() {
        return WeightNoteBasicFigure.super.getNetWeight(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transient
    public Integer getNoOfBags() {
        return tallyQuantity;
    }
    
}
