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
public class StockReportElement extends StockTotal{
    private ArrayList<StockReport> listgrades;

    public StockReportElement() {
    }

    public StockReportElement(ArrayList<StockReport> listgrades) {
        super();
        this.listgrades = listgrades;
    }
    
    /**
     * @return the listgrades
     */
    public ArrayList<StockReport> getListgrades() {
        return listgrades;
    }

    /**
     * @param listgrades the listgrades to set
     */
    public void setListgrades(ArrayList<StockReport> listgrades) {
        this.listgrades = listgrades;
    }
    
}
