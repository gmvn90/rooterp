package com.swcommodities.wsmill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;
import com.swcommodities.wsmill.hibernate.dto.query.result.FinanceContractCalculationResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.FinanceContractQuantityResult;
import com.swcommodities.wsmill.repository.FinanceContractRepository;
import com.swcommodities.wsmill.repository.InterestPaymentRepository;
import com.swcommodities.wsmill.repository.PaymentRepository;

/**
 * Created by macOS on 4/5/17.
 */
@Service
public class FinanceContractService {

    PaymentRepository paymentRepository;
    InterestPaymentRepository interestPaymentRepository;
    FinanceContractRepository financeContractRepository;

    @Autowired
    public FinanceContractService(PaymentRepository paymentRepository,FinanceContractRepository financeContractRepository,
                                  InterestPaymentRepository interestPaymentRepository) {
        this.paymentRepository = paymentRepository;
        this.financeContractRepository = financeContractRepository;
        this.interestPaymentRepository = interestPaymentRepository;
    }

    public FinanceContract saveFinanceContractDetailNumber(int financeContractId) {
        FinanceContract financeContract = getFinanceContractWithDetailNumer(financeContractId);
        financeContractRepository.save(financeContract);
        return financeContract;
    }

    public FinanceContract getFinanceContractWithDetailNumer(int financeContractId) {
        FinanceContract financeContract = financeContractRepository.findOne(financeContractId);
        FinanceContractQuantityResult financeContractQuantityResult = getFinanceContractQuantity(financeContract.getTotalFinanced(), financeContract.getInterestIncome(), financeContractId);
        financeContract.setTotalPayment(financeContractQuantityResult.getTotalPayment());
        financeContract.setBalancedUnpaid(financeContractQuantityResult.getBalancedUnpaid());
        financeContract.setTotalInterestPayment(financeContractQuantityResult.getTotalInterestPayment());
        financeContract.setInterestPaymentRemaining(financeContractQuantityResult.getInterestPaymentRemaining());

        return financeContract;
    }

    public FinanceContractQuantityResult getFinanceContractQuantity(double totalFinanced, double interestIncome, int financeContractId ) {
        List<Payment> payments = paymentRepository.getPaymentsByFinanceContract(financeContractId);
        List<InterestPayment> interestPayments = interestPaymentRepository.getInterestPaymentsByFinanceContract(financeContractId);


        FinanceContractCalculationResult result = new FinanceContractCalculationResult(totalFinanced, interestIncome, interestPayments, payments);
        FinanceContractQuantityResult quantityResult = new FinanceContractQuantityResult();
        quantityResult.setTotalPayment(result.getTotalPayment());
        quantityResult.setBalancedUnpaid(result.getBalancedUnpaid());
        quantityResult.setTotalInterestPayment(result.getTotalInterestPayment());
        quantityResult.setInterestPaymentRemaining(result.getInterestPaymentRemaining());
        return quantityResult;
    }
    
}
