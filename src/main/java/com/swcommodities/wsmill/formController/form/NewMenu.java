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
public class NewMenu {

    private Integer parent;
    private String name;
    private Integer id;
    private Byte order;
    private boolean showInMainMenu = false;
    private boolean isDefault = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getOrder() {
        return order;
    }

    public void setOrder(Byte order) {
        this.order = order;
    }
    
    public Integer getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getShowInMainMenu() {
        return showInMainMenu;
    }

    public void setShowInMainMenu(boolean showInMainMenu) {
        this.showInMainMenu = showInMainMenu;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public NewMenu(Integer parent, String name) {
        this.parent = parent;
        this.name = name;
        this.id = null;
    }

    public NewMenu() {
    }

}
