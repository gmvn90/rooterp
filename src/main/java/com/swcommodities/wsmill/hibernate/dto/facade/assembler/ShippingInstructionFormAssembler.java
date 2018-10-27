/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.formController.form.ShippingInstructionForm;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaShippingFormMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */

@Service
public class ShippingInstructionFormAssembler {
    public ShippingInstruction fromDto(ShippingInstructionForm form, ShippingInstruction shippingInstruction) {
        SelmaShippingFormMapper mapper = Selma.builder(SelmaShippingFormMapper.class).build();
        return mapper.fromDto(form, shippingInstruction);
    }
    
    public ShippingInstructionForm toForm(ShippingInstruction shippingInstruction) {
        SelmaShippingFormMapper mapper = Selma.builder(SelmaShippingFormMapper.class).build();
        return mapper.toForm(shippingInstruction);
    }
}
