/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleSentShortSummaryDTO;

/**
 *
 * @author macOS
 */
public class SampleSentToForm {
    public SampleSentShortSummaryDTO toDto(SampleSent sampleSent) {
        System.out.println("Inside " + sampleSent.getId());
        SampleSentShortSummaryDTO dto = new SampleSentShortSummaryDTO();
        if(sampleSent.getId() != null) {
            dto.setId(sampleSent.getId());
        }
        dto.setRefNumber(sampleSent.getRefNumber());
        CourierMaster courier = sampleSent.getCourierMaster();
        if(courier != null) {
            dto.setCourier(courier.getName());
            dto.setCost(courier.getCost());
        }
        User user = sampleSent.getUser();
        if(user != null) {
            dto.setUser(user.getUserName());
            
        }
        User savedUser = sampleSent.getSaveRemarkUser();
        if(savedUser != null) {
            dto.setSaveRemarkUser(savedUser.getUserName());
        }
        SendingStatusToString con1 = new SendingStatusToString();
        dto.setSendingStatus(con1.mapApprovalStatus(sampleSent.getSendingStatus()));
        
        return dto;
    }
}
