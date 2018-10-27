/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.swcommodities.wsmill.hibernate.dao.WarehouseMapDao;
import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;

/**
 *
 * @author duhc
 */
public class WarehouseMapService {
    private WarehouseMapDao warehouseMapDao;

    public void setWarehouseMapDao(WarehouseMapDao warehouseMapDao) {
        this.warehouseMapDao = warehouseMapDao;
    }
    
    public ArrayList<WarehouseMap> getWarehouseMapListByWarehouseId(int id) {
        return warehouseMapDao.getWarehouseMapListByWarehouseId(id);
    }
    
    public WarehouseMap getWarehouseMapById(int id) {
        return warehouseMapDao.getWarehouseMapById(id);
    }
    
    public int createNewMap(WarehouseMap wm) {
        return warehouseMapDao.createNewMap(wm);
    }
    
    public boolean checkDeletable(int map_id) {
        return warehouseMapDao.checkDeletable(map_id);
    } 
    
    public int updateWarehouseMap(WarehouseMap wm) {
        return warehouseMapDao.updateWarehouseMap(wm);
    }
    
    public ArrayList<HashMap> getWarehouseMaps(){
        return warehouseMapDao.getWarehouseMaps();
    }
    
    public ArrayList<Map> searchWnrsByAreaId(String searchTerm, String order, int start, int amount, String colName, int grade, int client, int pledge, int area) {
        return warehouseMapDao.searchWnrsByAreaId(searchTerm, order, start, amount, colName, grade, client, pledge, area);
    }
    
    public long countRow() {
        return warehouseMapDao.countRow();
    }
    
    public long getTotalAfterFilter() {
        return warehouseMapDao.getTotalAfterFilter();
    }
}
