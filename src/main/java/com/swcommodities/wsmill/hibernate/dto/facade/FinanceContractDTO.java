/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

import java.util.Date;

import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;

/**
 *
 * @author macOS
 */
public class FinanceContractDTO {
    private int id;
    private String refNumber = "";
    private String client = "";
    private String clientRef = "";
    private String approvalStatus = InstructionStatus.getApprovalStatuses().get(InstructionStatus.SampleSentApprovalStatus.PENDING);
    private String approvalUser = "";
    private Date approvalDate;
    private String completionStatus = InstructionStatus.getCompletionStatuses().get(InstructionStatus.InstructionCompletionStatus.PENDING);
    private String completionUser = "";
    private Date completionDate;
    private String remark = "";
    private Date dueDate;
    private String origin = "";
    private String quality = "";
    private String grade = "";
    private String location = "";
    private double tons = 0;
    private double monthlyStorage = 0;
    private double marketPrice = 0;
    private double qualityDiff = 0;
    private double marketValue = 0;
    private double financePercentage = 0;
    private double financePrice = 0;
    private double strikePrice = 0;
    private double totalFinanced = 0;
    private double interestRatePA = 0;
    private int maxTermDays = 0;
    private int daysToDate = 0;
    private double interestIncome = 0;
    private double amountDue = 0;
    private int daysOverdue = 0;
    private double stopLoss = 0;
    private double advanceAndDiff = 0;
    private double monthlyInterest = 0;
    private double variance = 0;
    private double totalPayment = 0;
    private double balancedUnpaid = 0;
    private double totalInterestPayment = 0;
    private double paymentRemaining = 0;
    private double interestPaymentRemaining = 0;
    private double dailyBasis = 0;
    private Date created;
    private Date updated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(String completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getCompletionUser() {
        return completionUser;
    }

    public void setCompletionUser(String completionUser) {
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTons() {
        return tons;
    }

    public void setTons(double tons) {
        this.tons = tons;
    }

    public double getMonthlyStorage() {
        return monthlyStorage;
    }

    public void setMonthlyStorage(double monthlyStorage) {
        this.monthlyStorage = monthlyStorage;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getQualityDiff() {
        return qualityDiff;
    }

    public void setQualityDiff(double qualityDiff) {
        this.qualityDiff = qualityDiff;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public double getFinancePercentage() {
        return financePercentage;
    }

    public void setFinancePercentage(double financePercentage) {
        this.financePercentage = financePercentage;
    }

    public double getFinancePrice() {
        return financePrice;
    }

    public void setFinancePrice(double financePrice) {
        this.financePrice = financePrice;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public double getTotalFinanced() {
        return totalFinanced;
    }

    public void setTotalFinanced(double totalFinanced) {
        this.totalFinanced = totalFinanced;
    }

    public double getInterestRatePA() {
        return interestRatePA;
    }

    public void setInterestRatePA(double interestRatePA) {
        this.interestRatePA = interestRatePA;
    }

    public int getMaxTermDays() {
        return maxTermDays;
    }

    public void setMaxTermDays(int maxTermDays) {
        this.maxTermDays = maxTermDays;
    }

    public int getDaysToDate() {
        return daysToDate;
    }

    public void setDaysToDate(int daysToDate) {
        this.daysToDate = daysToDate;
    }

    public double getInterestIncome() {
        return interestIncome;
    }

    public void setInterestIncome(double interestIncome) {
        this.interestIncome = interestIncome;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }

    public void setDaysOverdue(int daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getAdvanceAndDiff() {
        return advanceAndDiff;
    }

    public void setAdvanceAndDiff(double advanceAndDiff) {
        this.advanceAndDiff = advanceAndDiff;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
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

    public double getDailyBasis() {
        return dailyBasis;
    }

    public void setDailyBasis(double dailyBasis) {
        this.dailyBasis = dailyBasis;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    

}
