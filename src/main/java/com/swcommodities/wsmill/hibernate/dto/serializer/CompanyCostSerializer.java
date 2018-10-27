package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.util.Map;

/**
 * Created by dunguyen on 9/28/16.
 */
public class CompanyCostSerializer {
    private int id;

    private String name;
    private Map<String, Double> costs;

    public int getId() {
        return id;
    }

    public CompanyCostSerializer setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyCostSerializer setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, Double> getCosts() {
        return costs;
    }

    public CompanyCostSerializer setCosts(Map<String, Double> costs) {
        this.costs = costs;
        return this;
    }
}
