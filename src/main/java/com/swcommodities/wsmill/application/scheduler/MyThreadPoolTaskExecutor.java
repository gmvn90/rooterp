/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.application.scheduler;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author macOS
 */

@Component
public class MyThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    
    public MyThreadPoolTaskExecutor() {
        super();
        this.setCorePoolSize(20);
        this.setMaxPoolSize(30);
    }
    
}
