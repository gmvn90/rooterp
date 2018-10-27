/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.si;

import java.util.Objects;

import com.swcommodities.wsmill.domain.event.BaseObjectEvent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;

/**
 *
 * @author macOS
 */
public class SICostUpdateObjectEvent extends BaseObjectEvent<ShippingInstruction> {
    public SICostUpdateObjectEvent(ShippingInstruction item) {
    		super(item);
    }
}
