/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.swcommodities.wsmill.hibernate.dto.PackingMaster;

/**
 *
 * @author kiendn
 */
public class PackingDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public PackingDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<PackingMaster> getAllPackings(){
        //DetachedCriteria crit = DetachedCriteria.forClass(PackingMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(PackingMaster.class);
        crit.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name"))
                    .addOrder(Order.asc("name"))
                    .setResultTransformer(Transformers.aliasToBean(PackingMaster.class));
        return (ArrayList<PackingMaster>)crit.list();
    }
    
     
    public ArrayList<HashMap> getAllPackingsMap() {
        String sql = "select new map (id as id, name as name) from PackingMaster";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }
    
    public PackingMaster getPackingById(int id){
        return (PackingMaster)sessionFactory.getCurrentSession().load(PackingMaster.class, id);
    }
}
