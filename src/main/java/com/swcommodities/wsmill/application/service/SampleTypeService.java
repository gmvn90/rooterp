/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.domain.model.RefNumberType;
import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.policy.SampleSentFullInfoPolicy;
import com.swcommodities.wsmill.domain.service.SampleTypeDomainService;
import com.swcommodities.wsmill.formController.form.ApprovalStatusForm;
import com.swcommodities.wsmill.formController.form.SendingStatusForm;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.SampleType;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.SampleTypeDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.SampleTypeAssembler;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.SampleTypeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author macOS
 */

@Service
public class SampleTypeService {
    
    @Autowired SampleTypeRepository sampleTypeRepository;
    @Autowired SampleTypeDomainService sampleTypeDomainService;
    @Autowired private StorageService storageService;
    private SampleSentFullInfoPolicy sampleSentFullInfoPolicy = new SampleSentFullInfoPolicy();
    
    @Transactional
    public SampleType addSampleType(SampleTypeDTO dto, User user) {
        SampleTypeAssembler assembler = new SampleTypeAssembler();
        SampleType st = assembler.fromDto(dto);
        st = sampleTypeDomainService.save(st, user);
        return st;
    }
    
    @Transactional
    public SampleType updateSampleType(SampleType sampleType, SampleTypeDTO dto, User user) {
        SampleTypeAssembler assembler = new SampleTypeAssembler();
        SampleType st = assembler.fromDto(dto, sampleType);
        st = sampleTypeDomainService.update(st, user);
        return st;
    }
    
    @Transactional(rollbackFor = {ApplicationException.class})
    public SampleType updateApprovalStatus(ApprovalStatusForm approvalStatusForm, User user) throws ApplicationException {
        SampleType st = sampleTypeRepository.findOne(approvalStatusForm.getRefNumber()); // refnumber is is id in case of st
        if(approvalStatusForm.getStatus() == ApprovalStatus.APPROVED) {
            if(! sampleSentFullInfoPolicy.isAllowedToApprove(st)) {
                throw new ApplicationException("Sample type policy: document, recipient and lotref can not be empty");
            }
        }
        st.saveApprovalStatus(user, approvalStatusForm.getStatus());
        sampleTypeRepository.save(st);
        return st;
    }
    
    @Transactional
    public SampleType updateSendingStatus(SendingStatusForm sendingStatusForm, User user) {
        SampleType st = sampleTypeRepository.findOne(sendingStatusForm.getRefNumber()); // refnumber is is id in case of st
        st.saveSendingStatus(user, sendingStatusForm.getStatus());
        sampleTypeRepository.save(st);
        return st;
    }
    
    @Transactional
    public SampleType updateOrAddSampleType(SampleTypeDTO dto, User user) {
        if(dto.getId() != null && ! dto.getId().equals("")) {
            SampleType st = sampleTypeRepository.findOne(dto.getId());
            return updateSampleType(st, dto, user);
        }
        return addSampleType(dto, user);
    }
    
    @org.springframework.transaction.annotation.Transactional
    public void addSampleTypeDocument(String stRef, MultipartFile uploadfile, User user) {
        SampleType sampleType = sampleTypeRepository.findByRefNumber(stRef);
        Optional<FileSent> fileSent = storageService.saveFileSentFromFile(uploadfile, "", "", user);
        if (fileSent.isPresent()) {
            sampleType.addDocument(fileSent.get());
        } else {
            System.out.println("No file " + uploadfile.getName());
        }
        sampleTypeRepository.save(sampleType);
    }
    
    public SampleTypeDTO fromObjectId(String id) {
        SampleTypeAssembler assembler = new SampleTypeAssembler();
        if(RefNumberType.getType(id) == RefNumberType.SampleSentType) {
            return assembler.fromObject(sampleTypeRepository.findByRefNumber(id));
        }
        return assembler.fromObject(sampleTypeRepository.findOne(id));
    }
    
    public SampleType fromId(String id) {
        return sampleTypeRepository.findOne(id);
    }
    
}
