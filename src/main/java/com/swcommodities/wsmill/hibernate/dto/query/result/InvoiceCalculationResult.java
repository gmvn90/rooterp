/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.query.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.Payment;

/**
 *
 * @author macOS
 */

public class InvoiceCalculationResult {
    private double totalDeposit = 0;
    private double totalUpgrade = 0;
    private double totalWithdraw = 0;
    private double totalOther = 0;
    private double totalPayment = 0;
    private double total = 0;
    @JsonIgnore
    private List<OtherTransaction> otherTransactions = new ArrayList<>();
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();
    
    
    
    public InvoiceCalculationResult(List<TransactionCardViewResult> dis, List<TransactionCardViewResult> pis, List<TransactionCardViewResult> sis, 
        List<OtherTransaction> others, List<Payment> payments) {
        for(TransactionCardViewResult transaction: dis) {
            totalDeposit += transaction.getTotal();
        }
        for(TransactionCardViewResult transaction: pis) {
            totalUpgrade += transaction.getTotal();
        }
        for(TransactionCardViewResult transaction: sis) {
            totalWithdraw += transaction.getTotal();
        }
        
        for(OtherTransaction other: others) {
            totalOther += other.getTotal();
        }
        
        total += totalDeposit + totalUpgrade + totalWithdraw + totalOther;
                
        Collections.sort(payments, new Comparator<Payment>() {
            public int compare(Payment o1, Payment o2){
                return o1.getCreated().compareTo(o2.getCreated());
            }
        });
        
        double currentRemaning = total;
        
        for(Payment payment: payments) {
            payment.setRemaining(currentRemaning - payment.getAmount());
            currentRemaning = payment.getRemaining();
            totalPayment += payment.getAmount();
        }
        this.payments = payments;
        this.otherTransactions = others;
    }

    public double getTotalDeposit() {
        return totalDeposit;
    }

    public double getTotalUpgrade() {
        return totalUpgrade;
    }

    public double getTotalWithdraw() {
        return totalWithdraw;
    }

    public double getTotalOther() {
        return totalOther;
    }

    public double getTotal() {
        return total;
    }

    public double getTotalPayment() {return totalPayment;}

    public List<Payment> getPayments() {
        return payments;
    }

    public List<OtherTransaction> getOtherTransactions() {
        return otherTransactions;
    }
    
    
}
