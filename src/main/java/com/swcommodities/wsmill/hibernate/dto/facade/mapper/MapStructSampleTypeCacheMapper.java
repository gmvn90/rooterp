/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.domain.model.status.SendingStatus;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ApprovalStatusToInt;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.ApprovalStatusToString;
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
    SendingStatusEnumToStringAndInt.class, ApprovalStatusToInt.class, ApprovalStatusToString.class
})
public interface MapStructSampleTypeCacheMapper {
    
    MapStructSampleTypeCacheMapper INSTANCE = Mappers.getMapper(MapStructSampleTypeCacheMapper.class);
    
    @Mappings({
        @Mapping(source = "client.id", target = "clientInt"),
        @Mapping(source = "client", target = "client"),
        @Mapping(source = "refNumber", target = "sampleRef"),
        @Mapping(source = "createdDate", target = "firstDate"),
        @Mapping(source = "trackingNo", target = "aWBNo"),
        @Mapping(source = "sendingStatus", target = "sentStatus"),
        @Mapping(source = "sendingStatus", target = "sentStatusInt"),
        @Mapping(source = "buyer.id", target = "buyerInt"),
        @Mapping(target = "type", constant = "TYPE"), // TODO: need enum
        @Mapping(target = "typeInt", constant = "2"), // todo: need enum
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "approvalStatus", target = "approvalStatusInt"),
    })
    SampleSentCache toCache(SampleType st);
    
    @InheritConfiguration
    void toCache(SampleType st, @MappingTarget SampleSentCache sc);
    
}
