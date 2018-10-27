/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.domain.model.ContractWeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.facade.WeightNoteShippingAdviceDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaContractWeightNoteAdviceMapper;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaWeightNoteAdviceMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class ContractWeightNoteAdviceAssembler {
    
    public WeightNoteShippingAdviceDTO toDtoFromWeightNote(WeightNote wn) {
        SelmaWeightNoteAdviceMapper mapper = Selma.builder(SelmaWeightNoteAdviceMapper.class).build();
        return mapper.toDto(wn);
    }
    
    public WeightNoteShippingAdviceDTO toDtoFromContractWeightNote(ContractWeightNote wn) {
        SelmaContractWeightNoteAdviceMapper mapper = Selma.builder(SelmaContractWeightNoteAdviceMapper.class).build();
        return mapper.toDto(wn);
    }
    
}
