/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.service.TestService2;

/**
 *
 * @author trung
 */
@Controller
public class Test1Controller {
    @Autowired private TestService2 service1;
    
    @RequestMapping(value = "test1-controller.json", method = RequestMethod.GET)
    public @ResponseBody String get(Map<String, Object> model, @RequestParam(required = false) Integer id) throws Exception{
        return String.valueOf(service1.getRef());
        
    }
}
