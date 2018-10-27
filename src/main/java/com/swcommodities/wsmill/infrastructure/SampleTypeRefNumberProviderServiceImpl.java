/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.infrastructure;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfo;
import com.swcommodities.wsmill.domain.service.SampleTypeRefNumberProviderService;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

/**
 *
 * @author macOS
 */

@Service
public class SampleTypeRefNumberProviderServiceImpl extends BaseRefNumberProviderService implements SampleTypeRefNumberProviderService {
    
    private final static AtomicInteger refNumber = new AtomicInteger(0);
    private final String prefix = "TS";
    
    @Override
    public String getNumberWithoutIncreasing() {
        return super.getNumberWithoutIncreasing(prefix, refNumber);
    }
    
    @Override
    public String getNewNumber() {
        return super.incrementAndGet(prefix, refNumber);
    }
    
    @Override
    public void resetNumber(RefNumberCurrentInfo info) {
        super.resetNumber(info, refNumber);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
    
}
