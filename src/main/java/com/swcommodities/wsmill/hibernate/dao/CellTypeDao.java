/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.CellType;

/**
 *
 * @author duhc
 */
public class CellTypeDao {
    SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CellTypeDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public CellType getCellTypeById(int id) {
        return (CellType) sessionFactory.getCurrentSession().get(CellType.class, id);
    }
    
    public CellType getCellTypeByName(String name) {
        //DetachedCriteria crit = DetachedCriteria.forClass(CellType.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(CellType.class);
        crit.add(Restrictions.eq("cellTypeName", name));
        return (CellType) crit.uniqueResult();
    }
    
    public ArrayList<CellType> getListCellType() {
        //DetachedCriteria crit = DetachedCriteria.forClass(CellType.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(CellType.class);

        return (ArrayList<CellType>) crit.list();
    }
}
