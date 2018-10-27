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
public class PiReportInprocess {
    private ArrayList<StockReportObj> wns;

    public PiReportInprocess() {
    }

    public PiReportInprocess(ArrayList<StockReportObj> wns) {
        this.wns = wns;
    }

    /**
     * @return the wns
     */
    public ArrayList<StockReportObj> getWns() {
        return wns;
    }

    /**
     * @param wns the wns to set
     */
    public void setWns(ArrayList<StockReportObj> wns) {
        this.wns = wns;
    }
    
}
