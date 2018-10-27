/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.formController.form.RemarkUpdateSampleSentForm;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaRemarkUpdateSampleSentMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class RemarkUpdateSampleSentAssembler {
    public SampleSent fromDto(RemarkUpdateSampleSentForm form, SampleSent sampleSent) {
        SelmaRemarkUpdateSampleSentMapper mapper = Selma.builder(SelmaRemarkUpdateSampleSentMapper.class).build();
        return mapper.fromDto(form, sampleSent);
    }
}
