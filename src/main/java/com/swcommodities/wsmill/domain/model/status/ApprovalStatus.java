/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model.status;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author trung
 */
// for sample sent
public enum ApprovalStatus {

    NO_STATUS(-1), PENDING(0), APPROVED(1), REJECTED(2);

    private int order;

    public int getOrder() {
        return order;
    }

    private ApprovalStatus(int order) {
        this.order = order;
    }

    public static final ApprovalStatus[] values = values();

    public static Map<ApprovalStatus, String> getAll() {
        Map<ApprovalStatus, String> res = new LinkedHashMap<>();
        res.put(NO_STATUS, "All");
        res.put(PENDING, "Pending");
        res.put(APPROVED, "Approved");
        res.put(REJECTED, "Rejected");
        return res;
    }

    public static String getRepresentative(ApprovalStatus status) {
        return getAll().get(status);
    }
    
    public static ApprovalStatus getFromByte(byte order) {
        for(ApprovalStatus ap: values) {
            if((byte) ap.getOrder() == order) {
                return ap;
            }
        }
        return null;
    }
    
    public String getShownText() { //showntext is diff: Pending vs PENDING
        if(this == PENDING) {
            return "Pending";
        } else if(this == APPROVED) {
            return "Approved";
        } else if(this == REJECTED) {
            return "Rejected";
        }
        return "";
    }

}
