/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import com.swcommodities.wsmill.domain.model.status.RequestStatus;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author macOS
 */
public class RequestStatusForm extends StatusFormEnum {

    private final Map<RequestStatus, String> statuses = RequestStatus.getAll();
    private RequestStatus status;

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Map<RequestStatus, String> getStatuses() {
        return statuses;
    }

    public RequestStatusForm() {
    }

    public RequestStatusForm(String refNumber, String user, Date date, RequestStatus status) {
        super(refNumber, user, date);
        this.status = status;
    }

}
