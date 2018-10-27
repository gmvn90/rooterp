/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;



import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.FileUpload;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.FileSentRepository;

@RestController
public class FileSentController {
    
    static Logger log = Logger.getLogger("FileSentController");
    @Autowired private StorageService storageService;
    @Autowired private FileSentRepository fileSentRepository;
    @Autowired(required = true) private ServletContext context;
    @Autowired(required = true) private HttpServletRequest request;
    @Resource(name = "configConfigurer") Properties configConfigurer;

    @RequestMapping(value = "/uploadFileSent.json", method = RequestMethod.POST)
    public ResponseEntity<FileSent> uploadFile(
            @RequestParam("file") MultipartFile uploadfile,
            @RequestParam("name") String name,
            @RequestParam("emails") String emails) {
        FileUpload fileUpload;
        FileSent fileSent = new FileSent();
        try {
            
            // Get the filename and build the local file path
            String filename = uploadfile.getOriginalFilename();
            fileUpload = storageService.save(uploadfile.getBytes(), filename);
            fileSent.setName(name);
            fileSent.setFileUpload(fileUpload);
            fileSent.setEmails(emails);
            try {
                fileSent.setUpdater(((User) request.getSession().getAttribute("user")).getUserName());
            } catch(Exception e1) {
                System.out.println("com.swcommodities.wsmill.controller.FileSentController.uploadFile()");
            }
            
            fileSentRepository.save(fileSent);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fileSent, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/updateFileSent.json", method = RequestMethod.POST)
    public ResponseEntity<FileSent> updateFile(
            @RequestParam("remindName") String remindName, @RequestParam("id") Long id) {
        FileSent fileSent = fileSentRepository.findOne(id);
        fileSent.setRemindName(remindName);
        fileSentRepository.save(fileSent);
        return new ResponseEntity<>(fileSent, HttpStatus.CREATED);
    }
}
