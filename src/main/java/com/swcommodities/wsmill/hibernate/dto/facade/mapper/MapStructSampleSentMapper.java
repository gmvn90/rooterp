/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.formController.form.NewSampleSentForm;
import com.swcommodities.wsmill.formController.form.UpdateSampleSentForm;
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author macOS
 */

@Mapper
public interface MapStructSampleSentMapper {
    
    MapStructSampleSentMapper INSTANCE = Mappers.getMapper(MapStructSampleSentMapper.class);
    
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "courierMaster", source = "courierId"),
    })
    SampleSent fromDto(NewSampleSentForm form);
    
    default CourierMaster fromCourierId(int id) {
        return new CourierMaster(id);
    }
    
    @Mappings({
        @Mapping(target = "user", ignore = true),
        @Mapping(target = "createdDate", ignore = true),
        @Mapping(target = "updatedDate", ignore = true),
        @Mapping(target = "refNumber", ignore = true),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "courierMaster", source = "courierId"),
        @Mapping(target = "documents", ignore = true)
    })
    void fromDto(UpdateSampleSentForm form, @MappingTarget SampleSent ss);
    
    default Set<FileSentDTO> map(Set<FileSent> fss) {
        return fss.stream().map(fs -> MapStructFileSent.INSTANCE.fromObject(fs)).collect(Collectors.toSet());
    }
    
    @Mappings({
        @Mapping(target = "user", source = "user.userName"),
        @Mapping(target = "siRef", source = "shippingInstructionBySiId.refNumber"),
        @Mapping(target = "courierId", source = "courierMaster.id")
    })
    UpdateSampleSentForm fromObject(SampleSent ss);
    
    
}
