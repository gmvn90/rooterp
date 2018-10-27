/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macOS
 */
public class PageMenu {
    
    private int id;
    private Integer parent;
    private int order;
    private String url;
    private boolean selected = false;
    private boolean isDefault;
    private String name;
    private boolean isHome = false;
    private List<PageMenu> children = new ArrayList<>();
    private boolean showInMainMenu = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PageMenu> getChildren() {
        return children;
    }

    public void setChildren(List<PageMenu> children) {
        this.children = children;
    }
    
    public void addChild(PageMenu menu) {
        this.children.add(menu);
    }

    public boolean getShowInMainMenu() {
        return showInMainMenu;
    }

    public void setShowInMainMenu(boolean showInMainMenu) {
        this.showInMainMenu = showInMainMenu;
    }
    
    public PageMenu(int id, Integer parent, Byte order, String url, Boolean isDefault, String name, boolean showInMainMenu) {
        this.id = id;
        this.parent = parent;
        if(order != null) {
            this.order = Integer.valueOf(String.valueOf(order));
        } else {
            this.order = 0;
        }
        this.url = url;
        this.isDefault = isDefault;
        this.name = name;
        if(id == 1) {
            this.isHome = true;
        }
        this.showInMainMenu = showInMainMenu;
    }
    
    //int, int, byte, java.lang.String, boolean, java.lang.String
    

    public boolean getIsHome() {
        return isHome;
    }

    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    @Override
    public String toString() {
        return "PageMenu{" + "id=" + id + ", parent=" + parent + ", order=" + order + ", url=" + url + ", selected=" + selected + ", isDefault=" + isDefault + ", name=" + name + ", isHome=" + isHome + ", children=" + children + ", showInMainMenu=" + showInMainMenu + '}';
    }
    
}
