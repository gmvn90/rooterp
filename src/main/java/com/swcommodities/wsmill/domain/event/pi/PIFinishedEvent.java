/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.pi;

import com.swcommodities.wsmill.domain.event.BaseObjectEvent;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author macOS
 */
public class PIFinishedEvent extends BaseObjectEvent<ProcessingInstruction> {
    
    private User user;
    
    public PIFinishedEvent(ProcessingInstruction item, User user) {
        super(item);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
}
