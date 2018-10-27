/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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

import com.swcommodities.wsmill.object.QualityReportObj;
import com.swcommodities.wsmill.utils.Common;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author duhc
 */
public class QualityReportExcel extends ExcelFile{
    private ArrayList<QualityReportObj> qrs;

    public QualityReportExcel(String work_path) {
        super("quality_list.xls", 0, work_path);
    }
    
    public QualityReportExcel(String work_path, ArrayList<QualityReportObj> qrs) {
        super("quality_list.xls", 0, work_path);
        this.qrs = qrs;
    }
    
    public String generateQualityReportList(String webPath) {
        Map beans = new HashMap();
        beans.put("qrs", qrs);
        String filename = "quality_list" + Common.getDateFromDatabase(new Date(), Common.date_format_a);
        String destFileName = this.getAsbPath(filename) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "quality_list") + ".xls", beans, destFileName);
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
