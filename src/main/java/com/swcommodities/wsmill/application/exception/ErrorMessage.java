/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.exception;

/**
 *
 * @author trung
 */
public class ErrorMessage extends ServerMessage {
    
    public ErrorMessage(String message) {
        super(MessageType.ERROR, message);
    }
    
}
