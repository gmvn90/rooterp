/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.Date;

import javax.persistence.Embeddable;

import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author trung
 */

@Embeddable
public class EventTracking {
    
    private User user;
    private Date date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public EventTracking(User user, Date date) {
        this.user = user;
        this.date = date;
    }
    
    public EventTracking() {}
    
    public EventTracking(User user) {
        this.user = user;
        this.date = new Date();
    }
    
    public EventTracking update(User user) {
    		return new EventTracking(user);
    }
    
}
