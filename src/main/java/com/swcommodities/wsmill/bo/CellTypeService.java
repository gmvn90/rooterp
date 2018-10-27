/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.CellTypeDao;
import com.swcommodities.wsmill.hibernate.dto.CellType;

/**
 *
 * @author duhc
 */
public class CellTypeService {
    private CellTypeDao cellTypeDao;

    public void setCellTypeDao(CellTypeDao cellTypeDao) {
        this.cellTypeDao = cellTypeDao;
    }
    
    public CellType getCellTypeById(int id) {
        return cellTypeDao.getCellTypeById(id);
    }
    
    public ArrayList<CellType> getListCellType() {
        return cellTypeDao.getListCellType();
    }
    
    public CellType getCellTypeByName(String name) {
        return cellTypeDao.getCellTypeByName(name);
    }
}
