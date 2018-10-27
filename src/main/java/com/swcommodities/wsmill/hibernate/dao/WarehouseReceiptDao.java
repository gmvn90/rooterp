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
import org.hibernate.transform.Transformers;

import com.swcommodities.wsmill.hibernate.dto.WarehouseReceipt;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class WarehouseReceiptDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WarehouseReceiptDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public WarehouseReceipt findById(int id){
        return (WarehouseReceipt)sessionFactory.getCurrentSession().load(WarehouseReceipt.class, id);
    }
    
    public ArrayList<WarehouseReceipt> getWarehouseReceipt(String type){
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseReceipt.class);
        crit.add(Restrictions.eq("instType", type));
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("refNumber"), "refNumber"))
                .setResultTransformer(Transformers.aliasToBean(WarehouseReceipt.class));
        return (ArrayList<WarehouseReceipt>)crit.list();
    }
    
    public ArrayList<WeightNote> getWnByWR(int wr_id){
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("wrcId", wr_id));
        return (ArrayList<WeightNote>)crit.list();
    }
    
    public String getNewWrRef(String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseReceipt.class);
        crit.add(Restrictions.eq("instType", type));
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        String prefix = (type.equals("IM") ? "WR-" : "WC-");
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, prefix) : Common.getNewRefNumber(null, prefix);
    }
    
    public int update(WarehouseReceipt wr){
        sessionFactory.getCurrentSession().saveOrUpdate(wr);
        return wr.getId();
    }
    
    public WarehouseReceipt getWrByQrId(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseReceipt.class);
        crit.add(Restrictions.eq("qualityReport.id", id));
        return (WarehouseReceipt) crit.uniqueResult();
    }
}