/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swcommodities.wsmill.hibernate.dto.InterestPayment;
import com.swcommodities.wsmill.hibernate.dto.Payment;

/**
 *
 * @author macOS
 */

public class FinanceContractCalculationResult {
    private double totalPayment = 0;
    private double balancedUnpaid = 0;
    private double totalInterestPayment = 0;
    private double paymentRemaining = 0;
    private double interestPaymentRemaining = 0;


    @JsonIgnore
    private List<InterestPayment> interestPayments = new ArrayList<>();
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();



    public FinanceContractCalculationResult(double totalFinanced, double interestIncome, List<InterestPayment> interestPayments, List<Payment> payments) {

        paymentRemaining = totalFinanced;
        for (Payment payment: payments) {
            paymentRemaining = paymentRemaining - payment.getAmount();
            totalPayment += payment.getAmount();
        }


        interestPaymentRemaining = interestIncome;
        for (InterestPayment interestPayment: interestPayments) {
            interestPaymentRemaining = interestPaymentRemaining - interestPayment.getAmount();
            totalInterestPayment += interestPayment.getAmount();
        }
        balancedUnpaid = paymentRemaining + interestPaymentRemaining;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getBalancedUnpaid() {
        return balancedUnpaid;
    }

    public void setBalancedUnpaid(double balancedUnpaid) {
        this.balancedUnpaid = balancedUnpaid;
    }

    public double getTotalInterestPayment() {
        return totalInterestPayment;
    }

    public void setTotalInterestPayment(double totalInterestPayment) {
        this.totalInterestPayment = totalInterestPayment;
    }

    public double getPaymentRemaining() {
        return paymentRemaining;
    }

    public void setPaymentRemaining(double paymentRemaining) {
        this.paymentRemaining = paymentRemaining;
    }

    public double getInterestPaymentRemaining() {
        return interestPaymentRemaining;
    }

    public void setInterestPaymentRemaining(double interestPaymentRemaining) {
        this.interestPaymentRemaining = interestPaymentRemaining;
    }

    public List<InterestPayment> getInterestPayments() {
        return interestPayments;
    }

    public void setInterestPayments(List<InterestPayment> interestPayments) {
        this.interestPayments = interestPayments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
