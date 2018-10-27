/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.PalletMasterService;
import com.swcommodities.wsmill.hibernate.dto.PalletMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.ServletSupporter;

/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@Transactional(propagation = Propagation.REQUIRED)
public class PalletController {

    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private PalletMasterService palletMasterService;

    @RequestMapping(value = "get_current_pallet.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_current_pallet(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String last_ref = palletMasterService.getLatestRef();
        String[] splitted_str = last_ref.split("-");
        long number = Long.parseLong(splitted_str[splitted_str.length - 1]);
        Map<String, String> map = new HashMap<>();
        map.put("ref_number", last_ref);
        map.put("number", number + "");
        JSONObject json = new JSONObject(map);
        response.getWriter().print("[" + json + "]");
    }
    //check_pallet

    @RequestMapping(value = "check_pallet.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_pallet(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        Map<String, String> map = new HashMap<>();
        JSONObject json;
        if (palletMasterService.isExist(support.getLongValue("name"))) {
            map.put("name", "SW-BD-PA-" + Common.getPalletRefNumber(support.getLongValue("name")).toString());
        } else {
            map.put("name", "");
        }
        json = new JSONObject(map);
        response.getWriter().print("[" + json.toString() + "]");
    }
    //update_pallet

    @RequestMapping(value = "do_update_pallet.htm", method = RequestMethod.POST)
    public @ResponseBody
    void update_pallet(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        Map<String, String> map = new HashMap<>();
        JSONObject json;
        if (!support.getStringRequest("name").equals("")) {
            PalletMaster pm = palletMasterService.getPalletByRef(support.getStringRequest("name"));
            map.put("name", support.getStringRequest("name"));
            map.put("pvalue", support.getFloatValue("value") + "");
            map.put("pdate", Common.getDateFromDatabase(new Date(), Common.date_format));
            if (pm != null) {
                pm.setValue(support.getFloatValue("value"));
                pm.setLastUpdate(new Date());
                String[][] main_arr = {
                    {"type", "update"},
                    {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                    {"value", support.getFloatValue("value") + ""},
                    {"status",((pm.getStatus() != null) ? pm.getStatus() : 0) + ""},
                    {"date", Common.getDateFromDatabase(new Date(), Common.date_format)}
                };
                String log = ((pm.getLog() != null) ? pm.getLog() + "," : "") + Common.convertToJson((Object) main_arr);
                pm.setLog(log);
                if (palletMasterService.updatePallet(pm) != null) {
                    map.put("pstatus", "1");
                } else {
                    map.put("pstatus", "2");
                }
            } else {
                map.put("pstatus", "2");
            }
            json = new JSONObject(map);
            response.getWriter().print("[" + json.toString() + "]");
        } else {
            response.getWriter().print("[]");
        }
    }

    @RequestMapping(value = "add_new_pallet.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_new_pallet(HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=UTF-8");
        int result = 2;
        try{
        //get quantity request
        int quantity = new ServletSupporter(request).getIntValue("quantity");
        //get latest ref in number.
        String latest_ref = palletMasterService.getLatestRef();
        long latest = Long.parseLong(latest_ref.substring(latest_ref.lastIndexOf("-") + 1, latest_ref.length()));
        for (int i = 0; i < quantity; i++) {
            latest++;
            String ref = "SW-BD-PA-" + Common.getPalletRefNumber(latest).toString();
            PalletMaster pm = new PalletMaster();
            pm.setName(ref);
            pm.setValue(Float.valueOf(0));
            pm.setCreatedDate(new Date());
            pm.setLastUpdate(new Date());
            pm.setStatus(Constants.PENDING);
            String[][] main_arr = {
                {"type", "new"},
                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                {"value", "0"},
                {"status",Constants.PENDING + ""},
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)}
            };
            String log = Common.convertToJson((Object) main_arr);
            pm.setLog(log);
            palletMasterService.updatePallet(pm);
        }
        result = 1;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            response.getWriter().print("[" + result + "]");
        }
    }
}
