/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;

/**
 *
 * @author macOS
 */
public class CourierToId {
    
    public CourierMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new CourierMaster(value);
    }
    
    public Integer fromObject(CourierMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
    
}
