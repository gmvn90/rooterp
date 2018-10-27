/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.domain.event.si.SIFinishedEvent;
import com.swcommodities.wsmill.service.TransactionService;

/**
 *
 * @author macOS
 */
@Component
public class SIFinishedEventListener {

    @Autowired
    TransactionService transactionService;
    
    @EventListener
    public void handle(SIFinishedEvent event) throws InterruptedException {
        transactionService.createTransactionWhenCompletingSI(event.getId(), event.getUserId());
    }

}
