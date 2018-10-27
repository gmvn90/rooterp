/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import java.util.Set;
import java.util.stream.Collectors;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleSentShortSummaryDTO;

/**
 *
 * @author trung
 */
public class ListSampleSentsToDto {
    
    public Set<SampleSentShortSummaryDTO> toDto(Set<SampleSent> sss) {
        return sss.stream().map(sampleSent -> {
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
            SendingStatusEnumToStringAndInt con1 = new SendingStatusEnumToStringAndInt();
            dto.setSendingStatus(con1.mapToString(sampleSent.getSendingStatusEnum()));
            dto.setTrackingNo(sampleSent.getTrackingNo());
            dto.setRemark(sampleSent.getRemark());
            dto.setUpdatedDate(sampleSent.getUpdatedDate());
            dto.setSaveRemarkDate(sampleSent.getSaveRemarkDate());
            return dto;
        }).collect(Collectors.toSet());
    }
    
}
