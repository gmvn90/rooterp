/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.interceptors;

import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.swcommodities.wsmill.bo.AuthorizationService;
import com.swcommodities.wsmill.bo.PageService;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(AuthorizationInterceptor.class);
	
    private PageService pageService;
    private AuthorizationService authorizationService;

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        ServletContext context = request.getServletContext();

        String athorize_url = Common.readProperties(Constants.authorize_url_check_connection, context);

//        if ("http://tradecam.swcommodities.com:8484".equals(athorize_url) || "http://localhost".equals(athorize_url) || "http://cbtest.swcommodities.com".equals(athorize_url)) {
//            response.setHeader("Access-Control-Allow-Origin", athorize_url);
//        }
        //get url
        response.setHeader("Access-Control-Allow-Origin", athorize_url);
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With"); 
//        response.setHeader("Access-Control-Allow-Origin", "http://tradecam.swcommodities.com:8484");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        /* start:*/
        /* for debug information */
        String abc = response.getHeader("Access-Control-Allow-Origin");
                
        //System.out.println("Origin : " + abc);
        /* end */
        String request_uri = request.getRequestURI();
        String full_url = request_uri.substring(request_uri.lastIndexOf("/") + 1, request_uri.length());
        String url = full_url.substring(0, full_url.lastIndexOf("."));
        String ip = "";
        ip = request.getRemoteAddr();
        String username = "";
        try {
            username = ((User) request.getSession().getAttribute("user")).getUserName();
        } catch (Exception e) {
            //System.out.println("username ex: " +  e.toString());
        }
        
        //System.out.println("request url: " + url);
        String body = "";
        List names = Collections.list(request.getParameterNames());
        for(Object name: names) {
            String nameString = (String) name;
            body += nameString + "=" + request.getParameter(nameString) + ";";
        }
        logger.info("Request is: " + request_uri + ". Param: " + request.getQueryString() + ". Body: " + body + ". IP: " + ip + ". User: " + username);
        
        String ajax = (request.getHeader("X-Requested-With") != null) ? request.getHeader("X-Requested-With") : "";
        //if (request.getSession().getAttribute("user") != null) {
        //check url exist in page
        Page page = pageService.getPageCode(url);

        if (!ajax.equals("XMLHttpRequest")) {
            //if pageCode > 0 then check authorization
            if (page != null) {
                //check authorization to the current user
                User user = (User) request.getSession().getAttribute("user");
                if (user != null) {
                    //get permission
                    if (authorizationService.getPagePermission(user, page) == 0) {
                        return false;
                    }
                } else {
                    //response
                    response.sendRedirect(request.getContextPath() + "/login.htm");
                }
            }
        }
        // }
        return super.preHandle(request, response, handler); //To change body of generated methods, choose Tools | Templates.
    }
}
