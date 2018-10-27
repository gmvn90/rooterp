/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

/**
 *
 * @author macOS
 */
public class IntegerToLong {
    
    public Long fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return Long.valueOf(value.toString());
    }
    
    public Integer fromLong(Long value) {
        if(value == null) {
            return null;
        }
        return Integer.valueOf(String.valueOf(value));
    }
    
    public Integer fromLong(long value) {
        return Integer.valueOf(String.valueOf(value));
    }
    
    public int fromLongValue(long value) {
        return Integer.valueOf(String.valueOf(value));
    }
    
}
