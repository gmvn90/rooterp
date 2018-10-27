/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author macOS
 */
@Controller
public class AuthController {
    @RequestMapping(value = "logout.htm")
    public String logoutClient(HttpServletResponse responseWrapper, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if(session != null) {
            session.invalidate();
        } 
        return "redirect:/login.htm";
    }
}
