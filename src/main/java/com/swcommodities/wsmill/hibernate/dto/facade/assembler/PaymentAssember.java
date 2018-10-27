/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.facade.InterestPaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.PaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaPaymentMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class PaymentAssember {
    public PaymentDTO toPaymentDto(Payment payment) {
        SelmaPaymentMapper mapper = Selma.builder(SelmaPaymentMapper.class).build();
        return mapper.asPaymentDTO(payment);
    }
    
    public InterestPaymentDTO toPaymentDto(InterestPayment payment) {
        SelmaPaymentMapper mapper = Selma.builder(SelmaPaymentMapper.class).build();
        return mapper.asInterestPaymentDTO(payment);
    }
    
}
