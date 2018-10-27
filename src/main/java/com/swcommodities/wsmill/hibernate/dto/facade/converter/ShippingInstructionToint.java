/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;

/**
 *
 * @author macOS
 */
public class ShippingInstructionToint {
    public ShippingInstruction fromInteger(int value) {
        return new ShippingInstruction(value);
    }
    
    public int fromObject(ShippingInstruction value) {
        return value.getId();
    }
}
