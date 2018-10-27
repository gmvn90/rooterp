/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.service;

import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.repository.WnrAllocationRepository;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author macOS
 */

@Service("domain_service_AllocationService")
public class AllocationDomainService {
    
    @Autowired WnrAllocationRepository wnrAllocationRepository;
    
    /**
     * 
     * @param wnrs
     * @param shippingInstruction
     * @param user
     * @param log 
     * @return List<String>: a list of refnumber of wnr that can not be allocated
     */
    public List<String> allocate(List<WeightNoteReceipt> wnrs, ShippingInstruction shippingInstruction, User user) {
        List<String> res = new ArrayList<>();
        wnrs.forEach(wnr -> {
            if(! wnr.isAvailable()) {
                res.add(wnr.getRefNumber());
                return;
            }
            wnr.allocated(user);
            String[] wna1 = {"type", "user"};
            String[] wna2 = {"new", user.getUserName()};
            String log = Common.generateJsonString(wna1, wna2);
            WnrAllocation wnrAllocation = new WnrAllocation(wnr, user, log, shippingInstruction);
            wnrAllocationRepository.save(wnrAllocation);
        });
        return res;
    }
    
    public List<String> allocate(List<WeightNoteReceipt> wnrs, ProcessingInstruction processingInstruction, User user) {
        List<String> res = new ArrayList<>();
        wnrs.forEach(wnr -> {
            if(! wnr.isAvailable()) {
                res.add(wnr.getRefNumber());
                return;
            }
            wnr.allocated(user);
            String[] wna1 = {"type", "user"};
            String[] wna2 = {"new", user.getUserName()};
            String log = Common.generateJsonString(wna1, wna2);
            WnrAllocation wnrAllocation = new WnrAllocation(wnr, user, log, processingInstruction);
            wnrAllocationRepository.save(wnrAllocation);
        });
        return res;
    }
    // return a list of wnr that can not be deallocated
    public List<String> deallocate(List<WeightNoteReceipt> wnrs, User user) {
        List<String> res = new ArrayList<>();
        wnrs.forEach(wnr -> {
            WnrAllocation wnrAllocation = wnrAllocationRepository.findByWeightNoteReceiptByWnrId_Id(wnr.getId());
            if(! wnrAllocation.isPending()) {
                res.add(wnr.getRefNumber());
                return;
            }
            wnr.setStatus(Constants.AVAILABLE);
            String[] arr1 = {"type", "user", "date"};
            String[] arr2 = {"deallocate", user.getUserName(), Common.getDateValue(new Date(), Common.date_format)};
            String log = (wnr.getLog() != null) ? wnr.getLog() + "," + Common.generateJsonString(arr1, arr2) : Common.generateJsonString(arr1, arr2);
            wnr.setLog(log);
            wnr.setWnrAllocationsForWnrId(null);
            
            wnrAllocationRepository.delete(wnrAllocation);
            wnrAllocation = wnrAllocationRepository.findByWeightNoteReceiptByWnrId_Id(wnr.getId());
        });
        return res;
    }
    
    
}
