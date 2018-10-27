/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.domain.model.ContractWeightNote;
import com.swcommodities.wsmill.hibernate.dto.facade.WeightNoteShippingAdviceDTO;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withIgnoreMissing = IgnoreMissing.DESTINATION,
    withCustomFields = {
        @Field({"netWeight", "contractNetWeight"}),
        @Field({"grossWeight", "contractGrossWeight"}),
        @Field({"tareWeight", "contractTareWeight"}),
        @Field({"id", "weightNoteId"}),
        @Field({"noOfBags", "tallyQuantity"}),
    }
)
public interface SelmaContractWeightNoteAdviceMapper {
    WeightNoteShippingAdviceDTO toDto(ContractWeightNote wn);
}
