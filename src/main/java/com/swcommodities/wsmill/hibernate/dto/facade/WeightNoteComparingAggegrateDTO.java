/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

import com.swcommodities.wsmill.domain.model.WeightNoteComparingAggegrate;

/**
 *
 * @author macOS
 */
public class WeightNoteComparingAggegrateDTO extends WeightNoteComparingAggegrate {
    
    public WeightNoteComparingAggegrateDTO(WeightNoteComparingAggegrate aggegrate) {
        weightNotes = aggegrate.getWeightNotes();
        contractWeightNotes = aggegrate.getContractWeightNotes();
        totalWeightNotes = aggegrate.getTotalWeightNotes();
        totalContractWeightNotes = aggegrate.getTotalContractWeightNotes();
        diff = aggegrate.getDiff();
        
    }
}
