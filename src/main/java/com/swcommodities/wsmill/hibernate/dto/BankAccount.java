/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author macOS
 */

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends AbstractTimestampEntity {
    
    public BankAccount() {}
    
    public BankAccount(int id) {
        this.id = id;
    }
    
    public BankAccount(String id) {
        this.id = Integer.valueOf(id);
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    private Beneficiary beneficiary;
        
    @ManyToOne
    private CompanyMaster bank;
    
    private int currency;
    
    private String accountNumber;
    
    @Transient
    private String currencyString;
    
    @Column(columnDefinition="TEXT")
    private String transferInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public CompanyMaster getBank() {
        return bank;
    }

    public void setBank(CompanyMaster bank) {
        this.bank = bank;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getTransferInfo() {
        return transferInfo;
    }

    public void setTransferInfo(String transferInfo) {
        this.transferInfo = transferInfo;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getCurrencyString() {
        return currencyString;
    }

    public void setCurrencyString(String currencyString) {
        this.currencyString = currencyString;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    
    
    
    
    
    
    
    
    
    
}
