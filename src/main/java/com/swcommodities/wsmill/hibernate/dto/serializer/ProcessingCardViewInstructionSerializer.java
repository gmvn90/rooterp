package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;

@Component
public class ProcessingCardViewInstructionSerializer extends JsonSerializer<ProcessingInstruction> {
	public ProcessingCardViewInstructionSerializer() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public void serialize(ProcessingInstruction value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        
        jgen.writeNumberField("id", value.getId());
        if(value.getUser() != null) {
        	jgen.writeNumberField("user", value.getUser().getId());
        } else {
        	jgen.writeNumberField("user", null);
        }
        if(value.getCompanyMasterByClientId() != null) {
        	jgen.writeNumberField("companyMasterByClientId", value.getCompanyMasterByClientId().getId());
        } else {
        	jgen.writeNumberField("companyMasterByClientId", null);
        }
        if(value.getGradeMaster() != null) {
            jgen.writeNumberField("gradeMaster", value.getGradeMaster().getId());
        } else {
            jgen.writeNumberField("gradeMaster", null);
        }
        if(value.getPackingMaster() != null) {
            jgen.writeNumberField("packingMaster", value.getPackingMaster().getId());
        } else {
            jgen.writeNumberField("packingMaster", null);
        }
        jgen.writeObjectField("originId", value.getOriginId());
        jgen.writeObjectField("qualityId", value.getQualityId());
        jgen.writeStringField("refNumber", value.getRefNumber());
        jgen.writeObjectField("createdDate", value.getCreatedDate());
        jgen.writeStringField("clientRef", value.getClientRef());
        jgen.writeObjectField("quantity", value.getQuantity());
        jgen.writeObjectField("fromDate", value.getFromDate());
        jgen.writeObjectField("toDate", value.getToDate());
        jgen.writeStringField("remark", value.getRemark());
        jgen.writeObjectField("status", value.getStatus());
        jgen.writeStringField("log", value.getLog());
        jgen.writeObjectField("requestStatus",value.getRequestStatus());
        jgen.writeObjectField("requestStatusDate", value.getRequestStatusDate());
        jgen.writeObjectField("completionStatusDate", value.getCompletionStatusDate());
        jgen.writeStringField("requestRemark", value.getRequestRemark());
        jgen.writeObjectField("creditDate", value.getCreditDate());
        if(value.getUserByUpdateRequestUserId() != null) {
            jgen.writeNumberField("userByUpdateRequestUserId", value.getUserByUpdateRequestUserId().getId());
        } else {
            jgen.writeNumberField("userByUpdateRequestUserId", null);
        }
        if(value.getUserByUpdateCompletionUserId() != null) {
            jgen.writeNumberField("userByUpdateCompletionUserId", value.getUserByUpdateCompletionUserId().getId());
        } else {
            jgen.writeNumberField("userByUpdateCompletionUserId", null);
        }
        jgen.writeObjectField("updatedDate", value.getUpdatedDate());
        if(value.getPiType() != null) {
            jgen.writeNumberField("piType", value.getPiType().getId());
        } else {
            jgen.writeNumberField("piType", null);
        }
        jgen.writeObjectField("allocatedWeight", value.getAllocatedWeight());
        jgen.writeObjectField("inProcessWeight", value.getInProcessWeight());
        jgen.writeObjectField("exProcessWeight", value.getExProcessWeight());
        try {
            jgen.writeObjectField("pendingWeight", value.getInProcessWeight() - value.getExProcessWeight());
        } catch (Exception e) {
            jgen.writeObjectField("pendingWeight", 0);
        }
        
        jgen.writeObjectField("isCompletable", value.getCompletable());

        if (value.getUser() != null) {
            jgen.writeObjectField("userStr", value.getUser().getUserName());
        } else {
            jgen.writeObjectField("userStr", "");
        }

        if (value.getUserByUpdateRequestUserId() != null) {
            jgen.writeObjectField("userByUpdateRequestUserIdStr", value.getUserByUpdateRequestUserId().getUserName());
        } else {
            jgen.writeObjectField("userByUpdateRequestUserIdStr", "");
        }

        if (value.getUserByUpdateCompletionUserId() != null) {
            jgen.writeObjectField("userByUpdateCompletionUserIdStr", value.getUserByUpdateCompletionUserId().getUserName());
        } else {
            jgen.writeObjectField("userByUpdateCompletionUserIdStr", "");
        }

        jgen.writeEndObject();
    }
	
}
