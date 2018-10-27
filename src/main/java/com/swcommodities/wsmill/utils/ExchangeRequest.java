package com.swcommodities.wsmill.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dunguyen on 8/24/16.
 */
public class ExchangeRequest extends Request {

    public ExchangeRequest(String baseUrl) {
        super(baseUrl);
    }

    public String updateCurExchange(int id, String name, int value, String username) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("name", "");
        params.put("value", String.valueOf(value));
        params.put("username", username);
        return putContent("/exchange/" + id, params);
    }
}
