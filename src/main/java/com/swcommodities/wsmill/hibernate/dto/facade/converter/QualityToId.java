/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.QualityMaster;


/**
 *
 * @author macOS
 */
public class QualityToId {
    public QualityMaster fromInteger(Integer value) {
        if(value == null) {
            return null;
        }
        return new QualityMaster(value);
    }
    
    public Integer fromObject(QualityMaster value) {
        if(value == null) {
            return null;
        }
        return Integer.valueOf(String.valueOf(value.getId()));
    }
}
