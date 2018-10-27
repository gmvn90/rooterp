package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.GradeMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class GradeMasterForm {
    public GradeMasterForm() {

    }

    private GradeMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public GradeMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public GradeMaster getItem() {
        return item;
    }

    public GradeMasterForm setItem(GradeMaster item) {
        this.item = item;
        return this;
    }
}