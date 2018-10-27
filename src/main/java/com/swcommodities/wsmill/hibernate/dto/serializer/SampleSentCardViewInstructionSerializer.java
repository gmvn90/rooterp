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
import com.swcommodities.wsmill.service.InstructionService;

public class SampleSentCardViewInstructionSerializer extends JsonSerializer<SampleSent> {
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
        if(value.getShippingInstructionBySiId() != null) {
            jgen.writeNumberField("shippingInstructionBySiId", value.getShippingInstructionBySiId().getId());
        } else {
            jgen.writeNumberField("shippingInstructionBySiId", null);
        }
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
        if (value.getShippingInstructionBySiId() != null) {
            jgen.writeObjectField("siRef", value.getShippingInstructionBySiId().getRefNumber());
            jgen.writeObjectField("siDate", value.getShippingInstructionBySiId().getDate());
            if (value.getShippingInstructionBySiId().getCompanyMasterBySupplierId() != null) {
                jgen.writeObjectField("siSupplier", value.getShippingInstructionBySiId().getCompanyMasterBySupplierId().getName());
            } else {
                jgen.writeObjectField("siSupplier", "");
            }
            jgen.writeObjectField("siSupplierRef", value.getShippingInstructionBySiId().getSupplierRef());
            if (value.getShippingInstructionBySiId().getCompanyMasterByShipperId() != null) {
                jgen.writeObjectField("siShipper", value.getShippingInstructionBySiId().getCompanyMasterByShipperId().getName());
            } else {
                jgen.writeObjectField("siShipper", "");
            }
            jgen.writeObjectField("siShipperRef", value.getShippingInstructionBySiId().getShipperRef());
            if (value.getShippingInstructionBySiId().getCompanyMasterByBuyerId() != null) {
                jgen.writeObjectField("siBuyer", value.getShippingInstructionBySiId().getCompanyMasterByBuyerId().getName());
            } else {
                jgen.writeObjectField("siBuyer", "");
            }
            jgen.writeObjectField("siBuyerRef", value.getShippingInstructionBySiId().getBuyerRef());
            jgen.writeObjectField("siFromDate", value.getShippingInstructionBySiId().getFromDate());
            jgen.writeObjectField("siToDate", value.getShippingInstructionBySiId().getToDate());
            try {
                jgen.writeObjectField("siOrigin", value.getShippingInstructionBySiId().getOriginMaster().getCountry().getShortName());
            } catch (Exception e) {
                jgen.writeObjectField("siOrigin", "");
            }


            try {
                jgen.writeObjectField("siQualityWeightCertificate", instructionService.getWeightCertificateNameByOption(value.getShippingInstructionBySiId().getWeightQualityCertificate()));
            } catch (Exception e) {
                jgen.writeObjectField("siQualityWeightCertificate", "");
            }

            if (value.getShippingInstructionBySiId().getQualityMaster() != null) {
                jgen.writeObjectField("siQuality", value.getShippingInstructionBySiId().getQualityMaster().getName());
            } else {
                jgen.writeObjectField("siQuality", "");
            }
            try {
                jgen.writeObjectField("siFumigation", instructionService.getFumigationNameByOptionName(value.getShippingInstructionBySiId().getFumigation()));
            }
            catch (Exception e) {
                jgen.writeObjectField("siFumigation","");
            }
            if (value.getShippingInstructionBySiId().getGradeMaster() != null) {
                jgen.writeObjectField("siGrade", value.getShippingInstructionBySiId().getGradeMaster().getName());
            } else {
                jgen.writeObjectField("siGrade", "");
            }
            
            if (value.getType() != null) {
                jgen.writeObjectField("type", value.getType());
            } else {
                jgen.writeObjectField("type", "");
            }
        }

        jgen.writeEndObject();
    }
	
}
