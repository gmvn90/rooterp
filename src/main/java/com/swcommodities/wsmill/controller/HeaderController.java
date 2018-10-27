/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.AuthorizationService;
import com.swcommodities.wsmill.bo.MenuService;
import com.swcommodities.wsmill.bo.PageService;
import com.swcommodities.wsmill.hibernate.dto.Menu;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

/**
 *
 * @author kiendn
 */
@Controller
@Transactional(propagation = Propagation.REQUIRED)
public class HeaderController {

    private ServletSupporter supporter;
    @Autowired
    private MenuService menuService;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private PageService pageService;
    @Autowired
    private AuthorizationService authorizationService;
    private HttpSession session = null;

    @RequestMapping(value = "/menu.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getMenu(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        session = request.getSession();
        supporter = new ServletSupporter(request);
        try {
            User user = (User)request.getSession().getAttribute("user");
            GenTemplate gen = new GenTemplate(request);
            File full_head = new File(context.getRealPath("/") + "templates/full_header.html");
            File head = new File(context.getRealPath("/") + "templates/header.html");
            Menu menu = menuService.getMenuById(supporter.getIntValue("url"));
            Page page = pageService.getPageByPageCode(supporter.getIntValue("page"));
            if (menu != null) {
                String profile = Common.readProperties("profile", context);
                String header = gen.generateMenus(head, menu, user);
                String breadcrum = gen.generateBreadcrumbs(head, menu, page, ((User) session.getAttribute("user")).getFullName(), pageService.getPagesNotInMenu(), profile);
                response.getWriter().println(gen.generateFullHeader(full_head, header, breadcrum));
            } else {
                response.getWriter().println(gen.generateFullHeader(full_head, gen.generateDisabledMenus(head), ""));
            }
        } catch (Exception ex) {
            Logger.getLogger(HeaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/test.htm", method = RequestMethod.POST)
    public @ResponseBody
    void test(HttpServletResponse response) throws Exception {
        response.getWriter().print("1");
    }

    @RequestMapping(value = "/redirect.htm", method = RequestMethod.POST)
    public @ResponseBody
    void redirect(HttpServletResponse response) throws JSONException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        supporter = new ServletSupporter(request);
        Menu menu = menuService.getMenuById(supporter.getIntValue("page_code"));
        Page redirectedPage = menuService.getRedirectedPageFromMenu(menu, (User) request.getSession().getAttribute("user"));
        if (redirectedPage != null) {
            JSONObject page = new JSONObject();
            page.put("name", redirectedPage.getName());
            page.put("page_code", redirectedPage.getId());
            page.put("url", redirectedPage.getUrl());
            response.getWriter().print(page);
        } else {
            response.getWriter().print("-1");
        }
    }

    @RequestMapping(value = "/authorization.htm", method = RequestMethod.POST)
    public @ResponseBody
    void checkAuthorization(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
        supporter = new ServletSupporter(request);
        Page page = pageService.getPageByPageCode(supporter.getIntValue("page_code"));
        if (user != null && (authorizationService.getPagePermission(user, page) != 0)) {
            out.print("true");
        } else {
            out.print("false");
        }
    }

    @RequestMapping(value = "/userSession.htm", method = RequestMethod.POST)
    public @ResponseBody
    void checkSession(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            out.print("true");
        } else {
            out.print("false");
        }
    }

    @RequestMapping(value = "/get_profile_picture_url.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_profile_picture_url(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "images/maleLogo.jpg";
        User user = (User) request.getSession().getAttribute("user");
        PrintWriter out = response.getWriter();
        if (user != null) {
            String folder_name = "userdata/" + user.getId();
            File directory = new File(context.getRealPath("/") + folder_name);
            if (directory.listFiles() != null && directory.listFiles().length > 0) {
                for (File file : directory.listFiles()) {
//                String[] tmp = file.getName().split("\\.");
////                        System.out.println(tmp.length + "-" + file.getName());
//                if (tmp.length > 0 && tmp[0].equals(user.getUserName())) {
                    url = folder_name + "/" + file.getName();
//                }
                }
            }
        }
        out.print(url);
    }
}
