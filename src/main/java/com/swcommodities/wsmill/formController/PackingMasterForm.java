package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.PackingMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class PackingMasterForm {
    public PackingMasterForm() {

    }

    private PackingMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public PackingMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public PackingMaster getItem() {
        return item;
    }

    public PackingMasterForm setItem(PackingMaster item) {
        this.item = item;
        return this;
    }
}