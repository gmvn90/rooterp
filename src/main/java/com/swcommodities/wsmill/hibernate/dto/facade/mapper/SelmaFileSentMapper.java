/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.mapper;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.converter.IntegerToLong;

import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;

/**
 *
 * @author macOS
 */

@Mapper(withIgnoreMissing = IgnoreMissing.ALL, withCustomFields = {
    @Field({"url", "fileUpload.url"}),
    @Field({"com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO.originalName", "com.swcommodities.wsmill.hibernate.dto.FileSent.fileUpload.originalName"})
}, withCustom = {
    IntegerToLong.class
})
public interface SelmaFileSentMapper {
    public FileSentDTO fromObject(FileSent fileSent);
}
