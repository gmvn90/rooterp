/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.exception;

/**
 *
 * @author macOS
 */
public class UserNotExist extends Exception {
    public UserNotExist () {

    }

    public UserNotExist (String message) {
        super (message);
    }

    public UserNotExist (Throwable cause) {
        super (cause);
    }

    public UserNotExist (String message, Throwable cause) {
        super (message, cause);
    }
}
