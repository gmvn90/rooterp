/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.si;

import java.util.Objects;

/**
 *
 * @author macOS
 */
public class SIUpdatedEvent {
    
    private String refNumber;

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public SIUpdatedEvent(String refNumber) {
        this.refNumber = refNumber;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.refNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SIUpdatedEvent other = (SIUpdatedEvent) obj;
        if (!Objects.equals(this.refNumber, other.refNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SIUpdatedEvent{" + "refNumber=" + refNumber + '}';
    }
    
    
}
