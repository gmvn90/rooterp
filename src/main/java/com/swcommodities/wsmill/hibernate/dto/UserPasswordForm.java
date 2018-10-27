/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

/**
 *
 * @author macOS
 */
public class UserPasswordForm {

    private String oldRawPassword;
    private String newRawPassword;

    public String getOldRawPassword() {
        return oldRawPassword;
    }

    public void setOldRawPassword(String oldRawPassword) {
        this.oldRawPassword = oldRawPassword;
    }

    public String getNewRawPassword() {
        return newRawPassword;
    }

    public void setNewRawPassword(String newRawPassword) {
        this.newRawPassword = newRawPassword;
    }

}
