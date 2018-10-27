/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleSentShortSummaryDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.SendingStatusEnumToStringAndInt;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

// user in shippinginstruction detal page
@Mapper(withIgnoreMissing = IgnoreMissing.DESTINATION,
    withCustomFields = {
        @Field({"courierMaster.name", "courier"}),
        @Field({"user.username", "user"}),
        @Field({"saveRemarkUser.username", "saveRemarkUser"}),
        @Field({"SampleSent.courierMaster.cost", "SampleSentShortSummaryDTO.cost"}),
    },
    withCustom = {SendingStatusEnumToStringAndInt.class}
)
public interface SelmaSampleSentShortSummaryMapper {
    SampleSentShortSummaryDTO fromObject(SampleSent sampleSent);
}
