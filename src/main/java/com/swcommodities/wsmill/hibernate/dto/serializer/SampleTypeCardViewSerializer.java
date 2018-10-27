/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.service.InstructionService;

/**
 *
 * @author macOS
 */
public class SampleTypeCardViewSerializer extends JsonSerializer<SampleSent> {
    @Autowired
    private InstructionService instructionService;
	
	@Override
    public void serialize(SampleSent value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
		//Sample Sent Ref.	Courier	AWB	Status	Remarks	Last updated
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        jgen.writeStartObject();
        
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("refNumber", value.getRefNumber());
        jgen.writeObjectField("createdDate", value.getCreatedDate());
        if(value.getUser() != null) {
            jgen.writeNumberField("user", value.getUser().getId());
        } else {
            jgen.writeNumberField("user", null);
        }
        if(value.getCompanyMasterByCourierId() != null) {
            jgen.writeNumberField("companyMasterByCourierId", value.getCompanyMasterByCourierId().getId());
        } else {
            jgen.writeNumberField("companyMasterByCourierId", null);
        }
        if(value.getCourierMaster() != null) {
            jgen.writeNumberField("courierMaster", value.getCourierMaster().getId());
        } else {
            jgen.writeNumberField("courierMaster", null);
        }
        
        if(value.getCourierMaster() != null) {
            jgen.writeNumberField("courierMasterCost", value.getCourierMaster().getCost());
        } else {
            jgen.writeNumberField("courierMasterCost", null);
        }
        
        if(value.getCourierMaster() != null) {
            jgen.writeStringField("courierMasterName", value.getCourierMaster().getName());
        } else {
            jgen.writeStringField("courierMasterName", null);
        }
        jgen.writeStringField("trackingNo", value.getTrackingNo());
        jgen.writeObjectField("sentDate", value.getSentDate());
        try {
        	jgen.writeObjectField("updatedDate", value.getUpdatedDate());
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        jgen.writeObjectField("etaDate", value.getEtaDate());
        try {
        	jgen.writeStringField("sendingStatusStr", InstructionStatus.getSampleSentSendingStatus(value.getSendingStatus()));
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
        	jgen.writeStringField("approvalStatusStr", InstructionStatus.getSampleSentApprovalStatus(value.getApprovalStatus()));
		} catch (Exception e) {
			// TODO: handle exception
		}

        try {
            jgen.writeNumberField("sendingStatus", value.getSendingStatus());
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            jgen.writeNumberField("approvalStatus", value.getApprovalStatus());
        } catch (Exception e) {
            // TODO: handle exception
        }

        jgen.writeStringField("remark", value.getRemark());
        jgen.writeStringField("userRemark", value.getUserRemark());
        if(value.getSaveSendingStatusUser() != null) {
            jgen.writeNumberField("saveSendingStatusUser", value.getSaveSendingStatusUser().getId());
        } else {
            jgen.writeNumberField("saveSendingStatusUser", null);
        }
        if(value.getSaveApprovalStatusUser() != null) {
            jgen.writeNumberField("saveApprovalStatusUser", value.getSaveApprovalStatusUser().getId());
        } else {
            jgen.writeNumberField("saveApprovalStatusUser", null);
        }
        jgen.writeObjectField("saveSendingStatusDate", value.getSaveSendingStatusDate());
        jgen.writeObjectField("saveApprovalStatusDate", value.getSaveApprovalStatusDate());

        if (value.getUser() != null) {
            jgen.writeObjectField("userStr", value.getUser().getUserName());
        }
        if (value.getSaveSendingStatusUser() != null) {
            jgen.writeObjectField("saveSendingStatusUserStr", value.getSaveSendingStatusUser().getUserName());
        }
        if (value.getSaveApprovalStatusUser() != null) {
            jgen.writeObjectField("saveApprovalStatusUserStr", value.getSaveApprovalStatusUser().getUserName());
        }
        
        jgen.writeObjectField("lotRef", value.getLotRef());
        ShippingInstruction si = value.getShippingInstructionBySiId();
        try{
            jgen.writeObjectField("supplier", si.getCompanyMasterBySupplierId().getId());
        } catch(Exception e) {}
        try{
            jgen.writeObjectField("shipper", si.getCompanyMasterByShipperId().getId());
        } catch(Exception e) {}
        try{
            jgen.writeObjectField("buyer", si.getCompanyMasterByBuyerId().getId());
        } catch(Exception e) {}
        jgen.writeObjectField("clientRef", si.getClientRef());
        jgen.writeObjectField("supplierRef", si.getSupplierRef());
        jgen.writeObjectField("shipperRef", si.getShipperRef());
        jgen.writeObjectField("buyerRef", si.getBuyerRef());
        try{
            jgen.writeObjectField("origin", si.getOriginMaster().getId());
        } catch(Exception e) {}
        try{
            jgen.writeObjectField("quality", si.getQualityMaster().getId());
        } catch(Exception e) {}
        try{
            jgen.writeObjectField("grade", si.getGradeMaster().getId());
        } catch(Exception e) {}
        jgen.writeEndObject();
    }
}
