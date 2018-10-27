/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.DocumentMaster;
import com.swcommodities.wsmill.hibernate.dto.Documents;

/**
 *
 * @author duhc
 */
public class DocumentsDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public DocumentsDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<Documents> getDocumentsByInsId(int id, String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(Documents.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Documents.class);
        crit.add(Restrictions.eq("instId", id));
        crit.add(Restrictions.eq("type", type));
        return (ArrayList<Documents>) crit.list();
    }
    
    /**
     *
     * @param inst_id
     * @param document
     * @param type
     * @return
     */
    public  ArrayList<Documents> getDocumentsByInsIdAndDocumentId(int inst_id, DocumentMaster document,String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(Documents.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Documents.class);
        crit.add(Restrictions.eq("instId", inst_id));
        crit.add(Restrictions.eq("type", type));
        crit.add(Restrictions.eq("documentMaster", document));
        return (ArrayList<Documents>) crit.list();
    }
    
    public Documents getDocumentById(int id) {
        return (Documents)sessionFactory.getCurrentSession().get(Documents.class, id);
    }
    
    public void updateDocuments(Documents doc){
        sessionFactory.getCurrentSession().saveOrUpdate(doc);
    }
}
