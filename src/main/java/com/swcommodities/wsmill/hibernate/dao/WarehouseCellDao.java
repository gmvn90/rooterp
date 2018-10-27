/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.WarehouseCell;

/**
 *
 * @author duhc
 */
public class WarehouseCellDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WarehouseCellDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<WarehouseCell> getListWarehouseCellById(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseCell.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseCell.class);
        crit.add(Restrictions.eq("warehouseMap.id", id));
        return (ArrayList<WarehouseCell>) crit.list();
    }

    public void updateWarehouseCell(WarehouseCell warehouseCell) {
//        boolean isUpdated = false;
//        if (warehouseCell.getId() != null){
//            isUpdated = true;
//        }
        sessionFactory.getCurrentSession().saveOrUpdate(warehouseCell);
//        if (isUpdated){
//            return 0;
//        }
//        return warehouseCell.getId();
    }

    public ArrayList<WarehouseCell> getListWarehouseCellByOrdinate_X(int warehouseMapId, int ordinate_x) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseCell.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseCell.class);
        crit.add(Restrictions.eq("warehouseMap.id", warehouseMapId));
        crit.add(Restrictions.eq("ordinate_x", ordinate_x));
        return (ArrayList<WarehouseCell>) crit.list();
    }

    public ArrayList<WarehouseCell> getListWarehouseCellByOrdinate_Y(int warehouseMapId, int ordinate_y) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseCell.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseCell.class);
        crit.add(Restrictions.eq("warehouseMap.id", warehouseMapId));
        crit.add(Restrictions.eq("ordinate_y", ordinate_y));
        return (ArrayList<WarehouseCell>)crit.list();
    }

    public WarehouseCell getWarehouseCellById(int id) {
        return (WarehouseCell) sessionFactory.getCurrentSession().get(WarehouseCell.class, id);
    }
}
