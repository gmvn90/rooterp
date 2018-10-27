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
public class QualityMasterToName {
    public String mapQuality(QualityMaster qualityMaster) {
        if(qualityMaster == null) {
            return "";
        }
        return qualityMaster.getName();
    }
    public QualityMaster mapQuality(String qualityMaster) {
        return new QualityMaster(Integer.valueOf(qualityMaster));
        
    }
}
