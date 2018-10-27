/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.serializer;
import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.Cost;

/**
 * Created by dunguyen on 9/9/16.
 */
public class CostCalculatorSerializer {
    private Map<String, Cost> costMap = new HashMap<>();
    private double value;

    public Map<String, Cost> getCostMap() {
        return costMap;
    }

    public CostCalculatorSerializer setCostMap(Map<String, Cost> costMap) {
        this.costMap = costMap;
        return this;
    }

    public double getValue() {
        return value;
    }

    public CostCalculatorSerializer setValue(double value) {
        this.value = value;
        return this;
    }
}
