package com.swcommodities.wsmill.hibernate.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Created by macOS on 4/4/17.
 */
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractTimestampEntity implements BelongToInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String refNumber;

    private Integer type;
    private Integer approvalStatus;
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoicedDate;
    private Integer invoicedStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryInstruction di;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProcessingInstruction pi;
    @ManyToOne(fetch = FetchType.LAZY)
    private ShippingInstruction si;
    private String remark;
    private Double storageWeightLoss;
    private Double processingWeightLoss;
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyMaster client;
    private String clientRef;
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private User approveUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private User invoicedUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private LocationMaster location;
    @ManyToOne(fetch = FetchType.LAZY)
    private GradeMaster grade;
    @ManyToOne(fetch = FetchType.LAZY)
    private PIType piType;
    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @OneToMany(mappedBy = "transaction")
    private Set<TransactionItem> transactionItems = new HashSet<>();

    public ArrayList<TransactionItem> getDepositTransactionItems() {
        ArrayList<TransactionItem> result = new ArrayList<>();
        for (TransactionItem transactionItem : transactionItems) {
            if (transactionItem.getType() == 1) {
                result.add(transactionItem);
            }
        }
        Collections.sort(result);
        return result;
    }

    public ArrayList<TransactionItem> getWithdrawTransactionItems() {
        ArrayList<TransactionItem> result = new ArrayList<>();
        for (TransactionItem transactionItem : transactionItems) {
            if (transactionItem.getType() == 2) {
                result.add(transactionItem);
            }
        }
        Collections.sort(result);
        return result;
    }

    public double getWithdrawCost() {
        double result = 0;
        for (TransactionItem transactionItem : transactionItems) {
            if (transactionItem.getType() == 2) {
                result += transactionItem.getCost();
            }
        }
        return result;
    }

    public Transaction() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Integer getInvoicedStatus() {
        return invoicedStatus;
    }

    public void setInvoicedStatus(Integer invoicedStatus) {
        this.invoicedStatus = invoicedStatus;
    }

    public DeliveryInstruction getDi() {
        return di;
    }

    public void setDi(DeliveryInstruction di) {
        this.di = di;
    }

    public ProcessingInstruction getPi() {
        return pi;
    }

    public void setPi(ProcessingInstruction pi) {
        this.pi = pi;
    }

    public ShippingInstruction getSi() {
        return si;
    }

    public void setSi(ShippingInstruction si) {
        this.si = si;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getStorageWeightLoss() {
        return storageWeightLoss;
    }

    public void setStorageWeightLoss(Double storageWeightLoss) {
        this.storageWeightLoss = storageWeightLoss;
    }

    public Double getProcessingWeightLoss() {
        return processingWeightLoss;
    }

    public void setProcessingWeightLoss(Double processingWeightLoss) {
        this.processingWeightLoss = processingWeightLoss;
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

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(User approveUser) {
        this.approveUser = approveUser;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocationMaster getLocation() {
        return location;
    }

    public void setLocation(LocationMaster location) {
        this.location = location;
    }

    public GradeMaster getGrade() {
        return grade;
    }

    public void setGrade(GradeMaster grade) {
        this.grade = grade;
    }

    public Date getInvoicedDate() {
        return invoicedDate;
    }

    public void setInvoicedDate(Date invoicedDate) {
        this.invoicedDate = invoicedDate;
    }

    public User getInvoicedUser() {
        return invoicedUser;
    }

    public void setInvoicedUser(User invoicedUser) {
        this.invoicedUser = invoicedUser;
    }

    public Set<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(Set<TransactionItem> transactionItems) {
        this.transactionItems = transactionItems;
    }

    public PIType getPiType() {
        return piType;
    }

    public void setPiType(PIType piType) {
        this.piType = piType;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
