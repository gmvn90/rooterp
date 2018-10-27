package com.swcommodities.wsmill.formController;

import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;

/**
 * Created by dunguyen on 10/5/16.
 */
public class FinanceContractForm {
    public FinanceContractForm() {

    }

    private FinanceContract item = new FinanceContract();
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public FinanceContractForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public FinanceContract getItem() {
        return item;
    }

    public FinanceContractForm setItem(FinanceContract item) {
        this.item = item;
        return this;
    }
}