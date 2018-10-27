/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import java.util.ArrayList;
import java.util.List;

import com.swcommodities.wsmill.domain.model.ContractWeightNote;
import com.swcommodities.wsmill.hibernate.dto.facade.ContractWeightNoteDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaContractWeightNoteMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class ContractWeightNoteAssembler {
    public ContractWeightNoteDTO toDto(ContractWeightNote contractWeightNote) {
        SelmaContractWeightNoteMapper mapper = Selma.builder(SelmaContractWeightNoteMapper.class).build();
        return mapper.asContractWeightNoteDTO(contractWeightNote);
    }
    
    public ContractWeightNote fromDto(ContractWeightNoteDTO dto) {
        SelmaContractWeightNoteMapper mapper = Selma.builder(SelmaContractWeightNoteMapper.class).build();
        return mapper.asContractWeightNote(dto);
    }
    
    public List<ContractWeightNoteDTO> toListDto(List<ContractWeightNote> contractWeightNotes) {
        SelmaContractWeightNoteMapper mapper = Selma.builder(SelmaContractWeightNoteMapper.class).build();
        List<ContractWeightNoteDTO> result = new ArrayList<>();
        contractWeightNotes.forEach(cwn -> result.add(this.toDto(cwn)));
        return result;
    }
}
