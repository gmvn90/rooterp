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
public class ShippingInstructionObj {
    private int si_id;
    private String ref_number;
    private String supp_name;
    private String origin;
    private String quality;
    private String grade_name;
    private String packing_name;
    private Float tons;
    private Float delivered;
    private Float pendding;
    private Date from_date;
    private Date to_date;
    private Date date;
    private String client_name;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    /**
     * @return the si_id
     */
    public int getSi_id() {
        return si_id;
    }

    /**
     * @param si_id the si_id to set
     */
    public void setSi_id(int si_id) {
        this.si_id = si_id;
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

    /**
     * @return the supp_name
     */
    public String getSupp_name() {
        return supp_name;
    }

    /**
     * @param supp_name the supp_name to set
     */
    public void setSupp_name(String supp_name) {
        this.supp_name = supp_name;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the quality
     */
    public String getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(String quality) {
        this.quality = quality;
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

    /**
     * @return the tons
     */
    public Float getTons() {
        return tons;
    }

    /**
     * @param tons the tons to set
     */
    public void setTons(Float tons) {
        this.tons = tons;
    }

    /**
     * @return the delivered
     */
    public Float getDelivered() {
        return delivered;
    }

    /**
     * @param delivered the delivered to set
     */
    public void setDelivered(Float delivered) {
        this.delivered = delivered;
    }

    /**
     * @return the pendding
     */
    public Float getPendding() {
        return pendding;
    }

    /**
     * @param pendding the pendding to set
     */
    public void setPendding(Float pendding) {
        this.pendding = pendding;
    }

    /**
     * @return the from_date
     */
    public Date getFrom_date() {
        return from_date;
    }

    /**
     * @param from_date the from_date to set
     */
    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    /**
     * @return the to_date
     */
    public Date getTo_date() {
        return to_date;
    }

    /**
     * @param to_date the to_date to set
     */
    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }
    
}
