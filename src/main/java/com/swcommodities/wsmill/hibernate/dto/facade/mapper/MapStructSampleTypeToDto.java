/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleTypeDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CompanyMasterToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.CourierToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.GradeToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.OriginToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.QualityToId;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.UserToName;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author macOS
 */

@Mapper(uses = {CompanyMasterToId.class, CourierToId.class, UserToName.class, OriginToId.class, QualityToId.class, GradeToId.class})
public interface MapStructSampleTypeToDto {
    
    MapStructSampleTypeToDto INSTANCE = Mappers.getMapper(MapStructSampleTypeToDto.class);
    
    SampleTypeDTO toDto(SampleType st);
    
    default Set<FileSentDTO> map(Set<FileSent> fss) {
        return fss.stream().map(fs -> MapStructFileSent.INSTANCE.fromObject(fs)).collect(Collectors.toSet());
    }
}
