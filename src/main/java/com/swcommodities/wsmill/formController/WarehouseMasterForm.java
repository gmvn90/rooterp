package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.WarehouseMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class WarehouseMasterForm {
    public WarehouseMasterForm() {

    }

    private WarehouseMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public WarehouseMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public WarehouseMaster getItem() {
        return item;
    }

    public WarehouseMasterForm setItem(WarehouseMaster item) {
        this.item = item;
        return this;
    }
}