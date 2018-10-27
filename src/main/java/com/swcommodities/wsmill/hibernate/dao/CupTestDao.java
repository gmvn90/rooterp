/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.CupTest;

/**
 *
 * @author duhc
 */
public class CupTestDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CupTestDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<CupTest> getCupTestByQrId(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(CupTest.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(CupTest.class);
        crit.add(Restrictions.eq("qualityReport.id", id));
        return (ArrayList<CupTest>)crit.list();
    }
    
    public void deleteOldCupTest(ArrayList<CupTest> cts) {
       for (CupTest ct: cts) {
           sessionFactory.getCurrentSession().delete(ct);
       }
       
    }
    
    public void updateCupTest(CupTest ct){
        sessionFactory.getCurrentSession().saveOrUpdate(ct);
    } 
}
