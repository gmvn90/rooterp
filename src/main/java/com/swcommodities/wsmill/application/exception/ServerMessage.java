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
public class ServerMessage {
    
    private MessageType type;
    private String message = "";

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public ServerMessage(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
    
}
