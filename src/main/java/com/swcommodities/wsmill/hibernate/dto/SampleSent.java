package com.swcommodities.wsmill.hibernate.dto;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swcommodities.wsmill.domain.model.SampleSentItem;
import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.model.status.SendingStatus;
import com.swcommodities.wsmill.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.JoinTable;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Created by gmvn on 9/11/16.
 */
@Entity
@Table(name = "sample_sent")
public class SampleSent implements java.io.Serializable {

    private Integer id;
    private String refNumber;
    private Date createdDate;
    private ShippingInstruction shippingInstructionBySiId;
    private User user;
    private CompanyMaster companyMasterByCourierId;
    private CourierMaster courierMaster;
    private String trackingNo = "";
    private Date sentDate;
    private Date updatedDate; // when updated, this changes
    private Date etaDate;
    private Byte sendingStatus;
    private SendingStatus sendingStatusEnum = SendingStatus.PENDING;
    private Byte approvalStatus;
    private ApprovalStatus approvalStatusEnum = ApprovalStatus.PENDING;
    private String remark;
    private String userRemark; // not use any more
    private User saveSendingStatusUser;
    private User saveApprovalStatusUser;
    private Date saveSendingStatusDate;
    private Date saveApprovalStatusDate;

    private Date saveRemarkDate; // for remark (not userremark).
    private User saveRemarkUser; // for remark (not userremark).
    private String lotRef;
    // pss or type
    private Integer type;
    private double cost = 0;
    private String recipient;
    private List<SampleSentItem> sampleSentItems = new ArrayList<>();
    private Set<FileSent> documents = new HashSet<>();

    public SampleSent() {
    }

    public SampleSent setInitialInfo(String refNumber, User user) {
        this.refNumber = refNumber;
        this.id = null;
        this.createdDate = new Date();
        this.user = user;
        this.updatedDate = new Date();
        this.sendingStatus = Constants.PENDING;
        this.approvalStatus = Constants.APPROVAL_PENDING;
        this.approvalStatusEnum = ApprovalStatus.PENDING;
        this.saveSendingStatusDate = null;
        this.saveApprovalStatusDate = null;
        this.saveSendingStatusUser = null;
        this.saveSendingStatusUser = null;
        this.saveRemarkUser = null;
        this.saveRemarkDate = null;
        this.sampleSentItems = new ArrayList<>();
        this.type = InstructionStatus.SampleSentType.PSS;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ref_number", nullable = false)
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "si_id")
    public ShippingInstruction getShippingInstructionBySiId() {
        return shippingInstructionBySiId;
    }

    public void setShippingInstructionBySiId(ShippingInstruction shippingInstructionBySiId) {
        this.shippingInstructionBySiId = shippingInstructionBySiId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    public CompanyMaster getCompanyMasterByCourierId() {
        return companyMasterByCourierId;
    }

    public void setCompanyMasterByCourierId(CompanyMaster companyMasterByCourierId) {
        this.companyMasterByCourierId = companyMasterByCourierId;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier")

    public CourierMaster getCourierMaster() {
        return courierMaster;
    }

    public SampleSent setCourierMaster(CourierMaster courierMaster) {
        this.courierMaster = courierMaster;
        return this;
    }

    @Column(name = "tracking_no")
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_date", length = 19)
    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", length = 19)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eta_date", length = 19)
    public Date getEtaDate() {
        return etaDate;
    }

    public void setEtaDate(Date etaDate) {
        this.etaDate = etaDate;
    }

    @Column(name = "sending_status")
    public Byte getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(Byte sendingStatus) {
        this.sendingStatus = sendingStatus;
    }

    @Column(name = "sending_status_enum")
    @Enumerated(EnumType.STRING)
    public SendingStatus getSendingStatusEnum() {

        return sendingStatusEnum;
    }

    public void setSendingStatusEnum(SendingStatus sendingStatusEnum) {
        this.sendingStatusEnum = sendingStatusEnum;
    }

    @Column(name = "approval_status")
    public Byte getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Byte approvalStatus) {
        this.approvalStatus = approvalStatus;
        this.approvalStatusEnum = ApprovalStatus.values()[Integer.valueOf(approvalStatus)];
    }

