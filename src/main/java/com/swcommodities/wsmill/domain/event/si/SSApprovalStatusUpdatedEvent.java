/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.si;

import java.util.Objects;

import com.swcommodities.wsmill.domain.model.status.ApprovalStatus;

/**
 *
 * @author trung
 */
public class SSApprovalStatusUpdatedEvent {
    
    private final String refNumber;
    private final ApprovalStatus status;

    public SSApprovalStatusUpdatedEvent(String refNumber, ApprovalStatus status) {
        this.refNumber = refNumber;
        this.status = status;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.refNumber);
        hash = 37 * hash + Objects.hashCode(this.status);
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
        final SSApprovalStatusUpdatedEvent other = (SSApprovalStatusUpdatedEvent) obj;
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
        return "SSApprovalStatusUpdatedEvent{" + "refNumber=" + refNumber + ", status=" + status + '}';
    }
    
    
}
