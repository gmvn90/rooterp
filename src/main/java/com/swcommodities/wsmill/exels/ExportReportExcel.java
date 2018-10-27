/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.exels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.swcommodities.wsmill.object.ShippingInstructionObj;
import com.swcommodities.wsmill.object.StockReportElement;
import com.swcommodities.wsmill.utils.Common;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author duhc
 */
public class ExportReportExcel extends ExcelFile{
    private StockReportElement stockReportsExport;
    private ArrayList<ShippingInstructionObj> sis;
    private String client;
    private String from;
    private String to;

    public ExportReportExcel(String work_path) {
        super("export_report.xls", 0, work_path);
    }

    public ExportReportExcel(String work_path, ArrayList<ShippingInstructionObj> sis, String client, String from, String to) {
        super("export_report.xls", 0, work_path);
        this.sis = sis;
        this.client = client;
        this.from = from;
        this.to = to;
    }
    
    public ExportReportExcel(String work_path, StockReportElement stockReportsExport, String client, String from, String to) {
        super("export_report.xls", 0, work_path);
        this.stockReportsExport = stockReportsExport;
        this.client = client;
        this.from = from;
        this.to = to;
    }
    
    public String generateExportReportInDetail(String webPath) {
        Map beans = new HashMap();
            beans.put("import", stockReportsExport);
            beans.put("client", client);
            beans.put("from", from);
            beans.put("to", to);
            beans.put("isdetail", true);
            String filename = "export_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
            String destFileName = this.getAsbPath(filename) + ".xls";
            XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "export_report") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + filename + ".xls";
    }
    
    public String generateExportReport(String webPath) {
        Map beans = new HashMap();
            beans.put("elements", sis);
            beans.put("client", client);
            beans.put("from", from);
            beans.put("to", to);
            beans.put("isdetail", false);
            String filename = "export_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
            String destFileName = this.getAsbPath(filename) + ".xls";
            XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "export_report") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + filename + ".xls";
    }
}
