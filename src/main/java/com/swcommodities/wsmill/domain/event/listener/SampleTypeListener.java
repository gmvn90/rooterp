/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.listener;

import com.swcommodities.wsmill.domain.event.st.NewlyCreatedSTEvent;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.repository.SSCacheRepository;
import com.swcommodities.wsmill.service.CacheService;
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
public class SampleTypeListener {
    
    @Autowired CacheService cacheService; 
    
    @Async
    @TransactionalEventListener
    public void saveCache(NewlyCreatedSTEvent event) {
        SampleType st = event.getSampleType();
        cacheService.writeSampleTypeCache(st.getId());
    }
    
}
