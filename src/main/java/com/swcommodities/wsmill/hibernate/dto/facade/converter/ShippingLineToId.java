/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;

/**
 *
 * @author macOS
 */
public class ShippingLineToId {
    public ShippingLineCompanyMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new ShippingLineCompanyMaster(value);
    }
    
    public Integer fromByte(ShippingLineCompanyMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
}
