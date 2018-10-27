/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleTypeDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructDtoToSampleType;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructSampleTypeCacheMapper;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructSampleTypeToDto;

/**
 *
 * @author macOS
 */


public class SampleTypeAssembler {
    
    public SampleType fromDto(SampleTypeDTO dto) {
        return MapStructDtoToSampleType.INSTANCE.fromDto(dto);
    }
    
    public SampleType fromDto(SampleTypeDTO dto, SampleType obj) {
        MapStructDtoToSampleType.INSTANCE.fromDto(dto, obj);
        return obj;
    }
    
    public SampleSentCache toCache(SampleType obj) {
        return MapStructSampleTypeCacheMapper.INSTANCE.toCache(obj);
    }
    
    public SampleSentCache toCache(SampleType obj, SampleSentCache cache) {
        MapStructSampleTypeCacheMapper.INSTANCE.toCache(obj, cache);
        return cache;
    }
    
    public SampleTypeDTO fromObject(SampleType st) {
        return  MapStructSampleTypeToDto.INSTANCE.toDto(st);
    }
    
}
