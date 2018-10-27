/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.MarketFobDiff;
import com.swcommodities.wsmill.hibernate.dto.facade.MarketFobDiffDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeMasterToName;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withCustomFields = {       
        @Field(value = "gradeMaster", withCustom = GradeMasterToName.class),
    },
    withIgnoreFields = {"user", "log"}
)
public interface SelmaMarketFobDiffMapper {
    MarketFobDiffDTO toMarketFobDiffDTO(MarketFobDiff marketFobDiff);
}
