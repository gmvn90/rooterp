/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.ShippingInstructionAdviceDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.ShippingInstructionSummaryDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ContainerStatusToName;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

/**
 *
 * @author macOS
 */

@Mapper(withIgnoreMissing = IgnoreMissing.DESTINATION, withIgnoreFields = {"siNo"}, withCustomFields = {
    @Field({"companyMasterByClientId.name", "client"}),
        @Field({"companyMasterBySupplierId.name", "supplier"}),
        @Field({"companyMasterByShipperId.name", "shipper"}),
        @Field({"companyMasterByBuyerId.name", "buyer"}),
        @Field({"ShippingInstruction.weightQualityCertificateCompanyName", "ShippingInstructionSummaryDTO.weightQualityCertificate"}),
        @Field({"ShippingInstruction.fumigationCompanyName", "ShippingInstructionSummaryDTO.fumigation"}),
        
})
public interface SelmaShippingSummaryMapper {
    @Maps(
        withCustomFields = {
            @Field({"companyMasterByBuyerId.name", "buyer"}),
            @Field({"packingStr", "packing"}),
            @Field({"shippingLineCompanyMaster.name", "shippingLineCompanyMaster"}),
            @Field({"qualityMaster.name", "quality"}),
            @Field({"portMasterByDischargePortId.name", "destination"}),
            @Field({"shippingWeightNoteTotalInfo", "shippingWeightNoteTotalInfo"}),
            @Field({"gradeMaster.name", "grade"}),
            @Field(value = "containerStatus", withCustom = ContainerStatusToName.class),
            @Field({"ShippingInstruction.originMaster.country.shortName", "ShippingInstructionSummaryDTO.origin"}),
        }
    )
    public ShippingInstructionSummaryDTO asShippingInstructionSummaryDTO(ShippingInstruction shippingInstruction);
    
    @Maps(withCustomFields = {
        @Field({"companyMasterByBuyerId.name", "buyer"}),
        @Field({"packingStr", "packing"}),
        @Field({"shippingLineCompanyMaster.name", "shippingLineCompanyMaster"}),
        @Field({"qualityMaster.name", "quality"}),
        @Field({"portMasterByDischargePortId.name", "destination"}),
        @Field({"shippingWeightNoteTotalInfo", "shippingWeightNoteTotalInfo"}),
        
        @Field({"serviceContractNo", "saleNo"}),
        @Field({"gradeMaster.name", "grade"}),
        @Field(value = "containerStatus", withCustom = ContainerStatusToName.class),
        
        @Field({"companyMasterByConsigneeId.name", "consignee"}),
        @Field({"portMasterByLoadingPortId.name", "loadingPort"}),
        @Field({"originMaster.country.shortName", "origin"}),
        @Field({"portMasterByTransitPortId.name", "transitPort"}),
        @Field({"shippingAdvice.refNumber", "shippingAdviceRefNumber"}),
        @Field({"shippingAdvice.date", "shippingAdviceDate"}),
    })
    public ShippingInstructionAdviceDTO asShippingInstructionAdviceDTO(ShippingInstruction shippingInstruction);
}
