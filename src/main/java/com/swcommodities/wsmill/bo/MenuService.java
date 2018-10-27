/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.MenuDao;
import com.swcommodities.wsmill.hibernate.dto.Menu;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author kiendn
 */
@Transactional(propagation = Propagation.REQUIRED)
public class MenuService {
    
    private MenuDao menuDao;
    private AuthorizationService authorizationService;

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    @Transactional(readOnly = true)
    public Menu getMenuById(Integer id){
        return menuDao.getMenuById(id);
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Menu> getMenuLv1(){
        return menuDao.getMenuLv1();
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Menu> getSiblingMenu(Menu menu, User user){
        ArrayList<Menu> result = menuDao.getSiblingMenu(menu);
        ArrayList<Menu> menus = new ArrayList<>();
        for (Menu res : result) {
            if (authorizationService.getPagePermission(user, res.getPage()) != (byte) 0){
                menus.add(res);
            }
        }
        return menus;
    }
    
    public ArrayList<Menu> getSiblingMenu(Menu menu){
        return menuDao.getSiblingMenu(menu);
    }
    
    public ArrayList<Menu> getSiblingMenuLv1(Menu menu, int user_id){
        ArrayList<Menu> result = menuDao.getSiblingMenu(menu);
        ArrayList<Menu> menus = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            if (menuDao.countPermission(result.get(i).getId(), user_id)){
                menus.add(result.get(i));
            }
        }
        return menus;
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Menu> getDefaultPage() {
        return menuDao.getDefaultPage();
    }
    
    @Transactional(readOnly = true)
    public Page getRedirectedPageFromMenu(Menu menu, User user) {
        if (menu.getPage() != null) {
            return menu.getPage();
        } else {
            ArrayList<Menu> redirectedList = getDefaultPage();
            for (Menu m : redirectedList) {
                if (m.getMenu().getName().equals(menu.getName())) {
                    if (authorizationService.getPagePermission(user, m.getPage()) != (byte) 0){
                        return m.getPage();
                    }else{
                        for (Menu siblingMenu : getSiblingMenu(m)) {
                            if (authorizationService.getPagePermission(user, siblingMenu.getPage()) != (byte)0){
                                return siblingMenu.getPage();
                            }
                        }
                    }
                } else {
                    if (m.getMenu().getMenu() != null && m.getMenu().getMenu().getName().equals(menu.getName())) {
                        if (authorizationService.getPagePermission(user, m.getPage()) != (byte) 0) {
                            return m.getPage();
                        } else {
                            for (Menu siblingMenu : getSiblingMenu(m)) {
                                if (authorizationService.getPagePermission(user, siblingMenu.getPage()) != (byte) 0) {
                                    return siblingMenu.getPage();
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
}
