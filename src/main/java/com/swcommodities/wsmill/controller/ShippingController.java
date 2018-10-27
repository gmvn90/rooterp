/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.exels.ShippingInfoExcel;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.repository.ShippingInstructionRepository;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class ShippingController {

    @Autowired(required = true)
    private ServletContext context;
    
    @Autowired
    private ShippingService shippingService;
    
    @Autowired(required = true)
    private HttpServletRequest request;
    @Resource(name = "configConfigurer")
    Properties configConfigurer;
  

    @Autowired
    private ShippingInstructionRepository shippingInstructionRepository;


    

    @RequestMapping(value = "si_get_sup_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    void si_get_sup_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        ArrayList<CompanyMaster> list = shippingService.getCompanyInSi();
        response.getWriter().print(new GenTemplate(request).generateCompanyFilterList(tpl, list, "sup"));
    }

    @RequestMapping(value = "si_get_grade_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    String si_get_grade_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<GradeMaster> grades = shippingService.getAllGrades();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateGradeFilterList(tpl, grades, -1);
    }

    @RequestMapping(value = "delete_si.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_si(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int si_id = support.getIntValue("si_id");
        String reason = support.getStringRequest("reason");
        ShippingInstruction si = shippingService.getSiById(si_id);
        String username = ((User) request.getSession().getAttribute("user")).getUserName();
        boolean doDelete = shippingService.delete_si(si, username, reason);
        if (!doDelete) {
            String message = "[2]";
            response.getWriter().print(message);
        } else {
            String message = "[1]";
            response.getWriter().print(message);
        }
    }

    @RequestMapping(value = "delete_si_surround.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_si_surround(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int si_id = support.getIntValue("si_id");
        ShippingInstruction si = shippingService.getSiById(si_id);
        JSONObject json = new JSONObject();
        json = shippingService.delete_si_surround(si);
        response.getWriter().print("[" + json.toString() + "]");
    }

    @RequestMapping(value = "show_container.htm", method = RequestMethod.POST)
    public @ResponseBody
    void show_container(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int si_id = support.getIntValue("si_id");
        JSONArray json = shippingService.getContainers(si_id);
        response.getWriter().print("[" + json.toString() + "]");
    }

    @RequestMapping(value = "shipping_info.htm", method = RequestMethod.POST)
    public @ResponseBody
    void processing_report(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int si_id = support.getIntValue("si_id");

        String webPath = context.getContextPath() + "/";
        Map accessories = shippingService.getAccessoriesInMap(si_id);
        String documents = shippingService.getDocumentInString(si_id);
        Map info = shippingService.getShippingFullInfo(si_id);
        info.put("notify", shippingService.getNotifyInString(si_id));
        info.put("accessories", accessories);
        info.put("documents", documents);
        ShippingInfoExcel sie = new ShippingInfoExcel(context.getRealPath(""), info);
        response.getWriter().print(sie.generateShippingInfo(webPath));
    }

    @RequestMapping(value = "printShippingReport.htm", method = RequestMethod.POST)
    public @ResponseBody
    void printShippingReport(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int si_id = support.getIntValue("si_id");
        ShippingInstruction si = shippingService.getSiById(si_id);
        Map info = new HashMap();
        info.put("si_ref", si.getRefNumber());
        info.put("client", si.getCompanyMasterByClientId().getName());
        ArrayList<HashMap> listwn = shippingService.getShippingReport(si_id);

        String webPath = context.getContextPath() + "/";

        ShippingInfoExcel sie = new ShippingInfoExcel(context.getRealPath(""), listwn, info);
        response.getWriter().print(sie.generateShippingReport(webPath));
    }

    @RequestMapping(value = "printShippingReportDetail.htm", method = RequestMethod.POST)
    public @ResponseBody
    void printShippingReportDetail(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int si_id = support.getIntValue("si_id");
        ShippingInstruction si = shippingService.getSiById(si_id);
        Map info = new HashMap();
        info.put("si_ref", si.getRefNumber());
        info.put("client", si.getCompanyMasterByClientId().getName());
        ArrayList<HashMap> listwnr = shippingService.getShippingReportDetailWnr(si_id);
        ArrayList<HashMap> listwn = shippingService.getShippingReportDetailWn(si_id);

        String webPath = context.getContextPath() + "/";

        ShippingInfoExcel sie = new ShippingInfoExcel(context.getRealPath(""), listwnr, listwn, info);
        response.getWriter().print(sie.generateShippingReportDetail(webPath));
    }

    

    
}
