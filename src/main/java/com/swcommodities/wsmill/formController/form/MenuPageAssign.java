/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.formController.form;

/**
 *
 * @author macOS
 */
public class MenuPageAssign {
    
    private int id;
    private int page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MenuPageAssign(int id, int page) {
        this.id = id;
        this.page = page;
    }

    public MenuPageAssign() {
    }
    
    
    
    
}
