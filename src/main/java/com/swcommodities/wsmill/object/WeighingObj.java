/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

import java.util.Date;

/**
 *
 * @author duhc
 */
public class WeighingObj {
    private int wn_id;
    private String wn_ref;
    private String qr_ref;
    private String inst_ref;
    private String grade_name;
    private Date wn_date;
    private String packing_name;

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
     * @return the wn_ref
     */
    public String getWn_ref() {
        return wn_ref;
    }

    /**
     * @param wn_ref the wn_ref to set
     */
    public void setWn_ref(String wn_ref) {
        this.wn_ref = wn_ref;
    }

    /**
     * @return the qr_ref
     */
    public String getQr_ref() {
        return qr_ref;
    }

    /**
     * @param qr_ref the qr_ref to set
     */
    public void setQr_ref(String qr_ref) {
        this.qr_ref = qr_ref;
    }

    /**
     * @return the inst_ref
     */
    public String getInst_ref() {
        return inst_ref;
    }

    /**
     * @param inst_ref the inst_ref to set
     */
    public void setInst_ref(String inst_ref) {
        this.inst_ref = inst_ref;
    }

    /**
     * @return the grade_name
     */
    public String getGrade_name() {
        return grade_name;
    }

    /**
     * @param grade_name the grade_name to set
     */
    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    /**
     * @return the wn_date
     */
    public Date getWn_date() {
        return wn_date;
    }

    /**
     * @param wn_date the wn_date to set
     */
    public void setWn_date(Date wn_date) {
        this.wn_date = wn_date;
    }

    /**
     * @return the packing_name
     */
    public String getPacking_name() {
        return packing_name;
    }

    /**
     * @param packing_name the packing_name to set
     */
    public void setPacking_name(String packing_name) {
        this.packing_name = packing_name;
    }

}
