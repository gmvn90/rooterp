/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.WarehouseMaster;

/**
 *
 * @author macOS
 */
public class WarehouseMasterToint {
    
    public WarehouseMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new WarehouseMaster(value);
    }
    
    public Integer fromObject(WarehouseMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
    
}
