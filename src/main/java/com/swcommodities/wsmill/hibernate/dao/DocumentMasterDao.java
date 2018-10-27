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

import com.swcommodities.wsmill.hibernate.dto.DocumentMaster;

/**
 *
 * @author duhc
 */
public class DocumentMasterDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public DocumentMasterDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<DocumentMaster> getAllDocumentByType(String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(DocumentMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DocumentMaster.class);
        crit.add(Restrictions.eq("type", type));
        crit.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("name"), "name"))
                .addOrder(Order.asc("name"))
                .setResultTransformer(Transformers.aliasToBean(DocumentMaster.class));
        return (ArrayList<DocumentMaster>) crit.list();
    }
    
    public DocumentMaster getDocumentMasterById(int id){
        return (DocumentMaster)sessionFactory.getCurrentSession().get(DocumentMaster.class, id);
    }
}
