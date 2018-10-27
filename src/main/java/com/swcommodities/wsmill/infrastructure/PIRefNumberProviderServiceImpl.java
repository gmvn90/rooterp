package com.swcommodities.wsmill.infrastructure;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.domain.service.PIRefNumberProviderService;
import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfo;

@Service
public class PIRefNumberProviderServiceImpl extends BaseRefNumberProviderService implements PIRefNumberProviderService  {
	
	private final static AtomicInteger refNumber = new AtomicInteger(0);
    private final String prefix = "PI";
    
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
