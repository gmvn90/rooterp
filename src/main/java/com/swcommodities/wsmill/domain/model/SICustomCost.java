/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.swcommodities.wsmill.hibernate.dto.Costable;

/**
 *
 * @author trung
 */
@Embeddable
public class SICustomCost implements Costable {

    private String customCostId;
    private String optionUnit;
    private String optionName;
    private String description;
    private double costValue = 0;
    private double costInVND = 0;
    private double costPerMetricTon = 0;

    public SICustomCost() {
        this.customCostId = UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String getOptionUnit() {
        return optionUnit;
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOptionUnit(String optionUnit) {
        this.optionUnit = optionUnit;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    @Transient
    public String getId() {
        return customCostId;
    }

    // for hiberante
    public void setId(String id) {
        this.customCostId = id;
    }

    public String getCustomCostId() {
        return customCostId;
    }

    public void setCustomCostId(String customCostId) {
        this.customCostId = customCostId;
    }

    @Override
    public double getCostValue() {
        return costValue;
    }

    public void setCostValue(double costValue) {
        this.costValue = costValue;
    }

    @Transient
    public double getCostInVND() {
        return costInVND;
    }

    public void setCostInVND(double costInVND) {
        this.costInVND = costInVND;
    }

    @Transient
    public double getCostPerMetricTon() {
        return costPerMetricTon;
    }

    public void setCostPerMetricTon(double costPerMetricTon) {
        this.costPerMetricTon = costPerMetricTon;
    }

    public SICustomCost(String optionUnit, String optionName, String description, double costValue) {
        this.customCostId = UUID.randomUUID().toString().replace("-", "");
        this.optionUnit = optionUnit;
        this.optionName = optionName;
        this.description = description;
        this.costValue = costValue;
    }

    public SICustomCost(String optionUnit, String optionName, double costValue) {
        this(optionUnit, optionName, "", costValue);
    }

}
