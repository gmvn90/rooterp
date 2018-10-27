/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.MarketFobDiff;
import com.swcommodities.wsmill.hibernate.dto.facade.MarketFobDiffDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaMarketFobDiffMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class MarketFobDiffAssembler {
    public MarketFobDiffDTO toDto(MarketFobDiff marketFobDiff) {
        SelmaMarketFobDiffMapper mapper = Selma.builder(SelmaMarketFobDiffMapper.class).build();
        return mapper.toMarketFobDiffDTO(marketFobDiff);
    }
}
