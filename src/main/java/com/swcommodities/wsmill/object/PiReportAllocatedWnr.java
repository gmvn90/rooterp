/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

/**
 *
 * @author duhc
 */
public class PiReportAllocatedWnr extends StockTotal{

    private int wnr_id;
    private String wnr_ref;
    private Float allocated_tons;
    private String inprocess_wnr_ref;
    private Float new_tons;
    private Float balance;
    private String new_grade;
    private String packing;
    private String wn_ref;
    private String cup;

    public PiReportAllocatedWnr() {
    }

    public PiReportAllocatedWnr(int wnr_id, String wnr_ref, Float allocated_tons, String inprocess_wnr_ref, Float new_tons, Float balance, String new_grade, String packing, String wn_ref) {
        this.wnr_id = wnr_id;
        this.wnr_ref = wnr_ref;
        this.allocated_tons = allocated_tons;
        this.inprocess_wnr_ref = inprocess_wnr_ref;
        this.new_tons = new_tons;
        this.balance = balance;
        this.new_grade = new_grade;
        this.packing = packing;
        this.wn_ref = wn_ref;
    }

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public String getWn_ref() {
        return wn_ref;
    }

    public void setWn_ref(String wn_ref) {
        this.wn_ref = wn_ref;
    }

    /**
     * @return the wnr_id
     */
    public int getWnr_id() {
        return wnr_id;
    }

    /**
     * @param wnr_id the wnr_id to set
     */
    public void setWnr_id(int wnr_id) {
        this.wnr_id = wnr_id;
    }

    /**
     * @return the wnr_ref
     */
    public String getWnr_ref() {
        return wnr_ref;
    }

    /**
     * @param wnr_ref the wnr_ref to set
     */
    public void setWnr_ref(String wnr_ref) {
        this.wnr_ref = wnr_ref;
    }

    /**
     * @return the allocated_tons
     */
    public Float getAllocated_tons() {
        return allocated_tons;
    }

    /**
     * @param allocated_tons the allocated_tons to set
     */
    public void setAllocated_tons(Float allocated_tons) {
        this.allocated_tons = allocated_tons;
    }

    /**
     * @return the new_tons
     */
    public Float getNew_tons() {
        return new_tons;
    }

    /**
     * @param new_tons the new_tons to set
     */
    public void setNew_tons(Float new_tons) {
        this.new_tons = new_tons;
    }

    /**
     * @return the balance
     */
    public Float getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Float balance) {
        this.balance = balance;
    }

    /**
     * @return the new_grade
     */
    public String getNew_grade() {
        return new_grade;
    }

    /**
     * @param new_grade the new_grade to set
     */
    public void setNew_grade(String new_grade) {
        this.new_grade = new_grade;
    }

    /**
     * @return the packing
     */
    public String getPacking() {
        return packing;
    }

    /**
     * @param packing the packing to set
     */
    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getInprocess_wnr_ref() {
        return inprocess_wnr_ref;
    }

    public void setInprocess_wnr_ref(String inprocess_wnr_ref) {
        this.inprocess_wnr_ref = inprocess_wnr_ref;
    }
}
