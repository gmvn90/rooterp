/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author kiendn
 */

public class ApplicationContextProvider implements ApplicationContextAware {

    private ApplicationContext ctx = null;

    public ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.ctx = ac;
    }
}
