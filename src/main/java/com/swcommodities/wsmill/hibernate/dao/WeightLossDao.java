/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.WeightLoss;

/**
 *
 * @author duhc
 */
public class WeightLossDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WeightLossDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public void updateWeightLoss(WeightLoss wl) {
        sessionFactory.getCurrentSession().saveOrUpdate(wl);
    }
    
    public void deleteWeightLoss(WeightLoss wl) {
        sessionFactory.getCurrentSession().delete(wl);
    }
    
    public WeightLoss getWeightLossByPiId(Integer id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightLoss.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightLoss.class);
        crit.add(Restrictions.eq("processingInstruction.id", id));
        ArrayList obj =  (ArrayList) crit.list();
        if (obj != null && !obj.isEmpty()){
            return (WeightLoss)obj.get(0);
        }
        return null;
    }
}
