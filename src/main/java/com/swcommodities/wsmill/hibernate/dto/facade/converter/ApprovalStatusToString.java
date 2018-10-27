/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;

/**
 *
 * @author macOS
 */
public class ApprovalStatusToString {
    
    public String fromObj(ApprovalStatus approvalStatus) {
        return ApprovalStatus.getRepresentative(approvalStatus);
    }
    
}
