package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.Invoice;

/**
 * Created by dunguyen on 10/5/16.
 */
public class InvoiceForm {
    public InvoiceForm() {

    }

    private Invoice item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public InvoiceForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public Invoice getItem() {
        return item;
    }

    public InvoiceForm setItem(Invoice item) {
        this.item = item;
        return this;
    }
}