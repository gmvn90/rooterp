/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.formController.form.RemarkUpdateSampleSentForm;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;

import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(withIgnoreMissing = IgnoreMissing.ALL)
public interface SelmaRemarkUpdateSampleSentMapper {
    SampleSent fromDto(RemarkUpdateSampleSentForm form, SampleSent sampleSent);
}
