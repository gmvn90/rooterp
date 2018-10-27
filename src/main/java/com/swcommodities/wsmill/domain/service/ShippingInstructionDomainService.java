/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.domain.model.SICustomCost;
import com.swcommodities.wsmill.domain.model.ShippingUnit;
import com.swcommodities.wsmill.domain.model.exceptions.ActionNotPermitted;
import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;
import com.swcommodities.wsmill.domain.model.status.CompletionStatus;
import com.swcommodities.wsmill.hibernate.dto.Claim;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdvice;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdviceSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.CourierMasterRepository;
import com.swcommodities.wsmill.repository.ExchangeRepository;
import com.swcommodities.wsmill.repository.PackingCategoryRepository;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.utils.Constants;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

/**
 *
 * @author macOS
 */

@Service("domain_service_ShippingInstructionService")
public class ShippingInstructionDomainService {
    
    @Autowired ShippingAdviceRefNumberProviderService shippingAdviceRefNumberProviderService;
    @Autowired ShippingInstructionRepository shippingInstructionRepository;
    @Autowired SampleSentRefNumberProviderService sampleSentRefNumberProviderService;
    @Autowired ClaimRefNumberProviderService claimRefNumberProviderService;
    @Autowired ExchangeRepository exchangeRepository;
    @Autowired CostCalculator costCalculator;
    @Autowired CourierMasterRepository courierMasterRepository;
    @Autowired PackingCategoryRepository packingCategoryRepository;
    @Autowired ShippingInstructionRefNumberProviderService shippingInstructionRefNumberProviderService;
    
    public ShippingAdvice createShippingAdvice(ShippingInstruction shippingInstruction, User user) {
        if(shippingInstruction.getShippingAdvice() != null) {
            return shippingInstruction.getShippingAdvice();
        }
        ShippingAdvice shippingAdvice = new ShippingAdvice(user, "");
        shippingAdvice.setRefNumber(shippingAdviceRefNumberProviderService.getNewNumber());
        shippingAdvice.setDate(new Date());
        shippingAdvice.setStatus(Constants.ACTIVE);
        shippingAdvice.setShippingInstruction(shippingInstruction);
        shippingAdvice.setUser(user);
        shippingInstruction.setShippingAdvice(shippingAdvice);
        return shippingAdvice;
    }
    
    public ShippingAdviceSent createShippingAdviceSent(ShippingInstruction shippingInstruction, User user) {
        ShippingAdvice shippingAdvice = shippingInstruction.getShippingAdvice();
        ShippingAdviceSent sas = new ShippingAdviceSent();
        String refNumber = String.format("%s-%d", shippingAdvice.getRefNumber(), shippingAdvice.getShippingAdviceSents().size() + 1);
        sas.setRefNumber(refNumber);
        sas.setDate(new Date());
        sas.setEmail("");
        sas.setEmailCc("");
        sas.setStatus(Constants.ACTIVE);
        sas.setShippingAdvice(shippingInstruction.getShippingAdvice());
        sas.setUser(user);
        sas.setFileName(refNumber + ".pdf");
        shippingAdvice.getShippingAdviceSents().add(sas);
        return sas;
    }
    
    public void updateShippingInstructionCompletionStatus(ShippingInstruction shippingInstruction, CompletionStatus status, User user) throws ActionNotPermitted {
        shippingInstruction.updateCompletionStatus(status, user);
        if(shippingInstruction.isAllWeghtNoteCompleted()) {
            Double allocated = Optional.ofNullable(shippingInstructionRepository.getShippingAllocated(shippingInstruction.getId())).map(t->t).orElse(0.0);
            Double deliverd = Optional.ofNullable(shippingInstructionRepository.getShippingDeliverd(shippingInstruction.getId())).map(t->t).orElse(0.0);
            shippingInstruction.setAllocatedWeight(allocated);
            shippingInstruction.setDeliverdWeight(deliverd);
            shippingInstruction.setPendingWeight(allocated - deliverd);
        }
    }
    
    public void addNewSampleSent(ShippingInstruction shippingInstruction, SampleSent sampleSent, User user) {
        Validate.notNull(sampleSent);
        sampleSent.setCourierMaster(courierMasterRepository.findOne(sampleSent.getCourierMaster().getId()));
        sampleSent.setInitialInfo(sampleSentRefNumberProviderService.getNewNumber(), user);
        sampleSent.setShippingInstructionBySiId(shippingInstruction);
        shippingInstruction.addSampleSent(sampleSent);
    }
    
