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
public class PiReportExprocess {
    private String pi_ref;
    private Float weight_loss;
    private ArrayList<StockReport> listgrades;

    public PiReportExprocess() {
    }

    public PiReportExprocess(String pi_ref, Float weight_loss, ArrayList<StockReport> listgrades) {
        this.pi_ref = pi_ref;
        this.weight_loss = weight_loss;
        this.listgrades = listgrades;
    }

    /**
     * @return the pi_ref
     */
    public String getPi_ref() {
        return pi_ref;
    }

    /**
     * @param pi_ref the pi_ref to set
     */
    public void setPi_ref(String pi_ref) {
        this.pi_ref = pi_ref;
    }

    /**
     * @return the weight_loss
     */
    public Float getWeight_loss() {
        return weight_loss;
    }

    /**
     * @param weight_loss the weight_loss to set
     */
    public void setWeight_loss(Float weight_loss) {
        this.weight_loss = weight_loss;
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
