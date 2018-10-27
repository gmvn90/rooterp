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
public enum SendingStatus {
    
    PENDING(0), SENT(1);
    
	private int order;
	
	public int getOrder() {
        return order;
    }
	
	private SendingStatus(int order) {
		this.order = order;
	}
	
    public static final SendingStatus[] values = values();
    
    public static Map<SendingStatus, String> getAll() {
        Map<SendingStatus, String> result = new HashMap<>();
        result.put(PENDING, "Pending");
        result.put(SENT, "Sent");
        return result;
    }
    
    public static String getRepresentative(SendingStatus status) {
    		return getAll().get(status);
    }
    
    
}
