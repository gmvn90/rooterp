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

import com.swcommodities.wsmill.hibernate.dto.PortMaster;

/**
 *
 * @author duhc
 */
public class PortMasterDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public PortMasterDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<PortMaster> getAllPort() {
        //DetachedCriteria crit = DetachedCriteria.forClass(PortMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(PortMaster.class);
        crit.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name"))
                    .addOrder(Order.asc("name"))
                    .setResultTransformer(Transformers.aliasToBean(PortMaster.class));
        return (ArrayList<PortMaster>)crit.list();
    }
    
    public PortMaster getPortById(int id){
        return (PortMaster)sessionFactory.getCurrentSession().load(PortMaster.class, id);
    }
}
