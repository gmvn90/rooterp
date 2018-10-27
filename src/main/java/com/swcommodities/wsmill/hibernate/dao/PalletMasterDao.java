/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.PalletMaster;

/**
 *
 * @author kiendn
 */
public class PalletMasterDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public PalletMasterDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public PalletMaster getPalletByRef(String ref_number) {
        if (isExist(ref_number)) {
            //DetachedCriteria crit = DetachedCriteria.forClass(PalletMaster.class);
            Criteria crit = sessionFactory.getCurrentSession().createCriteria(PalletMaster.class);
            crit.add(Restrictions.eq("name", ref_number));
            return (PalletMaster) crit.uniqueResult();
        } else {
            return null;
        }

    }

    public boolean isExist(String ref_number) {
        String sql = "select id from pallet_master where name =:name";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("name", ref_number);
        Object obj = query.uniqueResult();
        return (obj != null) ? true : false;
    }

    public String getLatestRef() {
        String sql = "select max(name) from pallet_master";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return (String) query.uniqueResult();
    }

    public PalletMaster updatePallet(PalletMaster pm) {
        sessionFactory.getCurrentSession().saveOrUpdate(pm);
        return pm;
    }
}
