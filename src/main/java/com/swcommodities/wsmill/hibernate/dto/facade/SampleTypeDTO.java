/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.model.status.SendingStatus;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.OriginMaster;
import com.swcommodities.wsmill.hibernate.dto.QualityMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import java.util.Date;
import java.util.Set;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author macOS
 */

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class SampleTypeDTO {
    
    private String refNumber;
    private String typeSampleRef;
    private String id; // the same as typesampleref; convinient for client to use
    private Date createdDate;
    private String user;
    private Integer company;
    private Integer courier;
    private String trackingNo = "";
    private Date sentDate;
    private Date updatedDate; // when updated, this changes
    private Date etaDate;
    private SendingStatus sendingStatus = SendingStatus.PENDING;
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    private String remark;
    private String userRemark; // not use any more
    private String saveSendingStatusUser;
    private String saveApprovalStatusUser;
    private Date saveSendingStatusDate;
    private Date saveApprovalStatusDate;
    private Date saveRemarkDate; // for remark (not userremark).
    private String saveRemarkUser; // for remark (not userremark).
    private String lotRef;
    
    private Integer client;
    private Integer supplier;
    private Integer shipper;
    private Integer buyer;
    
    private String clientRef;
    private String supplierRef;
    private String shipperRef;
    private String buyerRef;
    private Integer origin;
    private Integer quality;
    private Integer grade;
    private double cost = 0;
    private String recipient;
    private Set<FileSentDTO> documents;
    
}
