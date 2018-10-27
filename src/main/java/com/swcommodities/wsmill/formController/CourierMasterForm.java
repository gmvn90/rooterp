package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class CourierMasterForm {
    public CourierMasterForm() {

    }

    private CourierMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public CourierMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public CourierMaster getItem() {
        return item;
    }

    public CourierMasterForm setItem(CourierMaster item) {
        this.item = item;
        return this;
    }
}