/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleSentShortSummaryDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaSampleSentShortSummaryMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class SampleSentShortSummaryAssembler {
    
    public List<SampleSentShortSummaryDTO> fromListObjects(Collection<SampleSent> sampleSents) {
        SelmaSampleSentShortSummaryMapper mapper = Selma.builder(SelmaSampleSentShortSummaryMapper.class).build();
        return sampleSents.stream().map(ss -> mapper.fromObject(ss)).collect(Collectors.toList());
    }
    
    public List<SampleSentShortSummaryDTO> fromListObjects(Set<SampleSent> sampleSents) {
        SelmaSampleSentShortSummaryMapper mapper = Selma.builder(SelmaSampleSentShortSummaryMapper.class).build();
        return sampleSents.stream().map(ss -> mapper.fromObject(ss)).collect(Collectors.toList());
    }
    
}
