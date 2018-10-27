/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import com.swcommodities.wsmill.hibernate.dao.PalletMasterDao;
import com.swcommodities.wsmill.hibernate.dto.PalletMaster;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author kiendn
 */
public class PalletMasterService {
    private PalletMasterDao palletMasterDao;

    public void setPalletMasterDao(PalletMasterDao palletMasterDao) {
        this.palletMasterDao = palletMasterDao;
    }
    
    public PalletMaster getPalletByRef(String ref_number){
        return palletMasterDao.getPalletByRef(ref_number);
    }
    
    public String getLatestRef(){
        return palletMasterDao.getLatestRef();
    }
    
    public boolean isExist(String name){
        return palletMasterDao.isExist(name);
    }
    
    public boolean isExist(long order_number){
        return palletMasterDao.isExist("SW-BD-PA-" + Common.getPalletRefNumber(order_number).toString());
    }
    
    public PalletMaster updatePallet(PalletMaster pm){
        try{
            return palletMasterDao.updatePallet(pm);
        }catch(Exception e){
            return null;
        }
        
    }
}
