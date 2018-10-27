/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.formController.form.NewSampleSentForm;
import com.swcommodities.wsmill.formController.form.UpdateSampleSentForm;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.MapStructSampleSentMapper;
/**
 *
 * @author macOS
 */

public class SampleSentAssembler {
    
    public SampleSent fromDto(NewSampleSentForm form) {
        //SelmaNewSampleSentMapper mapper = Selma.builder(SelmaNewSampleSentMapper.class).build();
        //return mapper.fromDto(form);
        return MapStructSampleSentMapper.INSTANCE.fromDto(form);
    }
    
    public SampleSent fromDto(UpdateSampleSentForm form, SampleSent ss) {
        //MapStructSampleSentMapper mapper = MapStructSampleSentMapper.INSTANCE;
        //SelmaNewSampleSentMapper mapper = Selma.builder(SelmaNewSampleSentMapper.class).build();
        MapStructSampleSentMapper.INSTANCE.fromDto(form, ss);
        return ss;
    }
    
    public UpdateSampleSentForm toForm(SampleSent ss) {
        return MapStructSampleSentMapper.INSTANCE.fromObject(ss);
        //SelmaNewSampleSentMapper mapper = Selma.builder(SelmaNewSampleSentMapper.class).build();
        //return mapper.fromObject(ss);
    }
}
