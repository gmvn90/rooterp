/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import static com.swcommodities.wsmill.utils.Constants.PENDING;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.GradeService;
import com.swcommodities.wsmill.bo.PackingService;
import com.swcommodities.wsmill.bo.UserService;
import com.swcommodities.wsmill.bo.WarehouseService;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.Warehouse;
import com.swcommodities.wsmill.hibernate.dto.view.DeliveryView;
import com.swcommodities.wsmill.object.DeliveryInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 *
 * @author duhc
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class DeliveryInsController {

    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private DeliveryInsService deliveryInsService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private PackingService packingService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "di_get_sup_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    void di_get_sup_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        ArrayList<CompanyMaster> list = deliveryInsService.getCompanyInDi("supplier");
        response.getWriter().print(new GenTemplate(request).generateCompanyFilterList(tpl, list, "sup"));
    }

    @RequestMapping(value = "di_get_buyer_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    void di_get_buyer_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        ArrayList<CompanyMaster> list = deliveryInsService.getCompanyInDi("buyer");
        response.getWriter().print(new GenTemplate(request).generateCompanyFilterList(tpl, list, "buyer"));
    }

    @RequestMapping(value = "di_get_grade_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    String di_get_grade_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<GradeMaster> grades = deliveryInsService.getAllGrades();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateGradeFilterList(tpl, grades, -1);
    }

    @RequestMapping(value = "update_deliveryIns.htm", method = RequestMethod.POST)
    public @ResponseBody
    void update_delivery(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter supporter = new ServletSupporter(request);
            DeliveryInstruction di = new DeliveryInstruction();

            boolean exist = false;
            int result = 0;
            String ref_number = supporter.getStringRequest("ref_number");
            if (ref_number != null && !ref_number.equals("")) {
                di = deliveryInsService.getDiByRef(ref_number);
                if (di == null) {
                    response.getWriter().print("-1");
                }
            } else {
                result = 1;
                di.setRefNumber(deliveryInsService.getNewContractRef());
                di.setDate(new Date());
            }
            CompanyMaster client = companyService.getCompanyById(supporter.getIntValue("client"));
            User user = (User) request.getSession().getAttribute("user");
            CompanyMaster supplier = companyService.getCompanyById(supporter.getIntValue("supplier"));
            PackingMaster packing = packingService.getPackingById(supporter.getIntValue("packing"));
            Warehouse warehouse = warehouseService.getWarehouseById(supporter.getIntValue("warehouse"));
            CompanyMaster weightControl = companyService.getCompanyById(supporter.getIntValue("weight_controller"));
            CompanyMaster qualityControl = companyService.getCompanyById(supporter.getIntValue("quality_controller"));
            GradeMaster grade = gradeService.getGradeById(supporter.getIntValue("grade"));
            Double tons = supporter.getDoubleValue("quantity");
            Float kgPerBag = supporter.getFloatValue("kg_per_bag");
            int noOfBags = supporter.getIntValue("no_of_bag");
            Date deliveryDate = supporter.getDateValue("delivery_date", Common.date_format_ddMMyyyy_dash);
            String from = supporter.getStringRequest("from_time");
            String to = supporter.getStringRequest("to_time");
            String marking = supporter.getStringRequest("no_marking_on_bags");
            int origin = supporter.getIntValue("origin");
            int quality = supporter.getIntValue("quality");
            String remark = supporter.getStringRequest("remark");
            Byte status = supporter.getByteValue("completed");

            di.setCompanyMasterByClientId(client);
            di.setClientRef(supporter.getStringRequest("client_ref"));
            di.setCompanyMasterBySupplierId(supplier);
            di.setSupplierRef(supporter.getStringRequest("supplier_ref"));
            di.setPackingMaster(packing);
            di.setTons(tons);
            di.setKgPerBag(kgPerBag);
            di.setNoOfBags(noOfBags);
            di.setWarehouse(warehouse);
            di.setDeliveryDate(deliveryDate);
            di.setFromTime(from);
            di.setToTime(to);
            di.setMarkingOnBags(marking);
            di.setCompanyMasterByWeightControllerId(weightControl);
            di.setCompanyMasterByQualityControllerId(qualityControl);
            di.setOriginId(origin);
            di.setQualityId(quality);
            di.setGradeMaster(grade);
            di.setRemark(remark);
            di.setStatus(status);
            di.setUser(user);

            String[][] element_arr = {
                {"client", client.getName()},
                {"supplier", supplier.getName()},
                {"packing", packing.getName()},
                {"tons", tons + ""},
                {"kgPerBag", kgPerBag + ""},
                {"noOfBags", noOfBags + ""},
                {"kgPerBag", kgPerBag + ""},
                {"deliveryDate", Common.getDateFromDatabase(deliveryDate, Common.date_format)},
                {"from", from},
                {"to", to},
                {"marking", marking},
                {"weightControl", weightControl.getName()},
                {"qualityControl", qualityControl.getName()},
                {"origin", origin + ""},
                {"quality", quality + ""},
                {"grade", grade.getName()},
                {"remark", remark}
            };
            String[][] main_arr = {
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                {"type", "update"},
                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                {"element", Common.convertToJson((Object) element_arr)}
            };
            String log = ((di.getLog() != null) ? di.getLog() : "") + Common.convertToJson((Object) main_arr);
            di.setLog(log);

            response.getWriter().print(deliveryInsService.updateDeliveryIns(di));
        } else {
            response.getWriter().print(-1);
        }
    }

    @RequestMapping(value = "add_DI_fromCB.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_delivery_fromCB(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
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
            JSONObject jsonData = Obj.getJSONObject("instr");

            /* create new DI for this request */
            DeliveryInstruction di;
            if (!jsonData.get("mill_instr_id").toString().equals("null")) {
                /* find in Mill for this DI */
                di = deliveryInsService.getDiById(jsonData.getInt("mill_instr_id"));
            } else {
                /* if this DI is not exist in Mill, add new */
                di = new DeliveryInstruction();
                di.setRefNumber(deliveryInsService.getNewContractRef());
                di.setDate(new Date());
            }
            
            /* get data value from ajax request for this DI*/
            CompanyMaster client = companyService.getCompanyById(jsonData.getInt("client_id"));
            User user = (User) request.getSession().getAttribute("user");
            CompanyMaster supplier = companyService.getCompanyById(jsonData.getInt("supplier_id"));
            PackingMaster packing = packingService.getPackingById(jsonData.getInt("packing_id"));
            Warehouse warehouse = warehouseService.getWarehouseById(jsonData.getInt("warehouse_id"));
            CompanyMaster weightControl = companyService.getCompanyById(jsonData.getInt("weight_controller_id"));
            CompanyMaster qualityControl = companyService.getCompanyById(jsonData.getInt("quality_controller_id"));
            GradeMaster grade = gradeService.getGradeById(jsonData.getInt("grade_id"));
            Double tons = jsonData.getDouble("tons");
            Float kgPerBag = Float.parseFloat(jsonData.getString("kg_per_bag"));
            int noOfBags = jsonData.getInt("no_of_bags");
            Date deliveryDate;
            deliveryDate = Common.convertStringToDate(jsonData.getString("delivery_date"), Common.date_format_yyyyMMdd);
            String from = jsonData.getString("from_time");
            if ("null".equals(from)) {
                from = "";
            }
            String to = jsonData.getString("to_time");
            if ("null".equals(to)) {
                to = "";
            }
            String marking = jsonData.getString("marking_on_bags");
            if ("null".equals(marking)) {
                marking = "";
            }
            int origin = jsonData.getInt("origin_id");
            int quality = jsonData.getInt("quality_id");
            String remark = jsonData.getString("remark");
            /* set status with pending */
            Byte status = PENDING;

            /* set value for this DI */
            di.setCompanyMasterByClientId(client);
            di.setClientRef(jsonData.getString("client_ref"));
            di.setCompanyMasterBySupplierId(supplier);
            di.setSupplierRef(jsonData.getString("supplier_ref"));
            di.setPackingMaster(packing);
            di.setTons(tons);
            di.setKgPerBag(kgPerBag);
            di.setNoOfBags(noOfBags);
            di.setWarehouse(warehouse);
            di.setDeliveryDate(deliveryDate);
            di.setFromTime(from);
            di.setToTime(to);
            di.setMarkingOnBags(marking);
            di.setCompanyMasterByWeightControllerId(weightControl);
            di.setCompanyMasterByQualityControllerId(qualityControl);
            di.setOriginId(origin);
            di.setQualityId(quality);
            di.setGradeMaster(grade);
            di.setRemark(remark);
            di.setStatus(status);
            di.setUser(user);

            /* set log for this DI*/
            String[][] element_arr = {
                {"client", client.getName()},
                {"supplier", supplier.getName()},
                {"packing", packing.getName()},
                {"tons", tons + ""},
                {"kgPerBag", kgPerBag + ""},
                {"noOfBags", noOfBags + ""},
                {"kgPerBag", kgPerBag + ""},
                {"deliveryDate", Common.getDateFromDatabase(deliveryDate, Common.date_format)}, //                {"from", from},
            //                {"to", to},
            //                {"marking", marking},
            //                {"weightControl", weightControl.getName()},
            //                {"qualityControl", qualityControl.getName()},
            //                {"origin", origin + ""},
            //                {"quality", quality + ""},
            //                {"grade", grade.getName()},
            //                {"remark", remark}
            };
            String[][] main_arr = {
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                {"type", "update"},
                {"user", "Client : " + client.getName() + " through CB system"},
                {"element", Common.convertToJson((Object) element_arr)}
            };
            String log = ((di.getLog() != null) ? di.getLog() : "") + Common.convertToJson((Object) main_arr);
            di.setLog(log);

            /* save new DI to database */
            deliveryInsService.updateDeliveryIns(di);
            /* return value for this request */
            JSONObject jo = new JSONObject();
            jo.put("status", "success");
            jo.put("id", di.getId());
            response.getWriter().print(jo.toString());

        } catch (IOException | JSONException | NumberFormatException | ParseException e) {
            System.out.println("Error: From "
                    + " request - " + e);
            /*report an error*/
            JSONObject jo = new JSONObject();
            jo.put("status", "failed");
            jo.put("reason", e.toString());
            response.getWriter().print(jo.toString());
        }
    }

    @RequestMapping(value = "deliveryIns_ref_list.htm", method = RequestMethod.POST)
    public @ResponseBody
    void deliveryIns_ref_list(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int firstResult = supporter.getIntValue("result_no");
        String searchString = supporter.getStringRequest("filter_ref");
        ArrayList<DeliveryInstruction> list = deliveryInsService.getDeliveryInsRefList(searchString);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        response.getWriter().print(new GenTemplate(request).generateDeliveryInsRefList(tpl, list));
    }

    @RequestMapping(value = "load_detail_di.json", method = RequestMethod.POST)
    public @ResponseBody
    DeliveryView load_detail_di(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        DeliveryView di = deliveryInsService.getLazyDiById(supporter.getIntValue("id"));
//        Map m = deliveryInsService.countDelivered(di.getId(), di.getStatus());
        if (di != null) {
//            response.setContentType("text/html;charset=UTF-8");
//            Template tpl = new Template(new File(context.getRealPath("/") + "templates/DeliveryIns.html"));
//            response.getWriter().print(new GenTemplate(request).generateDeliveryInsDetailPage(tpl, di, m));
            return di;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "getCountDelivered.htm", method = RequestMethod.POST)
    public @ResponseBody
    void getCountDelivered(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        int id = supporter.getIntValue("id");
        byte status = supporter.getByteValue("status");
        Map m = deliveryInsService.countDelivered(id, status);
        JSONObject json = new JSONObject(m);
        response.getWriter().print(json.toString());
    }
    //load_user_name

    @RequestMapping(value = "load_user_name.htm", method = RequestMethod.POST)
    public @ResponseBody
    String load_user_name(HttpServletResponse response) throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        int id = supporter.getIntValue("id");
        return userService.getUserNameById(id);
    }

    @RequestMapping(value = "empty_do.htm", method = RequestMethod.POST)
    public @ResponseBody
    void empty_do(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/DeliveryIns.html"));
        response.getWriter().print(new GenTemplate(request).generateEmptyDeliveryInsPage(tpl));
    }

    @RequestMapping("delivery_list_source.htm")
    public @ResponseBody
    void delivery_list_source(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "buyer_name", "ref_number", "supp_name", "origin_id", "quality_id", "grade_name", "packing_name", "tons", "net_weight", "pending", "delivery_date", "from_time", "to_time"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int grade_id = 0;
        int sup_id = 0;
        int buy_id = 0;
        Byte stt = 0;
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sSup = request.getParameter("sup");
        String sBuy = request.getParameter("buy");
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
        if (sSup != null) {
            sup_id = Integer.parseInt(sSup);
        }
        if (sBuy != null) {
            buy_id = Integer.parseInt(sBuy);
        }
        if (sStt != null) {
            stt = Byte.parseByte(sStt);
        }

        String colName = aColumns[col];
        long total = deliveryInsService.countRow();
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");

        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }

        ArrayList<DeliveryInstructionObj> searchGlobe = deliveryInsService.searchDeliveryIns(searchTerm, sdir, start, amount, colName, grade_id, sup_id, buy_id, stt);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (DeliveryInstructionObj di : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + di.getDi_id());
                ja.put((i++) + "", count);
                ja.put((i++) + "", di.getBuyer_name());
                ja.put((i++) + "", di.getRef_number());
                ja.put((i++) + "", di.getSupp_name());
                ja.put((i++) + "", di.getOrigin());
                ja.put((i++) + "", di.getQuality());
                ja.put((i++) + "", di.getGrade_name());
                ja.put((i++) + "", di.getPacking_name());
                ja.put((i++) + "", di.getTons());
                ja.put((i++) + "", di.getNet_weight() + "");
                ja.put((i++) + "", di.getPendding() + "");
                ja.put((i++) + "", Common.getDateFromDatabase(di.getDelivery_date(), Common.date_format_a));
                ja.put((i++) + "", di.getFrom_time());
                ja.put((i++) + "", di.getTo_time());
                array.put(ja);
                count++;
            }
        }

        Map mtotal = deliveryInsService.countTotals(searchTerm, grade_id, sup_id, buy_id, stt);

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
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", (mtotal.get("total") != null ? mtotal.get("total") : "0"));
        ja.put((i++) + "", (mtotal.get("delivered") != null ? mtotal.get("delivered") : "0"));
        ja.put((i++) + "", (mtotal.get("pending") != null ? mtotal.get("pending") : "0"));
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        ja.put((i++) + "", "");
        array.put(ja);

        totalAfterFilter = deliveryInsService.getTotalAfterFilter();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }

    @RequestMapping(value = "delete_di.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_di(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int di_id = support.getIntValue("di_id");
        String reason = support.getStringRequest("reason");
        DeliveryInstruction di = deliveryInsService.getDiById(di_id);
        String username = ((User) request.getSession().getAttribute("user")).getUserName();
        boolean doDelete = deliveryInsService.delete_di(di, username, reason);
        if (!doDelete) {
            String message = "[2]";
            response.getWriter().print(message);
        } else {
            String message = "[1]";
            response.getWriter().print(message);
        }
    }

    @RequestMapping(value = "delete_di_surround.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_di_surround(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int di_id = support.getIntValue("di_id");
        DeliveryInstruction di = deliveryInsService.getDiById(di_id);
        JSONObject json = new JSONObject();
        json = deliveryInsService.delete_di_surround(di);
        response.getWriter().print("[" + json.toString() + "]");

    }
}
