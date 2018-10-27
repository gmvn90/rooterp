/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import com.swcommodities.wsmill.hibernate.dao.WeightLossDao;
import com.swcommodities.wsmill.hibernate.dto.WeightLoss;

/**
 *
 * @author duhc
 */
public class WeightLossService {
    private WeightLossDao weightLossDao;

    public void setWeightLossDao(WeightLossDao weightLossDao) {
        this.weightLossDao = weightLossDao;
    }
    
    public void updateWeightLoss(WeightLoss wl) {
        weightLossDao.updateWeightLoss(wl);
    }
    
    public void deleteWeightLoss(WeightLoss wl) {
        weightLossDao.deleteWeightLoss(wl);
    }
    
    public WeightLoss getWeightLossByPiId(Integer id) {
        return weightLossDao.getWeightLossByPiId(id);
    }
}
