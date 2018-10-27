/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.swcommodities.wsmill.hibernate.dto.AccessoryMaster;

/**
 *
 * @author duhc
 */
public class AccessoryMasterDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public AccessoryMasterDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<AccessoryMaster> getAllAccessoryMaster() {
        //DetachedCriteria crit = DetachedCriteria.forClass(AccessoryMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(AccessoryMaster.class);
        crit.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("unit"), "unit")
                .add(Projections.property("name"), "name"))
                .addOrder(Order.asc("name"))
                .setResultTransformer(Transformers.aliasToBean(AccessoryMaster.class));
        return (ArrayList<AccessoryMaster>) crit.list();
    }
    
    public AccessoryMaster getAccessoryMasterById(int id){
        return (AccessoryMaster)sessionFactory.getCurrentSession().get(AccessoryMaster.class, id);
    }
}
