/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import com.swcommodities.wsmill.hibernate.dao.MenuDao;

/**
 *
 * @author kiendn
 */
public abstract class DaoService {
    protected MenuDao menuDao;

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
    
    
}
