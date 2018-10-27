/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.swcommodities.wsmill.bo.AuthorizationService;
import com.swcommodities.wsmill.bo.PageService;
import com.swcommodities.wsmill.bo.UserService;
import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@Controller
public class LoginController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private PageService pageService;
    private ServletSupporter supporter;

    @RequestMapping(value = "login.htm", method = RequestMethod.GET)
    public ModelAndView forwarding() {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        ModelAndView model = new ModelAndView("login");
        model.addObject("message", "");
        return model;
    }

    @RequestMapping(value = "login.htm", method = RequestMethod.POST)
    public ModelAndView doLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.getUser(username, password);
        ModelAndView model = null;
        if (user != null) {
            if (user.isActive()) {
                if (user.isOnlyForClientSite()) {
                    model = new ModelAndView("login");
                    model.addObject("message", "Your account is only use for client site!");
                } else {
                    request.getSession().setAttribute("user", user);
                    ArrayList<Authorization> authorizations = authorizationService.getAuthorizationOfUser(user);
                    for (Authorization authorization : authorizations) {
                        if (authorization.getPermission() != 0) {
                            Page page = authorizationService.getPageFromAuthorization(authorization);
                            String path = page.getUrl();
                            model = new ModelAndView("redirect:" + path + ".htm");
                            return model;
                        }
                        model = new ModelAndView("login");
                        model.addObject("message", "Your account is being locked. Please contact admin for further information.");
                    }
                }
            } else {
                model = new ModelAndView("login");
                model.addObject("message", "Your account is locked!");
            }

        } else {
            model = new ModelAndView("login");
            model.addObject("message", "Invalid Usernam or Password");
        }

        return model;
    }

    @RequestMapping(value = "show_login.htm", method = RequestMethod.POST)
    public @ResponseBody
    void showLogin(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/login.html"));
        response.getWriter().print(new GenTemplate(request).generateLoginDialog(tpl));
    }

    @RequestMapping(value = "login_by_dialog.htm", method = RequestMethod.POST)
    public @ResponseBody
    void login_by_dialog(HttpServletResponse responseWrapper) throws Exception {
        responseWrapper.setContentType("text/html;charset=UTF-8");
        supporter = new ServletSupporter(request);
        JSONObject json = new JSONObject();

        //Get username & password on the login form
        String username = supporter.getStringRequest("username");
        String password = supporter.getStringRequest("password");

        if (username.isEmpty() || password.isEmpty()) {
            json.put("msg", "Please enter username and password before press Login");
        } else {
            User user = userService.getUser(username, password);
            if (user != null) {
                if (user.isActive()) {
                    request.getSession().setAttribute("user", user);
                    request.getSession().setAttribute("username", user.getUserName());
                    json.put("userName", user.getUserName());
                    json.put("fullName", user.getFullName());
                } else {
                    json.put("msg", "Your account is locked!");
                }
            } else {
                json.put("msg", "Invalid username or password.");
            }
            responseWrapper.getWriter().print(json);
        }
    }
    
    @RequestMapping(value = "millclient/showdata.htm")
    public @ResponseBody
    void showData(HttpServletResponse responseWrapper) throws Exception {
    	System.out.println("our print on showdata.htm");
    	logger.info("getWelcome is executed!");
    	
    	if(request.getSession().getAttribute("username") != null) {
    		String username = (String) request.getSession().getAttribute("username");
    		responseWrapper.getWriter().print("acc ok " + username);
    	} else {
    	
    		responseWrapper.getWriter().print("acc not ok");
    	}
    }
}
