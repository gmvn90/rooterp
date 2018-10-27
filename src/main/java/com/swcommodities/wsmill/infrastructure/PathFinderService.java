/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.infrastructure;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 *
 * @author macOS
 */

@Service
public class PathFinderService {
    
    @Resource(name = "configConfigurer") private Properties configConfigurer;
    
    public String getStaticUrlFromUri(String uriWithoutSplash) {
        return configConfigurer.getProperty("base_static_host") + "/" + uriWithoutSplash; 
    }
    
    public String getBaseDir() {
        return configConfigurer.getProperty("base_dir");
    }
    
}
