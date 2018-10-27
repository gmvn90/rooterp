/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructCache;

/**
 *
 * @author macOS
 */
public class CacheAssembler {
    
    public SampleSentCache toCache(SampleSent ss) {
        return MapStructCache.INSTANCE.toCache(ss);
    }
    
}
