/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

/**
 *
 * @author macOS
 */
public enum RefNumberType {
    
    SampleSentType, SampleTypeType;
    
    public static RefNumberType getType(String refNumber) {
        if(refNumber == null) {
            return null;
        }
        
        if(refNumber.startsWith("SS-")) {
            return SampleSentType;
        } else if(refNumber.startsWith("TS-")) {
            return SampleTypeType;
        }
        return null;
    }
    
}
