/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author macOS
 */

@Entity
@Table(name = "interestpayment")
public class InterestPayment extends AbstractTimestampEntity implements BelongToFinanceContract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String refNumber;

    private String bank;
    private String accountNumber;
    private double amount;

    private Date approvalDate;
    private Date paidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User approvalUser;

    @ManyToOne
    @JsonIgnore
    private FinanceContract financeContract;

    @Transient
    private double remaining;

    public InterestPayment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public User getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(User approvalUser) {
        this.approvalUser = approvalUser;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public FinanceContract getFinanceContract() {
        return financeContract;
    }

    public void setFinanceContract(FinanceContract financeContract) {
        this.financeContract = financeContract;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }
    
    
}
