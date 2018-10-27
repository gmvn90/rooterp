/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swcommodities.wsmill.bo.DeliveryInsService;
import com.swcommodities.wsmill.bo.ProcessingService;
import com.swcommodities.wsmill.bo.ShippingService;
import com.swcommodities.wsmill.bo.WeightLossService;
import com.swcommodities.wsmill.bo.WeightNoteService;
import com.swcommodities.wsmill.exels.ExportReportExcel;
import com.swcommodities.wsmill.exels.ImportReportExcel;
import com.swcommodities.wsmill.exels.ProcessingReport;
import com.swcommodities.wsmill.exels.StockReportExcel;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.WeightLoss;
import com.swcommodities.wsmill.object.DeliveryInstructionObj;
import com.swcommodities.wsmill.object.PiReportAllocatedWnr;
import com.swcommodities.wsmill.object.PiReportExprocess;
import com.swcommodities.wsmill.object.ShippingInstructionObj;
import com.swcommodities.wsmill.object.StockReport;
import com.swcommodities.wsmill.object.StockReportElement;
import com.swcommodities.wsmill.object.StockReportInprocess;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;
import com.swcommodities.wsmill.utils.ServletSupporter;

/**
 *
 * @author duhc
 */
@Transactional(propagation = Propagation.REQUIRED)
@org.springframework.stereotype.Controller
public class ReportController {

    @Autowired
    private WeightLossService weightLossService;
    @Autowired
    private ProcessingService processingService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private DeliveryInsService deliveryInsService;
    @Autowired
    private WeightNoteService weightNoteService;
    @Autowired(required = true)
    private ServletContext context;
    @Autowired(required = true)
    private HttpServletRequest request;

    @RequestMapping(value = "get_sro.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_sro(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        String date = support.getStringRequest("date");

        int map_id = (support.getStringRequest("map") != null) ? support.getIntValue("map") : -1;
        int client_id = (support.getStringRequest("client") != null) ? support.getIntValue("client") : -1;

        if (!date.equals("")) {
            date = Common.getDateFromDatabase(Common.convertStringToDate(date, Common.date_format_ddMMyyyy_dash), Common.date_format_yyyyMMdd);
        } else {
            date = Common.getDateFromDatabase(new Date(), Common.date_format_yyyyMMdd);
        }

        String today = Common.getDateFromDatabase(new Date(), Common.date_format_yyyyMMdd);

        //ArrayList<GradeMaster> grades;
        ArrayList<HashMap> grades2; //due to mistake in stock report

        StockReportElement stockReportImport = new StockReportElement();
        ArrayList<StockReportInprocess> stockReportInprocess = new ArrayList<>();
        if (date.equals(today)) {
            //get Storage
            //grades = weightNoteService.getAllGradesByType("STORAGE");
            grades2 = weightNoteService.getGradeInStock2(-1, -1, -1);

            ArrayList<StockReport> listGrades = new ArrayList<>();
            for (HashMap gr : grades2) {
                StockReport sr = new StockReport(weightNoteService.getTodayReportObject(Integer.parseInt(gr.get("id").toString()), map_id, client_id), gr.get("name").toString());
                if (!sr.getStockReportObjs().isEmpty()) {
                    listGrades.add(sr);
                }
            }
            stockReportImport.setListgrades(listGrades);

            //get inprocess
            stockReportInprocess = weightNoteService.getTodayReportObject_Inprocess();

        } else {
            //get Storage
            //grades = weightNoteService.getAllGradesByType("STORAGE");
            grades2 = weightNoteService.getGradeInStock2(-1, -1, -1);
            ArrayList<StockReport> listGrades = new ArrayList<>();
            for (HashMap gr : grades2) {
                StockReport sr = new StockReport(weightNoteService.getReportObject(Integer.parseInt(gr.get("id").toString()), date, map_id, client_id), gr.get("name").toString());
                if (!sr.getStockReportObjs().isEmpty()) {
                    listGrades.add(sr);
                }
            }
            stockReportImport.setListgrades(listGrades);

            //get inprocess
            stockReportInprocess = weightNoteService.getReportObject_Inprocess(date);

        }

        String webPath = context.getContextPath() + "/";
        StockReportExcel sre = new StockReportExcel(context.getRealPath(""), stockReportImport, stockReportInprocess);
        response.getWriter().print(sre.generateStockReport(webPath));
    }

