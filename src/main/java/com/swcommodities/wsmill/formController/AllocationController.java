/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController;

import com.google.common.collect.ImmutableMap;
import com.swcommodities.wsmill.application.exception.ApplicationException;
import com.swcommodities.wsmill.application.exception.ErrorMessage;
import com.swcommodities.wsmill.application.service.AllocationService;
import com.swcommodities.wsmill.formController.form.CompletionStatusForm;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.ServletSupporter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author macOS
 */

@Controller("wsmill.formController.AllocationController")
public class AllocationController {
    
    @Autowired AllocationService allocationService;
    
    private User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
    
    @RequestMapping("allocate_wnr1.htm")
    public @ResponseBody
    void allocate_wnr(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            Map map = new HashMap();
            ServletSupporter support = new ServletSupporter(request);
            String[] wnr_str_arr = request.getParameter("data").split(",");
            List<Integer> wnrs = (new ArrayList<>(Arrays.asList(wnr_str_arr))).stream().map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            List<String> refNumbers = allocationService.allocateWnrs(wnrs, support.getIntValue("ins_id"), getUser(request), request.getParameter("type"));
            if (refNumbers.size() > 0) {
                map.put("title", "Some weight note receipts cannot be allocated");
                map.put("msg", new JSONArray(refNumbers.stream().map(ref -> ImmutableMap.of("a_row", String.format("%s was weighted out", ref))).collect(Collectors.toList())));
            } else {
                map.put("title", "Update Succeeded");
            }
            JSONObject json = new JSONObject(map);
            response.getWriter().print("[" + json.toString() + "]");
        }
    }
    
    @RequestMapping("allocate_wn1.htm")
    public @ResponseBody
    void allocate_wn(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            Map map = new HashMap();
            ServletSupporter support = new ServletSupporter(request);
            String[] wn_str_arr = request.getParameter("data").split(",");
            List<Integer> wns = (new ArrayList<>(Arrays.asList(wn_str_arr))).stream().map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            List<String> refNumbers = new ArrayList<>();
            wns.forEach((wn) -> {
                refNumbers.addAll(allocationService.allocateWn(wn, support.getIntValue("ins_id"), getUser(request), request.getParameter("type")));
            });
            if (refNumbers.size() > 0) {
                map.put("title", "Some weight note receipts cannot be allocated");
                map.put("msg", new JSONArray(refNumbers.stream().map(ref -> ImmutableMap.of("a_row", String.format("%s was weighted out", ref))).collect(Collectors.toList())));
            } else {
                map.put("title", "Update Succeeded");
            }
            JSONObject json = new JSONObject(map);
            response.getWriter().print("[" + json.toString() + "]");
        }
    }
    
    @RequestMapping("deallocate_wnr1.htm")
    public @ResponseBody
    void deallocate_wnr(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ArrayList<Map> message = new ArrayList<>();
            Map map = new HashMap();
            ServletSupporter support = new ServletSupporter(request);
            int inst_id = support.getIntValue("ins_id");
            String[] wn_str_arr = request.getParameter("data").split(",");
            List<Integer> wnrs = (new ArrayList<>(Arrays.asList(wn_str_arr))).stream().map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            List<String> refNumbers = allocationService.deallocateWnrs(wnrs, getUser(request));
            if (refNumbers.size() > 0) {
                map.put("title", "Some weight note receipts cannot be de-allocated");
                map.put("msg", new JSONArray(refNumbers.stream().map(ref -> ImmutableMap.of("a_row", String.format("%s was weighted out", ref))).collect(Collectors.toList())));
            } else {
                map.put("title", "Update Succeeded");
            }
            JSONObject json = new JSONObject(map);
            response.getWriter().print("[" + json.toString() + "]");
        }
    }
    
    @RequestMapping("deallocate_wn1.htm")
    public @ResponseBody
    void deallocate_wn(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            Map map = new HashMap();
            ServletSupporter support = new ServletSupporter(request);
            int inst_id = support.getIntValue("ins_id");
            String[] wn_str_arr = request.getParameter("data").split(",");
            List<Integer> wns = (new ArrayList<>(Arrays.asList(wn_str_arr))).stream().map(id -> Integer.valueOf(id)).collect(Collectors.toList());
            List<String> refNumbers = new ArrayList<>();
            wns.forEach(wn -> refNumbers.addAll(allocationService.deallocateWn(wn, inst_id, getUser(request))));
            if (refNumbers.size() > 0) {
                map.put("title", "Some weight note receipts cannot be de-allocated");
                map.put("msg", new JSONArray(refNumbers.stream().map(ref -> ImmutableMap.of("a_row", String.format("%s was weighted out", ref))).collect(Collectors.toList())));
            } else {
                map.put("title", "Update Succeeded");
            }
            JSONObject json = new JSONObject(map);
            response.getWriter().print("[" + json.toString() + "]");
        }
    }
}
