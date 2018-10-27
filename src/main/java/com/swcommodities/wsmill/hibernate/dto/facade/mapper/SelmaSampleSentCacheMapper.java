/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author trung
 */
@Mapper(withIgnoreMissing = IgnoreMissing.ALL, withCustomFields = {
    @Field({"sampleRef", "refNumber"}),
    @Field({"SampleSent.type", "SampleSentCache.type"}),
})
public interface SelmaSampleSentCacheMapper {
    SampleSentCache fromObject(SampleSent ss);
}
