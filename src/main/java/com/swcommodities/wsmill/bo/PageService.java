/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dao.PageDao;
import com.swcommodities.wsmill.hibernate.dto.Page;

/**
 *
 * @author kiendn
 */
@Transactional
public class PageService {
    private PageDao pageDao;

    public void setPageDao(PageDao pageDao) {
        this.pageDao = pageDao;
    }
    
    @Transactional(readOnly = true)
    public Page getPageByPageCode(int id) {
        return pageDao.getPageByPageCode(id);
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Page> getAllPagesExcludeSection(){
        return pageDao.getAllPagesExcludeSection();
    }
    
    @Transactional(readOnly = true)
    public ArrayList<Page> getPagesInMenus() {
        return pageDao.getPagesInMenus();
    }
     
    @Transactional(readOnly = true)
    public ArrayList<Page> getPagesNotInMenu() {
        ArrayList<Page> pages = pageDao.getAllPagesExcludeSection();
        ArrayList<Page> pagesInMenus = pageDao.getPagesInMenus();
        pages.removeAll(pagesInMenus);
        return pages;
    }
    
    public ArrayList<Page> getPages() {
        return pageDao.getPages();
    }
    
    public ArrayList<Page> getPageSections(Page page) {
        return pageDao.getPageSections(page);
    }
    
    public ArrayList<Page> getAllPages() {
        return pageDao.getAllPages();
    }
    
    public Page getPageCode(String url){
        return pageDao.getPageCode(url);
    }
}
