package com.swcommodities.wsmill.application.service;

import com.swcommodities.wsmill.domain.event.si.SIUpdatedEvent;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.swcommodities.wsmill.domain.model.SampleSentItem;
import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.service.ShippingInstructionDomainService;
import com.swcommodities.wsmill.formController.form.SampleSentItemNewForm;
import com.swcommodities.wsmill.formController.form.SampleSentItemUpdateForm;
import com.swcommodities.wsmill.hibernate.dto.Claim;
import com.swcommodities.wsmill.hibernate.dto.FileSent;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.SampleSentItemAssembler;
import com.swcommodities.wsmill.infrastructure.StorageService;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SISampleSentService {

    private ShippingInstructionRepository shippingInstructionRepository;
    private ShippingInstructionDomainService domainService;
    private ApplicationEventPublisher applicationEventPublisher;
    private StorageService storageService;

    public SISampleSentService(ShippingInstructionRepository shippingInstructionRepository, ShippingInstructionDomainService domainService, 
        ApplicationEventPublisher applicationEventPublisher, StorageService storageService) {
        Assert.notNull(shippingInstructionRepository);
        this.shippingInstructionRepository = shippingInstructionRepository;
        this.domainService = domainService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.storageService = storageService;
    }

    @Transactional
    public void addSampleSentItem(String siRef, String ssRef, SampleSentItemNewForm form) {
        SampleSentItemAssembler assembler = new SampleSentItemAssembler();
        ShippingInstruction shippingInstruction = shippingInstructionRepository
            .findFirstByRefNumber(siRef);
        SampleSent sampleSent = shippingInstruction.findChildSampleSent(ssRef);
        sampleSent.addSampleSentItem(assembler.fromNewDto(form));
    }

    @Transactional
    public void updateSampleSentItem(String siRef, String ssRef, SampleSentItemUpdateForm form) {
        SampleSentItemAssembler assembler = new SampleSentItemAssembler();
        ShippingInstruction shippingInstruction = shippingInstructionRepository
            .findFirstByRefNumber(siRef);
        SampleSent sampleSent = shippingInstruction.findChildSampleSent(ssRef);
        SampleSentItem item = sampleSent.findChildSampleSentItem(form.getSampleSentItemId());
        assembler.fromUpdateDto(form, item);
    }
    
    @org.springframework.transaction.annotation.Transactional
    public void addSampleSentDocument(String siRef, String ssRef, MultipartFile uploadfile, User user) {
        ShippingInstruction shippingInstruction = shippingInstructionRepository.findFirstByRefNumber(siRef);
        SampleSent sampleSent = shippingInstruction.findChildSampleSent(ssRef);
        Optional<FileSent> fileSent = storageService.saveFileSentFromFile(uploadfile, "", "", user);
        if (fileSent.isPresent()) {
            sampleSent.addDocument(fileSent.get());
        } else {
            System.out.println("No file " + uploadfile.getName());
        }
        shippingInstructionRepository.save(shippingInstruction);
    }
    
    @Transactional
    public void rejectSampleSent(ShippingInstruction shippingInstruction, SampleSent sampleSent, User user) {
        SampleSent newOne = domainService.rejectSampleSent(shippingInstruction, sampleSent, user);
        //applicationEventPublisher.publishEvent(new SIUpdatedEvent(shippingInstruction.getRefNumber()));
    }

}
