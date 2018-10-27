/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.Accessories;
import com.swcommodities.wsmill.hibernate.dto.AccessoryMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;

/**
 *
 * @author duhc
 */
public class AccessoriesDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public AccessoriesDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public Accessories getAccessoriesById(int id){
        return (Accessories) sessionFactory.getCurrentSession().get(Accessories.class, id);
    }
    
    public void updateAccessories(Accessories acc){
        sessionFactory.getCurrentSession().saveOrUpdate(acc);
    }
    
    public ArrayList<Accessories> getAccessoriesByMasterIdAndShipping(AccessoryMaster accessoryMaster, ShippingInstruction si){
        //DetachedCriteria crit = DetachedCriteria.forClass(Accessories.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Accessories.class);
        crit.add(Restrictions.eq("accessoryMaster", accessoryMaster));
        crit.add(Restrictions.eq("shippingInstruction", si));
        return (ArrayList<Accessories>) crit.list();
    }
}
