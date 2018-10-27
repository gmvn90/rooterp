/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.object;

import java.util.ArrayList;

/**
 *
 * @author duhc
 */
public class StockReportInprocess {
    int id;
    String ref_number;
    Float inprocess_tons;
    String client;
    ArrayList<ExprocessGrade> grades;

    public StockReportInprocess() {
        
    }

    public StockReportInprocess(int id, String ref_number, Float inprocess_tons,String client, ArrayList<ExprocessGrade> grades) {
        this.id = id;
        this.ref_number = ref_number;
        this.inprocess_tons = inprocess_tons;
        this.client = client;
        this.grades = grades;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef_number() {
        return ref_number;
    }

    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
    }

    public Float getInprocess_tons() {
        return inprocess_tons;
    }

    public void setInprocess_tons(Float inprocess_tons) {
        this.inprocess_tons = inprocess_tons;
    }

    public ArrayList<ExprocessGrade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<ExprocessGrade> grades) {
        this.grades = grades;
    }
    
    
    
    public static class ExprocessGrade {
        int gr_id;
        String gr_name;
        Float gr_tons;

        public ExprocessGrade() {
        }

        public ExprocessGrade(int gr_id, String gr_name, Float gr_tons) {
            this.gr_id = gr_id;
            this.gr_name = gr_name;
            this.gr_tons = gr_tons;
        }
        
        public int getGr_id() {
            return gr_id;
        }

        public void setGr_id(int gr_id) {
            this.gr_id = gr_id;
        }

        public String getGr_name() {
            return gr_name;
        }

        public void setGr_name(String gr_name) {
            this.gr_name = gr_name;
        }

        public Float getGr_tons() {
            return gr_tons;
        }

        public void setGr_tons(Float gr_tons) {
            this.gr_tons = gr_tons;
        }
    }
    
}
