/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.policy;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import org.springframework.util.StringUtils;

/**
 *
 * @author macOS
 */
public class SampleSentFullInfoPolicy {
    
    public boolean isAllowedToApprove(SampleSent sampleSent) {
        return sampleSent != null && sampleSent.getDocuments().size() > 0 && ! StringUtils.isEmpty(sampleSent.getRecipient()) && ! StringUtils.isEmpty(sampleSent.getLotRef());
    }
    
    public boolean isAllowedToApprove(SampleType sampleSent) {
        return sampleSent != null && sampleSent.getDocuments().size() > 0 && ! StringUtils.isEmpty(sampleSent.getRecipient()) && ! StringUtils.isEmpty(sampleSent.getLotRef());
    }
    
}
