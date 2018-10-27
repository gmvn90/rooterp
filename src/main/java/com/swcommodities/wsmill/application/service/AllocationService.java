/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.service;

import com.swcommodities.wsmill.domain.service.AllocationDomainService;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.repository.ProcessingInstructionRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.repository.WeightNoteReceiptRepository;
import com.swcommodities.wsmill.repository.WnrAllocationRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author macOS
 */

@Service("application_service_AllocationService")
public class AllocationService {
    
    @Autowired ShippingInstructionRepository shippingInstructionRepository;
    @Autowired ProcessingInstructionRepository processingInstructionRepository;
    @Autowired AllocationDomainService allocationDomainService;
    @Autowired WeightNoteReceiptRepository weightNoteReceiptRepository;
    @Autowired WnrAllocationRepository wnrAllocationRepository;
    
    @Transactional
    public List<String> allocateWnrs(List<Integer> wnrs, int instructionId, User user, String type) {
        // P: processing, E: export, M: movement
        if(type.equals("P")) {
            ProcessingInstruction processingInstruction = processingInstructionRepository.findOne(instructionId);
            return allocationDomainService.allocate(weightNoteReceiptRepository.findByIdIn(wnrs), processingInstruction, user);
        } else {
            ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(instructionId);
            return allocationDomainService.allocate(weightNoteReceiptRepository.findByIdIn(wnrs), shippingInstruction, user);
        }
    }
    
    @Transactional
    public List<String> allocateWn(int wn, int instructionId, User user, String type) {
        // P: processing, E: export, M: movement
        List<WeightNoteReceipt> wnrs = weightNoteReceiptRepository.findByWeightNote_Id(wn).stream().filter(wnr -> wnr.isAvailable()).collect(Collectors.toList());
        if(type.equals("P")) {
            ProcessingInstruction processingInstruction = processingInstructionRepository.findOne(instructionId);
            return allocationDomainService.allocate(wnrs, processingInstruction, user);
        } else {
            ShippingInstruction shippingInstruction = shippingInstructionRepository.findOne(instructionId);
            return allocationDomainService.allocate(wnrs, shippingInstruction, user);
        }
    }
    
    @Transactional
    public List<String> deallocateWnrs(List<Integer> wnrs, User user) {
        return allocationDomainService.deallocate(weightNoteReceiptRepository.findByIdIn(wnrs), user);
    }
    
    @Transactional
    public List<String> deallocateWn(int wn, int instructionId, User user) {
        List<WeightNoteReceipt> wnrs = weightNoteReceiptRepository.findByWeightNote_Id(wn).stream().filter(wnr -> wnr.isAllocated() && wnr.isAllocated() 
            && wnr.getWnrAllocationsForWnrId() != null && wnr.getWnrAllocationsForWnrId().getInstId() == instructionId).collect(Collectors.toList());
        return allocationDomainService.deallocate(wnrs, user);
    }
}
