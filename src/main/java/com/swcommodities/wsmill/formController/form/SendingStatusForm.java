/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import java.util.Date;
import java.util.Map;

import com.swcommodities.wsmill.domain.model.status.SendingStatus;

/**
 *
 * @author trung
 */
public class SendingStatusForm extends StatusFormEnum {
    private final Map<SendingStatus, String> statuses = SendingStatus.getAll();

    public Map<SendingStatus, String> getStatuses() {
        return statuses;
    }

    private SendingStatus status;

    public SendingStatus getStatus() {
        return status;
    }

    public void setStatus(SendingStatus status) {
        this.status = status;
    }
    
    public SendingStatusForm() {}
    
    public SendingStatusForm(String refNumber, String user, Date date, SendingStatus status) {
        super(refNumber, user, date);
        this.status = status;
    }
}
