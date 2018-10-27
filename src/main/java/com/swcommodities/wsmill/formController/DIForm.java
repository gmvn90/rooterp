package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;

/**
 * Created by dunguyen on 10/5/16.
 */
public class DIForm {
    public DIForm() {

    }

    private DeliveryInstruction item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public DIForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public DeliveryInstruction getItem() {
        return item;
    }

    public DIForm setItem(DeliveryInstruction item) {
        this.item = item;
        return this;
    }
}