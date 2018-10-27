/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.AllocationService;
import com.swcommodities.wsmill.bo.MovementService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.bo.WeightNoteReceiptService;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.Movement;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.object.AllocationList;
import com.swcommodities.wsmill.repository.WnrAllocationRepository;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class AllocationController {

    private final String ALLOCATE = "allocate";
    private final String DEALLOCATE = "deallocate";
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired
    private WeightNoteReceiptService weightNoteReceiptService;
    @Autowired
    private AllocationService allocationService;
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private MovementService movementService;
    @Autowired
    private WnrAllocationRepository wnrAllocationRepository;

    @RequestMapping("get_available_wnr.htm")
    public @ResponseBody
    void get_available_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        int id = new ServletSupporter(request).getIntValue("id");
        ArrayList<WeightNoteReceipt> available_wnr = weightNoteReceiptService.getAvailableWNR(id);
        int perm = Common.getPermissionId(ALLOCATE, context);
        //response.getWriter().print(new GenTemplate(request).generateWnInDetail(context, available_wnr, Constants.AVAILABLE, id));
        response.getWriter().print("[" + allocationService.getDetailWNJson(id, available_wnr, perm, "Allocate", "allocate") + "]");
    }

    @RequestMapping("get_allocated_wnr.htm")
    public @ResponseBody
    void get_allocated_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        int id = new ServletSupporter(request).getIntValue("id");
        int inst_id = new ServletSupporter(request).getIntValue("inst_id");
        int perm = Common.getPermissionId(ALLOCATE, context);
        ArrayList<WeightNoteReceipt> allocated_wnr = weightNoteReceiptService.getAllocatedWNR(id, inst_id);
        response.getWriter().print("[" + allocationService.getDetailWNJson(id, allocated_wnr, perm, "De-Allocate", "deallocate") + "]");
        //response.getWriter().print(new GenTemplate(request).generateWnInDetail(context, available_wnr, Constants.ALLOCATED, id));
    }

    @RequestMapping("get_a_grade_by_ins_id.htm")
    public @ResponseBody
    void get_a_grade_by_ins_id(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        char type = support.getCharValue("type");
        switch (type) {
            case 'P':
                GradeMaster pgrade = processingService.getPIGrades(processingService.getPiById(support.getIntValue("id")));
                int pgrade_id = (pgrade != null) ? pgrade.getId() : -1;
                response.getWriter().print("[" + pgrade_id + "]");
                break;
            case 'E'://shipping
                GradeMaster sgrade = shippingService.getSIGrades(shippingService.getSiById(support.getIntValue("id")));
                int sgrade_id = (sgrade != null) ? sgrade.getId() : -1;
                response.getWriter().print("[" + sgrade_id + "]");
                break;
            default:
                response.getWriter().print("[-1]");
        }

    }
    //alloc_get_inst_by_type

    @RequestMapping("alloc_get_grade_by_type.htm")
    public @ResponseBody
    void alloc_get_grade_by_type(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        GenTemplate gen = new GenTemplate(request);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        char type = support.getCharValue("type");
        switch (type) {
            case 'P':
                ArrayList<GradeMaster> pi_grades = processingService.getAllGrades();
                response.getWriter().print("<li id=\"grade_-1\" class=\"chosen\">All</li>" + gen.generateGradeFilterList(tpl, pi_grades, -1));
                break;
            case 'E'://shipping
                ArrayList<GradeMaster> si_grades = shippingService.getAllGrades();
                response.getWriter().print("<li id=\"grade_-1\" class=\"chosen\">All</li>" + gen.generateGradeFilterList(tpl, si_grades, -1));
                break;
            default:
                response.getWriter().print("");
        }

    }
    //alloc_get_inst_by_type

    @RequestMapping("alloc_get_inst_by_type.htm")
    public @ResponseBody
    void alloc_get_inst_by_type(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        GenTemplate gen = new GenTemplate(request);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        char type = support.getCharValue("type");
        switch (type) {
            case 'P':
                ArrayList<ProcessingInstruction> list = processingService.getAllPIRefList();
                response.getWriter().print("<li id=\"inst_-1\" class=\"chosen\">All</li>" + gen.generateRefList(tpl, list, "inst_"));
                break;
            case 'E'://shipping
                ArrayList<ShippingInstruction> si_list = shippingService.getAllSIRefList();
                response.getWriter().print("<li id=\"inst_-1\" class=\"chosen\">All</li>" + gen.generateRefList(tpl, si_list, "inst_"));
                break;
            case 'M':
                ArrayList<Movement> mlist = movementService.getMovementRefList();
                response.getWriter().print("<li id=\"inst_-1\" class=\"chosen\">All</li>" + gen.generateRefList(tpl, mlist, "inst_"));
                break;
            default:
                response.getWriter().print("");
        }
    }

    //get_inst_info
    @RequestMapping("get_inst_info.htm")
    public @ResponseBody
    void get_inst_info(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        String type = support.getStringRequest("type");
        int id = support.getIntValue("id");
        Map m = allocationService.getInstInfo(id, type);
        response.getWriter().print("[" + (new JSONObject(m)).toString() + "]");
    }

    @RequestMapping("allocation_list_source.htm")
    public @ResponseBody
    void allocation_list_source(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "ref_number", "grade", "packing", "quantity", "allocated", "delivered", "from_date", "to_date"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int grade_id = -1;
        int status = -1;
        int inst_id = -1;
        String from_date = "";
        String to_date = "";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sstatus = request.getParameter("status");
        String type = request.getParameter("type");
        String sInstId = request.getParameter("inst_id");
        String sTitle = "Double Click To View Weight Note Receipt";
        String sFromDate = request.getParameter("from_date");
        String sToDate = request.getParameter("to_date");
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sFromDate != null) {
            from_date = sFromDate;
        }
        if (sToDate != null) {
            to_date = sToDate;
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
        }
        if (sEcho != null) {
            echo = Integer.parseInt(sEcho);
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol + "");
            if (col < 0 || col > 25) {
                col = 0;
            }
        }
        if (sdir != null) {
            if (sdir.equals("asc")) {
                dir = "desc";
            } else {
                dir = "asc";
            }
        }
        if (sInstId != null) {
            inst_id = Integer.parseInt(sInstId);
        }
        if (sGrade != null) {
            grade_id = Integer.parseInt(sGrade);
        }
        if (sstatus != null) {
            status = Integer.parseInt(sstatus);
        }

        String colName = aColumns[col];
        long total = allocationService.countRow(type != null ? type : "P");
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");

        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }
        ArrayList<AllocationList> searchGlobe = allocationService.searchGlobe(searchTerm, sdir, start, amount, colName, grade_id, type, status, inst_id, from_date, to_date);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (AllocationList obj : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + obj.getId());
                ja.put((i++) + "", count);
                ja.put((i++) + "", obj.getRefNumber());
                ja.put((i++) + "", obj.getGrade());
                ja.put((i++) + "", obj.getPacking());
                ja.put((i++) + "", obj.getTotal() + "");
                ja.put((i++) + "", obj.getAllocated() + "");
                ja.put((i++) + "", obj.getDelivered() + "");
                ja.put((i++) + "", obj.getFrom_date());
                ja.put((i++) + "", obj.getTo_date());
                array.put(ja);
                count++;
            }
        }

        Map mtotal = allocationService.countTotals(searchTerm, grade_id, type, status, inst_id, from_date, to_date);
        if (mtotal != null && !mtotal.isEmpty()) {
            int i = 0;
            JSONObject ja = new JSONObject();
            //ja.put(m);
            ja.put("DT_RowClass", "footer");
            ja.put((i++) + "", "");
            ja.put((i++) + "", "Total");
            ja.put((i++) + "", "");
            ja.put((i++) + "", "");
            ja.put((i++) + "", (mtotal.get("tons") != null) ? mtotal.get("tons") : 0);
            ja.put((i++) + "", (mtotal.get("allocated") != null) ? mtotal.get("allocated") : 0);
            ja.put((i++) + "", (mtotal.get("delivered") != null) ? mtotal.get("delivered") : 0);
            ja.put((i++) + "", "");
            ja.put((i++) + "", "");

            array.put(ja);
        }
        totalAfterFilter = allocationService.getTotalAfterFilter();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }
}
