package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class CompanyMasterForm {
    public CompanyMasterForm() {

    }

    private CompanyMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public CompanyMasterForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public CompanyMaster getItem() {
        return item;
    }

    public CompanyMasterForm setItem(CompanyMaster item) {
        this.item = item;
        return this;
    }
}