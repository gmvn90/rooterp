/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import com.swcommodities.wsmill.hibernate.dto.Menu;
import java.util.List;

/**
 *
 * @author macOS
 */
public class SimpleMenu {
    
    private int menu;
    private Integer parent;
    private boolean isDefault;

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    

    public SimpleMenu(int menu, Integer parent, boolean isDefault) {
        this.menu = menu;
        this.parent = parent;
        this.isDefault = isDefault;
    }
    
    public SimpleMenu(byte menu, Byte parent, boolean isDefault) {
        this((int) menu, Integer.valueOf(String.valueOf(parent)), isDefault);
    }
    
}
