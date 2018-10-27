/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.facade.InterestPaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.PaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompletionStatusToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.UserToName;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(
    withCustomFields = {
        @Field(value = "approvalUser", withCustom = UserToName.class),
        @Field(value = "completionUser", withCustom = UserToName.class),
        @Field(value = "completionStatus", withCustom = CompletionStatusToName.class),
        @Field(value = "approvalStatus", withCustom = CompletionStatusToName.class),
    },
    withIgnoreFields = {"remaining", "financeContract", "invoice", "user"}
)
public interface SelmaPaymentMapper {
    PaymentDTO asPaymentDTO(Payment payment);
    InterestPaymentDTO asInterestPaymentDTO(InterestPayment payment);
    
}
