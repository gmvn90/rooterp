/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import static com.swcommodities.wsmill.utils.Constants.PENDING;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.bo.GradeService;
import com.swcommodities.wsmill.bo.PackingService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.bo.WeightLossService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingType;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WeightLoss;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.object.ProcessingInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class ProcessingController {

    @Autowired(required = true)
    private ServletContext context;
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private WeightNoteService weightNoteService;
    @Autowired
    private PackingService packingService;
    @Autowired
    private WeightLossService weightLossService;
    @Autowired(required = true)
    private HttpServletRequest request;

    @RequestMapping(value = "update_processing.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void update_processing(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        ProcessingInstruction pi = new ProcessingInstruction();
        //check whether it's new or update existence PI
        boolean exist = false;
        int result = 0;
        Float weight_loss = supporter.getFloatValue("wl_value");
        String ref_number = supporter.getStringRequest("ref_number");
        if (ref_number != null && !ref_number.equals("")) {
            //check ref number exist or not
            pi = processingService.getPiByRef(ref_number);
            if (pi == null) {
                response.getWriter().print("-1");
            }
        } else {
            //new
            result = 1;
            pi.setRefNumber(processingService.getNewContractRef());
            pi.setCreatedDate(new Date());
        }

        CompanyMaster client = companyService.getCompanyById(supporter.getIntValue("client"));
        String client_ref = supporter.getStringRequest("client_ref");
        ProcessingType pType = processingService.getTypeById(supporter.getIntValue("processing_type"));
        int origin = supporter.getIntValue("origin");
        int quality = supporter.getIntValue("quality");
        GradeMaster grade = gradeService.getGradeById(supporter.getIntValue("grade"));
        PackingMaster packing = packingService.getPackingById(supporter.getIntValue("packing"));
        Float quantity = supporter.getFloatValue("quantities");
        Date from = supporter.getDateValue("txtFrom", Common.date_format_ddMMyyyy_dash);
        Date to = supporter.getDateValue("txtTo", Common.date_format_ddMMyyyy_dash);
        String remark = supporter.getStringRequest("remark");
        Byte status = supporter.getByteValue("completed");
        User user = (User) request.getSession().getAttribute("user");

        //get the rest element in the form and return id if it's new PI, 0 if it's an update
        pi.setCompanyMasterByClientId(client);
        pi.setClientRef(client_ref);
        pi.setProcessingType(pType);
        pi.setOriginId(origin);
        pi.setQualityId(quality);
        pi.setGradeMaster(grade);
        pi.setPackingMaster(packing);
        pi.setQuantity(quantity);
        pi.setFromDate(from);
        pi.setToDate(to);
        pi.setRemark(remark);
        pi.setStatus(status);
        pi.setUser(user);

        String[][] element_arr = {
                {"client", client.getName()},
                {"client_ref", client_ref},
                {"processing_type", pType.getName()},
                {"origin", origin + ""},
                {"quality", quality + ""},
                {"grade", grade.getName()},
                {"packing", packing.getName()},
                {"from", Common.getDateFromDatabase(from, Common.date_format)},
                {"to", Common.getDateFromDatabase(to, Common.date_format)},
                {"remark", remark},
                {"status", status + ""},};
        String[][] main_arr = {
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                {"type", "update"},
                {"user", user.getUserName()},
                {"element", Common.convertToJson((Object) element_arr)}
        };
        String log = ((pi.getLog() != null) ? pi.getLog() : "") + Common.convertToJson((Object) main_arr);
        pi.setLog(log);

        int a = processingService.updateProcess(pi);
        WeightLoss wl = weightLossService.getWeightLossByPiId(pi.getId());
        if (wl != null) {
            if (supporter.getByteValue("completed") == Constants.COMPLETE) {
                wl.setWeightLoss(weight_loss);
                weightLossService.updateWeightLoss(wl);
            } else {
                weightLossService.deleteWeightLoss(wl);
            }
        } else {
            if (supporter.getByteValue("completed") == Constants.COMPLETE) {
                wl = new WeightLoss(pi, "", weight_loss);
                weightLossService.updateWeightLoss(wl);
            }
        }

        response.getWriter().print(a);

    }

    @RequestMapping(value = "update_processing_v2.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void update_processing_v2(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        ProcessingInstruction pi = new ProcessingInstruction();

        CompanyMaster client = companyService.getCompanyById(supporter.getIntValue("client"));
        String client_ref = supporter.getStringRequest("clientRef");
        ProcessingType pType = processingService.getTypeById(supporter.getIntValue("processingType"));
        GradeMaster grade = gradeService.getGradeById(supporter.getIntValue("grade"));
        PackingMaster packing = packingService.getPackingById(supporter.getIntValue("packing"));
        Float quantity = supporter.getFloatValue("quantities");
        String creditDateStr = supporter.getStringRequest("requestedCreditDate");
        Date creditDate = new Date();
        if (!creditDateStr.equals("")) {
            creditDate = supporter.getDateValue("requestedCreditDate", Common.date_format_a);
        }
        String remark = supporter.getStringRequest("remark");
        String requestRemark = supporter.getStringRequest("requestRemark");
        User user = (User) request.getSession().getAttribute("user");

        //check whether it's new or update existence PI
        boolean exist = false;
        int result = 0;
        String ref_number = supporter.getStringRequest("refNumber");
        if (ref_number != null && !ref_number.equals("")) {
            //check ref number exist or not
            pi = processingService.getPiByRef(ref_number);

        } else {
            //new
            result = 1;
            pi.setRefNumber(processingService.getNewContractRef());
            pi.setCreatedDate(new Date());
            pi.setStatus(Constants.PENDING);
            pi.setRequestStatus(Constants.PENDING);
            pi.setUserByUpdateRequestUserId(user);
            pi.setUserByUpdateCompletionUserId(user);
        }

        //get the rest element in the form and return id if it's new PI, 0 if it's an update
        pi.setCompanyMasterByClientId(client);
        pi.setClientRef(client_ref);
        pi.setProcessingType(pType);
        pi.setGradeMaster(grade);
        pi.setPackingMaster(packing);
        pi.setQuantity(quantity);
        pi.setRemark(remark);
        pi.setRequestRemark(requestRemark);
        if (!creditDateStr.equals("")) {
            pi.setCreditDate(creditDate);
        }

        pi.setUser(user);

        int a = processingService.updateProcess(pi);

        response.getWriter().print(a);

    }

    @RequestMapping(value = "add_PI_fromCB.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void add_processing_CB(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        /* process request and get data*/
        try {
            StringBuilder jb = new StringBuilder();
            String line;
            BufferedReader reader;

            /* read data from xhr request */
            reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }

            /* parse data to json object to get value */
            JSONObject Obj = new JSONObject(jb.toString());
            JSONObject supporter = Obj.getJSONObject("instr");
                        /* create new PI for this request */
            ProcessingInstruction pi;
            String abc = supporter.get("mill_instr_id").toString();
            if (!supporter.get("mill_instr_id").toString().equals("null")) {
                //check pi id exist or not
                pi = processingService.getPiById(supporter.getInt("mill_instr_id"));
                if (pi == null) {
                    response.getWriter().print("-1");
                }
            } else {
                pi = new ProcessingInstruction();
                pi.setRefNumber(processingService.getNewContractRef());
                pi.setCreatedDate(new Date());
            }
            /* create new PI for this request */
//            ProcessingInstruction pi = new ProcessingInstruction();

            CompanyMaster client = companyService.getCompanyById(supporter.getInt("client_id"));
            String client_ref = supporter.getString("client_ref");
            GradeMaster grade = gradeService.getGradeById(supporter.getInt("grade_id"));

//            ProcessingType pType = processingService.getTypeById(supporter.getInt("processing_type"));
            int origin = grade.getOriginId();
            int quality = grade.getQualityId();
            PackingMaster packing = packingService.getPackingById(supporter.getInt("packing_id"));
            Float quantity = Float.parseFloat(supporter.getString("quantity"));
            Date from = Common.convertStringToDate(supporter.getString("request_credit_date"), Common.date_format_ddMMyyyy_dash);
            Date to = Common.convertStringToDate(supporter.getString("request_credit_date"), Common.date_format_ddMMyyyy_dash);
            String remark = supporter.getString("detail_remark");
            Byte status = PENDING;
            User user = (User) request.getSession().getAttribute("user");

            //get the rest element in the form and return id if it's new PI, 0 if it's an update
            pi.setCompanyMasterByClientId(client);
            pi.setClientRef(client_ref);
//            pi.setProcessingType(pType);
            pi.setOriginId(origin);
            pi.setQualityId(quality);
            pi.setGradeMaster(grade);
            pi.setPackingMaster(packing);
            pi.setQuantity(quantity);
            pi.setFromDate(from);
            pi.setToDate(to);
            pi.setRemark(remark);
            pi.setStatus(status);
            pi.setUser(user);

            String[][] element_arr = {
                    {"client", client.getName()},
                    {"client_ref", client_ref},
                    //                {"processing_type", pType.getName()},
                    {"origin", origin + ""},
                    {"quality", quality + ""},
                    {"grade", grade.getName()},
                    {"packing", packing.getName()},
                    {"from", Common.getDateFromDatabase(from, Common.date_format)},
                    {"to", Common.getDateFromDatabase(to, Common.date_format)},
                    {"remark", remark},
                    {"status", status + ""},};
            String[][] main_arr = {
                    {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                    {"type", "update"},
                    {"user", "Client : " + client.getName() + " through CB system"},
                    {"element", Common.convertToJson((Object) element_arr)}
            };
            String log = ((pi.getLog() != null) ? pi.getLog() : "") + Common.convertToJson((Object) main_arr);
            pi.setLog(log);

            int a = processingService.updateProcess(pi);


            /*=========== return value for this request =============*/
            JSONObject jo = new JSONObject();
            jo.put("status", "success");
            jo.put("id", pi.getId());
            response.getWriter().print(jo.toString());

        } catch (IOException | JSONException | NumberFormatException | ParseException e) {
            System.out.println("Error: From CB request - " + e);
            /*report an error*/
            JSONObject jo = new JSONObject();
            jo.put("status", "failed");
            jo.put("reason", e.toString());
            response.getWriter().print(jo.toString());
        }
    }

    @RequestMapping(value = "processing_ref_list.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void processing_ref_list(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int firstResult = supporter.getIntValue("result_no");
        String searchString = supporter.getStringRequest("filter_ref");
        ArrayList<ProcessingInstruction> list = processingService.getProcessingRefList(searchString);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        response.getWriter().print(new GenTemplate(request).generateProcessRefList(tpl, list, -1));
    }

    @RequestMapping(value = "load_detail_po.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void load_detail_po(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        ProcessingInstruction pi = processingService.getPiById(supporter.getIntValue("po_id"));
        Map m = processingService.countAllocated(pi.getId());
        if (pi != null) {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/Processing.html"));
            response.getWriter().print(new GenTemplate(request).generateProcessDetailPage(tpl, pi, m));
        } else {
            response.getWriter().print("Loading failed");
        }
    }

    @RequestMapping(value = "load_detail_po_v2.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void load_detail_po_v2(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        ProcessingInstruction pi = processingService.getPiById(supporter.getIntValue("po_id"));
        Map m = processingService.countAllocated(pi.getId());
        if (pi != null) {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/Processing_v2.html"));
            response.getWriter().print(new GenTemplate(request).generateProcessDetailPage_v2(tpl, pi));
        } else {
            response.getWriter().print("Loading failed");
        }
    }

    @RequestMapping(value = "empty_po.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void empty_po(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/Processing.html"));
        response.getWriter().print(new GenTemplate(request).generateEmptyProcessPage(tpl));
    }

    @RequestMapping(value = "empty_po_v2.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void empty_po_v2(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/Processing_v2.html"));
        response.getWriter().print(new GenTemplate(request).generateEmptyProcessPage_v2(tpl));
    }

    @RequestMapping(value = "load_pending_pi.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void load_pending_pi(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Map m = processingService.getPendingProcessingMap();
        if (!m.isEmpty()) {
            JSONObject json = new JSONObject(processingService.getPendingProcessingMap());
            response.getWriter().print(json.toString());
        } else {
            response.getWriter().print("-1");
        }
    }

    @RequestMapping("processing_list_source.htm")
    public
    @ResponseBody
    void processing_list_source(HttpServletResponse response) throws Exception {
        DecimalFormat decim = new DecimalFormat("0.000");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "ref_number", "origin_id", "quality_id", "grade_name", "packing_name", "allocated", "in_process", "ex_process", "pending", "from_date", "to_date"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int grade_id = 0;
        int cli_id = 0;
        Byte stt = 0;
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sCli = request.getParameter("sup");
        String sStt = request.getParameter("stt");
        String sTitle = "Double Click To View Weight Note Receipt";
        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
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
        if (sGrade != null) {
            grade_id = Integer.parseInt(sGrade);
        }
        if (sCli != null) {
            cli_id = Integer.parseInt(sCli);
        }
        if (sStt != null) {
            stt = Byte.parseByte(sStt);
        }

        String colName = aColumns[col];
        long total = processingService.countRow();
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");

        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }
        ArrayList<ProcessingInstructionObj> searchGlobe = processingService.searchProcessingIns(searchTerm, sdir, start, amount, colName, grade_id, cli_id, stt);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (ProcessingInstructionObj pi : searchGlobe) {
                // ProcessingInstruction temp_pi = processingService.getPiById(pi.getPi_id());
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + pi.getPi_id());
                ja.put((i++) + "", count);
                ja.put((i++) + "", pi.getRef_number());
                ja.put((i++) + "", pi.getOrigin());
                ja.put((i++) + "", pi.getQuality());
                ja.put((i++) + "", pi.getGrade_name());
                ja.put((i++) + "", pi.getPacking_name());
                ja.put((i++) + "", decim.format(pi.getAllocated() / 1000));
                ja.put((i++) + "", decim.format(pi.getIn_process() / 1000));
                ja.put((i++) + "", decim.format(pi.getEx_process() / 1000));
                ja.put((i++) + "", (pi.getStatus() == Constants.COMPLETE) ? "0" : (decim.format(pi.getPending() / 1000)));
                ja.put((i++) + "", Common.getDateFromDatabase(pi.getFrom_date(), Common.date_format_a));
                ja.put((i++) + "", Common.getDateFromDatabase(pi.getTo_date(), Common.date_format_a));
                array.put(ja);
                count++;
            }
        }

        Map mtotal = processingService.countTotals(searchTerm, grade_id, cli_id, stt);

        int i = 0;
        JSONObject ja = new JSONObject();
        //ja.put(m);
        ja.put("DT_RowClass", "footer");
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", (mtotal.get("allocated") != null ? mtotal.get("allocated") : "0"));
        ja.put((i++) + "", (mtotal.get("inprocess") != null ? mtotal.get("inprocess") : "0"));
        ja.put((i++) + "", (mtotal.get("exprocess") != null ? mtotal.get("exprocess") : "0"));
        ja.put((i++) + "", (mtotal.get("pending") != null ? mtotal.get("pending") : "0"));
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        array.put(ja);

        totalAfterFilter = processingService.getTotalAfterFilter();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }

    @RequestMapping(value = "pi_get_client_filter.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void pi_get_sup_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        ArrayList<CompanyMaster> list = processingService.getCompanyInPi();
        response.getWriter().print(new GenTemplate(request).generateCompanyFilterList(tpl, list, "cli"));
    }

    @RequestMapping(value = "pi_get_grade_filter.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String pi_get_grade_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<GradeMaster> grades = processingService.getAllGrades();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateGradeFilterList(tpl, grades, -1);
    }

    @RequestMapping(value = "delete_po.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void delete_po(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int po_id = support.getIntValue("po_id");
        String reason = support.getStringRequest("reason");
        ProcessingInstruction po = processingService.getPiById(po_id);
        String username = ((User) request.getSession().getAttribute("user")).getUserName();
        boolean doDelete = processingService.delete_po(po, username, reason);
        if (!doDelete) {
            String message = "[2]";
            response.getWriter().print(message);
        } else {
            String message = "[1]";
            response.getWriter().print(message);
        }
    }

    @RequestMapping(value = "delete_po_surround.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void delete_po_surround(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int po_id = support.getIntValue("po_id");
        ProcessingInstruction po = processingService.getPiById(po_id);
        JSONObject json = new JSONObject();
        json = processingService.delete_po_surround(po);
        response.getWriter().print("[" + json.toString() + "]");

    }

    @RequestMapping(value = "check_pi_complete.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void check_pi_complete(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String instruction_isnot_used = "[2]";
        String weightnote_err = "[-1]";
        String oke = "[1]";
        ServletSupporter support = new ServletSupporter(request);
        int inst_id = support.getIntValue("pi_id");
        int type = support.getIntValue("type");
        //default di
        Map m = processingService.countAllocated(inst_id);

        ArrayList<WeightNote> wns = weightNoteService.getWeightNoteFromInst(inst_id, type);
        if (wns != null && !wns.isEmpty()) {
            boolean flag = true;
            for (WeightNote wn : wns) {
                if (wn.getStatus().equals(Constants.PENDING)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (Common.getFloatValue(m.get("in_process")).equals((float) 0) || Common.getFloatValue(m.get("ex_process")).equals((float) 0)) {
                    response.getWriter().print(instruction_isnot_used);
                } else {
                    response.getWriter().print(oke);
                }
            } else {
                response.getWriter().print(weightnote_err);
            }
        } else {
            response.getWriter().print(weightnote_err);
        }
    }

    @RequestMapping(value = "saveCompletionStatus.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void saveCompletionStatus(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String instruction_isnot_used = "2";
        String weightnote_err = "-1";
        String oke = "1";
        ServletSupporter support = new ServletSupporter(request);
        User user = (User) request.getSession().getAttribute("user");
        int inst_id = support.getIntValue("piId");
        int type = support.getIntValue("type");
        Byte completionStatus = support.getByteValue("status");
        if (completionStatus.equals(Byte.parseByte("0"))) {
            ProcessingInstruction pi = processingService.getPiById(inst_id);
            pi.setUserByUpdateCompletionUserId(user);
            pi.setStatus(completionStatus);
            pi.setCompletionStatusDate(new Date());
            int a = processingService.updateProcess(pi);
            response.getWriter().print(user.getUserName() + ":" + Common.getDateFromDatabase(new Date(), Common.date_format));
        } else {

            //default di
            Map m = processingService.countAllocated(inst_id);

            ArrayList<WeightNote> wns = weightNoteService.getWeightNoteFromInst(inst_id, type);
            if (wns != null && !wns.isEmpty()) {
                boolean flag = true;
                for (WeightNote wn : wns) {
                    if (wn.getStatus().equals(Constants.PENDING)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    if (Common.getFloatValue(m.get("in_process")).equals((float) 0) || Common.getFloatValue(m.get("ex_process")).equals((float) 0)) {
                        response.getWriter().print(instruction_isnot_used);
                    } else {
                        ProcessingInstruction pi = processingService.getPiById(inst_id);
                        pi.setUserByUpdateCompletionUserId(user);
                        pi.setStatus(completionStatus);
                        pi.setCompletionStatusDate(new Date());
                        int a = processingService.updateProcess(pi);
                        response.getWriter().print(user.getUserName() + ":" + Common.getDateFromDatabase(new Date(), Common.date_format));
                    }
                } else {
                    response.getWriter().print(weightnote_err);
                }
            } else {
                response.getWriter().print(weightnote_err);
            }
        }
    }

    @RequestMapping(value = "saveRequestStatus.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    void saveRequestStatus(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        User user = (User) request.getSession().getAttribute("user");
        int inst_id = support.getIntValue("piId");
        Byte requestStatus = support.getByteValue("requestStatus");
        ProcessingInstruction pi = processingService.getPiById(inst_id);
        pi.setUserByUpdateRequestUserId(user);
        pi.setRequestStatus(requestStatus);
        pi.setRequestStatusDate(new Date());
        int a = processingService.updateProcess(pi);
        response.getWriter().print(user.getUserName() + ":" + Common.getDateFromDatabase(new Date(), Common.date_format));
    }
}
