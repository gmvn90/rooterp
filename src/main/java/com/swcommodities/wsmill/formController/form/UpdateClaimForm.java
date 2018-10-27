/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import java.util.Date;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author macOS
 */

@Data
public class UpdateClaimForm {

    private String refNumber;
    private String siRef;
    private int siId;
    private String debitNote;
    private Date debitNoteDate;
    private Date created;
    private Date updated;
    private String createdUser;
    private Integer swornWeigherId;
    private Date landingDate;
    private Integer warehouseId;
    private Date firstDateOfWeighing;
    private Date finalDateOfWeighing;
    private String unloadingMethod;
    private Date samplingDate;
    private Float contractFranchise = 0F;
    private Float destinationFranchise = 0F;
    private String remark;
    
    private ApprovalStatus claimStatus;
    private Set<FileSentDTO> documents;
    private String updatedArrivalWeightNoteUser;
    private Date updateArrivalWeightNoteDate;
    
}
