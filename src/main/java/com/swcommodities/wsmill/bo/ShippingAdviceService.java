package com.swcommodities.wsmill.bo;

import com.swcommodities.wsmill.hibernate.dao.ShippingAdviceDao;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdvice;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdviceSent;

/**
 * Created by gmvn on 8/18/16.
 */
public class ShippingAdviceService {
    private ShippingAdviceDao shippingAdviceDao;

    public void setShippingAdviceDao(ShippingAdviceDao shippingAdviceDao) {
        this.shippingAdviceDao = shippingAdviceDao;
    }

    public ShippingAdvice updateShippingAdvice(ShippingAdvice shippingAdvice) {

        return shippingAdviceDao.updateShippingAdvice(shippingAdvice);
    }

    public ShippingAdvice newShippingAdvice(ShippingAdvice shippingAdvice) {

        return shippingAdviceDao.newShippingAdvice(shippingAdvice);
    }

    public ShippingAdviceSent newShippingAdviceSent(ShippingAdviceSent shippingAdviceSent) {

        return shippingAdviceDao.newShippingAdviceSent(shippingAdviceSent);
    }

    public String getNewShippingAdviceSentRef(String sa_ref) {
        return shippingAdviceDao.getNewShippingAdviceSentRef(sa_ref);
    }

    public String getNewShippingAdivceRef() {

        return shippingAdviceDao.getNewShippingAdivceRef();
    }

    
}
