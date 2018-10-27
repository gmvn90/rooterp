package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.PortMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class PortMasterForm {
    public PortMasterForm() {

    }

    private PortMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public PortMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public PortMaster getItem() {
        return item;
    }

    public PortMasterForm setItem(PortMaster item) {
        this.item = item;
        return this;
    }
}