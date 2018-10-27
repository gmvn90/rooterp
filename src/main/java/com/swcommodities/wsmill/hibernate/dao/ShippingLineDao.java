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

import com.swcommodities.wsmill.hibernate.dto.ShippingLineMaster;

/**
 *
 * @author duhc
 */
public class ShippingLineDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public ShippingLineDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<ShippingLineMaster> getAllShippingLineName() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingLineMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingLineMaster.class);
        crit.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name"))
                    .addOrder(Order.asc("name"))
                    .setResultTransformer(Transformers.aliasToBean(ShippingLineMaster.class));
        return (ArrayList<ShippingLineMaster>)crit.list();
    }
    
    public ShippingLineMaster getShippingLineMasterById(int id){
        return (ShippingLineMaster)sessionFactory.getCurrentSession().load(ShippingLineMaster.class, id);
    }
}
