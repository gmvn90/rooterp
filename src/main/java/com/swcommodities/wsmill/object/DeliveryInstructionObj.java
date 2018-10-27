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
public class DeliveryInstructionObj {

    private int di_id;
    private String ref_number;
    private String buyer_name;
    private String supp_name;
    private String origin;
    private String quality;
    private String grade_name;
    private String packing_name;
    private Float tons;
    private Byte isDelivery;
    private Date delivery_date;
    private String from_time;
    private String to_time;
    private Float net_weight;
    private Float pendding;
    private String ct_ref;

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    /**
     * @return the di_id
     */
    public int getDi_id() {
        return di_id;
    }

    /**
     * @param di_id the di_id to set
     */
    public void setDi_id(int di_id) {
        this.di_id = di_id;
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
     * @return the buyer_name
     */
    public String getBuyer_name() {
        return buyer_name;
    }

    /**
     * @param buyer_name the buyer_name to set
     */
    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
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
     * @return the isDelivery
     */
    public Byte getIsDelivery() {
        return isDelivery;
    }

    /**
     * @param isDelivery the isDelivery to set
     */
    public void setIsDelivery(Byte isDelivery) {
        this.isDelivery = isDelivery;
    }

    /**
     * @return the delivery_date
     */
    public Date getDelivery_date() {
        return delivery_date;
    }

    /**
     * @param delivery_date the delivery_date to set
     */
    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    /**
     * @return the from_time
     */
    public String getFrom_time() {
        return from_time;
    }

    /**
     * @param from_time the from_time to set
     */
    public void setFrom_time(String from_time) {
        this.from_time = from_time;
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
     * @return the net_weight
     */
    public Float getNet_weight() {
        return net_weight;
    }

    /**
     * @param net_weight the net_weight to set
     */
    public void setNet_weight(Float net_weight) {
        this.net_weight = net_weight;
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

    public String getCt_ref() {
        return ct_ref;
    }

    public void setCt_ref(String ct_ref) {
        this.ct_ref = ct_ref;
    }
    
    
}
