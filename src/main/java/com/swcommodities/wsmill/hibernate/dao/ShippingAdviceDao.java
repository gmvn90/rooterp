package com.swcommodities.wsmill.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.ShippingAdvice;
import com.swcommodities.wsmill.hibernate.dto.ShippingAdviceSent;
import com.swcommodities.wsmill.utils.Common;

/**
 * Created by gmvn on 8/18/16.
 */
public class ShippingAdviceDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public ShippingAdviceDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ShippingAdvice updateShippingAdvice(ShippingAdvice obj) {
        sessionFactory.getCurrentSession().update(obj);
        return obj;
    }

    public ShippingAdvice newShippingAdvice(ShippingAdvice obj) {
        sessionFactory.getCurrentSession().save(obj);
        return obj;
    }

    public String getNewShippingAdivceRef() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingAdvice.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingAdvice.class);
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "SA-") : Common.getNewRefNumber(null, "SA-");
    }

    public ShippingAdviceSent newShippingAdviceSent(ShippingAdviceSent obj) {
        sessionFactory.getCurrentSession().save(obj);
        return obj;
    }

    public String getNewShippingAdviceSentRef(String sa_ref) {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingAdviceSent.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingAdviceSent.class);
        crit.createAlias("shippingAdvice", "sa");
        crit.add(Restrictions.eq("sa.refNumber", sa_ref));
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        if (cur_ref != null) {
            int new_ref = Integer.parseInt(cur_ref.substring(cur_ref.lastIndexOf("-") + 1)) + 1;
            return sa_ref + "-" + ((new_ref < 10) ? "0" + new_ref : new_ref);
        } else {
            return sa_ref + "-01";
        }
    }


}
