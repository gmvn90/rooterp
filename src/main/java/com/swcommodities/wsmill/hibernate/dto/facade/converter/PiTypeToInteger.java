/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.PIType;

/**
 *
 * @author macOS
 */
public class PiTypeToInteger {
    
    public Integer fromModel(PIType type) {
        if(type == null) {
            return null;
        }
        return type.getId();
    }
    
    public PIType fromId(Integer id) {
        return id == null ? null : new PIType(id);
    }
    
}
