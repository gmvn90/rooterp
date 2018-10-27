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
public class InvoiceAggregateInfo {
    private double amount;
    private double paid;
    private double balance;
    
    public InvoiceAggregateInfo() {}
    
    public InvoiceAggregateInfo(Double amount, Double balance, Double paid) {
        this.amount = amount != null ? amount : 0;
        this.paid = paid != null ? paid : 0;
        this.balance = balance != null ? balance : 0;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    
    
}
