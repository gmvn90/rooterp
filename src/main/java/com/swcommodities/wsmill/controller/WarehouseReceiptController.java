/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.io.IOException;
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

import com.google.gson.Gson;
import com.swcommodities.wsmill.bo.CommonService;
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.QualityReportService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.bo.WarehouseReceiptService;
import com.swcommodities.wsmill.bo.WeightNoteReceiptService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.WarehouseReceipt;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.GenTemplate;
import com.swcommodities.wsmill.utils.ServletSupporter;

import net.sf.jtpl.Template;

/**
 *
 * @author kiendn
 */
@org.springframework.stereotype.Controller
@Transactional(propagation = Propagation.REQUIRED)
public class WarehouseReceiptController {
    //load_wr_info

    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired()
    private DeliveryInsService deliveryService;
    @Autowired()
    private WarehouseReceiptService warehouseReceiptService;
    @Autowired()
    private WeightNoteService weightNoteService;
    @Autowired()
    private WeightNoteReceiptService weightNoteReceiptService;
    @Autowired()
    private QualityReportService qualityReportService;
    @Autowired()
    private ShippingService shippingService;
    @Autowired()
    private CommonService commonService;

    @RequestMapping("load_ins_info.htm")
    public @ResponseBody
    void load_wr_info(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int inst_id = support.getIntValue("inst_id");
        //int wr_id = support.getIntValue("wr_id");
        String[] obj_arr = new String[4];
        switch (request.getParameter("type")) {
            case "IM": //load di info
                DeliveryInstruction di = deliveryService.getDiById(inst_id);
                obj_arr[0] = "";
                obj_arr[1] = di.getGradeMaster().getName();
                obj_arr[2] = di.getCompanyMasterBySupplierId().getName();
                obj_arr[3] = "";
                break;
            //obj_arr = []
            case "EX"://load si info
                ShippingInstruction si = shippingService.getSiById(inst_id);
                obj_arr[0] = "";
                obj_arr[1] = ((si.getGradeMaster() != null) ? si.getGradeMaster().getName() : "");
                obj_arr[2] = si.getCompanyMasterBySupplierId().getName();
                obj_arr[3] = "";
                break;
        }
        response.getWriter().print(new GenTemplate(request).generateWrInfo(context, obj_arr));
    }

    @RequestMapping("get_wr_list.htm")
    public @ResponseBody
    void get_wr_list(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int inst_id = support.getIntValue("inst_id");
        //int wr_id = support.getIntValue("wr_id");
        ArrayList<WarehouseReceipt> wrs = warehouseReceiptService.getWarehouseReceipt(support.getStringRequest("type"));
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        response.getWriter().print(new GenTemplate(request).generateRefList(tpl, wrs, "wr_"));
    }

