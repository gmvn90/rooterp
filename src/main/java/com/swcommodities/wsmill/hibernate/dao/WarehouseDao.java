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
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.swcommodities.wsmill.hibernate.dto.Warehouse;

/**
 *
 * @author duhc
 */
public class WarehouseDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WarehouseDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<Warehouse> getAllWarehouseNames() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Warehouse.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Warehouse.class);
        crit.add(Restrictions.eq("active", true));
        crit.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name"))
                    .addOrder(Order.asc("name"))
                    .setResultTransformer(Transformers.aliasToBean(Warehouse.class));
        return (ArrayList<Warehouse>)crit.list();
    }
    
    public Warehouse getWarehouseById(int id){
        return (Warehouse)sessionFactory.getCurrentSession().load(Warehouse.class, id);
    }
}
