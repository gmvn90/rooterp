/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import com.swcommodities.wsmill.hibernate.dto.Menu;

/**
 *
 * @author kiendn
 */
public class MenuDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public MenuDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public Menu getMenuById(Integer id) {
        return (Menu) sessionFactory.getCurrentSession().get(Menu.class, id);
    }

    public ArrayList<Menu> getMenuLv1() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Menu.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Menu.class);
        crit.add(Restrictions.isNull("menu"));
        crit.addOrder(Order.asc("order"));
        return (ArrayList<Menu>) crit.list();
    }

    public ArrayList<Menu> getSiblingMenu(Menu menu) {
        //DetachedCriteria crit = DetachedCriteria.forClass(Menu.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Menu.class);
        if (menu.getMenu() != null) {
            crit.add(Restrictions.eq("menu", menu.getMenu()));
        } else {
            crit.add(Restrictions.isNull("menu"));
        }
        crit.addOrder(Order.asc("order"));
        return (ArrayList<Menu>) crit.list();
    }

    public ArrayList<Menu> getDefaultPage() {
        //DetachedCriteria crit = DetachedCriteria.forClass(Menu.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Menu.class);
        crit.add(Restrictions.eq("default_", true));
        crit.add(Restrictions.isNotNull("page"));
        return (ArrayList<Menu>) crit.list();
    }

    public boolean countPermission(int parent_id, int user_id) {
        String sql = "select IFNULL(count(*),0) as count, (select count(*) from menu where parent_id = page_id) as child from authorization \n"
                + "where user_id = :user_id and permission = 1 and page_id in (\n"
                + "select url_id from menu where parent_id = :parent_id \n"
                + ")";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("count", IntegerType.INSTANCE)
                .addScalar("child", StringType.INSTANCE)
                .setParameter("user_id", user_id)
                .setParameter("parent_id", parent_id);
        Object[] obj = (Object[])query.uniqueResult();
        int count = Integer.valueOf(obj[0].toString());
        return count != 0 || obj[1] != null;
    }

}
