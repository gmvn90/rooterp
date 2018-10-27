/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.swcommodities.wsmill.bo.MenuService;
import com.swcommodities.wsmill.bo.UserService;

/**
 *
 * @author kiendn
 */
@Controller
@RequestMapping("index.htm")
public class UserController {
    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;
    
    @Autowired
    private UserService user;
    
    @Autowired
    private MenuService menu;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView printMessage(){
        ModelAndView model = new ModelAndView("index");
        String username = user.findUserById(1).getUserName();
        String menu_str = menu.getMenuLv1().get(0).getName();
        System.out.println(username);
        model.addObject("user", menu_str);
        return model;
    }
    
    
}
