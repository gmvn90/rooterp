/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;

/**
 *
 * @author macOS
 */

public class CourierMasterToName {
    public String toString(CourierMaster courierMaster) {
        return courierMaster != null ? courierMaster.getName() : "";
    }
}
