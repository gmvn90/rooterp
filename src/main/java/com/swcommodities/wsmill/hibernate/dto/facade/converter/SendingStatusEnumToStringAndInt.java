/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.domain.model.status.SendingStatus;

/**
 *
 * @author macOS
 */
public class SendingStatusEnumToStringAndInt {
    
    public String mapToString(SendingStatus status) {
        if(status == null) {
            return "";
        }
        return SendingStatus.getAll().get(status);
    }
    
    public Byte mapToInteger(SendingStatus status) {
        if(status == null) {
            return null;
        }
        return (byte) status.getOrder();
    }
    
}
