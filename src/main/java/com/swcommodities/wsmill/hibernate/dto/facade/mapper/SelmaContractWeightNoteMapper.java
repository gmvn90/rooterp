/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.domain.model.ContractWeightNote;
import com.swcommodities.wsmill.hibernate.dto.facade.ContractWeightNoteDTO;

import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withCustomFields = {
    },
    withIgnoreMissing = IgnoreMissing.DESTINATION,
    withIgnoreFields = {"tallyQuantityIncludingBagInfo"}
)
public interface SelmaContractWeightNoteMapper {
    ContractWeightNoteDTO asContractWeightNoteDTO(ContractWeightNote contractWeightNote);
    ContractWeightNote asContractWeightNote(ContractWeightNoteDTO dTO);
}
