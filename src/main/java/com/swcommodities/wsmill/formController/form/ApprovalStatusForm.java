/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import java.util.Date;
import java.util.Map;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;

/**
 *
 * @author trung
 */
public class ApprovalStatusForm extends StatusFormEnum {
    
    private final Map<ApprovalStatus, String> statuses = ApprovalStatus.getAll();

    public Map<ApprovalStatus, String> getStatuses() {
        return statuses;
    }

    private ApprovalStatus status;

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }
    
    public ApprovalStatusForm() {}
    
    public ApprovalStatusForm(String refNumber, String user, Date date, ApprovalStatus approvalStatus) {
        super(refNumber, user, date);
        this.status = approvalStatus;
    }
    
}