    @Column(name = "approval_status_enum")
    @Enumerated(EnumType.STRING)
    public ApprovalStatus getApprovalStatusEnum() {
        if (approvalStatusEnum != null) {
            return approvalStatusEnum;
        }
        return ApprovalStatus.values[Integer.valueOf(approvalStatus)];
    }

    public void setApprovalStatusEnum(ApprovalStatus approvalStatusEnum) {
        this.approvalStatusEnum = approvalStatusEnum;
        if (approvalStatusEnum != null) {
            this.approvalStatus = Byte.valueOf(String.valueOf(approvalStatusEnum.ordinal()));
        }
    }

    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return remark;
    }

    public SampleSent setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Column(name = "user_remark", length = 65535)
    public String getUserRemark() {
        return userRemark;
    }

    public SampleSent setUserRemark(String userRemark) {
        this.userRemark = userRemark;
        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "save_sending_user")
    public User getSaveSendingStatusUser() {
        return saveSendingStatusUser;
    }

    public SampleSent setSaveSendingStatusUser(User saveSendingStatusUser) {
        this.saveSendingStatusUser = saveSendingStatusUser;
        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "save_approval_user")
    public User getSaveApprovalStatusUser() {
        return saveApprovalStatusUser;
    }

    public String findApprovalStatusUsername() {
        return Optional.ofNullable(saveApprovalStatusUser).map(u -> u.getUserName()).orElse("");
    }

    public String findSendingStatusUsername() {
        return Optional.ofNullable(saveSendingStatusUser).map(u -> u.getUserName()).orElse("");
    }

    public SampleSent setSaveApprovalStatusUser(User saveApprovalStatusUser) {
        this.saveApprovalStatusUser = saveApprovalStatusUser;
        return this;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "save_sending_date")
    public Date getSaveSendingStatusDate() {
        return saveSendingStatusDate;
    }

    public SampleSent setSaveSendingStatusDate(Date saveSendingStatusDate) {
        this.saveSendingStatusDate = saveSendingStatusDate;
        return this;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "save_approval_date")
    public Date getSaveApprovalStatusDate() {
        return saveApprovalStatusDate;
    }

    public SampleSent setSaveApprovalStatusDate(Date saveApprovalStatusDate) {
        this.saveApprovalStatusDate = saveApprovalStatusDate;
        return this;
    }

    public Date getSaveRemarkDate() {
        return saveRemarkDate;
    }

    public void setSaveRemarkDate(Date saveRemarkDate) {
        this.saveRemarkDate = saveRemarkDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public User getSaveRemarkUser() {
        return saveRemarkUser;
    }

    public void setSaveRemarkUser(User saveRemarkUser) {
        this.saveRemarkUser = saveRemarkUser;
    }

    @Column(name = "lotRef")
    public String getLotRef() {
        return lotRef;
    }

    public void setLotRef(String lotRef) {
        this.lotRef = lotRef;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void updateUserRemark(User user) {
        this.setSaveRemarkDate(new Date());
        this.setSaveRemarkUser(user);
    }

    public void updateTimeAndUser(User user) {
        setUser(user);
        setUpdatedDate(new Date());
    }
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
        name = "sample_sent_documents",
        joinColumns = @JoinColumn(name = "sample_sent_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    public Set<FileSent> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<FileSent> fileSents) {
        this.documents = fileSents;
    }
    
    public void addDocument(FileSent doc) {
        this.documents.add(doc);
    }

    @ElementCollection(targetClass = SampleSentItem.class, fetch = FetchType.EAGER)
    @JoinTable(name = "sample_sent_items", joinColumns = @JoinColumn(name = "sample_sent_id"))
    @MapKey(name = "id")
    @Column(name = "sampleSentItems")
    @Fetch(FetchMode.SELECT)
    public List<SampleSentItem> getSampleSentItems() {
        return sampleSentItems;
    }

    public void addSampleSentItem(SampleSentItem sampleSentItem) {
        sampleSentItems.add(sampleSentItem);
    }

    public SampleSentItem findChildSampleSentItem(String localId) {
        return getSampleSentItems().stream().filter(x -> x.getSampleSentItemId().equals(localId))
            .findAny().orElse(null);
    }

    public void setSampleSentItems(List<SampleSentItem> sampleSentItems) {
        this.sampleSentItems = sampleSentItems;
    }

}
