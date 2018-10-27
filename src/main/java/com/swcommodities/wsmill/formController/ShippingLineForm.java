package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.ShippingLineCompanyMaster;

/**
 * Created by dunguyen on 10/5/16.
 */
public class ShippingLineForm {
    public ShippingLineForm() {

    }

    private ShippingLineCompanyMaster item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public ShippingLineForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public ShippingLineCompanyMaster getItem() {
        return item;
    }

    public ShippingLineForm setItem(ShippingLineCompanyMaster item) {
        this.item = item;
        return this;
    }
}