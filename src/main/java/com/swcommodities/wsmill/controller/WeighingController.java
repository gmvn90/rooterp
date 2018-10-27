/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.swcommodities.wsmill.bo.AllocationService;
import com.swcommodities.wsmill.bo.AuthorizationService;
import com.swcommodities.wsmill.bo.CommonService;
import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.GradeService;
import com.swcommodities.wsmill.bo.PackingService;
import com.swcommodities.wsmill.bo.PalletMasterService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.bo.QualityReportService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.bo.WarehouseCellService;
import com.swcommodities.wsmill.bo.WeightNoteReceiptService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CupTest;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;
import com.swcommodities.wsmill.hibernate.dto.PalletMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.hibernate.dto.view.DeliveryView;
import com.swcommodities.wsmill.object.WeighingObj;
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
public class WeighingController {

    private static final Logger logger = Logger.getLogger(WeighingController.class);
    private final String DELETE_WNR = "delete_wnr";
    private final String SAVE_WNR = "save_wnr";
    @Autowired
    private AllocationService allocationService;
    @Autowired
    private WarehouseCellService warehouseCellService;
    @Autowired
    private QualityReportService qualityReportService;
    @Autowired
    private PalletMasterService palletMasterService;
    @Autowired
    private WeightNoteReceiptService weightNoteReceiptService;
    @Autowired
    private WeightNoteService weightNoteService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private PackingService packingService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private DeliveryInsService deliveryInsService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private ShippingService shippingService;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;

    @RequestMapping(value = "get_ins_ref.htm", method = RequestMethod.POST)
    public @ResponseBody
    String get_ins_ref(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_type = support.getIntValue("wn_type");
        switch (wn_type) {
            case 1:
                return weightNoteService.getDIRefFilterList(request, context);
            case 2:
            case 3:
                return weightNoteService.getPIRefFilterList(request, context);
            case 4:
                return weightNoteService.getSIRefFilterList(request, context);
            default:
                return "";
        }
    }

    @RequestMapping(value = "gradeInIM.htm", method = RequestMethod.POST)
    public @ResponseBody
    void gradeInIM(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<HashMap> grades = gradeService.getAllGradeNamesMap();
        JSONObject json = new JSONObject();
        json.put("select", new JSONArray(grades));
        response.getWriter().print(json.toString());
    }

    @RequestMapping(value = "gradeInIP.htm", method = RequestMethod.POST)
    public @ResponseBody
    void gradeInIP(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<HashMap> grades = gradeService.getAllGradeNamesMap();
        JSONObject json = new JSONObject();
        json.put("select", new JSONArray(grades));
        response.getWriter().print(json.toString());
    }

    @RequestMapping(value = "gradeInAllocate.htm", method = RequestMethod.POST)
    public @ResponseBody
    void gradeInAllocate(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        ArrayList<HashMap> grades = gradeService.getAllGradeNamesMap();
        JSONObject json = new JSONObject();
        json.put("select", new JSONArray(grades));
        response.getWriter().print(json.toString());
    }

    @RequestMapping(value = "gradeInXP.htm", method = RequestMethod.POST)
    public @ResponseBody
    void gradeInXP(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<HashMap> grades = gradeService.getAllGradeNamesMap();
        JSONObject json = new JSONObject();
        json.put("select", new JSONArray(grades));
        response.getWriter().print(json.toString());
    }

    @RequestMapping(value = "gradeInEX.htm", method = RequestMethod.POST)
    public @ResponseBody
    void gradeInEX(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<HashMap> grades = gradeService.getAllGradeNamesMap();
        JSONObject json = new JSONObject();
        json.put("select", new JSONArray(grades));
        response.getWriter().print(json.toString());
    }

    @RequestMapping(value = "wn_get_grade_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    String wn_get_grade_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<GradeMaster> grades = weightNoteService.getAllGrades();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateGradeFilterList(tpl, grades, -1);
    }

    @RequestMapping(value = "get_supplier_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    String get_supplier_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ArrayList<CompanyMaster> list = companyService.getAllCompanyNames();
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateCompanyFilterList(tpl, list, "supplier");
    }

    @RequestMapping(value = "new_wn.htm", method = RequestMethod.POST)
    public @ResponseBody
    void new_wn(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        Byte type = support.getByteValue("ins_type");
        if (request.getSession().getAttribute("user") != null) {
            //get weight_note by type
            WeightNote wn = weightNoteService.getWnByType(type, support.getIntValue("ins_id"), (User) request.getSession().getAttribute("user"));
            //getqr by type
            QualityReport qr = new QualityReport(Common.getMillType(type), qualityReportService.getNewQRRef(Common.getMillType(type)));
            qr.setDate(new Date());
            qr.setStatus(Constants.PENDING);
            //update qr
            qualityReportService.updateQuality(qr);
            //set qr for wn
            wn.setQualityReport(qr);
            //update wn
            weightNoteService.updateWN(wn);
            //response new wn id
            response.getWriter().print(wn.getId());
        } else {
            response.getWriter().print("-1");
        }

    }

    //save_wn
    @RequestMapping(value = "save_wn.htm", method = RequestMethod.POST)
    public @ResponseBody
    void save_wn(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        if (request.getSession().getAttribute("user") != null) {
            WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
            String type = wn.getType();
            ArrayList<WeightNoteReceipt> wnrs = weightNoteReceiptService.getWNRByWN(wn.getId());
            if (type.equals("IP") && !wnrs.isEmpty()) {
                WarehouseCell whc = warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name"));

                Map wn_el = new HashMap();
                Map wn_map = new HashMap();

                wn.setUser((User) request.getSession().getAttribute("user"));
                wn.setTruckNo(support.getStringRequest("truck_no"));
                wn.setPackingMaster(packingService.getPackingById(support.getIntValue("packing")));
                wn.setDriver(support.getStringRequest("driver"));
                wn.setWarehouseCell(whc);
                wn.setStatus(support.getByteValue("wn_status"));
                if (!support.getStringRequest("container_no").equals("")) {
                    wn.setContainerNo(support.getStringRequest("container_no"));
                }
                if (!support.getStringRequest("ico_no").equals("")) {
                    wn.setIcoNo(support.getStringRequest("ico_no"));
                }
                if (!support.getStringRequest("seal_no").equals("")) {
                    wn.setSealNo(support.getStringRequest("seal_no"));
                }

                wn_el.put("date", Common.getDateFromDatabase(new Date(), Common.date_format));
                wn_el.put("packing", wn.getPackingMaster().getName());
                wn_el.put("truck_no", wn.getTruckNo());
                wn_el.put("driver", wn.getDriver());
                wn_el.put("area", support.getStringRequest("area"));
                wn_el.put("status", wn.getStatus());

                wn_map.put("type", "update");
                wn_map.put("user", ((User) request.getSession().getAttribute("user")).getUserName());
                wn_map.put("element", wn_el);

                String log = wn.getLog() + "," + (new JSONObject(wn_map)).toString();

                wn.setLog(log);
                weightNoteService.updateWN(wn);

                wnrs = weightNoteReceiptService.getWNRByWN(wn.getId());
                for (WeightNoteReceipt wnr : wnrs) {

                    Map el = new HashMap();
                    Map wnr_log_map = new HashMap();
                    Map reason = new HashMap();

                    wnr.setWarehouseCell(whc);
                    el.put("area", support.getStringRequest("area"));

                    wnr_log_map.put("type", "update");
                    wnr_log_map.put("user", ((User) request.getSession().getAttribute("user")).getUserName());
                    wnr_log_map.put("element", el);

                    String wnr_log = wnr.getLog() + "," + (new JSONObject(wnr_log_map)).toString();

                    wnr.setLog(wnr_log);
                    weightNoteReceiptService.updateWNR(wnr);
                }
                response.getWriter().print("1"); // update success
            } else {
                WarehouseCell whc = warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name"));
                GradeMaster grade = gradeService.getGradeById(support.getIntValue("grade"));

                Map wn_el = new HashMap();
                Map wn_map = new HashMap();

                wn.setUser((User) request.getSession().getAttribute("user"));
                wn.setTruckNo(support.getStringRequest("truck_no"));
                wn.setGradeMaster(grade);
                wn.setPackingMaster(packingService.getPackingById(support.getIntValue("packing")));
                wn.setDriver(support.getStringRequest("driver"));
                wn.setWarehouseCell(whc);
                wn.setStatus(support.getByteValue("wn_status"));
                if (!support.getStringRequest("container_no").equals("")) {
                    wn.setContainerNo(support.getStringRequest("container_no"));
                }
                if (!support.getStringRequest("ico_no").equals("")) {
                    wn.setIcoNo(support.getStringRequest("ico_no"));
                }
                if (!support.getStringRequest("seal_no").equals("")) {
                    wn.setSealNo(support.getStringRequest("seal_no"));
                }

                wn_el.put("date", Common.getDateFromDatabase(new Date(), Common.date_format));
                wn_el.put("packing", wn.getPackingMaster().getName());
                wn_el.put("grade", grade.getName());
                wn_el.put("truck_no", wn.getTruckNo());
                wn_el.put("driver", wn.getDriver());
                wn_el.put("area", support.getStringRequest("area"));
                wn_el.put("status", wn.getStatus());

                wn_map.put("type", "update");
                wn_map.put("user", ((User) request.getSession().getAttribute("user")).getUserName());
                wn_map.put("element", wn_el);

                String log = wn.getLog() + "," + (new JSONObject(wn_map)).toString();

                wn.setLog(log);
                weightNoteService.updateWN(wn);

                wnrs = weightNoteReceiptService.getWNRByWN(wn.getId());
                for (WeightNoteReceipt wnr : wnrs) {

                    Map el = new HashMap();
                    Map wnr_log_map = new HashMap();
                    Map reason = new HashMap();

                    wnr.setWarehouseCell(whc);
                    el.put("area", support.getStringRequest("area"));

                    wnr.setGradeMaster(grade);
                    el.put("grade", support.getIntValue("grade"));

                    wnr_log_map.put("type", "update");
                    wnr_log_map.put("user", ((User) request.getSession().getAttribute("user")).getUserName());
                    wnr_log_map.put("element", el);

                    String wnr_log = wnr.getLog() + "," + (new JSONObject(wnr_log_map)).toString();

                    wnr.setLog(wnr_log);
                    weightNoteReceiptService.updateWNR(wnr);
                }
                response.getWriter().print("1"); // update success
            }
        } else {
            response.getWriter().print("0"); // update failed
        }
    }

