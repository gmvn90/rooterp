/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.Menu;
import com.swcommodities.wsmill.hibernate.dto.Page;


/**
 *
 * @author kiendn
 */
public class PageDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public PageDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public Page getPageByPageCode(int id) {
        return (Page) sessionFactory.getCurrentSession().load(Page.class, id);
    }
    
    public ArrayList<Page> getAllPages() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Page.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Page.class);
        return (ArrayList<Page>) crit.list();
    }
    
    public ArrayList<Page> getAllPagesExcludeSection() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Page.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Page.class);
        crit.add(Restrictions.isNotNull("url"));
        return (ArrayList<Page>) crit.list();
    }
    
    public ArrayList<Page> getPagesInMenus() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Menu.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Menu.class);
        crit.add(Restrictions.isNotNull("page"));
        crit.setProjection(Projections.property("page"));
        return (ArrayList<Page>) crit.list();
    }
    
    public ArrayList<Page> getPages() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Page.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Page.class);
        crit.add(Restrictions.isNull("page"));

        return (ArrayList<Page>) crit.list();
    }
        
    public ArrayList<Page> getPageSections(Page page) {
        //DetachedCriteria crit = DetachedCriteria.forClass(Page.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Page.class);
        crit.add(Restrictions.eq("page", page));
        return (ArrayList<Page>) crit.list();
    }
    
    public Page getPageCode(String url){
        //DetachedCriteria crit = DetachedCriteria.forClass(Page.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Page.class);
        crit.add(Restrictions.eq("url", url));
        ArrayList<Object> obj = (ArrayList<Object>) crit.list();
        if (obj != null && !obj.isEmpty()) return (Page)obj.get(0);
        return null;
    }
}