    public SampleSent rejectSampleSent(ShippingInstruction shippingInstruction, SampleSent sampleSent, User user) {
        Assert.isTrue(sampleSent.getApprovalStatusEnum() == ApprovalStatus.REJECTED, "Approval status of already-rejected ss should be rejected");
        boolean isAllSSRejected = shippingInstruction.getSampleSents().stream().allMatch(item -> item.getApprovalStatusEnum()== ApprovalStatus.REJECTED);
        Validate.notNull(sampleSent);
        if(isAllSSRejected) {
            SampleSent newOne = new SampleSent();
            BeanUtils.copyProperties(sampleSent, newOne);
            newOne.setInitialInfo(sampleSentRefNumberProviderService.getNewNumber(), user);
            newOne.setApprovalStatusEnum(ApprovalStatus.PENDING);
            newOne.setSampleSentItems(new ArrayList<>());
            shippingInstruction.addSampleSent(newOne);
            shippingInstructionRepository.save(shippingInstruction);
            return newOne;
        }
        return null;
        
    }

    public String addNewClaim(ShippingInstruction shippingInstruction, Claim claim, User user) throws ActionNotPermitted {
        Validate.notNull(claim);
        claim.setInitialInfo(claimRefNumberProviderService.getNewNumber(), user);
        shippingInstruction.addClaim(claim);
        return claim.getRefNumber();
    }
    
    public List<SICustomCost> getCustomCostsForSIWithVNDInfo(ShippingInstruction shippingInstruction) {
        List<SICustomCost> costs = shippingInstruction.getShippingCost().getsICustomCosts();
        int ratio = exchangeRepository.getFirstObject().getRatio();
        costs.forEach(c -> {
            c.setCostInVND(c.getCostValue() * ratio);
            c.setCostPerMetricTon(fromString(c.getOptionUnit()).getPerMetricTonCost(c.getCostValue(), shippingInstruction.getShippingCost().getTonPerContainer(), 
                shippingInstruction.getShippingCost().getNumberOfContainer()));
        });
        return Collections.unmodifiableList(costs);
    };
    
    private ShippingUnit fromString(String unit) {
        Map<String, ShippingUnit> map = new HashMap<>();
        map.put("1 Pallet", ShippingUnit.OnePallet);
        map.put("Per Bag - 60kg", ShippingUnit.PerBag60Kg);
        map.put("Per Bag - Bulk", ShippingUnit.PerBagBulk);
        map.put("Per Bag - Big", ShippingUnit.PerBagBig);
        map.put("20 Cont.", ShippingUnit.TwentyContainer);
        map.put("Per/BL", ShippingUnit.PerBillOfLading);
        map.put("$/Mt.", ShippingUnit.PerTon);
        return map.get(unit);
    }
    
    public void updateSICost(ShippingInstruction shippingInstruction) {
        shippingInstruction.setCostToFob((float) costCalculator.getCostPerMetricTonForSINew(shippingInstruction));
    }
    
    public void updatePacking(ShippingInstruction shippingInstruction) {
        try {
            PackingMaster packingMaster = packingCategoryRepository.findOne(
                Long.valueOf(String.valueOf(shippingInstruction.getShippingCost().getPackingCostCategory()))).getPackingMaster();
            shippingInstruction.setPackingMaster(packingMaster);
        } catch (Exception e) {
        }
    }
    
    public void updateSICost(int id) throws Exception {
        updateSICost(shippingInstructionRepository.findOne(id));
    }
    
    public void addNewSI(ShippingInstruction shippingInstruction, User user) {
        
        shippingInstruction.setRefNumber(shippingInstructionRefNumberProviderService.getNewNumber());
        shippingInstruction.initNewSelf(user);
        shippingInstructionRepository.save(shippingInstruction);
    }
    
    public void addNewSI(ShippingInstruction shippingInstruction, User user, int client) throws Exception {
        shippingInstruction.setCompanyMasterByClientId(new CompanyMaster(client));
        shippingInstruction.setRefNumber(shippingInstructionRefNumberProviderService.getNewNumber());
        shippingInstruction.initNewSelf(user);
        shippingInstructionRepository.save(shippingInstruction);
    }
        
}
