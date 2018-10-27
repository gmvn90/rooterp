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
public class PiReportAllocated {
    private ArrayList<PiReportAllocatedWn> wns;

    public PiReportAllocated() {
    }

    public PiReportAllocated(ArrayList<PiReportAllocatedWn> wns) {
        this.wns = wns;
    }
    
    /**
     * @return the wns
     */
    public ArrayList<PiReportAllocatedWn> getWns() {
        return wns;
    }

    /**
     * @param wns the wns to set
     */
    public void setWns(ArrayList<PiReportAllocatedWn> wns) {
        this.wns = wns;
    }
    
    
}
