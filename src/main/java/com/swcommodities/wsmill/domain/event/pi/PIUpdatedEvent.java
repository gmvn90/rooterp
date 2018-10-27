/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.pi;

import com.swcommodities.wsmill.domain.event.BaseObjectEvent;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;

/**
 *
 * @author macOS
 */
public class PIUpdatedEvent extends BaseObjectEvent<ProcessingInstruction> {
    public PIUpdatedEvent(ProcessingInstruction item) {
    		super(item);
    }
}
