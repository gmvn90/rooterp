/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ApprovalStatusToString;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CourierMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.OriginMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.QualityMasterToName;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.SendingStatusEnumToStringAndInt;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author macOS
 */

@Mapper(uses = {CompanyMasterToName.class, OriginMasterToName.class, QualityMasterToName.class, GradeMasterToName.class, CourierMasterToName.class,
    SendingStatusEnumToStringAndInt.class, CompanyMasterToId.class, ApprovalStatusToString.class})
public interface MapStructCache {
    
    MapStructCache INSTANCE = Mappers.getMapper(MapStructCache.class);
    
    @Mappings({
        @Mapping(target = "sampleSent", source = "ss"),
        @Mapping(target = "sampleRef", source = "refNumber"),
        @Mapping(target = "siRef", source = "shippingInstructionBySiId.refNumber"),
        @Mapping(target = "firstDate", source = "shippingInstructionBySiId.fromDate"),
        @Mapping(target = "courier", source = "courierMaster"),
        @Mapping(target = "aWBNo", source = "trackingNo"),
        @Mapping(target = "sentStatus", source = "sendingStatusEnum"),
        @Mapping(target = "sentStatusInt", source = "sendingStatusEnum"),
        @Mapping(target = "clientInt", source = "shippingInstructionBySiId.companyMasterByClientId.id"),
        @Mapping(target = "approvalStatusInt", source = "approvalStatus"),
        @Mapping(target = "buyerInt", source = "shippingInstructionBySiId.companyMasterByBuyerId.id"),
        @Mapping(target = "typeInt", constant = "1"),
    })
    SampleSentCache toCache(SampleSent ss);
    
    default SampleSent sampleSentToSampleSent(SampleSent ss) {
        return ss;
    }
    
    @InheritConfiguration
    void toCache(SampleSent ss, @MappingTarget SampleSentCache ssc);
    
}
