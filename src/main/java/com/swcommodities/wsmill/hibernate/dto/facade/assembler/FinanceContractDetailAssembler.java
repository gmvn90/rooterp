/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import java.util.ArrayList;
import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.InterestPaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.PaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.query.result.FinanceContractDetailResult;

/**
 *
 * @author macOS
 */
public class FinanceContractDetailAssembler {
    public FinanceContractDetailResult toDto(FinanceContract financeContract, List<Payment> payments, List<InterestPayment> interestPayments) {
        FinanceContractAssembler assembler1 = new FinanceContractAssembler();
        FinanceContractDTO financeContractDTO = assembler1.toDto(financeContract);
        PaymentAssember assember2 = new PaymentAssember();
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        for(Payment payment: payments) {
            paymentDTOs.add(assember2.toPaymentDto(payment));
        }
        List<InterestPaymentDTO> interestPaymentDTOs = new ArrayList<>();
        for(InterestPayment payment: interestPayments) {
            interestPaymentDTOs.add(assember2.toPaymentDto(payment));
        }
        return new FinanceContractDetailResult(financeContractDTO, paymentDTOs, interestPaymentDTOs);
    }
}
