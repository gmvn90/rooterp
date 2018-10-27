package com.swcommodities.wsmill.hibernate.dto;

import static java.lang.Math.toIntExact;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Created by macOS on 4/24/17.
 */
@Entity
@Table(name = "finance_contract")
public class FinanceContract extends AbstractTimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String refNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyMaster client;
    private String clientRef;

    private Integer approvalStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private User approvalUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    private Integer completionStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private User completionUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionDate;

    private String remark;

    @Transient
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private BankAccount bankAccount;
    private String accountNumber;
    private String transferIns;
    @ManyToOne(fetch = FetchType.LAZY)
    private OriginMaster origin;
    @ManyToOne(fetch = FetchType.LAZY)
    private QualityMaster quality;
    @ManyToOne(fetch = FetchType.LAZY)
    private GradeMaster grade;
    @ManyToOne(fetch = FetchType.LAZY)
    private LocationMaster location;
    private double tons = 0;
    private double monthlyStorage = 0;
    private double marketPrice = 0;
    private double qualityDiff = 0;
    @Transient
    private double marketValue = 0;
    private double financePercentage = 0;
    @Transient
    private double financePrice = 0;
    @Transient
    private double strikePrice = 0;
    @Transient
    private double totalFinanced = 0;
    private double interestRatePA = 0;
    private int maxTermDays = 0;
    @Transient
    private int daysToDate = 0;
    @Transient
    private double interestIncome = 0;
    @Transient
    private double amountDue = 0;
    @Transient
    private int daysOverdue = 0;
    @Transient
    private double stopLoss = 0;
    @Transient
    private double advanceAndDiff = 0;
    @Transient
    private double monthlyInterest = 0;
    @Transient
    private double variance = 0;
    private double totalPayment = 0;
    private double balancedUnpaid = 0;
    private double totalInterestPayment = 0;
    private double paymentRemaining = 0;
    private double interestPaymentRemaining = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyBasis dailyBasis;

    public FinanceContract() {
    }

    public FinanceContract(int id) {
        this.id = id;
    }

    public FinanceContract(String id) {
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
        Calendar result = Calendar.getInstance();
        result.setTime(getCreated());
        result.add(Calendar.DATE, getMaxTermDays());
        return result.getTime();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransferIns() {
        return transferIns;
    }

    public void setTransferIns(String transferIns) {
        this.transferIns = transferIns;
    }

    public OriginMaster getOrigin() {
        return origin;
    }

    public void setOrigin(OriginMaster origin) {
        this.origin = origin;
    }

    public QualityMaster getQuality() {
        return quality;
    }

    public void setQuality(QualityMaster quality) {
        this.quality = quality;
    }

    public GradeMaster getGrade() {
        return grade;
    }

    public void setGrade(GradeMaster grade) {
        this.grade = grade;
    }

    public LocationMaster getLocation() {
        return location;
    }

    public void setLocation(LocationMaster location) {
        this.location = location;
    }

    public double getTons() {
        return tons;
    }

    public void setTons(double tons) {
        this.tons = tons;
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
        return (marketPrice + qualityDiff);
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
        return getMarketValue() * financePercentage / 100;
    }

    public void setFinancePrice(double financePrice) {
        this.financePrice = financePrice;
    }

    public double getStrikePrice() {
        return getFinancePrice() * 1.1;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public double getTotalFinanced() {
        return tons * getFinancePrice();
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
        if (getCreated() == null) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        LocalDate createdDate = new java.sql.Date(getCreated().getTime()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(createdDate, now);
        return toIntExact(daysBetween);
    }

    public void setDaysToDate(int daysToDate) {
        this.daysToDate = daysToDate;
    }

    public double getInterestIncome() {
        return (getTotalFinanced() * interestRatePA / 100 / 365) * getDaysToDate();
    }

    public void setInterestIncome(double interestIncome) {
        this.interestIncome = interestIncome;
    }

    public double getAmountDue() {
        return getTotalFinanced() + getInterestIncome();
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public int getDaysOverdue() {
        LocalDate now = LocalDate.now();
        LocalDate due = new java.sql.Date(getDueDate().getTime()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(due, now);
        return toIntExact(daysBetween >= 0 ? 0 : daysBetween);
    }

    public void setDaysOverdue(int daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public double getMonthlyStorage() {
        return monthlyStorage;
    }

    public void setMonthlyStorage(double monthlyStorage) {
        this.monthlyStorage = monthlyStorage;
    }

    public double getStopLoss() {
        return getMonthlyStorage() + getAdvanceAndDiff() + getMonthlyInterest();
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getAdvanceAndDiff() {
        return getQualityDiff() + getFinancePrice();
    }

    public void setAdvanceAndDiff(double advanceAndDiff) {
        this.advanceAndDiff = advanceAndDiff;
    }

    public double getMonthlyInterest() {
        if (getTons() == 0) {
            return 0;
        }
        return (((getTotalFinanced() * getInterestRatePA() / 100) / 365) * 31) / getTons();
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
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

    public DailyBasis getDailyBasis() {
        return dailyBasis;
    }

    public void setDailyBasis(DailyBasis dailyBasis) {
        this.dailyBasis = dailyBasis;
    }

    public double getVariance() {
        return (getDailyBasis() != null ? getDailyBasis().getCurrentBasis() : 0) - getStopLoss();
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }
}
