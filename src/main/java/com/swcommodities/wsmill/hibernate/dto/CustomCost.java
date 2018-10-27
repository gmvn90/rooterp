/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author macOS
 */
@Entity
@Table(name = "custom_costs")
public class CustomCost implements Costable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String optionUnit;
    private String optionName;
    private String description;
    private double value;

    @Override
    public String getOptionUnit() {
        return optionUnit;
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public double getCostValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
