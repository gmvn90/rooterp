/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.domain.service.ClaimRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.PIRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.SampleSentRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.SampleTypeRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.ShippingAdviceRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.ShippingInstructionRefNumberProviderService;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.SampleTypeRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;

/**
 *
 * @author macOS
 */
@Service
public class InitService {
    @Autowired private ShippingInstructionRepository shippingInstructionRepository;
    @Autowired ShippingAdviceRefNumberProviderService shippingAdviceRefNumberProviderService;
    @Autowired SampleSentRefNumberProviderService sampleSentRefNumberProviderService;
    @Autowired ShippingInstructionRefNumberProviderService shippingInstructionRefNumberProviderService;
    @Autowired ClaimRefNumberProviderService claimRefNumberProviderService;
    @Autowired ProcessingInstructionRepository processingInstructionRepository;
    @Autowired PIRefNumberProviderService piRefNumberProviderService;
    @Autowired SampleTypeRefNumberProviderService sampleTypeRefNumberProviderService;
    @Autowired SampleTypeRepository sampleTypeRepository;
    
    private final static AtomicInteger currentYear = new AtomicInteger(getCurrentYear());
    
    private static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    public void init() {
        shippingAdviceRefNumberProviderService.resetNumber(shippingInstructionRepository.findMaxShippingAdviceNumber());
        sampleSentRefNumberProviderService.resetNumber(shippingInstructionRepository.findMaxSampleSentNumber());
        shippingInstructionRefNumberProviderService.resetNumber(shippingInstructionRepository.findMaxShippingInstructionNumber());
        claimRefNumberProviderService.resetNumber(shippingInstructionRepository.findMaxClaimNumber());
        piRefNumberProviderService.resetNumber(processingInstructionRepository.findMaxProcessingInstructionNumber());
        sampleTypeRefNumberProviderService.resetNumber(sampleTypeRepository.findMaxSampleTypeNumber());
    }
    
    public void initOnNewYear() {
        if(getCurrentYear() > currentYear.get()) {
            currentYear.set(getCurrentYear());
            init();
        }
    }
}
