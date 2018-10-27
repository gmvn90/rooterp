/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.pi;

/**
 *
 * @author macOS
 */
public class PIUpdatedEventByRef {
    
    private String refNumber;

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public PIUpdatedEventByRef(String refNumber) {
        this.refNumber = refNumber;
    }
    
}
