/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model.status;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author trung
 */
public enum RequestStatus {

    PENDING, APPROVED;

    public static final RequestStatus[] values = values();

    public static Map<RequestStatus, String> getAll() {
        Map<RequestStatus, String> result = new HashMap<>();
        result.put(PENDING, "Pending");
        result.put(APPROVED, "Approved");
        return result;
    }
    
    public static String getRepresentative(RequestStatus status) {
    		return getAll().get(status);
    }

}
