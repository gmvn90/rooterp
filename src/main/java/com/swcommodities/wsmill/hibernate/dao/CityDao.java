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

import com.swcommodities.wsmill.hibernate.dto.City;

/**
 *
 * @author duhc
 */
public class CityDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CityDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<City> getAllCity() {
        //DetachedCriteria crit = DetachedCriteria.forClass(City.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(City.class);
        crit.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name"))
                    .addOrder(Order.asc("name"))
                    .setResultTransformer(Transformers.aliasToBean(City.class));
        return (ArrayList<City>)crit.list();
    }
    
    public City getCityById(int id){
        return (City)sessionFactory.getCurrentSession().load(City.class, id);
    }
}
