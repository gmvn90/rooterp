/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.Objects;

import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;

/**
 *
 * @author macOS
 */
public class WeightNoteExportSummary {
    
    private WeightNote weightNote;
    private double tairWeightEmptyContainer;
    private AccessoryWeight accessoryWeight;
    private int tallyNo;
    private PackingMaster packing;

    public WeightNote getWeightNote() {
        return weightNote;
    }

    public void setWeightNote(WeightNote weightNote) {
        this.weightNote = weightNote;
    }

    public double getTairWeightEmptyContainer() {
        return tairWeightEmptyContainer;
    }

    public void setTairWeightEmptyContainer(double tairWeightEmptyContainer) {
        this.tairWeightEmptyContainer = tairWeightEmptyContainer;
    }

    public AccessoryWeight getAccessoryWeight() {
        return accessoryWeight;
    }

    public void setAccessoryWeight(AccessoryWeight accessoryWeight) {
        this.accessoryWeight = accessoryWeight;
    }

    public int getTallyNo() {
        return tallyNo;
    }

    public void setTallyNo(int tallyNo) {
        this.tallyNo = tallyNo;
    }

    public PackingMaster getPacking() {
        return packing;
    }

    public void setPacking(PackingMaster packing) {
        this.packing = packing;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.weightNote);
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
        final WeightNoteExportSummary other = (WeightNoteExportSummary) obj;
        if (!Objects.equals(this.weightNote, other.weightNote)) {
            return false;
        }
        return true;
    }
    
    
    
}
