/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.ClientUser;
import com.swcommodities.wsmill.hibernate.dto.User;
import com.swcommodities.wsmill.hibernate.dto.view.ClientUserView;

/**
 *
 * @author kiendn
 */
public class UserDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public UserDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public User findById(int id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    public User getUser(String username, String password) {
        //DetachedCriteria crit = DetachedCriteria.forClass(User.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
        crit.add(Restrictions.eq("userName", username));
        crit.add(Restrictions.eq("password", password));
        ArrayList<Object> obj = new ArrayList<>(crit.list());
        if (!obj.isEmpty()){
            return (User)obj.get(0);
        }
        return null;
    }
    
    public ArrayList<User> getAllUsers() {
        //DetachedCriteria crit = DetachedCriteria.forClass(User.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
        return (ArrayList<User>)crit.list();
    }
    
    public String getUserNameById(int id){
        String sql = "select user_name from user where id =:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", id);
        return (String)query.uniqueResult();
    }
    
    public User updateUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user;
    }
    
    public ClientUser checkUserClient(int user_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(ClientUser.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ClientUser.class);
        crit.add(Restrictions.eq("user.id", user_id));
        ArrayList<Object> obj = new ArrayList<>(crit.list());
        if (!obj.isEmpty()){
            return (ClientUser)obj.get(0);
        }
        return null;
    }
    public ClientUserView getUserClient(int user_id) {
        String hql = "from ClientUserView as client where client.userId =:id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", user_id);
        return (ClientUserView)query.uniqueResult();
    }
    
    public void removeClientUserView(ClientUserView user){
        sessionFactory.getCurrentSession().delete(user);
    }
    
    public ClientUser updateClientUser(ClientUser client){
        sessionFactory.getCurrentSession().saveOrUpdate(client);
        return client;
    }
    
    public ClientUserView updateClientUserView(ClientUserView client){
        sessionFactory.getCurrentSession().saveOrUpdate(client);
        return client;
    }
}
