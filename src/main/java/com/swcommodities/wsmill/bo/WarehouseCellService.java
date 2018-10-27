/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.WarehouseCellDao;
import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author duhc
 */
public class WarehouseCellService {
    private WarehouseCellDao warehouseCellDao;

    public void setWarehouseCellDao(WarehouseCellDao warehouseCellDao) {
        this.warehouseCellDao = warehouseCellDao;
    }
    
    public ArrayList<WarehouseCell> getListWarehouseCellById(int id) {
        return warehouseCellDao.getListWarehouseCellById(id);
    }
    
    public void updateWarehouseCell(WarehouseCell warehouseCell) {
        this.warehouseCellDao.updateWarehouseCell(warehouseCell);
    }
    
    public ArrayList<WarehouseCell> getListWarehouseCellByOrdinate_X(int warehouseMapId, int ordinate_x) {
        return warehouseCellDao.getListWarehouseCellByOrdinate_X(warehouseMapId, ordinate_x);
    }
    
    public ArrayList<WarehouseCell> getListWarehouseCellByOrdinate_Y(int warehouseMapId, int ordinate_y) {
        return warehouseCellDao.getListWarehouseCellByOrdinate_Y(warehouseMapId, ordinate_y);
    }
    
    public WarehouseCell getWarehouseCellById(int id) {
        return warehouseCellDao.getWarehouseCellById(id);
    }
    
    public String convertIdIntoCode(WarehouseCell warehouseCell) {
        if (warehouseCell != null) {
            int first_before = warehouseCell.getOrdinateX();
            String[] wall_ver = warehouseCell.getWarehouseMap().getWallVertical().split(",");
            String[] check = new String[warehouseCell.getWarehouseMap().getWidth()];
            int count = 1;
            for (int i = 1; i <= warehouseCell.getWarehouseMap().getWidth(); i++) {
                boolean flag = false;
                for (int j = 0; j < wall_ver.length; j++) {
                    if (i == Integer.parseInt(wall_ver[j])) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    check[i - 1] = "0";
                } else {
                    check[i - 1] = Common.convertIntToAlphabet(count);
                    count++;
                }
            }
            String first_after = check[first_before - 1]; //first char

            int second_before = warehouseCell.getOrdinateY();
            String[] wall_hor = warehouseCell.getWarehouseMap().getWallHorizontal().split(",");
            int[] check_second = new int[warehouseCell.getWarehouseMap().getHeight()];
            int count_second = 1;
            for (int i = 1; i <= warehouseCell.getWarehouseMap().getHeight(); i++) {
                boolean flag = false;
                for (int j = 0; j < wall_hor.length; j++) {
                    if (i == Integer.parseInt(wall_hor[j])) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    check_second[i - 1] = 0;
                } else {
                    check_second[i - 1] = count_second;
                    count_second++;
                }
            }
            int second_after = check_second[second_before - 1]; //second int

            return warehouseCell.getWarehouseMap().getName() + "-" + first_after + second_after;
        }
        return "";
    }
}
