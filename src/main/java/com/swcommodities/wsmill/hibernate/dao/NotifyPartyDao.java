/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import org.hibernate.SessionFactory;

import com.swcommodities.wsmill.hibernate.dto.NotifyParty;

/**
 *
 * @author duhc
 */
public class NotifyPartyDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public NotifyPartyDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public NotifyParty getNotifyPartyById(int id){
        return (NotifyParty)sessionFactory.getCurrentSession().get(NotifyParty.class, id);
    }
    
    public void updateNotifyParty(NotifyParty np){
        sessionFactory.getCurrentSession().saveOrUpdate(np);
    }
    
    public void deleteNotifyParty(NotifyParty np) {
        np.getShippingInstruction().getNotifyParties().remove(np);
        sessionFactory.getCurrentSession().delete(np);
    }
}