    @RequestMapping(value = "saveArea.htm", method = RequestMethod.POST)
    public @ResponseBody
    void saveArea(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        if (request.getSession().getAttribute("user") != null) {
            WarehouseCell whc = warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name"));
            WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));

            wn.setWarehouseCell(whc);
            weightNoteService.updateWN(wn);

            ArrayList<WeightNoteReceipt> wnrs = weightNoteReceiptService.getWNRByWN(wn.getId());
            for (WeightNoteReceipt wnr : wnrs) {

                wnr.setWarehouseCell(whc);

                weightNoteReceiptService.updateWNR(wnr);
            }
            response.getWriter().print("1"); // update success
        } else {
            response.getWriter().print("0"); // update failed
        }
    }

    @RequestMapping(value = "get_area_for_bridge.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_area_for_bridge(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Map m = new HashMap();
        m.put("cell_id", Common.readProperties("cell_id", context));
        m.put("area", Common.readProperties("cell_code", context));
        m.put("map", Common.readProperties("map_id", context));
        JSONObject json = new JSONObject(m);
        response.getWriter().print(json.toString());
    }

    @RequestMapping(value = "get_wn.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_wn(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        if (request.getSession().getAttribute("user") != null) {
            WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
            //String str_area = (wn.getWarehouseCell() != null) ? warehouseCellService.convertIdIntoCode(wn.getWarehouseCell()) : "";
            if (wn != null) {

                int user_id = ((User) request.getSession().getAttribute("user")).getId();
                boolean save_right = true;
                boolean del_right = true;
                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
                if (auth == null || Objects.equals(auth.getPermission(), Constants.PERMISSION_YES_NO.get(0))) {
                    del_right = false;
                }
                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
                if (auth == null || Objects.equals(auth.getPermission(), Constants.PERMISSION_YES_NO.get(0))) {
                    save_right = false;
                }

                //response.getWriter().print(weightNoteService.generateWeightNote(request, context, wn, del_right, save_right));
                response.getWriter().print("[" + weightNoteService.convertToJson(wn, del_right, save_right) + "]");
            } else {
                Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
                String message = "Could not create new weight note! An error has been occured!";
                response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
            }
        } else {
            response.getWriter().print("");
        }
    }

    @RequestMapping(value = "get_wn_ref_list.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_wn_ref_list(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        String type = Common.getMillType(support.getByteValue("type"));
        byte status = support.getByteValue("status");
        int inst_id = support.getIntValue("instruction");
        int grade_id = support.getIntValue("grade");
        int supplier = support.getIntValue("supplier");
        int pledge_id = support.getIntValue("pledge");
        String searchStr = support.getStringRequest("searchStr");
        ArrayList<Object[]> obj = weightNoteService.getWeightNoteRefList(type, searchStr, inst_id, grade_id, status, supplier, pledge_id, 0);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        if (obj != null && !obj.isEmpty()) {
            response.getWriter().print(new GenTemplate(request).generateRefFilterList(tpl, obj, "wn"));
        } else {
            response.getWriter().print("");
        }
    }

    @RequestMapping(value = "add_first_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_first_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter support = new ServletSupporter(request);
//            PalletMaster pallet = palletMasterService.getPalletByRef(support.getStringRequest("pallet"));
//            if (pallet != null) {
                WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
                String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                PackingMaster packing = packingService.getPackingById(support.getIntValue("packing"));
                float gross = support.getFloatValue("gross");
                int noOfBags = 1;
                float tare = (noOfBags * packing.getWeight());
                //wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name")));
                //log will be based on JSON object
//                String[][] element_arr = {
//                    {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
//                    {"packing", packing.getName()},
//                    {"noOfBags", noOfBags + ""},
//                    {"pallet_name", ""},
//                    {"pallet_weight", ""},
//                    {"gross", gross + ""}
//                };
//                String[][] main_arr = {
//                    {"type", "new"},
//                    {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
//                    {"element", Common.convertToJson((Object) element_arr)}
//                };
//                String log = Common.convertToJson((Object) main_arr);

                //WarehouseCell whc = (wn.getType().equals("IP") || wn.getType().equals("EX")) ? null : wn.getWarehouseCell();
                WeightNoteReceipt wnr = new WeightNoteReceipt(null, null, null, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, null, null, null, Constants.COMPLETE, "", null, null, null, null);

                //add client for wnr - start
                CompanyMaster client = weightNoteService.getClientFromInst(wn.getInstId(), wn.getType());
                wnr.setCompanyMasterByClientId(client);
//                if (wn.getType().equals("IM")) {
//                    CompanyMaster pledge = weightNoteService.getPledgeFromInst(wn.getInstId());
//                    wnr.setCompanyMasterByPledgeId(pledge);
//                }
                wnr.setGradeMaster(wn.getGradeMaster());
                //add client for wnr - end

                //update wnr
                weightNoteReceiptService.updateWNR(wnr);
                //initialize new wnr list for generating new wnr list
                ArrayList<WeightNoteReceipt> list = new ArrayList<>();
                list.add(wnr);

                //get list of packing
                ArrayList<PackingMaster> packings = packingService.getAllPackings();

                int user_id = ((User) request.getSession().getAttribute("user")).getId();
                boolean save_right = true;
                boolean del_right = true;
                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
                if (auth == null || Objects.equals(auth.getPermission(), Constants.PERMISSION_YES_NO.get(0))) {
                    del_right = false;
                }
                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
                if (auth == null || Objects.equals(auth.getPermission(), Constants.PERMISSION_YES_NO.get(0))) {
                    save_right = false;
                }

                response.getWriter().print(new GenTemplate(request).generateWNRList(context, wn.getType(), list, packings, del_right, save_right));
//            } else {
//                Map map = new HashMap();
//                map.put("msg", "pallet");
//                JSONObject json = new JSONObject(map);
//                response.getWriter().print(json.toString());
////                Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
////                String message = "Pallet does not exist in database!";
////                response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
//            }
        } else {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
            String message = "Please Login before weighing!";
            response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
        }
    }

    @RequestMapping(value = "add_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter support = new ServletSupporter(request);
//            PalletMaster pallet = palletMasterService.getPalletByRef(support.getStringRequest("pallet"));
//            if (pallet != null) {
                WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
                String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                PackingMaster packing = packingService.getPackingById(support.getIntValue("packing"));
                float gross = support.getFloatValue("gross");
                int noOfBags = 1;
                float tare = (noOfBags * packing.getWeight());

                //log will be based on JSON object
//                String[][] element_arr = {
//                    {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
//                    {"packing", packing.getName()},
//                    {"noOfBags", noOfBags + ""},
//                    {"pallet_name", ""},
//                    {"pallet_weight", ""},
//                    {"gross", gross + ""}
//                };
//                String[][] main_arr = {
//                    {"type", "new"},
//                    {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
//                    {"element", Common.convertToJson((Object) element_arr)}
//                };
//                String log = Common.convertToJson((Object) main_arr);

//                WarehouseCell whc = (wn.getType().equals("IP") || wn.getType().equals("EX")) ? null : wn.getWarehouseCell();
                WeightNoteReceipt wnr = new WeightNoteReceipt(null, null, null, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, null, null, null, Constants.COMPLETE, "", null, null, null, null);

                //add client for wnr - start
                CompanyMaster client = weightNoteService.getClientFromInst(wn.getInstId(), wn.getType());
                wnr.setCompanyMasterByClientId(client);
//                if (wn.getType().equals("IM")) {
//                    CompanyMaster pledge = weightNoteService.getPledgeFromInst(wn.getInstId());
//                    wnr.setCompanyMasterByPledgeId(pledge);
//                }
                wnr.setGradeMaster(wn.getGradeMaster());
                //add client for wnr - end

                //update wnr
                weightNoteReceiptService.updateWNR(wnr);

                int no = weightNoteReceiptService.countNumberOfWnr(wn.getId());

                //get list of packing
                ArrayList<PackingMaster> packings = packingService.getAllPackings();

                int user_id = ((User) request.getSession().getAttribute("user")).getId();
                boolean save_right = true;
                boolean del_right = true;
                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
                if (auth == null || Objects.equals(auth.getPermission(), Constants.PERMISSION_YES_NO.get(0))) {
                    del_right = false;
                }
                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
                if (auth == null || Objects.equals(auth.getPermission(), Constants.PERMISSION_YES_NO.get(0))) {
                    save_right = false;
                }
                response.getWriter().print(new GenTemplate(request).generateWNRRow(context, wn.getType(), wnr, packings, no, del_right, save_right));
//            } else {
////                Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
////                String message = "Pallet does not exist in database!";
////                response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
//                Map map = new HashMap();
//                map.put("msg", "pallet");
//                JSONObject json = new JSONObject(map);
//                response.getWriter().print(json.toString());
//            }
        } else {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
            String message = "Please Login before weighing!";
            response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
        }
    }

    @RequestMapping(value = "bind_map_data_area.htm", method = RequestMethod.POST)
    public @ResponseBody
    String bind_map_data_area() throws Exception {
        ServletSupporter supporter = new ServletSupporter(request);
        ArrayList<WarehouseCell> warehousecells = warehouseCellService.getListWarehouseCellById(supporter.getIntValue("map_id"));
        String[] total = new String[warehousecells.size()];
        for (int i = 0; i < warehousecells.size(); i++) {
            Float net = weightNoteReceiptService.getTotalByAreaId(warehousecells.get(i).getId(), -1, -1, -1);
            if (net == null) {
                total[i] = "";
            } else {
                total[i] = "Total: " + net + " Mts";
            }
        }
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/warehouse_map_cell_area.html"));
        return new GenTemplate(request).generateWarehouseCellsArea(tpl, warehousecells, total);
    }

    @RequestMapping(value = "generate_list_on_area", method = RequestMethod.POST)
    public @ResponseBody
    String generate_list_on_area() throws Exception {
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/list_on_cell_area.html"));
        return new GenTemplate(request).generateListOnCellArea(tpl);
    }

    @RequestMapping(value = "generate_area_weight_note_receipts", method = RequestMethod.POST)
    public @ResponseBody
    String generate_area_weight_notes() throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        ArrayList<Map> weightnotereceipts = weightNoteReceiptService.getWeightNoteReceiptsByArea(support.getIntValue("cell_id"), -1);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/weight_note_receipts_area.html"));
        return new GenTemplate(request).generateWeightNoteReceiptsArea(tpl, weightnotereceipts);
    }

    @RequestMapping(value = "add_first_wnr_ip.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_first_wnr_ip(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter support = new ServletSupporter(request);
            WeightNoteReceipt old_wnr = weightNoteReceiptService.getWNRById(support.getIntValue("oldwnr"));
            if (old_wnr != null) {
                WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
                String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                PackingMaster packing = old_wnr.getPackingMaster();
                float gross = support.getFloatValue("gross");
                int noOfBags = old_wnr.getNoOfBags();
                float tare = old_wnr.getTareWeight();
                //wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name")));
                //log will be based on JSON object
//                String[][] element_arr = {
//                    {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
//                    {"packing", packing.getName()},
//                    {"noOfBags", noOfBags + ""},
//                    {"pallet_name", ""},
//                    {"pallet_weight", ""},
//                    {"gross", gross + ""}
//                };
//                String[][] main_arr = {
//                    {"type", "new"},
//                    {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
//                    {"element", Common.convertToJson((Object) element_arr)}
//                };
//                String log = Common.convertToJson((Object) main_arr);

                //WarehouseCell whc = (wn.getType().equals("IP") || wn.getType().equals("EX")) ? null : wn.getWarehouseCell();
                WeightNoteReceipt wnr = new WeightNoteReceipt(null, null, null, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, "", (float) 0, null, Constants.COMPLETE, "", null, null, null, null);

                //add client for wnr - start
                CompanyMaster client = weightNoteService.getClientFromInst(wn.getInstId(), wn.getType());
                wnr.setCompanyMasterByClientId(client);
                wnr.setGradeMaster(wn.getGradeMaster());
                //add client for wnr - end

                //update wnr
                int new_wnr = weightNoteReceiptService.updateWNR(wnr);
                wnr.setId(new_wnr);
                //update wnr_allocation by the old wnr
                WnrAllocation allocation = allocationService.findByWnrId(old_wnr.getId());
                allocation.setDateOut(new Date());
                allocation.setUserByWeightOutUser((User) request.getSession().getAttribute("user"));
                allocation.setWeightNoteReceiptByOutWnrId(wnr);
                allocation.setWeightOut(wnr.getGrossWeight() - wnr.getTareWeight());
                allocation.setStatus(Constants.COMPLETE);

                allocationService.updateAllocation(allocation);

                //initialize new wnr list for generating new wnr list
                ArrayList<WeightNoteReceipt> list = new ArrayList<>();
                list.add(wnr);

                //get list of packing
                ArrayList<PackingMaster> packings = packingService.getAllPackings();

                int user_id = ((User) request.getSession().getAttribute("user")).getId();
                boolean save_right = true;
                boolean del_right = true;
                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                    del_right = false;
                }
                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                    save_right = false;
                }

                response.getWriter().print(new GenTemplate(request).generateWNRList(context, wn.getType(), list, packings, del_right, save_right));
            } else {
                Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
                String message = "WNR does not exist in database!";
                response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
            }
        } else {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
            String message = "Please Login before weighing!";
            response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
        }
    }

    @RequestMapping(value = "add_wnr_ip.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_wnr_ip(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter support = new ServletSupporter(request);
            WeightNoteReceipt old_wnr = weightNoteReceiptService.getWNRById(support.getIntValue("oldwnr"));
            if (old_wnr != null) {
                WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
                String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                PackingMaster packing = old_wnr.getPackingMaster();
                float gross = support.getFloatValue("gross");
                int noOfBags = old_wnr.getNoOfBags();
                float tare = old_wnr.getTareWeight();
                //wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name")));
                //log will be based on JSON object
//                String[][] element_arr = {
//                    {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
//                    {"packing", packing.getName()},
//                    {"noOfBags", noOfBags + ""},
//                    {"pallet_name", ""},
//                    {"pallet_weight", ""},
//                    {"gross", gross + ""}
//                };
//                String[][] main_arr = {
//                    {"type", "new"},
//                    {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
//                    {"element", Common.convertToJson((Object) element_arr)}
//                };
//                String log = Common.convertToJson((Object) main_arr);

                //WarehouseCell whc = (wn.getType().equals("IP") || wn.getType().equals("EX")) ? null : wn.getWarehouseCell();
                WeightNoteReceipt wnr = new WeightNoteReceipt(null, null, null, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, "", (float) 0, null, Constants.COMPLETE, "", null, null, null, null);

                //add client for wnr - start
                CompanyMaster client = weightNoteService.getClientFromInst(wn.getInstId(), wn.getType());
                wnr.setCompanyMasterByClientId(client);
                wnr.setGradeMaster(wn.getGradeMaster());
                //add client for wnr - end

                //update wnr
                int new_wnr = weightNoteReceiptService.updateWNR(wnr);
                wnr.setId(new_wnr);
                //update wnr_allocation by the old wnr
                WnrAllocation allocation = allocationService.findByWnrId(old_wnr.getId());
                allocation.setDateOut(new Date());
                allocation.setUserByWeightOutUser((User) request.getSession().getAttribute("user"));
                allocation.setWeightNoteReceiptByOutWnrId(wnr);
                allocation.setWeightOut(wnr.getGrossWeight() - wnr.getTareWeight());
                allocation.setStatus(Constants.COMPLETE);

                allocationService.updateAllocation(allocation);

                //initialize new wnr list for generating new wnr list
                int no = weightNoteReceiptService.countNumberOfWnr(wn.getId());

                //get list of packing
                ArrayList<PackingMaster> packings = packingService.getAllPackings();

                int user_id = ((User) request.getSession().getAttribute("user")).getId();
                boolean save_right = true;
                boolean del_right = true;
                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                    del_right = false;
                }
                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                    save_right = false;
                }
                response.getWriter().print(new GenTemplate(request).generateWNRRow(context, wn.getType(), wnr, packings, no, del_right, save_right));
            } else {
                Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
                String message = "Weight Note Receipt Doest Not Exist";
                response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
            }
        } else {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
            String message = "Please Login before weighing!";
            response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
        }
    }

    @RequestMapping(value = "add_first_gross_truck.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_first_wnr_truck(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter support = new ServletSupporter(request);
            WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
            String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
            PackingMaster packing = packingService.getPackingById(support.getIntValue("packing"));
            float gross = support.getFloatValue("gross");
            int noOfBags = support.getIntValue("noOfBags");
            float tare = Float.valueOf("0");
            String options = Common.convertToJson((Object) new String[][]{{"truck_no", support.getStringRequest("truck_no")}, {"truck_mode", "true"}});
            //wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name")));
            //log will be based on JSON object
            String[][] element_arr = {
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                {"packing", packing.getName()},
                {"noOfBags", noOfBags + ""},
                {"gross", gross + ""},
                {"truck_mode", "weighing gross"}
            };
            String[][] main_arr = {
                {"type", "new"},
                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                {"element", Common.convertToJson((Object) element_arr)}
            };
            String log = Common.convertToJson((Object) main_arr);

            WarehouseCell whc = (wn.getType().equals("IP") || wn.getType().equals("EX")) ? null : wn.getWarehouseCell();
            WeightNoteReceipt wnr = new WeightNoteReceipt(null, null, whc, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, "", (float) 0, log, Constants.COMPLETE, options, null, null, null, null);

            //add client for wnr - start
            CompanyMaster client = weightNoteService.getClientFromInst(wn.getInstId(), wn.getType());
            wnr.setCompanyMasterByClientId(client);
            wnr.setGradeMaster(wn.getGradeMaster());
            //add client for wnr - end

            //update wnr
            int new_wnr = weightNoteReceiptService.updateWNR(wnr);
            wnr.setId(new_wnr);

            //initialize new wnr list for generating new wnr list
            ArrayList<WeightNoteReceipt> list = new ArrayList<>();
            list.add(wnr);

            //get list of packing
            ArrayList<PackingMaster> packings = packingService.getAllPackings();

            int user_id = ((User) request.getSession().getAttribute("user")).getId();
            boolean save_right = true;
            boolean del_right = true;
            Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
            if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                del_right = false;
            }
            auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
            if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                save_right = false;
            }

            response.getWriter().print(new GenTemplate(request).generateWNRList(context, wn.getType(), list, packings, del_right, save_right));
        } else {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
            String message = "Please Login before weighing!";
            response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
        }
    }

    //weighing_truck
    @RequestMapping(value = "bridge_gross.htm", method = RequestMethod.POST)
    public @ResponseBody
    void bridge_gross(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Map wnr_map = new HashMap();
            ServletSupporter support = new ServletSupporter(request);
            PrintWriter out = response.getWriter();

            if (request.getSession().getAttribute("user") != null) {
                boolean isFail = false;
                WeightNoteReceipt wnr;
                int user_id = ((User) request.getSession().getAttribute("user")).getId();
                boolean save_right = true;
                boolean del_right = true;

                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                    del_right = false;
                }
                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                    save_right = false;
                }

                String type = support.getStringRequest("type");

                if (type.equals("IM")) {
                    WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
                    if (wn != null) {
                        String snap = support.getStringRequest("snap");
                        String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                        PackingMaster packing = packingService.getPackingById(support.getIntValue("packing"));
                        float gross = support.getFloatValue("gross");
                        int noOfBags = support.getIntValue("noOfBags");
                        float tare = noOfBags * packing.getWeight();
                        String truck_no = support.getStringRequest("truck_no");
                        String options = Common.convertToJson((Object) new String[][]{{"truck_no", truck_no}, {"truck_mode", "true"}, {"snap_gross", snap}});
                        wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("area")));
                        weightNoteService.updateWN(wn);

                        String[][] element_arr = {
                            {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                            {"packing", packing.getName()},
                            {"noOfBags", noOfBags + ""},
                            {"gross", gross + ""},
                            {"truck_mode", "weighing gross"}
                        };
                        String[][] main_arr = {
                            {"type", "new"},
                            {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                            {"element", Common.convertToJson((Object) element_arr)}
                        };
                        String log = Common.convertToJson((Object) main_arr);

                        WarehouseCell whc = wn.getWarehouseCell();
                        wnr = new WeightNoteReceipt(null, null, whc, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, truck_no, (float) 0, log, Constants.COMPLETE, options, null, null, null, null);

                        //add client for wnr - start
                        CompanyMaster client = weightNoteService.getClientFromInst(wn.getInstId(), wn.getType());
                        CompanyMaster pledge = weightNoteService.getPledgeFromInst(wn.getInstId());
                        wnr.setCompanyMasterByClientId(client);
                        wnr.setCompanyMasterByPledgeId(pledge);
                        wnr.setGradeMaster(wn.getGradeMaster());
                        //add client for wnr - end

                        int new_wnr = weightNoteReceiptService.updateWNR(wnr);
                        wnr.setId(new_wnr);

                        int no = weightNoteReceiptService.countNumberOfWnr(wn.getId());

                        wnr_map = weightNoteService.convertWnrToMap(wnr, del_right, save_right);
                        wnr_map.put("no", no);
                    } else {
                        isFail = true;
                    }
                    if (!isFail) {
                        if (wnr_map.isEmpty()) {
                            out.print("1");
                        } else {
                            JSONObject json = new JSONObject(wnr_map);
                            out.print(json.toString());
                        }
                    } else {
                        out.print("-1");
                    }
                }
                if (type.equals("EX")) {
                    int wn_id = support.getIntValue("wn_id");
                    wnr = weightNoteReceiptService.getWNRById(support.getIntValue("wnr_id"));
                    if (wnr != null) {
                        if (wnr.getWeightNote().getId().equals(wn_id)) {
                            wnr.setGrossWeight(support.getFloatValue("gross"));
                            String[][] element_arr = {
                                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                                {"gross", wnr.getGrossWeight() + ""},
                                {"truck_mode", "update gross"}
                            };
                            String[][] main_arr = {
                                {"type", "update"},
                                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                                {"element", Common.convertToJson((Object) element_arr)}
                            };
                            String log = wnr.getLog() + Common.convertToJson((Object) main_arr);
                            wnr.setLog(log);
                            weightNoteReceiptService.updateWNR(wnr);
                        } else {
                            isFail = true;
                        }
                    } else {
                        isFail = true;
                    }
                    if (!isFail) {
                        out.print("[1]");
                    } else {
                        out.print("[-1]");
                    }
                }
            } else {
                out.print("-2");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("-1");
        }
    }

    @RequestMapping(value = "bridge_tare.htm", method = RequestMethod.POST)
    public @ResponseBody
    void bridge_tare(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Map wnr_map = new HashMap();
            ServletSupporter support = new ServletSupporter(request);
            PrintWriter out = response.getWriter();
            if (request.getSession().getAttribute("user") != null) {
                User user = (User) request.getSession().getAttribute("user");
                boolean isFail = false;
                WeightNoteReceipt wnr;
                int user_id = user.getId();
                boolean save_right = true;
                boolean del_right = true;

//                Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
//                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
//                    del_right = false;
//                }
//                auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
//                if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
//                    save_right = false;
//                }
                String snap = support.getStringRequest("snap");
                String type = support.getStringRequest("type");

                if (type.equals("IM")) {
                    int wn_id = support.getIntValue("wn_id");
                    int pi_id = support.getIntValue("processing");
                    wnr = weightNoteReceiptService.getWNRById(support.getIntValue("wnr_id"));
                    if (wnr != null) {
                        WeightNote im_wn = weightNoteService.getWnById(wn_id);
                        if (wnr.getWeightNote().getId().equals(wn_id)) {
                            float tare = wnr.getTareWeight() + support.getFloatValue("tare");
                            wnr.setPalletWeight(support.getFloatValue("tare"));
                            wnr.setTareWeight(tare);
                            if (wnr.getOptions() != null) {
                                JSONObject json = new JSONObject(wnr.getOptions());
                                json.put("snap_tare", snap);
                                wnr.setOptions(json.toString());
                            }
                            String[][] element_arr = {
                                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                                {"tare", tare + ""},
                                {"truck_mode", "update tare"},
                                {"more_info", "allocated to pi_id " + pi_id}
                            };
                            String[][] main_arr = {
                                {"type", "update"},
                                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                                {"element", Common.convertToJson((Object) element_arr)}
                            };
                            String log = wnr.getLog() + Common.convertToJson((Object) main_arr);
                            wnr.setLog(log);
                            wnr.setStatus(Constants.ALLOCATED);
                            weightNoteReceiptService.updateWNR(wnr);

                            /**
                             * Create IP Weight note - Start *
                             */
                            //get weight_note by type
                            WeightNote wn = weightNoteService.getWNByPI(pi_id, "IP", (User) request.getSession().getAttribute("user"));
                            //get grade based on imported weight note
                            wn.setGradeMaster(im_wn.getGradeMaster());
                            //getqr by type
                            QualityReport qr = new QualityReport("IP", qualityReportService.getNewQRRef("IP"));
                            qr.setDate(new Date());
                            qr.setStatus(Constants.PENDING);
                            //update qr
                            qualityReportService.updateQuality(qr);
                            //set qr for wn
                            wn.setQualityReport(qr);
                            //update wn
                            //set status to complete
                            wn.setStatus(Constants.COMPLETE);
                            weightNoteService.updateWN(wn);

                            /**
                             * Create IP Weight note - End *
                             */
                            /**
                             * ============ Create WNR for WN - Start
                             */
                            String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                            PackingMaster packing = wnr.getPackingMaster();
                            float gross = wnr.getGrossWeight();
                            int noOfBags = wnr.getNoOfBags();
                            float new_tare = wnr.getTareWeight();
                            wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name")));

                            String[][] new_element_arr = {
                                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                                {"packing", packing.getName()},
                                {"noOfBags", noOfBags + ""},
                                {"gross", gross + ""},
                                {"truck_mode", "weighing gross"}
                            };
                            String[][] new_main_arr = {
                                {"type", "new"},
                                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                                {"element", Common.convertToJson((Object) new_element_arr)}
                            };
                            String new_log = Common.convertToJson((Object) new_main_arr);

                            WeightNoteReceipt ip_wnr = new WeightNoteReceipt(null, null, null, im_wn.getGradeMaster(), wn, packing, ref_number, new Date(), noOfBags, gross, tare, "", (float) 0, log, Constants.COMPLETE, "", null, null, null, null);
                            int new_wnr = weightNoteReceiptService.updateWNR(ip_wnr);

                            /**
                             * ============ Create WNR for WN - End
                             */
                            /* create allocation record */
                            WnrAllocation wna = new WnrAllocation(user, user, ip_wnr, wnr, pi_id, "P", wn_id, new Date(), Byte.valueOf("1"), new Date(), ip_wnr.getGrossWeight() - ip_wnr.getTareWeight(), "");
                            allocationService.updateAllocation(wna);

                            wnr_map.put("new_wnr", ip_wnr.getRefNumber());
                        } else {
                            isFail = true;
                        }
                    } else {
                        isFail = true;
                    }
                    if (!isFail) {
                        JSONObject json = new JSONObject(wnr_map);
                        out.print("[" + json.toString() + "]");
                    } else {
                        out.print("[-1]");
                    }
                }
                if (type.equals("EX")) {
                    WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
                    if (wn != null) {
                        String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
                        PackingMaster packing = packingService.getPackingById(support.getIntValue("packing"));
                        float gross = 0;
                        int noOfBags = support.getIntValue("noOfBags");
                        float tare = (noOfBags * packing.getWeight()) + support.getFloatValue("tare");
                        String truck_no = support.getStringRequest("truck_no");
                        String options = Common.convertToJson((Object) new String[][]{{"truck_no", truck_no}, {"truck_mode", "true"}});
                        weightNoteService.updateWN(wn);

                        String[][] element_arr = {
                            {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                            {"packing", packing.getName()},
                            {"noOfBags", noOfBags + ""},
                            {"gross", gross + ""},
                            {"truck_mode", "weighing gross"}
                        };
                        String[][] main_arr = {
                            {"type", "new"},
                            {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                            {"element", Common.convertToJson((Object) element_arr)}
                        };
                        String log = Common.convertToJson((Object) main_arr);

                        wnr = new WeightNoteReceipt(null, null, null, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, truck_no, (float) 0, log, Constants.COMPLETE, options, null, null, null, null);
                        int new_wnr = weightNoteReceiptService.updateWNR(wnr);
                        wnr.setId(new_wnr);

                        int no = weightNoteReceiptService.countNumberOfWnr(wn.getId());

                        wnr_map = weightNoteService.convertWnrToMap(wnr, del_right, save_right);
                        wnr_map.put("no", no);
                    } else {
                        isFail = true;
                    }
                    if (!isFail) {
                        if (wnr_map.isEmpty()) {
                            out.print("-1");
                        } else {
                            JSONObject json = new JSONObject(wnr_map);
                            out.print(json.toString());
                        }
                    } else {
                        out.print("-1");
                    }
                }

            } else {
                out.print("-2");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("-1");
        }
    }

    @RequestMapping(value = "add_gross_truck.htm", method = RequestMethod.POST)
    public @ResponseBody
    void add_gross_truck(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getSession().getAttribute("user") != null) {
            ServletSupporter support = new ServletSupporter(request);
            WeightNote wn = weightNoteService.getWnById(support.getIntValue("wn_id"));
            String ref_number = weightNoteReceiptService.getNewWnrRef(wn.getRefNumber());
            PackingMaster packing = packingService.getPackingById(support.getIntValue("packing"));
            float gross = support.getFloatValue("gross");
            int noOfBags = support.getIntValue("noOfBags");
            float tare = Float.valueOf("0");
            String options = Common.convertToJson((Object) new String[][]{{"truck_no", support.getStringRequest("truck_no")}, {"truck_mode", "true"}});
            //wn.setWarehouseCell(warehouseCellService.getWarehouseCellById(support.getIntValue("cell_id_name")));
            //log will be based on JSON object
            String[][] element_arr = {
                {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
                {"packing", packing.getName()},
                {"noOfBags", noOfBags + ""},
                {"gross", gross + ""},
                {"truck_mode", "weighing gross"}
            };
            String[][] main_arr = {
                {"type", "new"},
                {"user", ((User) request.getSession().getAttribute("user")).getUserName()},
                {"element", Common.convertToJson((Object) element_arr)}
            };
            String log = Common.convertToJson((Object) main_arr);

            WarehouseCell whc = (wn.getType().equals("IP") || wn.getType().equals("EX")) ? null : wn.getWarehouseCell();
            WeightNoteReceipt wnr = new WeightNoteReceipt(null, null, whc, null, wn, packing, ref_number, new Date(), noOfBags, gross, tare, "", (float) 0, log, Constants.COMPLETE, options, null, null, null, null);
            //update wnr
            int new_wnr = weightNoteReceiptService.updateWNR(wnr);
            wnr.setId(new_wnr);

            //initialize new wnr list for generating new wnr list
            int no = weightNoteReceiptService.countNumberOfWnr(wn.getId());

            //get list of packing
            ArrayList<PackingMaster> packings = packingService.getAllPackings();

            int user_id = ((User) request.getSession().getAttribute("user")).getId();
            boolean save_right = true;
            boolean del_right = true;
            Authorization auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(DELETE_WNR, context));
            if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                del_right = false;
            }
            auth = authorizationService.getAuthorizationOfUserInPageSimple(user_id, Common.getPermissionId(SAVE_WNR, context));
            if (auth == null || auth.getPermission() == Constants.PERMISSION_YES_NO.get(0)) {
                save_right = false;
            }
            response.getWriter().print(new GenTemplate(request).generateWNRRow(context, wn.getType(), wnr, packings, no, del_right, save_right));
        } else {
            Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
            String message = "Please Login before weighing!";
            response.getWriter().print(new GenTemplate(request).printAlert(tpl, message));
        }
    }

    @RequestMapping(value = "check_wnr_for_ip.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wnr_for_ip(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        WnrAllocation allocation = allocationService.findByWnrRef(request.getParameter("ref_number"), Integer.parseInt(request.getParameter("inst_id")));
        WeightNote wn = weightNoteService.getWnById(Integer.parseInt(request.getParameter("wnid")));
        if (allocation != null && allocation.getStatus().equals(Constants.PENDING)) {
            WeightNoteReceipt wnr = allocation.getWeightNoteReceiptByWnrId();
            if (wnr.getCompanyMasterByPledgeId() != null) {
                response.getWriter().print("[-2]"); //Pledge is restrict
            } else if (wn.getGradeMaster().getId() != wnr.getGradeMaster().getId()) {
                response.getWriter().print("[-3]"); //Diff in Grade
            } else if (wn.getPackingMaster() == null) {
                response.getWriter().print("[-4]"); //Diff in Grade
            } else {
                response.getWriter().print("[" + wnr.getId() + "]");
            }
        } else {
            response.getWriter().print("[-1]"); //Completed allocate
        }
    }

    //check_wnr
    @RequestMapping(value = "check_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletSupporter support = new ServletSupporter(request);
        WeightNoteReceipt wnr = weightNoteReceiptService.getWNRByRef(support.getStringRequest("wnr"));
        if (wnr != null) {
            if (wnr.getWeightNote().getId().equals(support.getIntValue("wn_id"))) {
                out.print("[" + wnr.getId() + "]");
            } else {
                out.print("[-1]");
            }
        } else {
            out.print("[-1]");
        }
    }

    @RequestMapping("load_inst_by_type.htm")
    public @ResponseBody
    void load_inst_by_type(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        String type = support.getStringRequest("type");
        ArrayList<Object[]> wns = weightNoteService.getListInstructionByType(type);
        response.getWriter().print(new GenTemplate(request).generateInstList_weighing(context, wns));
    }

    @RequestMapping("get_di_status.htm")
    public @ResponseBody
    void get_di_status(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        byte status = deliveryInsService.getDiStatus(id);
        response.getWriter().print("[" + status + "]");
    }

    @RequestMapping("wn_get_grade_by_type.htm")
    public @ResponseBody
    void wn_get_grade_by_type(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        GenTemplate gen = new GenTemplate(request);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        String type = support.getStringRequest("type");
        ArrayList<GradeMaster> grades = weightNoteService.getAllGradesByType(type);
        response.getWriter().print("<li id=\"grade_-1\" class=\"chosen\">All</li>" + gen.generateGradeFilterList(tpl, grades, -1));
    }

    @RequestMapping("get_qr_status.htm")
    public @ResponseBody
    void get_qr_status(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("wn_id");
        byte status = weightNoteService.getQrStatus(wn_id);
        response.getWriter().print("[" + status + "]");
    }

    @RequestMapping("weighing_list_source.htm")
    public @ResponseBody
    void weighing_list_source(HttpServletResponse response) throws Exception {
        DecimalFormat decim = new DecimalFormat("0.0000");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "wn_ref", "qr_ref", "inst_ref", "grade_name", "wn_date", "packing_name", "num", "gross_weight", "tare_weight", "net_weight"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "asc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int grade_id = 0;
        int inst_id = 0;
        String type = "";
        Byte stt = 0;
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sInstId = request.getParameter("inst_id");
        String sType = request.getParameter("type");
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
        if (sType != null) {
            type = sType;
        }
        if (sInstId != null) {
            inst_id = Integer.parseInt(sInstId);
        }
        if (sStt != null) {
            stt = Byte.parseByte(sStt);
        }

        String colName = aColumns[col];
        long total = weightNoteService.countRow();
        long totalAfterFilter = total;
        String searchTerm = request.getParameter("sSearch");

        if (searchTerm.equals("undefined")) {
            searchTerm = "";
        }

        ArrayList<WeighingObj> searchGlobe = weightNoteService.searchWeightNote(searchTerm, sdir, start, amount, colName, grade_id, inst_id, type, stt);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (WeighingObj wo : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + wo.getWn_id());
                ja.put((i++) + "", count);
                ja.put((i++) + "", wo.getWn_ref());
                ja.put((i++) + "", wo.getQr_ref());
                ja.put((i++) + "", wo.getInst_ref());
                ja.put((i++) + "", wo.getGrade_name());
                ja.put((i++) + "", Common.getDateFromDatabase(wo.getWn_date(), Common.date_format_a));
                ja.put((i++) + "", wo.getPacking_name());
                HashMap map = weightNoteService.getWNSum(wo.getWn_id());
                ja.put((i++) + "", map.get("num"));
                ja.put((i++) + "", decim.format(Common.getFloatValue(map.get("gross_weight"))));
                ja.put((i++) + "", decim.format(Common.getFloatValue(map.get("tare_weight"))));
                ja.put((i++) + "", decim.format(Common.getFloatValue(map.get("net_weight"))));
                array.put(ja);
                count++;
            }
        }

        Map mtotal = weightNoteService.countTotals(searchTerm, grade_id, inst_id, type, stt);

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
        ja.put((i++) + "", (mtotal.get("num") != null) ? mtotal.get("num") : "0");
        ja.put((i++) + "", (mtotal.get("gross_weight") != null ? mtotal.get("gross_weight") : "0"));
        ja.put((i++) + "", (mtotal.get("tare_weight") != null ? mtotal.get("tare_weight") : "0"));
        ja.put((i++) + "", (mtotal.get("net_weight") != null ? mtotal.get("net_weight") : "0"));
        array.put(ja);

        totalAfterFilter = weightNoteService.getTotalAfterFilter();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }

    @RequestMapping(value = "update_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void update_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wnr_id = support.getIntValue("wnr_id");
        int packing = support.getIntValue("packing");
        int noOfBag = support.getIntValue("noOfBag");
        float gross = support.getFloatValue("gross");
        float tare = support.getFloatValue("tare");
        WeightNoteReceipt wnr = weightNoteReceiptService.getWNRById(wnr_id);
        String username = ((User) request.getSession().getAttribute("user")).getUserName();
        wnr.setPackingMaster(packingService.getPackingById(packing));
        wnr.setNoOfBags(noOfBag);
        wnr.setGrossWeight(gross);
        wnr.setTareWeight(tare);

        String[][] element_arr = {
            {"date", Common.getDateFromDatabase(new Date(), Common.date_format)},
            {"packing", packingService.getPackingById(packing).getName()},
            {"noOfBags", noOfBag + ""},
            {"gross", gross + ""},
            {"tare", tare + ""}
        };
        String[][] main_arr = {
            {"type", "update"},
            {"user", username},
            {"element", Common.convertToJson((Object) element_arr)}
        };
        String log = Common.convertToJson((Object) main_arr);
        String temp_log = (wnr.getLog() == null) ? "" : wnr.getLog();
        wnr.setLog(temp_log + "," + log);

        weightNoteReceiptService.updateWNR(wnr);

        response.getWriter().print("1");
    }

    @RequestMapping(value = "delete_wnr.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_wnr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("wn_id");
        int wnr_id = support.getIntValue("wnr_id");
        String reason = support.getStringRequest("reason");
        WeightNoteReceipt wnr = weightNoteReceiptService.getWNRById(wnr_id);
        WeightNote wn = weightNoteService.getWnById(wn_id);
        String username = ((User) request.getSession().getAttribute("user")).getUserName();
        Map m = weightNoteReceiptService.delete_wnr(wn, wnr, username, reason);
        response.getWriter().print("[" + (new JSONObject(m)).toString() + "]");
    }

    @RequestMapping(value = "delete_wn.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_wn(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("wn_id");
        String reason = support.getStringRequest("reason");
        WeightNote wn = weightNoteService.getWnById(wn_id);
        String username = ((User) request.getSession().getAttribute("user")).getUserName();
        boolean doDelete = weightNoteService.delete_wn(wn, username, reason);
        if (!doDelete) {
            String message = "[2]";
            response.getWriter().print(message);
        } else {
            String message = "[1]";
            response.getWriter().print(message);
        }
    }

    @RequestMapping(value = "delete_wn_surround.htm", method = RequestMethod.POST)
    public @ResponseBody
    void delete_wn_surround(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("wn_id");
        WeightNote wn = weightNoteService.getWnById(wn_id);
        JSONObject json = new JSONObject();
        json = weightNoteService.delete_wn_surround(wn);
        response.getWriter().print("[" + json.toString() + "]");

    }

    @RequestMapping(value = "check_container_seal.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_container_seal(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("wn_id");
        WeightNote wn = weightNoteService.getWnById(wn_id);

        if (wn.getContainerNo() == null || wn.getContainerNo().equals("") || wn.getSealNo() == null || wn.getSealNo().equals("")) {
            String message = "[2]";
            response.getWriter().print(message);
        } else {
            String message = "[1]";
            response.getWriter().print(message);
        }

    }

    @RequestMapping(value = "check_wn_area.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wn_area(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("wn_id");
        int cur_area_id = support.getIntValue("cur_area");
        WeightNote wn = weightNoteService.getWnById(wn_id);
        if (wn.getWarehouseCell() != null) {
            if (wn.getWarehouseCell().getId().equals(cur_area_id)) {
                String message = "[1]";
                response.getWriter().print(message);
            } else {
                String message = "[3]";
                response.getWriter().print(message);
            }
        } else {
            String message = "[4]";
            response.getWriter().print(message);
        }
    }

    @RequestMapping(value = "get_wn_test.json", method = RequestMethod.POST)
    public @ResponseBody
    DeliveryView get_wn_test(HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        ServletSupporter support = new ServletSupporter(request);
        int wn_id = support.getIntValue("di_id");
//        WeightNote wn = weightNoteService.getWnById(wn_id);
        DeliveryView di = deliveryInsService.getLazyDiById(wn_id);
//        response.getWriter().print("[" + weightNoteService.convertToJson(wn, true, true) + "]");
        return di;
    }

    @RequestMapping("print_wnr_list.htm")
    public @ResponseBody
    void print_wnr_list(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String client = "";
        String client_ref = "";
        String supplier = "";
        String supplier_ref = "";
        String inst_ref = "";
        Map<String, Object> m = new HashMap<>();
        try {
            int wn_id = new ServletSupporter(request).getIntValue("wn_id");
            WeightNote weight_note = weightNoteService.getWnById(wn_id);
            QualityReport qr = weight_note.getQualityReport();
            ArrayList<WeightNoteReceipt> wnr_list = weightNoteReceiptService.getWNRByWN(wn_id);

            m.put("logo", Common.getAbsolutePath(request));
            m.put("wn_type", weight_note.getType());
            m.put("wn_ref", weight_note.getRefNumber());
            m.put("wn_date", Common.getDateFromDatabase(weight_note.getCreatedDate(), Common.date_format));
            m.put("grade", weight_note.getGradeMaster().getName());
            m.put("truck_no", weight_note.getTruckNo() != null ? weight_note.getTruckNo() : "");
            m.put("qr_ref", qr.getRefNumber());
            m.put("black", qr.getBlack() != null ? qr.getBlack().toString() : "0");
            m.put("broken", qr.getBroken() != null ? qr.getBroken().toString() : "0");
            m.put("brown", qr.getBrown() != null ? qr.getBrown().toString() : "0");
            m.put("black_broken", qr.getBlackBroken() != null ? qr.getBlackBroken().toString() : "0");
            m.put("foreign_matter", qr.getForeignMatter() != null ? qr.getForeignMatter().toString() : "0");
            m.put("moisture", qr.getMoisture() != null ? qr.getMoisture().toString() : "0");
            m.put("old_crop", qr.getOldCrop() != null ? qr.getOldCrop().toString() : "0");
            m.put("other_bean", qr.getOtherBean() != null ? qr.getOtherBean().toString() : "0");
            m.put("moldy", qr.getMoldy() != null ? qr.getMoldy().toString() : "0");
            m.put("worm", qr.getWorm() != null ? qr.getWorm().toString() : "0");
            m.put("above_sc20", qr.getAboveSc20() != null ? qr.getAboveSc20().toString() : "0");
            m.put("sc20", qr.getSc20() != null ? qr.getSc20().toString() : "0");
            m.put("sc19", qr.getSc19() != null ? qr.getSc19().toString() : "0");
            m.put("sc18", qr.getSc18() != null ? qr.getSc18().toString() : "0");
            m.put("sc17", qr.getSc17() != null ? qr.getSc17().toString() : "0");
            m.put("sc16", qr.getSc16() != null ? qr.getSc16().toString() : "0");
            m.put("sc15", qr.getSc15() != null ? qr.getSc15().toString() : "0");
            m.put("sc14", qr.getSc14() != null ? qr.getSc14().toString() : "0");
            m.put("sc13", qr.getSc13() != null ? qr.getSc13().toString() : "0");
            m.put("sc12", qr.getSc12() != null ? qr.getSc12().toString() : "0");
            m.put("below_sc12", qr.getBelowSc12() != null ? qr.getBelowSc12().toString() : "0");

            ArrayList<Map<String, String>> ct_map_list = new ArrayList<>();
            ArrayList<CupTest> cup_test_list = new ArrayList<>(qr.getCupTests());
            int cup_no = 1;
            for (int i = 0; i < cup_test_list.size(); i = i + 5) {
                Map<String, String> ct_map = new HashMap<>();
                ct_map.put("cup_no", cup_no + "");
                ct_map.put("Cup1", cup_test_list.get(i).getNote());
                ct_map.put("Cup2", cup_test_list.get(i + 1).getNote());
                ct_map.put("Cup3", cup_test_list.get(i + 2).getNote());
                ct_map.put("Cup4", cup_test_list.get(i + 3).getNote());
                ct_map.put("Cup5", cup_test_list.get(i + 4).getNote());
                cup_no++;
                ct_map_list.add(ct_map);
            }
            m.put("test_cup", new JSONArray(ct_map_list));

            ArrayList<Map<String, String>> wnr_map_list = new ArrayList<>();
            float total_gross = 0;
            float total_tare = 0;
            float total_net = 0;
            int total_bag = 0;
            int count = 1;
            for (WeightNoteReceipt wnr : wnr_list) {
                Map<String, String> wnr_map = new HashMap<>();
                wnr_map.put("order", count + "");
                wnr_map.put("ref_number", wnr.getRefNumber());
                //wnr_map.put("pallet", wnr.getPalletName());
                wnr_map.put("date_time", Common.getDateFromDatabase(wnr.getDate(), Common.date_format));
                wnr_map.put("no_of_bags", wnr.getNoOfBags().toString());
                wnr_map.put("packing", wnr.getPackingMaster().getName());
                wnr_map.put("gross_weight", wnr.getGrossWeight().toString());
                wnr_map.put("tare_weight", wnr.getTareWeight().toString());
                wnr_map.put("net_weight", (wnr.getGrossWeight() - wnr.getTareWeight()) + "");
                total_gross += wnr.getGrossWeight();
                total_tare += wnr.getTareWeight();
                total_net += (wnr.getGrossWeight() - wnr.getTareWeight());
                total_bag += wnr.getNoOfBags();
                count++;
                wnr_map_list.add(wnr_map);
            }
            m.put("wnr", (new JSONArray(wnr_map_list)));

            m.put("quantity", total_bag + "");
            m.put("gross", total_gross + "");
            m.put("tare", total_tare + "");
            m.put("net", total_net + "");

            switch (weight_note.getType()) {
                case "IM": {
                    DeliveryInstruction di = deliveryInsService.getDiById(weight_note.getInstId());
                    client = di.getCompanyMasterByClientId().getName();
                    client_ref = di.getClientRef();
                    supplier = di.getCompanyMasterBySupplierId().getName();
                    supplier_ref = di.getSupplierRef();
                    inst_ref = di.getRefNumber();
                }
                break;
                case "XP":
                case "IP": {
                    ProcessingInstruction pi = processingService.getPiById(weight_note.getInstId());
                    client = pi.getCompanyMasterByClientId().getName();
                    client_ref = pi.getClientRef();
                    inst_ref = pi.getRefNumber();
                }
                break;
                case "EX": {
                    ShippingInstruction si = shippingService.getSiById(weight_note.getInstId());
                    client = si.getCompanyMasterByClientId().getName();
                    client_ref = si.getClientRef();
                    supplier = si.getCompanyMasterBySupplierId().getName();
                    supplier_ref = si.getSupplierRef();
                    inst_ref = si.getRefNumber();
                }
                break;
            }

            m.put("client", client);
            m.put("client_ref", client_ref);
            m.put("supplier", supplier);
            m.put("supplier_ref", supplier_ref);
            m.put("inst_ref", inst_ref);
            m.put("username", ((User) request.getSession().getAttribute("user")).getUserName());
            JSONObject json = new JSONObject(m);
            System.out.println(json.toString());
            response.getWriter().print(json.toString());
        } catch (Exception e) {
            response.getWriter().print(e.getMessage());
        }
    }

    @RequestMapping(value = "check_wn_grade_empty.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wn_grade_empty(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        if (id > 0) {
            response.getWriter().print(commonService.fieldIsEmpty("weight_note", "grade_id", id));
        } else {
            response.getWriter().print(false);
        }
    }

    @RequestMapping(value = "check_wn_packing_empty.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wn_packing_empty(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        if (id > 0) {
            response.getWriter().print(commonService.fieldIsEmpty("weight_note", "packing_id", id));
        } else {
            response.getWriter().print(false);
        }
    }

    @RequestMapping(value = "check_wn_editable.htm", method = RequestMethod.POST)
    public @ResponseBody
    void check_wn_editable(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        response.getWriter().print(weightNoteService.checkWnEditable(id));
    }

    @RequestMapping(value = "weighing/waypoint.htm", method = RequestMethod.GET)
    public @ResponseBody
    void test(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        String type = Common.getMillType(support.getByteValue("type"));
        byte status = support.getByteValue("status");
        int inst_id = support.getIntValue("instruction");
        int grade_id = support.getIntValue("grade");
        int supplier = support.getIntValue("supplier");
        int pledge_id = support.getIntValue("pledge");
        String searchStr = support.getStringRequest("searchStr");
        int row = support.getIntValue("row");
        ArrayList<Object[]> obj = weightNoteService.getWeightNoteRefList(type, searchStr, inst_id, grade_id, status, supplier, pledge_id, row);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        if (obj != null && !obj.isEmpty()) {
            response.getWriter().print(new GenTemplate(request).generateRefFilterList(tpl, obj, "wn"));
        } else {
            response.getWriter().print("empty");
        }
    }

    @RequestMapping(value = "upload_snap.htm", method = RequestMethod.POST)
    public @ResponseBody
    void uploadImage(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
        try {
            //if (request instanceof MultipartHttpServletRequest){
//                MultipartHttpServletRequest multiPartRequest = (MultipartHttpServletRequest)request;
//                Iterator<String> filenames = multiPartRequest.getFileNames();
//                while(filenames.hasNext()){
//                    String filename = filenames.next();
//                    MultipartFile file = multiPartRequest.getFile(filename);
            this.saveMultipartToDisk(file);
//                }

            response.getWriter().print(Common.getUrlPath(request) + "screen_shots/" + file.getOriginalFilename());
            //}
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

    }

    //get directory base on user_id
    private String calculateDestinationDirectory() {
        String result = this.context.getRealPath("");
        result += "/";
        result += "screen_shots";
        return result;
    }

    //get the file dir
    private String calculateDestinationPath(MultipartFile file) {
        String result = this.calculateDestinationDirectory();
        result += "/";
        result += file.getOriginalFilename();
        return result;
    }

    private String saveMultipartToDisk(MultipartFile file) throws Exception {
        File dir = new File(this.calculateDestinationDirectory());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String path = this.calculateDestinationPath(file);
        File multipartFile = new File(path);
        file.transferTo(multipartFile);
        return path;
    }
}
