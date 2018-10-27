/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.clientController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.UserService;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.UserRepository;

/**
 *
 * @author macOS
 */

@Controller
@RequestMapping("/millclient/authentication")
public class AuthenticationController {
    
    @Autowired
    private UserService userService;
    
    @Autowired private UserRepository userRepository;
    
    @Autowired(required = true)
    private HttpServletRequest request;
    
    @RequestMapping(value = "/login.htm", method = RequestMethod.POST)
    public @ResponseBody void doLogin(HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        User user = userService.getUser(username, password);
        int company_id = 0;
        System.out.println(username + " " + password);
        if (user != null) {
        	User userRepo = userRepository.findOne(user.getId());
            if (user.isActive()) {
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("username", user.getUserName());
                try {
                	company_id = userRepo.getCompanyMasters().iterator().next().getId();
                	request.getSession().setAttribute("company_id", company_id);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw new Exception();
				}
                
                System.out.println("has user");
            } else {
                System.out.println("no active at all");
            }
        } else {
            System.out.println("no user at all");
        }
        JSONObject object = new JSONObject();
        object.put("company_id", company_id);
        try {
        	object.put("name", user.getFullName());
        } catch (Exception e) {
			// TODO: handle exception
        	object.put("name", "Name");
		}
        

        response.getWriter().print(object.toString());
    }
    
    @RequestMapping(value = "/logout.htm", method = RequestMethod.POST)
    public @ResponseBody
    void logoutClient(HttpServletResponse responseWrapper) throws Exception {
        HttpSession session = request.getSession();
        if(session != null) {
            session.invalidate();
            responseWrapper.getWriter().print("logout successful");
        } else {
            responseWrapper.getWriter().print("try logout; but no session");
        }
    }
    
}
