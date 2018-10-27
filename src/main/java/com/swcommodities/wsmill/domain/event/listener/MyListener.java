/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.event.listener;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author macOS
 */
public class MyListener implements MessageListener {

    private JmsTemplate jmsTemplate;
    private Destination destination;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void onMessage(Message msg) {
        System.out.println("mymesssageis: " + msg);
    }

}
