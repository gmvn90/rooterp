/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;
import java.util.HashMap;

import com.swcommodities.wsmill.hibernate.dao.PackingDao;
import com.swcommodities.wsmill.hibernate.dto.PackingMaster;

/**
 *
 * @author kiendn
 */
public class PackingService {
    private PackingDao packingDao;

    public void setPackingDao(PackingDao packingDao) {
        this.packingDao = packingDao;
    }
    
    public ArrayList<PackingMaster> getAllPackings(){
        return packingDao.getAllPackings();
    }
    
    public PackingMaster getPackingById(int id){
        return packingDao.getPackingById(id);
    }
    
    public ArrayList<HashMap> getAllPackingsMap() {
        return packingDao.getAllPackingsMap();
    }
}
