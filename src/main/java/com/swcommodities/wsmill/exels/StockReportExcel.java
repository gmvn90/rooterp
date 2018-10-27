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
import org.json.JSONException;

import com.swcommodities.wsmill.object.StockReportElement;
import com.swcommodities.wsmill.object.StockReportInprocess;
import com.swcommodities.wsmill.utils.Common;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author duhc
 */
public class StockReportExcel extends ExcelFile{
    private StockReportElement stockReportsImport;
    private ArrayList<StockReportInprocess> stockReportsInprocess;
    private Map stockreportJson;

    public StockReportExcel(String work_path) {
        super("stock_report.xls", 0, work_path);
    }

    public StockReportExcel(String work_path, StockReportElement stockReportsImport, ArrayList<StockReportInprocess> stockReportsInprocess) {
        super("stock_report.xls", 0, work_path);
        this.stockReportsImport = stockReportsImport;
        this.stockReportsInprocess = stockReportsInprocess;
    }
    
    public StockReportExcel(String work_path, Map stockreportJson) {
        super("stock_report_1.xls", 0, work_path);
        this.stockreportJson = stockreportJson;
    }
    
    public String generateStockReport(String webPath) {
        Map beans = new HashMap();
            beans.put("import", stockReportsImport);
            beans.put("inprocess", stockReportsInprocess);
            String filename = "stock_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
            String destFileName = this.getAsbPath(filename) + ".xls";
            XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "stock_report") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + filename + ".xls";
    }
    
    public String generateStockReport_1(String webPath) throws JSONException{
        Map beans = new HashMap();
            beans.put("storage", stockreportJson.get("storage"));
            beans.put("pis", stockreportJson.get("pis"));
            String filename = "stock_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
            String destFileName = this.getAsbPath(filename) + ".xls";
            XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "stock_report_1") + ".xls", beans, destFileName);
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
