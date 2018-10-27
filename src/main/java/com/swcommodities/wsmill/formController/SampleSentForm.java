package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.SampleSent;

/**
 * Created by dunguyen on 10/5/16.
 */
public class SampleSentForm {
    public SampleSentForm() {

    }

    private SampleSent item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public SampleSentForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public SampleSent getItem() {
        return item;
    }

    public SampleSentForm setItem(SampleSent item) {
        this.item = item;
        return this;
    }
}