package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.OriginMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class OriginMasterForm {
    public OriginMasterForm() {

    }

    private OriginMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public OriginMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public OriginMaster getItem() {
        return item;
    }

    public OriginMasterForm setItem(OriginMaster item) {
        this.item = item;
        return this;
    }
}