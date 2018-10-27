/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import com.swcommodities.wsmill.domain.event.st.NewlyCreatedSTEvent;
import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.model.status.SendingStatus;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.data.domain.AbstractAggregateRoot;

/**
 *
 * @author macOS
 */
@Entity
@Table(name = "sample_type")
@Data
public class SampleType extends AbstractAggregateRoot {

    @Id
    private String typeSampleRef;
    private String refNumber;

    private Date createdDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private CourierMaster courier;
    private String trackingNo = "";
    private Date sentDate;
    private Date updatedDate; // when updated, this changes
    private Date etaDate;
    @Enumerated(EnumType.STRING)
    private SendingStatus sendingStatus = SendingStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    private String remark;
    private String userRemark; // not use any more
    @ManyToOne
    private User saveSendingStatusUser;
    @ManyToOne
    private User saveApprovalStatusUser;
    private Date saveSendingStatusDate;
    private Date saveApprovalStatusDate;
    private Date saveRemarkDate; // for remark (not userremark).
    @ManyToOne
    private User saveRemarkUser; // for remark (not userremark).
    private String lotRef;
    @ManyToOne
    private CompanyMaster client;
    @ManyToOne
    private CompanyMaster supplier;
    @ManyToOne
    private CompanyMaster shipper;
    @ManyToOne
    private CompanyMaster buyer;
    private String clientRef;
    private String supplierRef;
    private String shipperRef;
    private String buyerRef;
    @ManyToOne
    private OriginMaster origin;
    @ManyToOne
    private QualityMaster quality;
    @ManyToOne
    private GradeMaster grade;
    private double cost = 0;
    private String recipient;
    
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
        name = "sample_type_documents",
        joinColumns = @JoinColumn(name = "sample_type_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Set<FileSent> documents = new HashSet<>();

    @Transient
    public String getId() {
        return typeSampleRef;
    }

    public SampleType initNewSelf(String refNumber, String typeSampleRef, User user) {
        this.refNumber = refNumber;
        this.typeSampleRef = typeSampleRef;
        this.user = user;
        this.createdDate = new Date();
        this.updatedDate = new Date();
        registerEvent(new NewlyCreatedSTEvent(this));
        return this;
    }

    public SampleType initUpdateSelf(User user) {
        this.user = user;
        this.updatedDate = new Date();
        registerEvent(new NewlyCreatedSTEvent(this));
        return this;
    }

    public SampleType saveSendingStatus(User user, SendingStatus status) {
        this.sendingStatus = status;
        this.saveSendingStatusDate = new Date();
        this.saveSendingStatusUser = user;
        registerEvent(new NewlyCreatedSTEvent(this));
        return this;
    }

    public SampleType saveApprovalStatus(User user, ApprovalStatus status) {
        this.approvalStatus = status;
        this.saveApprovalStatusDate = new Date();
        this.saveApprovalStatusUser = user;
        registerEvent(new NewlyCreatedSTEvent(this));
        return this;
    }
    
    public void addDocument(FileSent doc) {
        this.documents.add(doc);
    }

}
