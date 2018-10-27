/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import org.springframework.beans.BeanUtils;

import com.swcommodities.wsmill.hibernate.dto.WeightNote;

/**
 *
 * @author macOS
 */
public class FormattedWeightNote extends ContractWeightNote {
    
    private String containerNo;
    private String sealNo;

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public FormattedWeightNote() {}
    
    public FormattedWeightNote(WeightNote weightNote, ContractWeightNote contractWeightNote) {
        BeanUtils.copyProperties(contractWeightNote, this);
        containerNo = weightNote.getContainerNo();
        sealNo = weightNote.getSealNo();
        if(weightNote.getPackingMaster() == null) {
            packing = "";
        } else {
            packing = weightNote.getPackingMaster().getName();
        }
        
    }
}
