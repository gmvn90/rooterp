/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.OriginMaster;

/**
 *
 * @author macOS
 */
public class OriginToId {
    public OriginMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new OriginMaster(value);
    }
    
    public Integer fromObject(OriginMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
}
