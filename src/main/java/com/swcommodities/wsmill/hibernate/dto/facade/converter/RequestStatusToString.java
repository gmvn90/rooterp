/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;

/**
 *
 * @author macOS
 */
public class RequestStatusToString {
    public String mapApprovalStatus(Byte status) {
        if(status == null) {
            return "";
        }
        return InstructionStatus.getRequestStatuses().get(status);
    }
}
