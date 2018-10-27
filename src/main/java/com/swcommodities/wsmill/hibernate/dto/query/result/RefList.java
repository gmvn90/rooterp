package com.swcommodities.wsmill.hibernate.dto.query.result;

/**
 * Created by gmvn on 11/3/16.
 */
public class RefList {
    Integer id;
    String value;

    public RefList() {
    }

    public RefList(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public RefList setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public RefList setValue(String value) {
        this.value = value;
        return this;
    }
}
