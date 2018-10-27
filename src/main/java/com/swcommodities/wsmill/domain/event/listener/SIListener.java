/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.listener;

import com.swcommodities.wsmill.domain.event.si.NewlyCreatedSIEvent;
import com.swcommodities.wsmill.domain.event.si.SSSendingStatusUpdatedEvent;
import com.swcommodities.wsmill.domain.service.ShippingInstructionRefNumberProviderService;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 *
 * @author macOS
 */
@Service
public class SIListener {
    @Autowired ShippingInstructionRefNumberProviderService shippingInstructionRefNumberProviderService;
    
    @EventListener
    public void handleNewlyCreated(NewlyCreatedSIEvent event) {
        ShippingInstruction shippingInstruction = event.getItem();
        shippingInstruction.setRefNumber(shippingInstructionRefNumberProviderService.getNewNumber());
    }
    
}
