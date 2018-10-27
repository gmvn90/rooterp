package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;

@Component
public class DeliveryCardViewInstructionSerializer extends JsonSerializer<DeliveryInstruction> {
	public DeliveryCardViewInstructionSerializer() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public void serialize(DeliveryInstruction value, JsonGenerator jgen, SerializerProvider provider)
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
        
        if(value.getCompanyMasterByWeightControllerId() != null) {
        	jgen.writeNumberField("companyMasterByWeightControllerId", value.getCompanyMasterByWeightControllerId().getId());
        } else {
        	jgen.writeNumberField("companyMasterByWeightControllerId", null);
        }
        
        if(value.getCompanyMasterBySupplierId() != null) {
        	jgen.writeNumberField("companyMasterBySupplierId", value.getCompanyMasterBySupplierId().getId());
        } else {
        	jgen.writeNumberField("companyMasterBySupplierId", null);
        }
        
        if(value.getCompanyMasterByControllerId() != null) {
        	jgen.writeNumberField("companyMasterByControllerId", value.getCompanyMasterByControllerId().getId());
        } else {
        	jgen.writeNumberField("companyMasterByControllerId", null);
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
        
        if(value.getCompanyMasterByPledger() != null) {
        	jgen.writeNumberField("companyMasterByPledger", value.getCompanyMasterByPledger().getId());
        } else {
        	jgen.writeNumberField("companyMasterByPledger", null);
        }
        
        if(value.getWarehouse() != null) {
        	jgen.writeNumberField("warehouse", value.getWarehouse().getId());
        } else {
        	jgen.writeNumberField("warehouse", null);
        }
        
        if(value.getCompanyMasterByQualityControllerId() != null) {
        	jgen.writeObjectField("companyMasterByQualityControllerId", value.getCompanyMasterByQualityControllerId().getId());
        } else {
        	jgen.writeNumberField("companyMasterByQualityControllerId", null);
        }
        
        jgen.writeStringField("refNumber", value.getRefNumber());
        jgen.writeStringField("clientRef", value.getClientRef());
        jgen.writeObjectField("date", value.getDate());
        jgen.writeStringField("supplierRef", value.getSupplierRef());
        jgen.writeNumberField("tons", value.getTons());
        jgen.writeObjectField("kgPerBag", value.getKgPerBag());
        jgen.writeObjectField("noOfBags", value.getNoOfBags());
        jgen.writeObjectField("deliveryDate", value.getDeliveryDate());
        jgen.writeObjectField("fromTime", value.getFromTime());
        jgen.writeObjectField("toTime", value.getToTime());
        jgen.writeObjectField("markingOnBags", value.getMarkingOnBags());
        jgen.writeStringField("remark", value.getRemark());
        jgen.writeObjectField("status", value.getStatus());
        jgen.writeStringField("supllierRef", value.getSupllierRef());
        if(value.getOriginMaster() != null) {
        	jgen.writeNumberField("originMaster", value.getOriginMaster().getId());
        } else {
        	jgen.writeNumberField("originMaster", null);
        }
        
        if(value.getLocation() != null) {
        	jgen.writeNumberField("location", value.getLocation().getId());
        } else {
        	jgen.writeNumberField("location", null);
        }
        
        if(value.getLocationMaster() != null) {
        	jgen.writeNumberField("locationMaster", value.getLocationMaster().getId());
        } else {
        	jgen.writeNumberField("locationMaster", null);
        }
        
        if(value.getQualificationCompany() != null) {
        	jgen.writeNumberField("qualificationCompany", value.getQualificationCompany().getId());
        } else {
        	jgen.writeNumberField("qualificationCompany", null);
        }
        
        jgen.writeObjectField("firstDate", value.getFirstDate());
        jgen.writeObjectField("lastDate", value.getLastDate());
        
        if(value.getUserByRequestUserId() != null) {
        	jgen.writeNumberField("userByRequestUserId", value.getUserByRequestUserId().getId());
        } else {
        	jgen.writeNumberField("userByRequestUserId", null);
        }
        
        if(value.getUserByUpdateUserId() != null) {
        	jgen.writeNumberField("userByUpdateUserId", value.getUserByUpdateUserId().getId());
        } else {
        	jgen.writeNumberField("userByUpdateUserId", null);
        }
        
        jgen.writeObjectField("updateDate", value.getUpdateDate());
        jgen.writeObjectField("requestDate", value.getRequestDate());
        jgen.writeObjectField("requestStatus", value.getRequestStatus());
        jgen.writeObjectField("userUpdateDate", value.getUserUpdateDate());
        jgen.writeObjectField("deleteStatus", value.getDeleteStatus());
        jgen.writeStringField("requestRemark", value.getRequestRemark());
        if(value.getQualityMaster() != null) {
        	jgen.writeNumberField("qualityMaster", value.getQualityMaster().getId());
        } else {
        	jgen.writeNumberField("qualityMaster", null);
        }
        jgen.writeObjectField("isCompletable", value.getCompletable());
        jgen.writeObjectField("deliverd", value.getDeliverd());
        jgen.writeObjectField("pending", value.getPending());
        
        if (value.getUser() != null) {
            jgen.writeObjectField("userStr", value.getUser().getUserName());
        } else {
            jgen.writeObjectField("userStr", "");
        }

        if (value.getUserByRequestUserId() != null) {
            jgen.writeObjectField("userByRequestUserIdStr", value.getUserByRequestUserId().getUserName());
        } else {
            jgen.writeObjectField("userByRequestUserIdStr", "");
        }

        if (value.getUserByUpdateUserId() != null) {
            jgen.writeObjectField("userByUpdateUserIdStr", value.getUserByUpdateUserId().getUserName());
        } else {
            jgen.writeObjectField("userByUpdateUserIdStr", "");
        }

        jgen.writeEndObject();
    }
	
}
