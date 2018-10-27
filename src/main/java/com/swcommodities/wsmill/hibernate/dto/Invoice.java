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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Created by macOS on 4/24/17.
 */
@Entity
@Table(name = "invoice")
public class Invoice extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String refNumber;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("clientId")
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyMaster client;
    private String clientRef;
    private Integer approvalStatus;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User approvalUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    private Integer completionStatus;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User completionUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;

    private String remark;

    private Integer daysOverDue;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPaymentDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    private double amount = 0;
    private double balance = 0;
    private double paidAmount = 0;

    @ManyToOne
    @JsonIgnore
    private BankAccount bankAccount;

    public Invoice() {
    }

    public Invoice(int id) {
        this.id = id;
    }

    public Invoice(String id) {
        this.id = Integer.valueOf(id);
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

    public CompanyMaster getClient() {
        return client;
    }

    public void setClient(CompanyMaster client) {
        this.client = client;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public User getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(User approvalUser) {
        this.approvalUser = approvalUser;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Integer completionStatus) {
        this.completionStatus = completionStatus;
    }

    public User getCompletionUser() {
        return completionUser;
    }

    public void setCompletionUser(User completionUser) {
        this.completionUser = completionUser;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getDaysOverDue() {

        return daysOverDue;
    }

    public void setDaysOverDue(Integer daysOverDue) {
        this.daysOverDue = daysOverDue;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @JsonProperty("client")
    public String getTheClientName() {
        if (client != null) {
            return client.getName();
        }
        return "";
    }

    @JsonProperty("approvalUser")
    public String getTheApprovalUser() {
        if (approvalUser != null) {
            return approvalUser.getUserName();
        }
        return "";
    }

    public boolean getCompleted() {
        return completionStatus == InstructionStatus.InvoiceCompletionStatus.COMPLETED;
    }
}
