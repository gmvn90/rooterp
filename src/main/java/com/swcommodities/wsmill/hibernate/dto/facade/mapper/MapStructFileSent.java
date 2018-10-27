/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author macOS
 */
@Mapper
public interface MapStructFileSent {
    
    MapStructFileSent INSTANCE = Mappers.getMapper(MapStructFileSent.class);
    
    @Mappings({
        @Mapping(target = "url", source = "fileUpload.url"),
        @Mapping(target = "originalName", source = "fileUpload.originalName")
    })
    FileSentDTO fromObject(FileSent fs);
    
}
