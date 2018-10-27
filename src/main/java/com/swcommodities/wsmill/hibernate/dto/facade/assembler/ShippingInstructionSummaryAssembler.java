/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.ShippingInstructionAdviceDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.ShippingInstructionSummaryDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaShippingSummaryMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class ShippingInstructionSummaryAssembler {
    
    public ShippingInstructionSummaryDTO toDto(ShippingInstruction shippingInstruction) {
        SelmaShippingSummaryMapper mapper = Selma.builder(SelmaShippingSummaryMapper.class).build();
        return mapper.asShippingInstructionSummaryDTO(shippingInstruction);
    }
    
    public ShippingInstructionAdviceDTO toAdviceDto(ShippingInstruction shippingInstruction) {
        SelmaShippingSummaryMapper mapper = Selma.builder(SelmaShippingSummaryMapper.class).build();
        return mapper.asShippingInstructionAdviceDTO(shippingInstruction);
    }
    
}
