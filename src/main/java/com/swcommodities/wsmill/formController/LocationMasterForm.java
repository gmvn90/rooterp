package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.LocationMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class LocationMasterForm {
    public LocationMasterForm() {

    }

    private LocationMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public LocationMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public LocationMaster getItem() {
        return item;
    }

    public LocationMasterForm setItem(LocationMaster item) {
        this.item = item;
        return this;
    }
}