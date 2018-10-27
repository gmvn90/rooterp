/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.Transaction;
import com.swcommodities.wsmill.hibernate.dto.facade.TransactionDTO;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author trung
 */

@Mapper(withIgnoreMissing = IgnoreMissing.DESTINATION,
        withCustomFields = {
            @Field({"com.swcommodities.wsmill.hibernate.dto.Transaction.createdUser.username", "com.swcommodities.wsmill.hibernate.dto.facade.TransactionDTO.createdUser"})
        })
public interface SelmaTransactionShortMapper {
    TransactionDTO toDto(Transaction transaction);
}
