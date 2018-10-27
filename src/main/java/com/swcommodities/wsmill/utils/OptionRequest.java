package com.swcommodities.wsmill.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dunguyen on 8/24/16.
 */
public class OptionRequest extends Request {

    public OptionRequest(String baseUrl) {
        super(baseUrl);
    }

    public String updateOption(int id, String name, double value , String username) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("name", "");
        params.put("value", String.valueOf(value));
        params.put("username", username);
        return putContent("/option/" + id, params);
    }
}
