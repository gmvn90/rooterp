/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.LocationMaster;

/**
 *
 * @author macOS
 */
public class LocationMasterToName {
    public String mapLocation(LocationMaster locationMaster) {
        if(locationMaster == null) {
            return "";
        }
        return locationMaster.getName();
    }
}
