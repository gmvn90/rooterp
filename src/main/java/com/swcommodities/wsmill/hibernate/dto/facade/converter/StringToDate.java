/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.facade.converter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author macOS
 */
public class StringToDate {
    
    public Date fromString(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        try {
            return dateFormat.parse(date);
        } catch (Exception ex) {
            
        }
        return null;
    }
    
    public String toString(Date date) {
        if(date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        return dateFormat.format(date);
    }
    
}
