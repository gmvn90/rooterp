/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

/**
 *
 * @author macOS
 */
public class CustomCostForm {
    
    private String id;
    private String optionName;
    private String optionUnit;
    private double costValue;
    private String action;
    private int siId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionUnit() {
        return optionUnit;
    }

    public void setOptionUnit(String optionUnit) {
        this.optionUnit = optionUnit;
    }

    public double getCostValue() {
        return costValue;
    }

    public void setCostValue(double costValue) {
        this.costValue = costValue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getSiId() {
        return siId;
    }

    public void setSiId(int siId) {
        this.siId = siId;
    }
    
}
