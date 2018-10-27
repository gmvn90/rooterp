/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import java.util.Date;
import java.util.Map;

import com.swcommodities.wsmill.domain.model.status.CompletionStatus;

/**
 *
 * @author trung
 */
public class CompletionStatusForm extends StatusFormEnum {
    
    public final Map<CompletionStatus, String> statuses = CompletionStatus.getAll();
    private CompletionStatus status;

    public CompletionStatus getStatus() {
        return status;
    }

    public void setStatus(CompletionStatus status) {
        this.status = status;
    }

    public CompletionStatusForm() {}
    
    public CompletionStatusForm(String refNumber, String user, Date date, CompletionStatus status) {
        super(refNumber, user, date);
        this.status = status;
    }
    
    public Map<CompletionStatus, String> getStatuses() {
        return statuses;
    }
    
}
