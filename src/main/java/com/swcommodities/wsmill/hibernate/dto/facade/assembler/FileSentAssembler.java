/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.assembler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.facade.FileSentDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.mapper.SelmaFileSentMapper;

import fr.xebia.extras.selma.Selma;

/**
 *
 * @author macOS
 */
public class FileSentAssembler {
    
    public List<FileSentDTO> fromObject(Set<FileSent> fileSents) {
        SelmaFileSentMapper mapper = Selma.builder(SelmaFileSentMapper.class).build();
        return fileSents.stream().map(fs -> mapper.fromObject(fs)).collect(Collectors.toList());
    }
    
}
