package com.swcommodities.wsmill.hibernate.specification;

import java.text.MessageFormat;

/**
 * Created by dunguyen on 10/18/16.
 */

public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private String salt = "";

    public String getKey() {
        return key;
    }

    public SearchCriteria setKey(String key) {
        this.key = key;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public SearchCriteria setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public SearchCriteria setValue(Object value) {
        this.value = value;
        return this;
    }

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
    public SearchCriteria(String key, String operation, Object value, String salt) {
        this(key, operation, value);
        this.salt = salt;
    }

    public String sanitizeKey() {
        return key.replace(".", "__") + salt;
    }

    public String getHibernateCompare(String tableReplacement) {
        return MessageFormat.format("{0}.{1} {2} :{3}", tableReplacement, key, operation, sanitizeKey());
    }
}