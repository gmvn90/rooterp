/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.FileUpload;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.FileSentRepository;

/**
 *
 * @author macOS
 */

@Service
public class FileUploadService {
    
    @Autowired StorageService storageService;
    @Autowired FileSentRepository fileSentRepository;
    @Resource(name = "configConfigurer") Properties configConfigurer;
    
    public FileSent createFileSentFromBaseDir(String uploader, String originName) throws IOException {
        String baseStaticHost = configConfigurer.getProperty("base_static_host");
        String fullPathWithoutName = configConfigurer.getProperty("base_dir");
        Validate.notNull(baseStaticHost);
        Validate.notNull(fullPathWithoutName);
        FileUpload fileUpload = storageService.saveFromLocalFile(fullPathWithoutName, originName, baseStaticHost);
        FileSent fileSent = new FileSent(fileUpload, uploader, "Loading report");
        return fileSentRepository.save(fileSent);
    }
    
}
