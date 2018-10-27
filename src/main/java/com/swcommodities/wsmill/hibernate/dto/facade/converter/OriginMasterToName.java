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
public class OriginMasterToName {
    public String mapOrigin(OriginMaster originMaster) {
        if(originMaster != null && originMaster.getCountry() != null) {
            return originMaster.getCountry().getShortName();
        }
        return "";
    }
}
