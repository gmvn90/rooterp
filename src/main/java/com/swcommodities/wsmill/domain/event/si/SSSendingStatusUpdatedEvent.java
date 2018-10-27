/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.si;

import java.util.Objects;

import com.swcommodities.wsmill.domain.model.status.SendingStatus;

/**
 *
 * @author trung
 */
public class SSSendingStatusUpdatedEvent {

    private final String refNumber;
    private final SendingStatus status;

    public SSSendingStatusUpdatedEvent(String refNumber, SendingStatus status) {
        this.refNumber = refNumber;
        this.status = status;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public SendingStatus getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.refNumber);
        hash = 41 * hash + Objects.hashCode(this.status);
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
        final SSSendingStatusUpdatedEvent other = (SSSendingStatusUpdatedEvent) obj;
        if (!Objects.equals(this.refNumber, other.refNumber)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SSSendingStatusUpdatedEvent{" + "refNumber=" + refNumber + ", status=" + status + '}';
    }

    
    
}
