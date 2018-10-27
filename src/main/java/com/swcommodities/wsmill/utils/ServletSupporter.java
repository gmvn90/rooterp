/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kiendn
 */
public class ServletSupporter {

    private HttpServletRequest request;
    
    private static ServletSupporter supporter;

    public ServletSupporter(HttpServletRequest request) {
        this.request = request;
    }
    
    public static ServletSupporter getInstances(HttpServletRequest request){
    	if (supporter == null){
    		supporter = new ServletSupporter(request);
    	}
    	return supporter;
    }

    public String getStringRequest(String parameter) {
        return (request.getParameter(parameter) == null) ? "" : request.getParameter(parameter).trim();
    }

    public Float getFloatValue(String parameter) {
        String result = request.getParameter(parameter);
        if (result.trim() == null || result.trim().equals("")) {
            return new Float(0);
        }
        try {
            return Float.parseFloat(result);
        } catch (NumberFormatException e) {
            return (float) 0;
        }
    }

    public Double getDoubleValue(String parameter) {
        String result = request.getParameter(parameter);
        if (result.trim() == null || result.trim().equals("")) {
            return new Double(0);
        }
        try {
            return Double.parseDouble(result);
        } catch (NumberFormatException e) {
            return (double) 0;
        }
    }

    public int getIntValue(String parameter) {
        String result = request.getParameter(parameter);
        if (result == null || result.trim().equals("")) {
            return 0;
        }
        try {
            return Integer.parseInt(result.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public Long getLongValue(String parameter) {
        String result = request.getParameter(parameter);
        if (result == null || result.trim().equals("")) {
            return (long) 0;
        }
        try {
            return Long.parseLong(result.trim());
        } catch (NumberFormatException e) {
            return (long) 0;
        }
    }

    public char getCharValue(String parameter) {
        return (request.getParameter(parameter) != null) ? request.getParameter(parameter).trim().charAt(0) : 0;
    }

    public Date getDateValue(String parameter) throws ParseException {
        return Common.convertStringToDate((request.getParameter(parameter) != null)
                ? request.getParameter(parameter).trim()
                : "",
                Common.date_format_a);
    }
    
    public Date getDateValue(String parameter, String format) throws ParseException {
        return Common.convertStringToDate((request.getParameter(parameter) != null)
                ? request.getParameter(parameter).trim()
                : "",
                format);
    }

    public Boolean getBooleanValue(String parameter) {
        String result = request.getParameter(parameter);
        if (result != null) {
            if (result.equals("true") || result.equals("1")) {
                return true;
            }
        }
        return false;
    }

    public Byte getByteValue(String parameter) {
        String result = request.getParameter(parameter);
        if (result == null || result.trim().equals("")) {
            return 0;
        }
        return Byte.valueOf(result);
    }
    
    public String[] getStringValues(String parameter) {
        String[] res = request.getParameterValues(parameter);
        if(res == null) {
            return new String[0];
        }
        return res;
    }
}
