package com.swcommodities.wsmill.controller;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swcommodities.wsmill.hibernate.dto.FileUpload;

/**
 * Created by dunguyen on 9/23/16.
 */

import com.swcommodities.wsmill.infrastructure.StorageService;

@RestController
public class FileUploadController {
    
    @Autowired private StorageService storageService;
    @Autowired(required = true) private ServletContext context;
    @Resource(name = "configConfigurer") Properties configConfigurer;

    @RequestMapping(value = "/uploadFile.json", method = RequestMethod.POST)
    public ResponseEntity<FileUpload> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {
        FileUpload fileUpload;
        try {
            // Get the filename and build the local file path
            String filename = uploadfile.getOriginalFilename();
            String baseDir = configConfigurer.getProperty("base_dir");
            storageService.setBaseDir(baseDir);
            fileUpload = storageService.save(uploadfile.getBytes(), filename);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fileUpload, HttpStatus.OK);
    } // method uploadFile
}