/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

import java.util.Date;

/**
 *
 * @author trung
 */
public class StatusFormEnum {
    
    private String refNumber;
    private String user;
    private Date date;

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatusFormEnum(String refNumber, String user, Date date) {
        this.refNumber = refNumber;
        this.user = user;
        this.date = date;
    }
    
    public StatusFormEnum() {}
    
    
}
