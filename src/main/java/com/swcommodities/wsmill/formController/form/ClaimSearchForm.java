/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;

import java.util.Map;

/**
 *
 * @author macOS
 */
public class ClaimSearchForm extends BaseSearchStandard {
    private ApprovalStatus claimStatus = ApprovalStatus.PENDING;
    private Map<ApprovalStatus, String> claimStatuses = ApprovalStatus.getAll();

    public ApprovalStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ApprovalStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public Map<ApprovalStatus, String> getClaimStatuses() {
        return claimStatuses;
    }

    public void setClaimStatuses(Map<ApprovalStatus, String> claimStatuses) {
        this.claimStatuses = claimStatuses;
    }
}
