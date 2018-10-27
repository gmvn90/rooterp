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
public class CompanyMasterToId {
    public CompanyMaster getCompanyMaster(Integer value) {
        if(value == null) {
            return null;
        }
        return new CompanyMaster(value);
    }
    
    public Integer getInteger(CompanyMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
}
