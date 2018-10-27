/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

/**
 *
 * @author macOS
 */
public class FinanceContractQuantityResult {
    private double totalFinanced;
    private double totalPayment;
    private double balancedUnpaid;
    private double totalInterestPayment;
    private double interestPaymentRemaining;
    private double stopLoss;

    public double getTotalFinanced() {
        return totalFinanced;
    }

    public void setTotalFinanced(double totalFinanced) {
        this.totalFinanced = totalFinanced;
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

    public double getInterestPaymentRemaining() {
        return interestPaymentRemaining;
    }

    public void setInterestPaymentRemaining(double interestPaymentRemaining) {
        this.interestPaymentRemaining = interestPaymentRemaining;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }
}
