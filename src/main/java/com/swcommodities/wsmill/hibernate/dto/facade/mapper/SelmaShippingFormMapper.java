/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.formController.form.ShippingInstructionForm;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompletionStatusToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompletionStatusToString;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.IntegerToByte;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.IntegerToLong;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ListCustomCostToCustomCost;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ListNotifyPartyToDto;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ListSampleSentsToDto;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.OriginToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.PortToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.QualityToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.RequestStatusEnumToString;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ShippingLineToId;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

/**
 *
 * @author macOS
 */
@Mapper(withIgnoreMissing = IgnoreMissing.ALL,
    withCustomFields = {
        @Field({"client", "companyMasterByClientId"}),
        @Field({"supplier", "companyMasterBySupplierId"}),
        @Field({"shipper", "companyMasterByShipperId"}),
        @Field({"buyer", "companyMasterByBuyerId"}),
        @Field({"consignee", "companyMasterByConsigneeId"}),
        @Field({"shippingLine", "shippingLineCompanyMaster"}),
        @Field({"loadingPort", "portMasterByLoadingPortId"}),
        @Field({"transitPort", "portMasterByTransitPortId"}),
        @Field({"dischargePort", "portMasterByDischargePortId"}),
        @Field({"origin", "originMaster"}),
        @Field({"quality", "qualityMaster"}),
        @Field({"allocationGrade", "gradeMasterByAllocationGradeId"}),
        @Field({"grade", "gradeMaster"}),
        @Field({"totalTons", "quantity"}),
        @Field({"packingCostCategory", "shippingCost.packingCostCategory"}),
        @Field({"markingCategory", "shippingCost.markingCategory"}),
        @Field({"fumigationDetailCost", "shippingCost.fumigationDetailCost"}),
        @Field({"fumigationInStore", "shippingCost.fumigationInStore"}),
        @Field({"certificateCost", "shippingCost.certificateCost"}),
        @Field({"tonPerContainer", "shippingCost.tonPerContainer"}),
        @Field({"numberOfContainer", "shippingCost.numberOfContainer"}),
        @Field({"optionalDocumentNumber", "shippingCost.optionalDocumentNumber"}),
        @Field({"documentCosts", "shippingCost.documentCosts"}),
        @Field({"packingItemCosts", "shippingCost.packingItemCosts"}),
        @Field({"fumigationProviderCategory", "shippingCost.fumigationProviderCategory"}),  
    }, 
    withCustom = {IntegerToByte.class, IntegerToLong.class, CompanyMasterToId.class, ShippingLineToId.class, PortToId.class, GradeToId.class , QualityToId.class, 
        OriginToId.class})
public interface SelmaShippingFormMapper {
    @Maps(withIgnoreFields = {"refNumber", "requestDate", "user", "date", "status", "requestStatus", 
    		"requestStatusEnum", "requestUser", "sampleSents", "notifyParties", "completionStatus", "sICustomCosts", "updateUser", 
    		"shipmentStatusUpdateDate", "shipmentStatusUpdateUser", "updateCompletionDate", "completionStatus", "completionStatusUser"
    		}, 
        withCustomFields = {})
    ShippingInstruction fromDto(ShippingInstructionForm form, ShippingInstruction shippingInstruction);
    
    @Maps(withCustomFields = {
        @Field({"shipmentStatusUpdateUser.userName", "shipmentStatusUpdateUser"}),
        @Field({"user.userName", "user"}),
        @Field({"completionStatusUser.userName", "completionStatusUser"}),
        @Field({"loadingAndTransportCategory", "shippingCost.loadingAndTransportCategory"}),
        @Field({"allMarkingCategory", "shippingCost.allMarkingCategory"}),
        @Field({"certificateCategory", "shippingCost.certificateCategory"}),
        @Field({"documentCategory", "shippingCost.documentCategory"}),
        @Field({"fumigationCategory", "shippingCost.fumigationCategory"}),
        @Field({"optionalCategory", "shippingCost.optionalCategory"}),
        @Field(value = {"status"}, withCustom = CompletionStatusToName.class),
        @Field(value = {"sampleSents"}, withCustom = ListSampleSentsToDto.class),
        @Field(value = {"notifyParties"}, withCustom = ListNotifyPartyToDto.class),
        @Field(value = {"requestStatusEnum"}, withCustom = RequestStatusEnumToString.class),
        @Field(value = {"completionStatus"}, withCustom = CompletionStatusToString.class),
        @Field(value = {"sICustomCosts", "shippingCost.sICustomCosts"}, withCustom = ListCustomCostToCustomCost.class),
    })
    ShippingInstructionForm toForm(ShippingInstruction shippingInstruction);
}
