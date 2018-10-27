/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model.status;

import static com.swcommodities.wsmill.domain.model.status.RequestStatus.getAll;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author trung
 */
public enum CompletionStatus {
    
    PENDING(0), COMPLETED(1), NO_STATUS(-1);
    
    private int order;
    private CompletionStatus(int order) {
        this.order = order;
    }
    
    public int getOrder() {
        return order;
    }
    
    public static final CompletionStatus[] values = values();
    
    public static Map<CompletionStatus, String> getAll() {
        Map<CompletionStatus, String> result = new LinkedHashMap<>();
        result.put(NO_STATUS, "All");
        result.put(PENDING, "Pending");
        result.put(COMPLETED, "Completed");
        return result;
    }
    
    public static String getRepresentative(CompletionStatus status) {
    		return getAll().get(status);
    }
    
}
