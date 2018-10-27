package com.swcommodities.wsmill.formController;

import com.swcommodities.wsmill.hibernate.dto.LuCafeHistory;

import java.util.HashMap;
import java.util.Map;

public class LuCafeHistoryForm {
    public LuCafeHistoryForm() {

    }

    private LuCafeHistory item;
    private Map<String, Object> properties = new HashMap<>();

    public Map<String, Object> getProperties() {
        return properties;
    }

    public LuCafeHistoryForm setProperties(Map<String, Object> properties) {
        this.properties = properties;
        return this;
    }

    public LuCafeHistory getItem() {
        return item;
    }

    public LuCafeHistoryForm setItem(LuCafeHistory item) {
        this.item = item;
        return this;
    }
}