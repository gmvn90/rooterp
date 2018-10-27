/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.WarehouseDao;
import com.swcommodities.wsmill.hibernate.dto.Warehouse;

/**
 *
 * @author duhc
 */
public class WarehouseService {
    private WarehouseDao warehouseDao;

    public void setWarehouseDao(WarehouseDao warehouseDao) {
        this.warehouseDao = warehouseDao;
    }
    
    public ArrayList<Warehouse> getAllWarehouseNames() {
        return warehouseDao.getAllWarehouseNames();
    }
    
    public Warehouse getWarehouseById(int id){
        return warehouseDao.getWarehouseById(id);
    }
}
