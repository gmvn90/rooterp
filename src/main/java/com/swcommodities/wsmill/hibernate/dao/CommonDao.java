/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

/**
 *
 * @author kiendn
 */
public class CommonDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CommonDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public Object[] getGradeCompanyFromInst(int inst_id, String table, String client_buyer) {
        String sql = "SELECT gm.name as grade, cm.name as company"
                + "	FROM "+ table +" inst"
                + "	left join grade_master gm on inst.grade_id = gm.id"
                + "	left join company_master cm on inst."+client_buyer+" = cm.id"
                + "	where inst.id =:inst_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("grade", StringType.INSTANCE).addScalar("company", StringType.INSTANCE);
        query.setParameter("inst_id", inst_id);
        return (Object[])query.uniqueResult();
    }
    public ArrayList<HashMap> getSelect(String table){
        String column = "name";
        if(table.equals("Country")) {
            column = "shortName";
        }
        String sql = "select new map (id as id, " + column + " as name) from " + table + " order by name";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }
    public boolean fieldIsEmpty(String table, String field, int id){
        String sql = "select " + field + " from " + table + " where id=:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", id);
        Object result = query.uniqueResult();
        return result != null;
    }
    public Object getCoreInfo(int id, String table){
        String sql = "from " + table + " where id=:id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", id);
        return query.uniqueResult();
    }

    public ArrayList<HashMap> getEmailListForMultiSelection() {
        String sql = "select new map (id as id, email as text) from EmailMaster order by email";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }
}
