/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model.common;

/**
 *
 * @author macOS
 */
public class BaseIdAndNameImpl implements BaseIdAndName {
    
    
    private int id;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BaseIdAndNameImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BaseIdAndNameImpl() {
    }
    
    
    
}
