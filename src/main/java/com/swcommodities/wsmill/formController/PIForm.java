package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;

/**
 * Created by dunguyen on 10/5/16.
 */
public class PIForm {
    public PIForm() {

    }

    private ProcessingInstruction item;
    private Map<String, Object> properties = new HashMap<>();

    public ProcessingInstruction getItem() {
        return item;
    }

    public PIForm setItem(ProcessingInstruction item) {
        this.item = item;
        return this;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public PIForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }
}