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
public class PiReportAllocatedWn extends StockTotal{
    private int wn_id;
    private String ref_number;
    private String client;
    private String supplier;
    private String grade;
    private String cup;
    private ArrayList<PiReportAllocatedWnr> wnrs;

    public PiReportAllocatedWn() {
    }

    public PiReportAllocatedWn(int wn_id, String ref_number, String client, String supplier, String grade, String cup, ArrayList<PiReportAllocatedWnr> wnrs) {
        super();
        this.wn_id = wn_id;
        this.ref_number = ref_number;
        this.client = client;
        this.supplier = supplier;
        this.grade = grade;
        this.cup = cup;
        this.wnrs = wnrs;
    }
    
    /**
     * @return the wn_id
     */
    public int getWn_id() {
        return wn_id;
    }

    /**
     * @param wn_id the wn_id to set
     */
    public void setWn_id(int wn_id) {
        this.wn_id = wn_id;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the supplier
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    /**
     * @return the cup
     */
    public String getCup() {
        return cup;
    }

    /**
     * @param cup the cup to set
     */
    public void setCup(String cup) {
        this.cup = cup;
    }

    /**
     * @return the wnrs
     */
    public ArrayList<PiReportAllocatedWnr> getWnrs() {
        return wnrs;
    }

    /**
     * @param wnrs the wnrs to set
     */
    public void setWnrs(ArrayList<PiReportAllocatedWnr> wnrs) {
        this.wnrs = wnrs;
    }

    /**
     * @return the ref_number
     */
    public String getRef_number() {
        return ref_number;
    }

    /**
     * @param ref_number the ref_number to set
     */
    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
    }
    
    
}
