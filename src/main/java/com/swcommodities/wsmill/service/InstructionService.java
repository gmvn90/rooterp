/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.swcommodities.wsmill.hibernate.dto.OperationalCost;
import com.swcommodities.wsmill.repository.CategoryRepository;
import com.swcommodities.wsmill.repository.OptionRepository;

/**
 *
 * @author trung
 */
@Transactional
@Service
public class InstructionService {
    private OptionRepository optionRepository;
    private CategoryRepository categoryRepository;
    
    @Autowired
    public InstructionService(OptionRepository optionRepository, CategoryRepository categoryRepository) {
        Assert.notNull(optionRepository);
        Assert.notNull(categoryRepository);
        this.optionRepository = optionRepository;
        this.categoryRepository = categoryRepository;
    }
    
    public String getWeightCertificateNameByOption(String optionName) {
        if(optionName == null || optionName.isEmpty()) {
            return "";
        }
        OperationalCost option = optionRepository.findFirstByOptionName(optionName);
        if(option == null) {
            return "";
        }
        return option.getName();
    }
    
    public String getFumigationNameByOptionName(String optionName) {
        if(optionName == null || optionName.isEmpty()) {
            return "";
        }
        OperationalCost option = optionRepository.findFirstByOptionName(optionName);
        if(option == null) {
            return "";
        }
        return option.getCategory().getName();
    }
}
