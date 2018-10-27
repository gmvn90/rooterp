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

import com.swcommodities.wsmill.utils.Common;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author duhc
 */
public class ShippingInfoExcel extends ExcelFile{
    private Map info;
    private ArrayList<HashMap> exportReport;
    private ArrayList<HashMap> exportReportWn;

    public ShippingInfoExcel(String work_path) {
        super("shipping_info.xls", 0, work_path);
    }

    public ShippingInfoExcel(String work_path, Map info) {
        super("shipping_info.xls", 0, work_path);
        this.info = info;
    }
    
    public ShippingInfoExcel(String work_path, ArrayList<HashMap> exportReport, Map info) {
        super("shipping_report_detail.xls", 0, work_path);
        this.exportReport = exportReport;
        this.info = info;
    }
    
    public ShippingInfoExcel(String work_path, ArrayList<HashMap> exportReport, ArrayList<HashMap> exportReportWn, Map info) {
        super("shipping_report_detail.xls", 0, work_path);
        this.exportReport = exportReport;
        this.exportReportWn = exportReportWn;
        this.info = info;
    }
    
    public String generateShippingInfo(String webPath) {
        Map beans = new HashMap();
        beans.put("info", info);
        String filename = "SI_" + info.get("si_ref");
        String destFileName = this.getAsbPath(filename) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        
        try {
            transformer.transformXLS(getAsbPath("reports", "shipping_info") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + filename + ".xls";
    }
    
    public String generateShippingReport(String webPath) {
        Map beans = new HashMap();
        beans.put("wnlist", exportReport);
        beans.put("info", info);
        String filename = Common.getDateFromDatabase(new Date(), Common.date_format_a) + "_" + info.get("si_ref");
        String destFileName = this.getAsbPath(filename) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        
        try {
            transformer.transformXLS(getAsbPath("reports", "shipping_report") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + filename + ".xls";
    }
    
    public String generateShippingReportDetail(String webPath) {
        Map beans = new HashMap();
        beans.put("wnrlist", exportReport);
        beans.put("wnlist", exportReportWn);
        beans.put("info", info);
        String filename = Common.getDateFromDatabase(new Date(), Common.date_format_a) + "_" + info.get("si_ref");
        String destFileName = this.getAsbPath(filename) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        
        try {
            transformer.transformXLS(getAsbPath("reports", "shipping_report_detail") + ".xls", beans, destFileName);
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
