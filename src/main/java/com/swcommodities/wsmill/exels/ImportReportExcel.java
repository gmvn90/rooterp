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

import com.swcommodities.wsmill.object.DeliveryInstructionObj;
import com.swcommodities.wsmill.object.StockReportElement;
import com.swcommodities.wsmill.utils.Common;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author duhc
 */
public class ImportReportExcel extends ExcelFile{
    private StockReportElement stockReportsImport;
    private ArrayList<DeliveryInstructionObj> dis;
    private String client;
    private String from;
    private String to;

    public ImportReportExcel(String work_path) {
        super("import_report.xls", 0, work_path);
    }

    public ImportReportExcel(String work_path, ArrayList<DeliveryInstructionObj> dis, String client, String from, String to) {
        super("import_report.xls", 0, work_path);
        this.dis = dis;
        this.client = client;
        this.from = from;
        this.to = to;
    }
    
    //Export report in detail
    public ImportReportExcel(String work_path, StockReportElement stockReportsImport, String client, String from, String to) {
        super("import_reportd.xls", 0, work_path);
        this.stockReportsImport = stockReportsImport;
        this.client = client;
        this.from = from;
        this.to = to;
    }
    
    public String generateImportReportInDetail(String webPath) {
        Map beans = new HashMap();
            beans.put("import", stockReportsImport);
            beans.put("client", client);
            beans.put("from", from);
            beans.put("to", to);
            beans.put("isdetail", true);
            String filename = "import_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
            String destFileName = this.getAsbPath(filename) + ".xls";
            XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "import_report") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + filename + ".xls";
    }
    
    public String generateImportReport(String webPath) {
        Map beans = new HashMap();
            beans.put("elements", dis);
            beans.put("client", client);
            beans.put("from", from);
            beans.put("to", to);
            beans.put("isdetail", false);
            String filename = "import_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
            String destFileName = this.getAsbPath(filename) + ".xls";
            XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "import_report") + ".xls", beans, destFileName);
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
