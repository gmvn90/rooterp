/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.si;

import com.swcommodities.wsmill.domain.event.BaseObjectEvent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;

/**
 *
 * @author macOS
 */
public class NewlyCreatedSIEvent extends BaseObjectEvent<ShippingInstruction> {
    
	public NewlyCreatedSIEvent(ShippingInstruction item) {
		super(item);
	}
	
	
        
}