    @RequestMapping(value = "get_exim_report.htm", method = RequestMethod.POST)
    public @ResponseBody
    void get_exim_report(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        String type = support.getStringRequest("type");
        String from = support.getStringRequest("from");
        Boolean isDetail = support.getBooleanValue("isDetail");
        String from2 = "";
        String to = support.getStringRequest("to");
        String to2 = "";
        int client_id = (support.getStringRequest("client") != null) ? support.getIntValue("client") : -1;
        String client = support.getStringRequest("client_name");
        if (!from.equals("")) {
            from2 = from;
            from = Common.getDateFromDatabase(Common.convertStringToDate(from, Common.date_format_ddMMyyyy_dash), Common.date_format_yyyyMMdd);
        } else {
            from = "1971-01-01";
        }
        if (!to.equals("")) {
            to2 = to;
            to = Common.getDateFromDatabase(Common.convertStringToDate(to, Common.date_format_ddMMyyyy_dash), Common.date_format_yyyyMMdd);
        } else {
            to = Common.getDateFromDatabase(new Date(), Common.date_format_yyyyMMdd);
        }
        String webPath = context.getContextPath() + "/";
        if (type.equals("IM")) {
            if (isDetail) {
                ArrayList<HashMap> grades = weightNoteService.getGradeImportExportInStockOnSpecificDate(from, to, type);

                StockReportElement stockReportImport = new StockReportElement();
                ArrayList<StockReport> listGrades = new ArrayList<>();
                for (HashMap gr : grades) {
                    StockReport sr = new StockReport(weightNoteService.getImportReportObject(client_id, from, to, Integer.parseInt(gr.get("id").toString())), gr.get("name").toString());
                    if (!sr.getStockReportObjs().isEmpty()) {
                        listGrades.add(sr);
                    }
                }
                stockReportImport.setListgrades(listGrades);
                ImportReportExcel ire = new ImportReportExcel(context.getRealPath(""), stockReportImport, client, from2, to2);
                response.getWriter().print(ire.generateImportReportInDetail(webPath));
            } else {
                ArrayList<DeliveryInstructionObj> dis = deliveryInsService.getImportReportInfo(client_id, from, to);
                ImportReportExcel ire = new ImportReportExcel(context.getRealPath(""), dis, client, from2, to2);
                response.getWriter().print(ire.generateImportReport(webPath));
            }

        } else {

            if (isDetail) {
                ArrayList<HashMap> grades = weightNoteService.getGradeImportExportInStockOnSpecificDate(from, to, type);

                StockReportElement stockReportExport = new StockReportElement();
                ArrayList<StockReport> listGrades = new ArrayList<>();
                for (HashMap gr : grades) {
                    StockReport sr = new StockReport(weightNoteService.getExportReportObject(client_id, from, to, Integer.parseInt(gr.get("id").toString())), gr.get("name").toString());
                    if (!sr.getStockReportObjs().isEmpty()) {
                        listGrades.add(sr);
                    }
                }
                stockReportExport.setListgrades(listGrades);
                ExportReportExcel ire = new ExportReportExcel(context.getRealPath(""), stockReportExport, client, from2, to2);
                response.getWriter().print(ire.generateExportReportInDetail(webPath));
            } else {
                ArrayList<ShippingInstructionObj> sis = shippingService.getExportReportInfo(client_id, from, to);
                ExportReportExcel ere = new ExportReportExcel(context.getRealPath(""), sis, client, from2, to2);
                response.getWriter().print(ere.generateExportReport(webPath));
            }

        }

    }

    @RequestMapping(value = "processing_report.htm", method = RequestMethod.POST)
    public @ResponseBody
    void processing_report(HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        ServletSupporter support = new ServletSupporter(request);
        int po_id = support.getIntValue("po_id");

        //get allocated elements
        ArrayList<PiReportAllocatedWnr> wnrs = weightNoteService.getPiReportAllocatedWnr(po_id);

        //get exprocess elements
        PiReportExprocess exprocess = new PiReportExprocess();
        ArrayList<GradeMaster> grades = weightNoteService.getAllGradesByType("XP");
        ArrayList<StockReport> listGrades = new ArrayList<>();
        for (GradeMaster grade : grades) {
            StockReport sr = new StockReport(weightNoteService.getWnExprocessPiReport(po_id, grade.getId()), grade.getName());
            if (!sr.getStockReportObjs().isEmpty()) {
                listGrades.add(sr);
            }
        }
        exprocess.setListgrades(listGrades);
        /////////////////////////

        //get info elements (pi ref, weight loss)
        Map info = new HashMap();
        ProcessingInstruction pi = processingService.getPiById(po_id);
        WeightLoss wl;

        info.put("pi_ref", pi.getRefNumber());
        info.put("client", pi.getCompanyMasterByClientId().getName());
        info.put("client_ref", pi.getClientRef());
        info.put("allocated_total_detail", processingService.countTotalAllocatedPiReport(po_id));

        if (pi.getStatus() == Constants.COMPLETE) {
            info.put("weight_loss", pi.calculateInprocess() - pi.calculateExprocess());
        }

        String webPath = context.getContextPath() + "/";
        ProcessingReport pr = new ProcessingReport(context.getRealPath(""), wnrs, exprocess, info);
        response.getWriter().print(pr.generateProcessingReport(webPath));
    }
}
