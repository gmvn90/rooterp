/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.domain.model.exceptions.ActionNotPermitted;
import com.swcommodities.wsmill.domain.model.exceptions.DomainException;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import com.swcommodities.wsmill.domain.model.status.RequestStatus;
import com.swcommodities.wsmill.domain.service.PIDomainService;
import com.swcommodities.wsmill.formController.form.CompletionStatusForm;
import com.swcommodities.wsmill.formController.form.NewPIForm;
import com.swcommodities.wsmill.formController.form.RequestStatusForm;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.PIAssembler;
import com.swcommodities.wsmill.hibernate.specification.CompletedInstruction;
import com.swcommodities.wsmill.hibernate.specification.DateBetweenDates;
import com.swcommodities.wsmill.hibernate.specification.ItemNotDeleted;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.utils.Request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author macOS
 */

@Service("application_piService")
public class PIService {
    
    @Autowired ProcessingInstructionRepository processingInstructionRepository;
    @Autowired PIDomainService pIDomainService;
    
    @Transactional
    public String updateCompletionStatus(CompletionStatusForm form, User user) throws ApplicationException {
        ProcessingInstruction processingInstruction = processingInstructionRepository.findFirstByRefNumber(form.getRefNumber());
        
        try {
            processingInstruction.updateCompletionStatus(form.getStatus(), user);
        } catch (ActionNotPermitted ex) {
            throw new ApplicationException(ex.getMessage());
        }
        processingInstructionRepository.save(processingInstruction);
        return processingInstruction.getRefNumber();
    }
    
    @Transactional
    public String updateRequestStatus(RequestStatusForm form, User user) throws ApplicationException {
    		ProcessingInstruction processingInstruction = processingInstructionRepository.findFirstByRefNumber(form.getRefNumber());
    		try {
    			processingInstruction.updateRequestStatus(form.getStatus(), user);
		} catch (DomainException e) {
			throw new ApplicationException(e.getMessage());
		}
        processingInstructionRepository.save(processingInstruction);
        return form.getRefNumber();
    }
    
    @Transactional
    public String newOrUpdatePI(NewPIForm form, User user) {
    		PIAssembler assembler = new PIAssembler();
    		if(StringUtils.isEmpty(form.getRefNumber())) {
    			ProcessingInstruction processingInstruction = assembler.fromForm(form, new ProcessingInstruction());
    			pIDomainService.newPI(processingInstruction, user);
    			processingInstructionRepository.save(processingInstruction);
    			return processingInstruction.getRefNumber();
    		}
    		ProcessingInstruction fromDb = processingInstructionRepository.findFirstByRefNumber(form.getRefNumber());
    		fromDb = assembler.fromForm(form, fromDb);
    		fromDb.updateSelf(user);
    		processingInstructionRepository.save(fromDb);
    		return form.getRefNumber();
    }

	@Transactional
	public List<ProcessingInstruction> getPIListByCreatedDate(Date startDate, Date endDate) {

		Specification<ProcessingInstruction> spec = Specifications
				.where(new DateBetweenDates(startDate, endDate, "createdDate"))
				.and(new ItemNotDeleted());
		return processingInstructionRepository.findAll(spec);
	}

	@Transactional
	public List<ProcessingInstruction> getPIListByCompletionDate(Date startDate, Date endDate) {

		Specification<ProcessingInstruction> spec = Specifications
				.where(new DateBetweenDates(startDate, endDate, "completionStatusDate"))
				.and(new CompletedInstruction());
		return processingInstructionRepository.findAll(spec);
	}
	
	public ProcessingInstruction findOneByRefNumber(String refNumber) {
		return processingInstructionRepository.findFirstByRefNumber(refNumber);
	}
	
	public ProcessingInstruction findOneById(int id) {
		return processingInstructionRepository.findOne(id);
	}
	
}
