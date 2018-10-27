/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.Exchange;
import com.swcommodities.wsmill.hibernate.dto.facade.ExchangeDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaExchangeMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class ExchangeAssembler {
    public ExchangeDTO toDto(Exchange exchange) {
        SelmaExchangeMapper mapper = Selma.builder(SelmaExchangeMapper.class).build();
        return mapper.toDto(exchange);
    }
}
