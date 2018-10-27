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
public class IntegerToByte {
    public Byte fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return Byte.valueOf(String.valueOf(value));
    }
    
    public Integer fromByte(Byte value) {
        if(value == null) {
            return null;
        }
        return Integer.valueOf(String.valueOf(value));
    }
}
