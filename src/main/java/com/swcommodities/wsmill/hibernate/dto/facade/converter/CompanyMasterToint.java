/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;

/**
 *
 * @author macOS
 */
public class CompanyMasterToint {
    
    public CompanyMaster fromInteger(int value) {
        return new CompanyMaster(value);
    }
    
    public CompanyMaster fromIntegerObject(Integer value) {
        return new CompanyMaster(value);
    }
    
    public int fromObject(CompanyMaster value) {
        return value.getId();
    }
    
    public Integer fromObjectInteger(CompanyMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
}
