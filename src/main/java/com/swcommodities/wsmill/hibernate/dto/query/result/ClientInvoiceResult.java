/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.ArrayList;
import java.util.List;

import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.Payment;

/**
 *
 * @author macOS
 */
public class ClientInvoiceResult {
    private int daysOverDue;
    private List<TransactionCardViewResult> deposits;
    private List<TransactionCardViewResult> upgrades;
    private List<TransactionCardViewResult> withdraws;
    private InvoiceCalculationResult result;
    private List<OtherTransaction> otherTransactions = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    public int getDaysOverDue() {
        return daysOverDue;
    }

    public void setDaysOverDue(int daysOverDue) {
        this.daysOverDue = daysOverDue;
    }

    public List<TransactionCardViewResult> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<TransactionCardViewResult> deposits) {
        this.deposits = deposits;
    }

    public List<TransactionCardViewResult> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(List<TransactionCardViewResult> upgrades) {
        this.upgrades = upgrades;
    }

    public List<TransactionCardViewResult> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List<TransactionCardViewResult> withdraws) {
        this.withdraws = withdraws;
    }

    public InvoiceCalculationResult getResult() {
        return result;
    }

    public void setResult(InvoiceCalculationResult result) {
        this.result = result;
    }

    public List<OtherTransaction> getOtherTransactions() {
        return otherTransactions;
    }

    public void setOtherTransactions(List<OtherTransaction> otherTransactions) {
        this.otherTransactions = otherTransactions;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    
    
}
