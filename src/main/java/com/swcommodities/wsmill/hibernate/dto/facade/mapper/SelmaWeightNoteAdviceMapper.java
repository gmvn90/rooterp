/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.facade.WeightNoteShippingAdviceDTO;

import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withIgnoreMissing = IgnoreMissing.DESTINATION
)
public interface SelmaWeightNoteAdviceMapper {
    WeightNoteShippingAdviceDTO toDto(WeightNote wn);
}
