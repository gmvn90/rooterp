/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.swcommodities.wsmill.hibernate.dto.Movement;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.view.MovementView;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class MovementDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public MovementDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public int saveMovementView(MovementView move) {
        if (move.getId() != null) {
            sessionFactory.getCurrentSession().update(move);
        } else {
            sessionFactory.getCurrentSession().save(move);
        }
        return move.getId();
    }

    public Movement getMovementById(int id) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Movement.class);
        crit.add(Restrictions.eq("id", id));
        return (Movement) crit.uniqueResult();
    }

    public String getNewMovementRef() {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Movement.class);
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "MOV-") : Common.getNewRefNumber(null, "MOV-");
    }

    public ArrayList<HashMap> getMovementRefList(String searchString) {
        String sql = "select new map(m.id as id, m.refNumber as name) from MovementView as m where m.status <> 2 order by m.refNumber desc";
//        DetachedCriteria crit = DetachedCriteria.forClass(DeliveryInstruction.class);
//        crit.add(Restrictions.like("refNumber", "%" + searchString + "%"));
//        crit.add(Restrictions.lt("status", Constants.DELETED));
//        crit.addOrder(Order.desc("refNumber"));
//        return (ArrayList<DeliveryInstruction>) getHibernateTemplate().findByCriteria(crit, firstResult, maxResults);
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        //query.setParameter("search", "%" + searchString + "%");
        return (ArrayList<HashMap>) query.list();
    }

    public ArrayList<Movement> getMovementRefList() {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(Movement.class);
        crit.add(Restrictions.ne("status", Constants.DELETED));
        crit.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("refNumber"), "refNumber"))
                .setResultTransformer(Transformers.aliasToBean(ProcessingInstruction.class));
        return (ArrayList<Movement>) crit.list();
    }

    public HashMap getAllocatedMoved(int moveId) {
        String sql = "call getAllocatedMoved(:id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", moveId);
        Object[] res = (Object[]) query.uniqueResult();
        HashMap map = new HashMap();
        
        if (res != null) {
            float allocated = Float.valueOf(res[0].toString());
            float moved = Float.valueOf(res[1].toString());
            float pending = allocated - moved;
            map.put("allocated", allocated > 0 ? allocated : "0.000");
            map.put("moved", moved > 0 ? moved : "0.000");
            map.put("pending", pending > 0 ? pending : "0.000");
        }
        return map;
    }
}
