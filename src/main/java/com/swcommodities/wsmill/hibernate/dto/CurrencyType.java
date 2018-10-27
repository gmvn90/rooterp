/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author macOS
 */
public class CurrencyType {

    public static int VND = 1;
    public static int USD = 2;

    public static Map<String, Integer> getAll() {
        Map<String, Integer> result = new HashMap<>();
        result.put("VND", VND);
        result.put("USD", USD);
        return result;
    }

    public static Map<Integer, String> getAllReverse() {
        Map<Integer, String> result = new HashMap<>();
        result.put(VND, "VND");
        result.put(USD, "USD");
        return result;
    }
}
