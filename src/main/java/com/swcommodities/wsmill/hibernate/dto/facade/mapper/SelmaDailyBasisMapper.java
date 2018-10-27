/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.DailyBasis;
import com.swcommodities.wsmill.hibernate.dto.facade.DailyBasisDTO;

import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withCustomFields = {        
    },
    withIgnoreFields = {"user"}
)
public interface SelmaDailyBasisMapper {
    DailyBasisDTO asDailyBasisDTO(DailyBasis dailyBasis);
}