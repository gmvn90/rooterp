package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.Transaction;

/**
 * Created by dunguyen on 10/5/16.
 */
public class TransactionForm {
    public TransactionForm() {

    }

    private Transaction item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public TransactionForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public Transaction getItem() {
        return item;
    }

    public TransactionForm setItem(Transaction item) {
        this.item = item;
        return this;
    }
}