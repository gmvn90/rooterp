package com.swcommodities.wsmill.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.application.service.InitService;

@Component
public class AppRunner implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired InitService initService;
    
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        initService.init();
    }

}
