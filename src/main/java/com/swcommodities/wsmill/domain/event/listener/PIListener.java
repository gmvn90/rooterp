package com.swcommodities.wsmill.domain.event.listener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.swcommodities.wsmill.domain.event.pi.NewlyCreatedPIEvent;
import com.swcommodities.wsmill.domain.event.pi.PIFinishedEvent;
import com.swcommodities.wsmill.domain.event.pi.PIUpdatedEvent;
import com.swcommodities.wsmill.domain.event.pi.PIUpdatedEventByRef;
import com.swcommodities.wsmill.domain.service.PIRefNumberProviderService;
import com.swcommodities.wsmill.service.CacheService;
import com.swcommodities.wsmill.service.TransactionService;

@Component
public class PIListener {
	
	@Autowired PIRefNumberProviderService piRefNumberProviderService;
	@Autowired CacheService cacheService;
    @Autowired TransactionService transactionService;
	
	@EventListener
	public void handleNewlyCreatedObject(NewlyCreatedPIEvent event) {
		event.getItem().setRefNumber(piRefNumberProviderService.getNewNumber());
	}
	
	@Async
	@TransactionalEventListener
	public void handleUpdatedObject(PIUpdatedEvent event) {
		cacheService.writePICache(event.getItem());
	}
    
    @Async
	@TransactionalEventListener
	public void handleUpdatedObjectByRef(PIUpdatedEventByRef event) {
		cacheService.writePICache(event.getRefNumber());
	}
    
    @Async
	@TransactionalEventListener
	public void handleFinishedEvent(PIFinishedEvent event) {
		transactionService.createTransactionWhenCompletingPI(event.getItem(), event.getUser());
	}
	
}
