/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

/**
 *
 * @author macOS
 */
public class NewSampleSentForm {

    private int id;
    private int siId; // not used in mapping, but instead used in getting shipping instruction
    private int courierId; 
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiId() {
        return siId;
    }

    public void setSiId(int siId) {
        this.siId = siId;
    }
    
    

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    public NewSampleSentForm(int siId) {
        this.siId = siId;
    }
    
    public NewSampleSentForm() {}
    
}
