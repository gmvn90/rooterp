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
public class CompletionStatusToName {
    public String mapApprovalStatus(Integer status) {
        if(status == null) {
            return "";
        }
        byte statusByte = (byte) ((int) status);
        return InstructionStatus.getCompletionStatuses().get(statusByte);
    }
    public String mapApprovalStatus(Byte status) {
        if(status == null) {
            return "";
        }
        return InstructionStatus.getCompletionStatuses().get(status);
    }
    
}