    @RequestMapping("get_wr.htm")
    public @ResponseBody
    void get_wr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int id = support.getIntValue("id");
        //int wr_id = support.getIntValue("wr_id");
        Gson gson = new Gson();
        WarehouseReceipt wr = warehouseReceiptService.findById(id);
        //get wn list based on wr
        ArrayList<WeightNote> wns = warehouseReceiptService.getWnByWR(id);
        String grade = "";
        String supplier = "";
        Object[] grade_supplier;
        switch (wr.getInstType()) {
            case "IM":
                grade_supplier = commonService.getGradeCompanyFromInst(wr.getInstId(), "IM", "supplier_id");
                if (grade_supplier != null && grade_supplier.length > 0) {
                    grade = grade_supplier[0].toString();
                    supplier = grade_supplier[1].toString();
                }
                break;
            case "EX":
                grade_supplier = commonService.getGradeCompanyFromInst(wr.getInstId(), "EX", "client_id");
                if (grade_supplier != null && grade_supplier.length > 0) {
                    grade = grade_supplier[0].toString();
                    supplier = grade_supplier[1].toString();
                }
                break;
        }
        String json = gson.toJson(new com.swcommodities.wsmill.json.obj.WarehouseReceipt(wr, wns, grade, supplier));
        response.getWriter().print("[" + json + "]");
    }

    @RequestMapping("load_avail_wn_scroll.htm")
    public @ResponseBody
    void load_avail_wn_scroll(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int inst_id = support.getIntValue("inst_id");
        String type = support.getStringRequest("type");
        ArrayList<Object[]> wns = weightNoteService.getAvailableWeightNoteForWR(inst_id, type);
        response.getWriter().print(new GenTemplate(request).generateWrRowScroll(context, wns, "avai"));
    }

    @RequestMapping("load_avail_wn.htm")
    public @ResponseBody
    void load_avail_wn(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int inst_id = support.getIntValue("inst_id");
        String type = support.getStringRequest("type");
        ArrayList<Object[]> wns = weightNoteService.getAvailableWeightNoteForWR(inst_id, type);
        response.getWriter().print(new GenTemplate(request).generateWrRow(context, wns, "avai"));
    }

    //getWnListFromData
    @RequestMapping("getWnSelectedListFromData.htm")
    public @ResponseBody
    void getWnSelectedListFromData(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String[] id_arr = request.getParameter("wn_list").split(",");
        if (id_arr != null && id_arr.length > 0) {
            response.getWriter().print(warehouseReceiptService.wnJsonObject(id_arr, "selected"));
        } else {
            response.getWriter().print("");
        }
    }

    @RequestMapping("update_warehouse_receipt.htm")
    public @ResponseBody
    void update_warehouse_receipt(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        QualityReport qr = null;
        Gson gson = new Gson();
        ServletSupporter support = new ServletSupporter(request);
        String[] id_arr = request.getParameter("wn_list").split(",");
        com.swcommodities.wsmill.json.obj.WarehouseReceipt wrJson = gson.fromJson(support.getStringRequest("data"), com.swcommodities.wsmill.json.obj.WarehouseReceipt.class);
        WarehouseReceipt wr = warehouseReceiptService.getWrFromJsonObj(wrJson, request);

        if (wrJson.getId() == -1) { /* new warehouse receipt */

            String type = (wrJson.getInstType().equals("IM")) ? "WR" : "WC";
            wr.setInstId(wrJson.getInstId());
            qr = new QualityReport(type, qualityReportService.getNewQRRef(type));
            qr.setDate(new Date());
            qr.setStatus(Constants.COMPLETE);
            //qualityReportService.updateQuality(qr);
            
            wr.setRefNumber(warehouseReceiptService.getNewWrRef(wrJson.getInstType()));
            wr.setDate(new Date());
        }else{
            qr = wr.getQualityReport();
        }
        
        //update quality report information based on wrjson
        String wn_arr = "";
        for (int i = 0; i < wrJson.getWn().size(); i++) {
            if (i != 0) {
                wn_arr += ",";
            }
            wn_arr += wrJson.getWn().get(i).toString();
        }
        if (!wn_arr.equals("")) {
            QualityReport new_qr = weightNoteService.getweightedAverageFromQr(wn_arr);
            qr.setAboveSc20(new_qr.getAboveSc20());
            qr.setSc20(new_qr.getSc20());
            qr.setSc19(new_qr.getSc19());
            qr.setSc18(new_qr.getSc18());
            qr.setSc17(new_qr.getSc17());
            qr.setSc16(new_qr.getSc16());
            qr.setSc15(new_qr.getSc15());
            qr.setSc14(new_qr.getSc14());
            qr.setSc13(new_qr.getSc13());
            qr.setSc12(new_qr.getSc12());
            qr.setBelowSc12(new_qr.getBelowSc12());
            qr.setBlack(new_qr.getBlack());
            qr.setBroken(new_qr.getBroken());
            qr.setBlackBroken(new_qr.getBlackBroken());
            qr.setBrown(new_qr.getBrown());
            qr.setMoisture(new_qr.getMoisture());
            qr.setOldCrop(new_qr.getOldCrop());
            qr.setExcelsa(new_qr.getExcelsa());
            qr.setForeignMatter(new_qr.getForeignMatter());
            qr.setWorm(new_qr.getWorm());
            qr.setMoldy(new_qr.getMoldy());
            qr.setOtherBean(new_qr.getOtherBean());
        }
        
        qualityReportService.updateQuality(qr);
        
        if (wrJson.getId() == -1) {
            wr.setQualityReport(qr);
        }
        
        //update wr
        int wr_id = warehouseReceiptService.update(wr, id_arr);

        //update weight note if any
        warehouseReceiptService.updateSelectedWN(wrJson.getWn(), wr_id);

        String new_json = warehouseReceiptService.wrJsonObject(wr, id_arr);
        response.getWriter().print("[" + new_json + "]");

    }

