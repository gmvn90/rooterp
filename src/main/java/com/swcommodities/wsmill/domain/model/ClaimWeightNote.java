package com.swcommodities.wsmill.domain.model;

import com.swcommodities.wsmill.domain.model.common.RandomIdProvider;
import java.beans.Transient;
import java.util.UUID;

import javax.persistence.Embeddable;

@Embeddable
public class ClaimWeightNote implements WeightBasicFigure {
	
	private float grossWeight;
	private float tareWeight;
    private Integer weightNoteId;

	@Override
	public Float getGrossWeight() {
		return grossWeight;
	}

	@Override
	public Float getTareWeight() {
		return tareWeight;
	}
	
	@Override
    @Transient
    public Float getNetWeight() {
        return WeightBasicFigure.super.getNetWeight(); //To change body of generated methods, choose Tools | Templates.
    }

	public void setGrossWeight(float grossWeight) {
		this.grossWeight = grossWeight;
	}

	public void setTareWeight(float tareWeight) {
		this.tareWeight = tareWeight;
	}

    @Override
    public Integer getWeightNoteId() {
        return weightNoteId;
    }
    

    public void setWeightNoteId(int weightNoteId) {
        this.weightNoteId = weightNoteId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(grossWeight);
		result = prime * result + Float.floatToIntBits(tareWeight);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaimWeightNote other = (ClaimWeightNote) obj;
		if (Float.floatToIntBits(grossWeight) != Float.floatToIntBits(other.grossWeight))
			return false;
		if (Float.floatToIntBits(tareWeight) != Float.floatToIntBits(other.tareWeight))
			return false;
		return true;
	}

	public ClaimWeightNote(int weightNoteId, float grossWeight, float tareWeight) {
		super();
		this.grossWeight = grossWeight;
		this.tareWeight = tareWeight;
        this.weightNoteId = weightNoteId;
	}
	
	public ClaimWeightNote() {}
	
	
	
	
}
