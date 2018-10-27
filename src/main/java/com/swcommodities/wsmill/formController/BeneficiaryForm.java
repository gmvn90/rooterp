/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.Beneficiary;

/**
 *
 * @author macOS
 */
public class BeneficiaryForm {
    private Beneficiary item;
    private Map<String, Object> properties = new HashMap<>();

    public Beneficiary getItem() {
        return item;
    }

    public void setItem(Beneficiary item) {
        this.item = item;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    
}
