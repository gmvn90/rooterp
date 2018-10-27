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
public class CompanyMasterToName {
    public String getName(CompanyMaster company) {
        if(company == null) {
            return "";
        }
        return company.getName();
    }
}
