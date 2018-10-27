/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

import java.util.ArrayList;

/**
 *
 * @author duhc
 */
public class StockReport extends StockTotal{
    private ArrayList<StockReportObj> stockReportObjs;
    private String grade;

    public StockReport() {
    }

    public StockReport(ArrayList<StockReportObj> stockReportObjs, String grade) {
        super();
        this.stockReportObjs = stockReportObjs;
        this.grade = grade;
    }

    /**
     * @return the stockReportObjs
     */
    public ArrayList<StockReportObj> getStockReportObjs() {
        return stockReportObjs;
    }

    /**
     * @param stockReportObjs the stockReportObjs to set
     */
    public void setStockReportObjs(ArrayList<StockReportObj> stockReportObjs) {
        this.stockReportObjs = stockReportObjs;
    }

    /**
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    
}
