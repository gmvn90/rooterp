/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.swcommodities.wsmill.domain.event.si.SIUpdatedEvent;
import com.swcommodities.wsmill.service.CacheService;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author macOS
 */

@Component
public class SIUpdatedEventListener {
    
    @Autowired CacheService cacheService;
    
    @Async
    @TransactionalEventListener
    @Transactional
    public void handle(SIUpdatedEvent event) throws InterruptedException {
        cacheService.writeSICache(event.getRefNumber());
        cacheService.writeChildSampleSentCaches(event.getRefNumber());
    }
    
}
