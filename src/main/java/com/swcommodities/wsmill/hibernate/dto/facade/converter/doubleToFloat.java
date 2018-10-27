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
public class doubleToFloat {
    public Float mapDouble(double value) {
        return Float.valueOf(String.valueOf(value));
    }
}
