/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.GradeMaster;

/**
 *
 * @author macOS
 */
public class GradeToId {
    public GradeMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new GradeMaster(value);
    }
    
    public Integer fromByte(GradeMaster value) {
        if(value == null) {
            return null;
        }
        return value.getId();
    }
}