//    @RequestMapping("get_inst_by_wr_id.htm")
//    public @ResponseBody
//    void get_inst_by_wr_id(HttpServletResponse response) throws Exception {
//        ServletSupporter support = new ServletSupporter(request);
//        DeliveryInstruction di = deliveryService.getDiById(support.getIntValue("id"));
//        response.getWriter().print("<li class='chosen' id='ins_" + di.getId() + "'>" + di.getRefNumber() + "</li>");
//    }

    @RequestMapping("deleteWr.htm")
    public @ResponseBody
    void deleteWr(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        Gson gson = new Gson();
        ServletSupporter support = new ServletSupporter(request);
        com.swcommodities.wsmill.json.obj.WarehouseReceipt wrJson = gson.fromJson(support.getStringRequest("data"), com.swcommodities.wsmill.json.obj.WarehouseReceipt.class);
        if (wrJson.getId() > 0) {
            //update wrc of all wn to null
            String[] id_arr = request.getParameter("wn_list").split(",");
            if (wrJson.getWn().size() > 0) {
                for (int wn_id : wrJson.getWn()) {
                    WeightNote wn = weightNoteService.getWnById(wn_id);
                    wn.setWrcId(null);
                    weightNoteService.updateWN(wn);
                }
            }
            //update status qr_id to deleted
            QualityReport qr = qualityReportService.getQualityReportById(wrJson.getQrId());
            qr.setStatus(Constants.DELETED);
            qualityReportService.updateQuality(qr);

            //update warehouse_receipt
            WarehouseReceipt wr = warehouseReceiptService.findById(wrJson.getId());
            wr.setStatus(Constants.DELETED);
            warehouseReceiptService.update(wr, id_arr);
        }
    }
    
    @RequestMapping("print_wr.htm")
    public @ResponseBody
    void print_wr(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String client = "";
        String client_ref = "";
        String supplier = "";
        String supplier_ref = "";
        String inst_ref = "";
        String grade = "";
        Map<String, Object> m = new HashMap<>();
        try {
            int id = new ServletSupporter(request).getIntValue("id");
            WarehouseReceipt wr = warehouseReceiptService.findById(id);
            QualityReport qr = wr.getQualityReport();
            ArrayList<WeightNote> wn_list = weightNoteService.getWeightNotesByWrcId(id);
            switch(wr.getInstType()){
                case "IM": {
                    DeliveryInstruction di = deliveryService.getDiById(wr.getInstId());
                    client = di.getCompanyMasterByClientId().getName();
                    client_ref = di.getClientRef();
                    supplier = di.getCompanyMasterBySupplierId().getName();
                    supplier_ref = di.getSupplierRef();
                    grade = di.getGradeMaster().getName();
                    inst_ref = di.getRefNumber();
                }break;
                case "EX":{
                    ShippingInstruction si = shippingService.getSiById(wr.getInstId());
                    client = si.getCompanyMasterByClientId().getName();
                    client_ref = si.getClientRef();
                    supplier = si.getCompanyMasterBySupplierId().getName();
                    supplier_ref = si.getSupplierRef();
                    grade = si.getGradeMaster().getName();
                    inst_ref = si.getRefNumber();
                }break;
            }
            m.put("logo", Common.getAbsolutePath(request));
            m.put("type", wr.getInstType());
            m.put("wr_ref", wr.getRefNumber());
            m.put("client", client);
            m.put("client_ref", client_ref);
            m.put("supplier", supplier);
            m.put("supplier_ref", supplier_ref);
            m.put("inst_ref", inst_ref);
            m.put("wr_date", Common.getDateFromDatabase(wr.getDate(), Common.date_format));
            m.put("quality", "Robusta");
            m.put("grade", grade);
            m.put("qr_ref", qr.getRefNumber());
            m.put("black", qr.getBlack() != null ? qr.getBlack().toString() : "0");
            m.put("broken", qr.getBroken() != null ? qr.getBroken().toString() : "0");
            m.put("brown", qr.getBrown() != null ? qr.getBrown().toString() : "0");
            m.put("black_broken", qr.getBlackBroken() != null ? qr.getBlackBroken().toString() : "0");
            m.put("foreign_matter", qr.getForeignMatter() != null ? qr.getForeignMatter().toString() : "0");
            m.put("moisture", qr.getMoisture() != null ? qr.getMoisture().toString() : "0");
            m.put("old_crop", qr.getOldCrop() != null ? qr.getOldCrop().toString() : "0");
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
            
            ArrayList<Map<String, String>> wn_map_list = new ArrayList<>();
            int count = 1;
            int total_bag = 0;
            double total_gross = 0;
            double total_tare = 0;
            double total_net = 0;
            for (WeightNote wn : wn_list) {
                Map wnr_total = weightNoteService.getWnrTotal(wn.getId());
                Map<String, String> wn_map = new HashMap<>();
                wn_map.put("order", count + "");
                wn_map.put("ref_number", wn.getRefNumber());
                wn_map.put("quality_ref", wn.getQualityReport().getRefNumber());
                wn_map.put("date_time", Common.getDateFromDatabase(wn.getCreatedDate(), Common.date_format));
                wn_map.put("packing", wn.getPackingMaster().getName());
                wn_map.put("truck_no", wn.getTruckNo() != null ? wn.getTruckNo() : "");
                wn_map.put("quantity", String.valueOf(wnr_total.get("quantity")));
                wn_map.put("gross_weight", String.valueOf(wnr_total.get("gross")));
                wn_map.put("tare_weight", String.valueOf(wnr_total.get("tare")));
                wn_map.put("net_weight", String.valueOf(wnr_total.get("net")));
                total_bag += (long)wnr_total.get("quantity");
                total_gross += (double)wnr_total.get("gross");
                total_tare += (double)wnr_total.get("tare");
                total_net += (double)wnr_total.get("net");
                count++;
                wn_map_list.add(wn_map);
            }
            m.put("wn", new JSONArray(wn_map_list));
            m.put("quantity", total_bag + "");
            m.put("gross", total_gross + "");
            m.put("tare", total_tare + "");
            m.put("net", total_net + "");
            JSONObject json = new JSONObject(m);
            System.out.println(json.toString());
            response.getWriter().print(json.toString());
        } catch (IOException e) {
            response.getWriter().print(e.getMessage());
        }
    }
}
