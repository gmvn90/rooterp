/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.service;

import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.SampleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author macOS
 */

@Service
public class SampleTypeDomainService {
    
    @Autowired SampleTypeRepository sampleTypeRepository;
    @Autowired SampleTypeRefNumberProviderService sampleTypeRefNumberProviderService;
    @Autowired SampleSentRefNumberProviderService sampleSentRefNumberProviderService;
    
    public SampleType save(SampleType st, User user) {
        st.initNewSelf(sampleSentRefNumberProviderService.getNewNumber(), sampleTypeRefNumberProviderService.getNewNumber(), user);
        st = sampleTypeRepository.save(st);
        return st;
    } 
    
    public SampleType update(SampleType st, User user) {
        st.initUpdateSelf(user);
        st = sampleTypeRepository.save(st);
        return st;
    } 
    
}
