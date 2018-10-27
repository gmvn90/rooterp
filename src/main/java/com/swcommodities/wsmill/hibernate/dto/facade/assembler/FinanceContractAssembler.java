/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaFinanceContractMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class FinanceContractAssembler {
    public FinanceContractDTO toDto(final FinanceContract contract) {
        SelmaFinanceContractMapper mapper = Selma.builder(SelmaFinanceContractMapper.class).build();
        return mapper.asContractDTO(contract);
    } 
}
