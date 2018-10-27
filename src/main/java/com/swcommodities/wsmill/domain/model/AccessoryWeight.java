/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

/**
 *
 * @author macOS
 */
public class AccessoryWeight {
    private double dryBag;
    private double draft;
    private double carton;
    private double pallet;

    public double getDryBag() {
        return dryBag;
    }

    public void setDryBag(double dryBag) {
        this.dryBag = dryBag;
    }

    public double getDraft() {
        return draft;
    }

    public void setDraft(double draft) {
        this.draft = draft;
    }

    public double getCarton() {
        return carton;
    }

    public void setCarton(double carton) {
        this.carton = carton;
    }

    public double getPallet() {
        return pallet;
    }

    public void setPallet(double pallet) {
        this.pallet = pallet;
    }
    
    
}
