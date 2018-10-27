/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.swcommodities.wsmill.hibernate.dao.CommonDao;
import com.swcommodities.wsmill.hibernate.dto.Page;

/**
 *
 * @author kiendn
 */
public class CommonService {

    private CommonDao commonDao;
    @Autowired
    private PageService pageService;
    @Autowired
    private MenuService menuService;

    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    public ArrayList<Page> getPagesNotInMenu() {
        ArrayList<Page> pages = pageService.getAllPagesExcludeSection();
        ArrayList<Page> pagesInMenus = pageService.getPagesInMenus();
        pages.removeAll(pagesInMenus);
        return pages;
    }

    public Object[] getGradeCompanyFromInst(int inst_id, String type, String client_buyer) {
        String table;
        switch (type) {
            case "IP":
            case "XP":
                table = "processing_instruction";
                break;
            case "EX":
                table = "shipping_instruction";
                break;
            case "IM":
            default:
                table = "delivery_instruction";
        }
        return commonDao.getGradeCompanyFromInst(inst_id, table, client_buyer);
    }

    public JSONObject getSelect(String table) throws Exception {
        if (table != null && !table.equals("")) {
            ArrayList<HashMap> list = commonDao.getSelect(table);
            JSONObject json = new JSONObject();
            json.put("select", new JSONArray(list));
            return json;
        }
        return null;
    }
    
    public boolean fieldIsEmpty(String table, String field, int id){
        return commonDao.fieldIsEmpty(table, field, id);
    }
    
    public Object getCoreInfo(int id, String table){
        return commonDao.getCoreInfo(id, table);
    }

    public ArrayList<HashMap> getEmailListForMultiSelection() { return commonDao.getEmailListForMultiSelection();}
}
