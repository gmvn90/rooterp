/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.io.File;
import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.CompanyService;
import com.swcommodities.wsmill.bo.CupTestService;
import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.bo.QualityReportService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.bo.WarehouseReceiptService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.exels.QualityReportExcel;
import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.CupTest;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.InstructionStatus;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.WarehouseReceipt;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.object.QualityReportObj;
import com.swcommodities.wsmill.repository.CityRepository;
import com.swcommodities.wsmill.service.MyAsyncService;
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
public class QualityController {

    @Autowired
    private CupTestService cupTestService;
    @Autowired
    private DeliveryInsService deliveryInsService;
    @Autowired
    private QualityReportService qualityReportService;
    @Autowired
    private WeightNoteService weightNoteService;
    @Autowired
    private WarehouseReceiptService warehouseReceiptService;
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private ShippingService shippingService;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "update_quality_report", method = RequestMethod.POST)
    public @ResponseBody
    void update_quality_report(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int qrid = supporter.getIntValue("qrid");
        QualityReport qr = qualityReportService.getQualityReportById(qrid);
        if (request.getSession().getAttribute("user") != null) {
            if (qr.getStatus().equals((byte) 1)) {
                User user = (User) request.getSession().getAttribute("user");
                if (user.getId().equals(10)) {
                    qr.setBlack(supporter.getFloatValue("black"));
                    qr.setBrown(supporter.getFloatValue("brown"));
                    qr.setBroken(supporter.getFloatValue("broken"));
                    qr.setBlackBroken(supporter.getFloatValue("black_broken"));
                    qr.setWorm(supporter.getFloatValue("worm"));
                    qr.setForeignMatter(supporter.getFloatValue("fm"));
                    qr.setMoisture(supporter.getFloatValue("moisture"));
                    qr.setMoldy(supporter.getFloatValue("moldy"));
                    qr.setOldCrop(supporter.getFloatValue("old_crop"));
                    qr.setOtherBean(supporter.getFloatValue("other_bean"));
                    qr.setCherry((float) 0);
                    qr.setDefect(supporter.getFloatValue("defect"));
                    qr.setAboveSc20((float) 0);
                    qr.setSc20(supporter.getFloatValue("sc20"));
                    qr.setSc19(supporter.getFloatValue("sc19"));
                    qr.setSc18(supporter.getFloatValue("sc18"));
                    qr.setSc17(supporter.getFloatValue("sc17"));
                    qr.setSc16(supporter.getFloatValue("sc16"));
                    qr.setSc15(supporter.getFloatValue("sc15"));
                    qr.setSc14(supporter.getFloatValue("sc14"));
                    qr.setSc13(supporter.getFloatValue("sc13"));
                    qr.setSc12(supporter.getFloatValue("sc12"));
                    qr.setBelowSc12(supporter.getFloatValue("below_sc12"));
                    qr.setUpdatedDate(new Date());
                    qr.setRejectedCup(supporter.getIntValue("rejected_value"));
                    qr.setRemark(supporter.getStringRequest("remark"));
                    qr.setStatus(supporter.getByteValue("status"));
                    qr.setUser((User) request.getSession().getAttribute("user"));

                    cupTestService.deleteOldCupTest(cupTestService.getCupTestByQrId(qrid));
                    int count_cup = supporter.getIntValue("totalcup");
                    String[] cup1 = new String[count_cup];
                    String[] cup2 = new String[count_cup];
                    for (int i = 1; i <= count_cup; i++) {

                        String note = supporter.getStringRequest("ct_" + i);
                        cup1[i - 1] = "cup" + i;
                        cup2[i - 1] = note;
                        CupTest ct = new CupTest(qr, note);
                        cupTestService.updateCupTest(ct);
                    }

                    String ct_json = Common.generateJsonString(cup1, cup2);
                    String[] el1 = {"date", "status", "above_sc20", "sc20", "sc19", "sc18", "sc17", "sc16", "sc15", "sc14", "sc13", "sc12", "below_sc12", "black", "broken", "black_broken", "brown", "moisture", "old_crop", "foreign_matter", "worm", "moldy", "other_bean", "cherry", "defect", "rejected_cup", "remark", "cups_value"};
                    String[] el2 = {Common.getDateFromDatabase(new Date(), Common.date_format), qr.getStatus().toString(), qr.getAboveSc20().toString(), qr.getSc20().toString(), qr.getSc19().toString(), qr.getSc18().toString(), qr.getSc17().toString(), qr.getSc16().toString(), qr.getSc15().toString(),
                            qr.getSc14().toString(), qr.getSc13().toString(), qr.getSc12().toString(), qr.getBelowSc12().toString(), qr.getBlack().toString(), qr.getBroken().toString(), qr.getBlackBroken().toString(), qr.getBrown().toString(), qr.getMoisture().toString(),
                            qr.getOldCrop().toString(), qr.getForeignMatter().toString(), qr.getWorm().toString(), qr.getMoldy().toString(), qr.getOtherBean().toString(), qr.getCherry().toString(), qr.getDefect().toString(), qr.getRejectedCup().toString(), qr.getRemark(), ct_json};
                    String el_json = Common.generateJsonString(el1, el2);
                    String[] arr1 = {"type", "user", "element"};
                    String[] arr2 = {"update", qr.getUser().getUserName(), el_json};
                    if (qr.getLog() == null) {
                        qr.setLog("");
                    }
                    String log = qr.getLog() + "," + Common.generateJsonString(arr1, arr2);
                    qr.setLog(log);

                    response.getWriter().print(qualityReportService.updateQuality(qr));
                } else {
                    response.getWriter().print(-1);
                }
            } else {
                qr.setBlack(supporter.getFloatValue("black"));
                qr.setBrown(supporter.getFloatValue("brown"));
                qr.setBroken(supporter.getFloatValue("broken"));
                qr.setBlackBroken(supporter.getFloatValue("black_broken"));
                qr.setWorm(supporter.getFloatValue("worm"));
                qr.setForeignMatter(supporter.getFloatValue("fm"));
                qr.setMoisture(supporter.getFloatValue("moisture"));
                qr.setMoldy(supporter.getFloatValue("moldy"));
                qr.setOldCrop(supporter.getFloatValue("old_crop"));
                qr.setOtherBean(supporter.getFloatValue("other_bean"));
                qr.setCherry((float) 0);
                qr.setDefect(supporter.getFloatValue("defect"));
                qr.setAboveSc20((float) 0);
                qr.setSc20(supporter.getFloatValue("sc20"));
                qr.setSc19(supporter.getFloatValue("sc19"));
                qr.setSc18(supporter.getFloatValue("sc18"));
                qr.setSc17(supporter.getFloatValue("sc17"));
                qr.setSc16(supporter.getFloatValue("sc16"));
                qr.setSc15(supporter.getFloatValue("sc15"));
                qr.setSc14(supporter.getFloatValue("sc14"));
                qr.setSc13(supporter.getFloatValue("sc13"));
                qr.setSc12(supporter.getFloatValue("sc12"));
                qr.setBelowSc12(supporter.getFloatValue("below_sc12"));
                qr.setUpdatedDate(new Date());
                qr.setRejectedCup(supporter.getIntValue("rejected_value"));
                qr.setRemark(supporter.getStringRequest("remark"));
                qr.setStatus(supporter.getByteValue("status"));
                qr.setUser((User) request.getSession().getAttribute("user"));

                cupTestService.deleteOldCupTest(cupTestService.getCupTestByQrId(qrid));
                int count_cup = supporter.getIntValue("totalcup");
                String[] cup1 = new String[count_cup];
                String[] cup2 = new String[count_cup];
                for (int i = 1; i <= count_cup; i++) {

                    String note = supporter.getStringRequest("ct_" + i);
                    cup1[i - 1] = "cup" + i;
                    cup2[i - 1] = note;
                    CupTest ct = new CupTest(qr, note);
                    cupTestService.updateCupTest(ct);
                }

                String ct_json = Common.generateJsonString(cup1, cup2);
                String[] el1 = {"date", "status", "above_sc20", "sc20", "sc19", "sc18", "sc17", "sc16", "sc15", "sc14", "sc13", "sc12", "below_sc12", "black", "broken", "black_broken", "brown", "moisture", "old_crop", "foreign_matter", "worm", "moldy", "other_bean", "cherry", "defect", "rejected_cup", "remark", "cups_value"};
                String[] el2 = {Common.getDateFromDatabase(new Date(), Common.date_format), qr.getStatus().toString(), qr.getAboveSc20().toString(), qr.getSc20().toString(), qr.getSc19().toString(), qr.getSc18().toString(), qr.getSc17().toString(), qr.getSc16().toString(), qr.getSc15().toString(),
                        qr.getSc14().toString(), qr.getSc13().toString(), qr.getSc12().toString(), qr.getBelowSc12().toString(), qr.getBlack().toString(), qr.getBroken().toString(), qr.getBlackBroken().toString(), qr.getBrown().toString(), qr.getMoisture().toString(),
                        qr.getOldCrop().toString(), qr.getForeignMatter().toString(), qr.getWorm().toString(), qr.getMoldy().toString(), qr.getOtherBean().toString(), qr.getCherry().toString(), qr.getDefect().toString(), qr.getRejectedCup().toString(), qr.getRemark(), ct_json};
                String el_json = Common.generateJsonString(el1, el2);
                String[] arr1 = {"type", "user", "element"};
                String[] arr2 = {"update", qr.getUser().getUserName(), el_json};
                if (qr.getLog() == null) {
                    qr.setLog("");
                }
                String log = qr.getLog() + "," + Common.generateJsonString(arr1, arr2);
                qr.setLog(log);

                response.getWriter().print(qualityReportService.updateQuality(qr));
            }
        } else {
            response.getWriter().print(-1);
        }

    }
    
    @RequestMapping(value = "update_quality_report1.htm", method = RequestMethod.POST)
    public @ResponseBody
    void update_quality_report1(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int qrid = supporter.getIntValue("qrid");
        QualityReport qr = qualityReportService.getQualityReportById(qrid);

        qr.setBlack(supporter.getFloatValue("black"));
        qr.setBrown(supporter.getFloatValue("brown"));
        qr.setBroken(supporter.getFloatValue("broken"));
        qr.setBlackBroken(supporter.getFloatValue("black_broken"));
        qr.setWorm(supporter.getFloatValue("worm"));
        qr.setForeignMatter(supporter.getFloatValue("fm"));
        qr.setMoisture(supporter.getFloatValue("moisture"));
        qr.setMoldy(supporter.getFloatValue("moldy"));
        qr.setOldCrop(supporter.getFloatValue("old_crop"));
        qr.setOtherBean(supporter.getFloatValue("other_bean"));
        qr.setCherry(supporter.getFloatValue("cherry"));
        qr.setDefect(supporter.getFloatValue("defect"));
        qr.setAboveSc20(supporter.getFloatValue("above_sc20"));
        qr.setSc20(supporter.getFloatValue("sc20"));
        qr.setSc19(supporter.getFloatValue("sc19"));
        qr.setSc18(supporter.getFloatValue("sc18"));
        qr.setSc17(supporter.getFloatValue("sc17"));
        qr.setSc16(supporter.getFloatValue("sc16"));
        qr.setSc15(supporter.getFloatValue("sc15"));
        qr.setSc14(supporter.getFloatValue("sc14"));
        qr.setSc13(supporter.getFloatValue("sc13"));
        qr.setSc12(supporter.getFloatValue("sc12"));
        qr.setBelowSc12(supporter.getFloatValue("below_sc12"));
        
        qr.setUpdatedDate(new Date());
        qr.setRejectedCup(supporter.getIntValue("rejected_value"));
        qr.setRemark(supporter.getStringRequest("remark"));
        qr.setStatus(supporter.getByteValue("status"));
        qr.setUser((User) request.getSession().getAttribute("user"));

        cupTestService.deleteOldCupTest(cupTestService.getCupTestByQrId(qrid));
        int count_cup = supporter.getIntValue("totalcup");
        String[] cup1 = new String[count_cup];
        String[] cup2 = new String[count_cup];
        for (int i = 1; i <= count_cup; i++) {

            String note = supporter.getStringRequest("ct_" + i);
            cup1[i - 1] = "cup" + i;
            cup2[i - 1] = note;
            CupTest ct = new CupTest(qr, note);
            cupTestService.updateCupTest(ct);
        }

        String ct_json = Common.generateJsonString(cup1, cup2);
        String[] el1 = {"date", "status", "above_sc20", "sc20", "sc19", "sc18", "sc17", "sc16", "sc15", "sc14", "sc13", "sc12", "below_sc12", "black", "broken", "black_broken", "brown", "moisture", "old_crop", "foreign_matter", "worm", "moldy", "other_bean", "cherry", "defect", "rejected_cup", "remark", "cups_value"};
        String[] el2 = {Common.getDateFromDatabase(new Date(), Common.date_format), qr.getStatus().toString(), qr.getAboveSc20().toString(), qr.getSc20().toString(), qr.getSc19().toString(), qr.getSc18().toString(), qr.getSc17().toString(), qr.getSc16().toString(), qr.getSc15().toString(),
            qr.getSc14().toString(), qr.getSc13().toString(), qr.getSc12().toString(), qr.getBelowSc12().toString(), qr.getBlack().toString(), qr.getBroken().toString(), qr.getBlackBroken().toString(), qr.getBrown().toString(), qr.getMoisture().toString(),
            qr.getOldCrop().toString(), qr.getForeignMatter().toString(), qr.getWorm().toString(), qr.getMoldy().toString(), qr.getOtherBean().toString(), qr.getCherry().toString(), qr.getDefect().toString(), qr.getRejectedCup().toString(), qr.getRemark(), ct_json};
        String el_json = Common.generateJsonString(el1, el2);
        String[] arr1 = {"type", "user", "element"};
        String[] arr2 = {"update", qr.getUser().getUserName(), el_json};
        if (qr.getLog() == null) {
            qr.setLog("");
        }
        String log = qr.getLog() + "," + Common.generateJsonString(arr1, arr2);
        qr.setLog(log);

        response.getWriter().print(qualityReportService.updateQuality(qr));

    }

    @RequestMapping(value = "get_qr_ref_list.htm", method = RequestMethod.POST)
    public @ResponseBody
    String get_qr_ref_list(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        String qr_type = supporter.getStringRequest("qrType_id");
        ArrayList<QualityReport> qrs = qualityReportService.getQrRefList(qr_type);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateQrRefList(tpl, qrs);
    }

    @RequestMapping(value = "get_qr_content.htm", method = RequestMethod.POST)
    public @ResponseBody
    String get_qr_content(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int qr_id = supporter.getIntValue("qr_id");
        QualityReport qr = qualityReportService.getQualityReportById(qr_id);
        WeightNote wn;
        WarehouseReceipt wr;
        DeliveryInstruction di;
        ProcessingInstruction pi;
        ShippingInstruction si;
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/quality_report_info_import.html"));
        switch (qr.getType()) {
            case Constants.IM:
                wn = weightNoteService.getWnByQrId(qr.getId());
                di = deliveryInsService.getDiById(wn.getInstId());
                return new GenTemplate(request).generateQualityReportContent(tpl, qr, wn.getRefNumber(), wn.getGradeMaster().getName(), di.getCompanyMasterBySupplierId().getName());
            case Constants.XP:
                wn = weightNoteService.getWnByQrId(qr.getId());
                pi = processingService.getPiById(wn.getInstId());
                return new GenTemplate(request).generateQualityReportContent(tpl, qr, wn.getRefNumber(), wn.getGradeMaster().getName(), pi.getCompanyMasterByClientId().getName());
            case Constants.EX:
                wn = weightNoteService.getWnByQrId(qr.getId());
                si = shippingService.getSiById(wn.getInstId());
                return new GenTemplate(request).generateQualityReportContent(tpl, qr, wn.getRefNumber(), wn.getGradeMaster().getName(), si.getCompanyMasterByClientId().getName());
            case Constants.IP:
                wn = weightNoteService.getWnByQrId(qr.getId());
                pi = processingService.getPiById(wn.getInstId());
                return new GenTemplate(request).generateQualityReportContent(tpl, qr, wn.getRefNumber(), wn.getGradeMaster().getName(), pi.getCompanyMasterByClientId().getName());
            case Constants.WR:
                wr = warehouseReceiptService.getWrByQrId(qr.getId());
                di = deliveryInsService.getDiById(wr.getInstId());
                return new GenTemplate(request).generateQualityReportContent(tpl, qr, wr.getRefNumber(), di.getGradeMaster().getName(), di.getCompanyMasterBySupplierId().getName());
            default:
                wr = warehouseReceiptService.getWrByQrId(qr.getId());
                si = shippingService.getSiById(wr.getInstId());
                return new GenTemplate(request).generateQualityReportContent(tpl, qr, wr.getRefNumber(), si.getGradeMaster().getName(), si.getCompanyMasterByClientId().getName());
        }

    }

    @RequestMapping(value = "get_qr_content_1.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_qr_content_1(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int qr_id = supporter.getIntValue("qr_id");
        String type = supporter.getStringRequest("type");
        JSONObject result = qualityReportService.searchQualityReport_1(type, qr_id);
        response.getWriter().print("[" + result.toString() + "]");
    }

    @RequestMapping(value = "get_cuptest.htm", method = RequestMethod.POST)
    public @ResponseBody
    String get_cuptest(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter supporter = new ServletSupporter(request);
        int qr_id = supporter.getIntValue("qualityrId");
        ArrayList<CupTest> cuptestes = (cupTestService.getCupTestByQrId(qr_id) != null) ? cupTestService.getCupTestByQrId(qr_id) : new ArrayList<CupTest>();

        Template tpl = new Template(new File(context.getRealPath("/") + "templates/cuptest_row.html"));
        return new GenTemplate(request).generateCupTestRows(tpl, cuptestes);
    }

    @RequestMapping("quality_list_source.htm")
    public @ResponseBody
    void quality_list_source(HttpServletResponse response) throws Exception {
        DecimalFormat decim = new DecimalFormat("0.0000");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String[] aColumns = {"0", "qr_ref", "wn_ref", "supplier_name", "grade_name", "qr_date", "black", "brown", "FM", "broken", "moist", "ocrop", "moldy", "asc20", "sc20", "sc19", "sc18", "sc17", "sc16", "sc15", "sc14", "sc13", "sc12", "bsc12"};
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String dir = "desc";
        int amount = 10;
        int start = 0;
        int echo = 0;
        int col = 0;
        int grade_id = 0;
        int sup_id = 0;
        int buyer_id = 0;
        String type = "";
        String from = "";
        String to = "";
        Byte stt = 0;
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sEcho = request.getParameter("sEcho");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");
        String sGrade = request.getParameter("grade");
        String sSup = request.getParameter("sup");
        String sBuyer = request.getParameter("buyer");
        String sType = request.getParameter("type");
        String sStt = request.getParameter("stt");
        String sFrom = request.getParameter("from_date");
        String sTo = request.getParameter("to_date");
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
        if (sBuyer != null) {
            buyer_id = Integer.parseInt(sBuyer);
        }
        if (sType != null) {
            type = sType;
        }

        if (sFrom != null) {
            from = sFrom;
        }

        if (sTo != null) {
            to = sTo;
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

        ArrayList<QualityReportObj> searchGlobe = qualityReportService.searchQualityReport(searchTerm, sdir, start, amount, colName, grade_id, sup_id, buyer_id, type, stt, from, to);
        int count = 1;

        if (searchGlobe != null && !searchGlobe.isEmpty()) {
            for (QualityReportObj qr : searchGlobe) {
                int i = 0;
                JSONObject ja = new JSONObject();
                ja.put("DT_RowClass", "data_row masterTooltip");
                ja.put("DT_RowId", "row_" + qr.getQr_id());
                ja.put((i++) + "", count);
                ja.put((i++) + "", qr.getQr_ref());
                ja.put((i++) + "", qr.getWn_ref());
                ja.put((i++) + "", qr.getSuppplier_name());
                ja.put((i++) + "", qr.getGrade_name());
                ja.put((i++) + "", Common.getDateFromDatabase(qr.getQr_date(), Common.date_format_a));
                ja.put((i++) + "", qr.getBlack());
                ja.put((i++) + "", qr.getBrown());
                ja.put((i++) + "", qr.getFm());
                ja.put((i++) + "", qr.getBroken());
                ja.put((i++) + "", qr.getMoisture());
                ja.put((i++) + "", qr.getOcrop());
                ja.put((i++) + "", qr.getMoldy());
                ja.put((i++) + "", qr.getAsc20());
                ja.put((i++) + "", qr.getSc20());
                ja.put((i++) + "", qr.getSc19());
                ja.put((i++) + "", qr.getSc18());
                ja.put((i++) + "", qr.getSc17());
                ja.put((i++) + "", qr.getSc16());
                ja.put((i++) + "", qr.getSc15());
                ja.put((i++) + "", qr.getSc14());
                ja.put((i++) + "", qr.getSc13());
                ja.put((i++) + "", qr.getSc12());
                ja.put((i++) + "", qr.getBsc12());
                array.put(ja);
                count++;
            }
        }

        totalAfterFilter = qualityReportService.getTotalAfterFilter();
        result.put("iTotalRecords", total);
        result.put("iTotalDisplayRecords", totalAfterFilter);
        result.put("aaData", array);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        response.getWriter().print(result);
    }

    @RequestMapping(value = "exportQualityList.htm", method = RequestMethod.POST)
    public @ResponseBody
    void exportQualityList(HttpServletResponse response) throws Exception {
        ServletSupporter support = new ServletSupporter(request);
        int grade_id = 0;
        int sup_id = 0;
        int buyer_id = 0;
        String type = "";
        String from = "";
        String to = "";
        Byte stt = 0;

        String sGrade = request.getParameter("grade");
        String sSup = request.getParameter("sup");
        String sBuyer = request.getParameter("buyer");
        String sType = request.getParameter("type");
        String sStt = request.getParameter("stt");
        String sFrom = request.getParameter("from_date");
        String sTo = request.getParameter("to_date");

        if (sGrade != null) {
            grade_id = Integer.parseInt(sGrade);
        }
        if (sSup != null) {
            sup_id = Integer.parseInt(sSup);
        }
        if (sBuyer != null) {
            buyer_id = Integer.parseInt(sBuyer);
        }
        if (sType != null) {
            type = sType;
        }

        if (sFrom != null) {
            from = sFrom;
        }

        if (sTo != null) {
            to = sTo;
        }

        if (sStt != null) {
            stt = Byte.parseByte(sStt);
        }

        ArrayList<QualityReportObj> qrs = qualityReportService.searchQualityReport("", "", 0, 0, "0", grade_id, sup_id, buyer_id, type, stt, from, to);

        String webPath = context.getContextPath() + "/";
        QualityReportExcel qre;
        qre = new QualityReportExcel(context.getRealPath(""), qrs);
        response.getWriter().print(qre.generateQualityReportList(webPath));
    }

    @RequestMapping(value = "qr_get_grade_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    String qr_get_grade_filter(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("type");
        ArrayList<Map> grades = qualityReportService.getAllGrades(type);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        return new GenTemplate(request).generateFilterListCommon(tpl, grades, -1, "grade_");
    }

    @RequestMapping(value = "qr_get_sup_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    void qr_get_sup_filter(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("type");
        ArrayList<Map> grades = qualityReportService.getCompany(type);
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        response.getWriter().print(new GenTemplate(request).generateFilterListCommon(tpl, grades, -1, "comp_"));
    }

    @RequestMapping(value = "qr_get_buyer_filter.htm", method = RequestMethod.POST)
    public @ResponseBody
    void qr_get_buyer_filter(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String type = request.getParameter("type");
        ArrayList<Map> grades = qualityReportService.getCompany("buyer");
        Template tpl = new Template(new File(context.getRealPath("/") + "templates/common.html"));
        response.getWriter().print(new GenTemplate(request).generateFilterListCommon(tpl, grades, -1, "buyer_"));
    }

    

    @RequestMapping(value = "citydaotest.json")
    public @ResponseBody
    void getCityDao(HttpServletResponse response) throws Exception {
        long count = cityRepository.count();
        response.getWriter().print(count);
    }

}
