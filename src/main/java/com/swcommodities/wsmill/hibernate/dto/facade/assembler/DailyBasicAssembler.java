/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.DailyBasis;
import com.swcommodities.wsmill.hibernate.dto.facade.DailyBasisDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaDailyBasisMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class DailyBasicAssembler {
    public DailyBasisDTO toDto(DailyBasis dailyBasis) {
        SelmaDailyBasisMapper mapper = Selma.builder(SelmaDailyBasisMapper.class).build();
        return mapper.asDailyBasisDTO(dailyBasis);
    }
}
