/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.InterestPaymentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.PaymentDTO;

/**
 *
 * @author macOS
 */
public class FinanceContractDetailResult {
    
    private FinanceContractDTO financeContract;
    private List<PaymentDTO> payments;
    private List<InterestPaymentDTO> interestPayments;
    
    public FinanceContractDetailResult(FinanceContractDTO financeContract, List<PaymentDTO> payments, List<InterestPaymentDTO> interestPayments) {
        this.financeContract = financeContract;
        this.payments = payments;
        this.interestPayments = interestPayments;
    }

    public FinanceContractDTO getFinanceContract() {
        return financeContract;
    }

    public void setFinanceContract(FinanceContractDTO financeContract) {
        this.financeContract = financeContract;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }

    public List<InterestPaymentDTO> getInterestPayments() {
        return interestPayments;
    }

    public void setInterestPayments(List<InterestPaymentDTO> interestPayments) {
        this.interestPayments = interestPayments;
    }
    
    
    
    
}
