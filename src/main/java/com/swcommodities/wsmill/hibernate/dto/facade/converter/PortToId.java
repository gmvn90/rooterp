/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.PortMaster;

/**
 *
 * @author macOS
 */
public class PortToId {
    public PortMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new PortMaster(value);
    }
    
    public Integer fromByte(PortMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
}
