/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.domain.service.ShippingInstructionRefNumberProviderService;

/**
 *
 * @author trung
 */
@Service
public class TestService2 {
 
    @Autowired ShippingInstructionRefNumberProviderService shippingInstructionRefNumberProviderService;
    
    public String getRef() {
        return shippingInstructionRefNumberProviderService.getNewNumber();
    }
    
}
