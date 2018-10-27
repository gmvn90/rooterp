/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.facade.TransactionDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaTransactionShortMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author trung
 */
public class TransactionShortAssembler {
    public List<TransactionDTO> fromDto(List<Transaction> transactions) {
        SelmaTransactionShortMapper mapper = Selma.builder(SelmaTransactionShortMapper.class).build();
        return transactions.stream().map(ts -> mapper.toDto(ts)).collect(Collectors.toList());
    }
}
