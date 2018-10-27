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
public class ClaimForm {

    private int id;
    private String siRef;
    private int siId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiRef() {
        return siRef;
    }

    public void setSiRef(String siRef) {
        this.siRef = siRef;
    }

    public int getSiId() {
        return siId;
    }

    public void setSiId(int siId) {
        this.siId = siId;
    }

    public ClaimForm(int siId) {
        this.siId = siId;
    }

    public ClaimForm() {}
    
}
