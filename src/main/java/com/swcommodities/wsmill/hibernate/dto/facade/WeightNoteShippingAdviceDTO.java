/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade;

/**
 *
 * @author macOS
 */

public class WeightNoteShippingAdviceDTO {
    private int id;
    private String sealNo;
    private String containerNo;
    private double grossWeight;
    private double tareWeight;
    private double netWeight;
    private int noOfBags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(double tareWeight) {
        this.tareWeight = tareWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public int getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(int noOfBags) {
        this.noOfBags = noOfBags;
    }

    

   
    
}
