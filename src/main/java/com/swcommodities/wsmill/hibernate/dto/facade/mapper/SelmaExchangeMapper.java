/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.Exchange;
import com.swcommodities.wsmill.hibernate.dto.facade.ExchangeDTO;

import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withCustomFields = {        
    },
    withIgnoreFields = {"exchangeHistory"}
)
public interface SelmaExchangeMapper {
    ExchangeDTO toDto(Exchange exchange);
}
