/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleTypeDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CourierToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.OriginToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.QualityToId;
import javassist.tools.reflect.Sample;
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

@Mapper(uses = {CompanyMasterToId.class, CourierToId.class, OriginToId.class, QualityToId.class, GradeToId.class})
public interface MapStructDtoToSampleType {
    
    MapStructDtoToSampleType INSTANCE = Mappers.getMapper(MapStructDtoToSampleType.class);
    
    @Mappings({
        @Mapping(target = "user", ignore = true),
        @Mapping(target = "saveSendingStatusUser", ignore = true),
        @Mapping(target = "saveApprovalStatusUser", ignore = true),
        @Mapping(target = "saveRemarkUser", ignore = true),
        @Mapping(target = "saveSendingStatusDate", ignore = true),
        @Mapping(target = "saveApprovalStatusDate", ignore = true),
        @Mapping(target = "sendingStatus", ignore = true),
        @Mapping(target = "approvalStatus", ignore = true),
        @Mapping(target = "refNumber", ignore = true),
        @Mapping(target = "typeSampleRef", ignore = true),
        
    }) 
    SampleType fromDto(SampleTypeDTO dto);
    
    @InheritConfiguration
    @Mappings({
        
        @Mapping(target = "createdDate", ignore = true),
    }) 
    void fromDto(SampleTypeDTO dto, @MappingTarget SampleType st);
}
