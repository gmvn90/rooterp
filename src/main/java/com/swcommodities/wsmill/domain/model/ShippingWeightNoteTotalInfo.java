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
public class ShippingWeightNoteTotalInfo {
    
    private double netWeight;
    private double tareWeight;
    private double grossWeight;
    private double emptyContainerWeight;
    private double vgmWeight;
    private double maxGrossContainerWeight;
    private double dryBagWeight;
    private double draftWeight;
    private double cartonWeight;
    private double palletWeight;

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(double tareWeight) {
        this.tareWeight = tareWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getVgmWeight() {
        return vgmWeight;
    }

    public void setVgmWeight(double vgmWeight) {
        this.vgmWeight = vgmWeight;
    }

    public double getMaxGrossContainerWeight() {
        return maxGrossContainerWeight;
    }

    public void setMaxGrossContainerWeight(double maxGrossContainerWeight) {
        this.maxGrossContainerWeight = maxGrossContainerWeight;
    }

    public double getEmptyContainerWeight() {
        return emptyContainerWeight;
    }

    public void setEmptyContainerWeight(double emptyContainerWeight) {
        this.emptyContainerWeight = emptyContainerWeight;
    }

    public double getDryBagWeight() {
        return dryBagWeight;
    }

    public void setDryBagWeight(double dryBagWeight) {
        this.dryBagWeight = dryBagWeight;
    }

    public double getDraftWeight() {
        return draftWeight;
    }

    public void setDraftWeight(double draftWeight) {
        this.draftWeight = draftWeight;
    }

    public double getCartonWeight() {
        return cartonWeight;
    }

    public void setCartonWeight(double cartonWeight) {
        this.cartonWeight = cartonWeight;
    }

    public double getPalletWeight() {
        return palletWeight;
    }

    public void setPalletWeight(double palletWeight) {
        this.palletWeight = palletWeight;
    }
    
    
    
}
