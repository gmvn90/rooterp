/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author macOS
 */
public class Converter {
    public static Date getDateValue(String date) throws ParseException {
        return Common.convertStringToDate((date != null)
                ? date.trim()
                : "",
                Common.date_format_a);
    }
}
