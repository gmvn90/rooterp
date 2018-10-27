/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.ShippingLineDao;
import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;

/**
 *
 * @author duhc
 */
public class ShippingLineService {
    private ShippingLineDao shippingLineDao;

    public void setShippingLineDao(ShippingLineDao shippingLineDao) {
        this.shippingLineDao = shippingLineDao;
    }
    
    public ArrayList<ShippingLineMaster> getAllShippingLineName() {
        return shippingLineDao.getAllShippingLineName();
    }
    
    public ShippingLineMaster getShippingLineMasterById(int id){
        return shippingLineDao.getShippingLineMasterById(id);
    }
}
