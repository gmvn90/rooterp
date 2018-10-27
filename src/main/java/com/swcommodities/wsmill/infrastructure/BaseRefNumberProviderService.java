/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.infrastructure;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.Validate;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfo;

/**
 *
 * @author trung
 */
public class BaseRefNumberProviderService {
    
    protected String getRefNumber(String prefix, int ref) {
        int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
        return String.format("%s-%d-%05d", prefix, lastTwoDigits, ref);
    }
    
    public void resetNumber(RefNumberCurrentInfo info, AtomicInteger refNumber) {
        if(info == null) {
            return;
        }
        Validate.isTrue(info.getTwoNumberCurrentYear() >= info.getTwoNumberMaxYearInDatabase());
        if(info.getTwoNumberCurrentYear() > info.getTwoNumberMaxYearInDatabase()) {
            refNumber.set(0);
        } else {
            Validate.isTrue(info.getMaxNumber() > 0);
            refNumber.set(info.getMaxNumber());
        }
    }
    
    public String getNumberWithoutIncreasing(String prefix, AtomicInteger refNumber) {
        return getRefNumber(prefix, refNumber.get());
    }
    
    public String incrementAndGet(String prefix, AtomicInteger refNumber) {
        return getRefNumber(prefix, refNumber.incrementAndGet());
    }
}
