/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.Authorization;
import com.swcommodities.wsmill.hibernate.dto.Page;
import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author kiendn
 */
public class AuthorizationDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public AuthorizationDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public Authorization getAuthorizationOfUserInPage(User user, Page page) {
        //System.out.println("page id " + page.getId());
        //DetachedCriteria crit = DetachedCriteria.forClass(Authorization.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Authorization.class);
        crit.add(Restrictions.eq("user", user));
        crit.add(Restrictions.eq("page", page));
        ArrayList result = (ArrayList)crit.list();
        if (result != null && !result.isEmpty()){
            return (Authorization)result.get(0);
        }
        return null;
    }
    
    public Authorization getAuthorizationOfUserInPageSimple(int user_id, int page_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(Authorization.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Authorization.class);
        crit.add(Restrictions.eq("user.id", user_id));
        crit.add(Restrictions.eq("page.id", page_id));
        ArrayList result = (ArrayList)crit.list();
        if (result != null && !result.isEmpty()){
            return (Authorization)result.get(0);
        }
        return null;
    }
    
    public boolean isAuthorized(int user_id, int page_id){
        String sql = "select permission from authorization where user_id =:user_id and page_id =:page_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("user_id", user_id)
                .setParameter("page_id", page_id);
        Object obj = query.uniqueResult();
        return (obj != null && Integer.valueOf(obj.toString()) == 1);
    }

    public ArrayList<Authorization> getAuthorizationOfUser(User user) {
        //DetachedCriteria crit = DetachedCriteria.forClass(Authorization.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Authorization.class);
        crit.add(Restrictions.eq("user", user));
        crit.addOrder(Order.asc("page"));
        return (ArrayList<Authorization>) crit.list();
    }
    
    public Page getPageFromAuthorization(Authorization authorization){
        Query query = sessionFactory.getCurrentSession().createQuery("select a.page from Authorization a where a.id =:id");
        query.setParameter("id", authorization.getId());
        return (Page)query.uniqueResult();
    }
    
    public Authorization updateAuthorization(Authorization authorization) {
        sessionFactory.getCurrentSession().saveOrUpdate(authorization);
        return authorization;
    }
    
    public ArrayList<Map> getPermission(int user_id){
        String sql = "select new map(auth.page.id as page, permission as perm) from Authorization auth where auth.user.id = :userId";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("userId", user_id);
        return (ArrayList<Map>)query.list();
    }
    
    public Authorization updateAuth(Authorization auth){
        sessionFactory.getCurrentSession().saveOrUpdate(auth);
        return auth;
    }
}
