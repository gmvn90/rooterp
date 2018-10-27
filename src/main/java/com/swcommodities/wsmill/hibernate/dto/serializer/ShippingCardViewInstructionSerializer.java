package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.domain.model.status.RequestStatus;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;

@Component
public class ShippingCardViewInstructionSerializer extends JsonSerializer<ShippingInstruction> {
	public ShippingCardViewInstructionSerializer() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public void serialize(ShippingInstruction value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        
        jgen.writeNumberField("id", value.getId());
        if(value.getUser() != null) {
            jgen.writeNumberField("user", value.getUser().getId());
            jgen.writeObjectField("userStr", value.getUser().getUserName());
        } else {
            jgen.writeNumberField("user", null);
            jgen.writeObjectField("userStr", "");
        }
        if(value.getCompanyMasterByClientId() != null) {
            jgen.writeNumberField("companyMasterByClientId", value.getCompanyMasterByClientId().getId());
        } else {
            jgen.writeNumberField("companyMasterByClientId", null);
        }
        if(value.getCity() != null) {
            jgen.writeNumberField("city", value.getCity().getId());
        } else {
            jgen.writeNumberField("city", null);
        }
        if(value.getCompanyMasterByShipperId() != null) {
            jgen.writeNumberField("companyMasterByShipperId", value.getCompanyMasterByShipperId().getId());
        } else {
            jgen.writeNumberField("companyMasterByShipperId", null);
        }
        if(value.getPortMasterByDischargePortId() != null) {
            jgen.writeNumberField("portMasterByDischargePortId", value.getPortMasterByDischargePortId().getId());
        } else {
            jgen.writeNumberField("portMasterByDischargePortId", null);
        }
        if(value.getCompanyMasterByQualityCertId() != null) {
            jgen.writeNumberField("companyMasterByQualityCertId", value.getCompanyMasterByQualityCertId().getId());
        } else {
            jgen.writeNumberField("companyMasterByQualityCertId", null);
        }
        if(value.getCompanyMasterByWeightCertId() != null) {
            jgen.writeNumberField("companyMasterByWeightCertId", value.getCompanyMasterByWeightCertId().getId());
        } else {
            jgen.writeNumberField("companyMasterByWeightCertId", null);
        }
        if(value.getCompanyMasterByConsigneeId() != null) {
            jgen.writeNumberField("companyMasterByConsigneeId", value.getCompanyMasterByConsigneeId().getId());
        } else {
            jgen.writeNumberField("companyMasterByConsigneeId", null);
        }
        if(value.getCompanyMasterBySupplierId() != null) {
            jgen.writeNumberField("companyMasterBySupplierId", value.getCompanyMasterBySupplierId().getId());
        } else {
            jgen.writeNumberField("companyMasterBySupplierId", null);
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
        if(value.getShippingLineMaster() != null) {
            jgen.writeNumberField("shippingLineMaster", value.getShippingLineMaster().getId());
        } else {
            jgen.writeNumberField("shippingLineMaster", null);
        }
        if(value.getShippingLineCompanyMaster() != null) {
            jgen.writeNumberField("shippingLineCompanyMaster", value.getShippingLineCompanyMaster().getId());
        } else {
            jgen.writeNumberField("shippingLineCompanyMaster", null);
        }
        if(value.getPortMasterByLoadingPortId() != null) {
            jgen.writeNumberField("portMasterByLoadingPortId", value.getPortMasterByLoadingPortId().getId());
        } else {
            jgen.writeNumberField("portMasterByLoadingPortId", null);
        }
        jgen.writeStringField("refNumber", value.getRefNumber());
        jgen.writeStringField("clientRef", value.getClientRef());
        jgen.writeObjectField("date", value.getDate());
        jgen.writeStringField("supplierRef", value.getSupplierRef());
        jgen.writeObjectField("originId", value.getOriginId());
        jgen.writeObjectField("qualityId", value.getQualityId());
        jgen.writeObjectField("contractQuantity", value.getContractQuantity());
        jgen.writeObjectField("quantity", value.getQuantity());
        jgen.writeObjectField("loadDate", value.getLoadDate());
        jgen.writeObjectField("fromDate", value.getFromDate());
        jgen.writeObjectField("toDate", value.getToDate());
        jgen.writeStringField("serviceContractNo", value.getServiceContractNo());
        jgen.writeStringField("feederVessel", value.getFeederVessel());
        jgen.writeObjectField("feederEts", value.getFeederEts());
        jgen.writeObjectField("feederEta", value.getFeederEta());
        jgen.writeStringField("oceanVessel", value.getOceanVessel());
        jgen.writeObjectField("oceanEts", value.getOceanEts());
        jgen.writeObjectField("oceanEta", value.getOceanEta());
        jgen.writeObjectField("containerStatus", value.getContainerStatus());
        jgen.writeStringField("marking", value.getMarking());
        jgen.writeStringField("freight", value.getFreight());
        jgen.writeStringField("lcNo", value.getLcNo());
        jgen.writeObjectField("lcDate", value.getLcDate());
        jgen.writeStringField("invoiceNo", value.getInvoiceNo());
        jgen.writeObjectField("invoiceDate", value.getInvoiceDate());
        jgen.writeStringField("blNumber", value.getBlNumber());
        jgen.writeObjectField("blDate", value.getBlDate());
        jgen.writeStringField("remark", value.getRemark());
        jgen.writeStringField("userRemark", value.getUserRemark());
        jgen.writeObjectField("status", value.getStatus());
        jgen.writeStringField("log", value.getLog());
        if(value.getShippingAdvice() != null) {
            jgen.writeNumberField("shippingAdvice", value.getShippingAdvice().getId());
        } else {
            jgen.writeNumberField("shippingAdvice", null);
        }
        jgen.writeStringField("shipperRef", value.getShipperRef());
        jgen.writeStringField("buyerRef", value.getBuyerRef());
        if(value.getCompanyMasterByBuyerId() != null) {
            jgen.writeNumberField("companyMasterByBuyerId", value.getCompanyMasterByBuyerId().getId());
        } else {
            jgen.writeNumberField("companyMasterByBuyerId", null);
        }
        if(value.getPortMasterByTransitPortId() != null) {
            jgen.writeNumberField("portMasterByTransitPortId", value.getPortMasterByTransitPortId().getId());
        } else {
            jgen.writeNumberField("portMasterByTransitPortId", null);
        }
        if(value.getGradeMasterByAllocationGradeId() != null) {
            jgen.writeNumberField("gradeMasterByAllocationGradeId", value.getGradeMasterByAllocationGradeId().getId());
        } else {
            jgen.writeNumberField("gradeMasterByAllocationGradeId", null);
        }
        jgen.writeStringField("icoNumber", value.getIcoNumber());
        jgen.writeStringField("bookingRef", value.getBookingRef());
        jgen.writeObjectField("closingDate", value.getClosingDate());
        jgen.writeStringField("closingTime", value.getClosingTime());
        jgen.writeStringField("fullContReturn", value.getFullContReturn());
        if (value.getCostToFob() != null) {
            jgen.writeNumberField("costToFob", value.getCostToFob());
        } else {
            jgen.writeNumberField("costToFob", null);
        }
        jgen.writeStringField("clientSiCostListJson", value.getClientSiCostListJson());
        if (value.getTotalCost() != null) {
            jgen.writeNumberField("totalCost", value.getTotalCost());
        } else {
            jgen.writeNumberField("totalCost", null);
        }
        jgen.writeObjectField("weightQualityCertificate", value.getWeightQualityCertificate());
        jgen.writeObjectField("fumigation", value.getFumigation());
        jgen.writeObjectField("costNames", value.getCostNames());
        jgen.writeObjectField("allocatedWeight", value.getAllocatedWeight());
        jgen.writeObjectField("pendingWeight", value.getPendingWeight());
        jgen.writeObjectField("deliverdWeight", value.getDeliverdWeight());
        if(value.getOriginMaster() != null) {
            jgen.writeNumberField("originMaster", value.getOriginMaster().getId());
        } else {
            jgen.writeNumberField("originMaster", null);
        }
        if(value.getQualityMaster() != null) {
            jgen.writeNumberField("qualityMaster", value.getQualityMaster().getId());
        } else {
            jgen.writeNumberField("qualityMaster", null);
        }
        jgen.writeBooleanField("isCompletable", value.isAllWeghtNoteCompleted());
        if(value.getCompanyMasterByShippingLineNewId() != null) {
            jgen.writeNumberField("companyMasterByShippingLineNewId", value.getCompanyMasterByShippingLineNewId().getId());
        } else {
            jgen.writeNumberField("companyMasterByShippingLineNewId", null);
        }
        if (value.getRequestStatusEnum() != null) {
            jgen.writeNumberField("requestStatus", value.getRequestStatusEnum().ordinal());
        } else {
            jgen.writeNumberField("requestStatus", null);
        }
        if(value.getRequestUser() != null) {
            jgen.writeNumberField("requestUser", value.getRequestUser().getId());
            jgen.writeObjectField("requestUserStr", value.getRequestUser().getUserName());
        } else {
            jgen.writeNumberField("requestUser", null);
            jgen.writeObjectField("requestUserStr", "");
        }
        jgen.writeObjectField("requestDate", value.getRequestDate());
        jgen.writeObjectField("updateCompletionDate", value.getUpdateCompletionDate());
        jgen.writeStringField("weightAndQualityCertificateStr", value.getWeightAndQualityCertificateStr());
        jgen.writeStringField("packingStr", value.getPackingStr());
        jgen.writeObjectField("sampleSents", value.getSampleSents());
        jgen.writeObjectField("internalReferenceFileSents", value.getInternalReferenceFileSents());
        jgen.writeObjectField("referenceFileSents", value.getReferenceFileSents());

        jgen.writeEndObject();
    }
	
}
