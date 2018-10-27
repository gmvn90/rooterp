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

import com.swcommodities.wsmill.object.PiReportAllocatedWnr;
import com.swcommodities.wsmill.object.PiReportExprocess;
import com.swcommodities.wsmill.utils.Common;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author duhc
 */
public class ProcessingReport extends ExcelFile {

    //private PiReportAllocated allocate;
    //private PiReportInprocess inprocess;
    private ArrayList<PiReportAllocatedWnr> list_allocate;
    private PiReportExprocess exprocess;
    private Map info;
    public ProcessingReport(String work_path) {
        super("processing_report.xls", 0, work_path);
    }

    public ProcessingReport(String work_path, ArrayList<PiReportAllocatedWnr> list_allocate, PiReportExprocess exprocess, Map info) {
        super("processing_report.xls", 0, work_path);
        this.list_allocate = list_allocate;
        this.exprocess = exprocess;
        this.info = info;
    }

    public String generateProcessingReport(String webPath) {
        Map beans = new HashMap();
        beans.put("allocate", list_allocate);
        beans.put("exprocess", exprocess);
        beans.put("info", info);
        String filename = "processing_report" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
        String destFileName = this.getAsbPath(filename) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "processing_report") + ".xls", beans, destFileName);
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
