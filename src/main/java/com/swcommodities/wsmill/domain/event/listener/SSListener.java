/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.listener;

import com.swcommodities.wsmill.application.service.SISampleSentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.swcommodities.wsmill.domain.event.si.SSApprovalStatusUpdatedEvent;
import com.swcommodities.wsmill.domain.event.si.SSRejectedEvent;
import com.swcommodities.wsmill.domain.event.si.SSSendingStatusUpdatedEvent;
import com.swcommodities.wsmill.service.CacheService;
import org.springframework.context.event.EventListener;

/**
 *
 * @author trung
 */
@Service
public class SSListener {

    @Autowired CacheService cacheService;
    @Autowired SISampleSentService sISampleSentService;

    @Async
    @TransactionalEventListener
    public void handleApprovalStatus(SSApprovalStatusUpdatedEvent event) {
        cacheService.writeSampleSentCache(event.getRefNumber(), event.getStatus());
    }

    @Async
    @TransactionalEventListener
    public void handleSendingStatus(SSSendingStatusUpdatedEvent event) {
        cacheService.writeSampleSentCache(event.getRefNumber(), event.getStatus());
    }
    
    @EventListener
    public void handleReject(SSRejectedEvent event) {
        sISampleSentService.rejectSampleSent(event.getShippingInstruction(), event.getSampleSent(), event.getUser());
    }

}
