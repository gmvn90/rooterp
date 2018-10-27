/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.WarehouseMap;

/**
 *
 * @author duhc
 */
public class WarehouseMapDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WarehouseMapDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    private long totalAfterFilter;

    public long getTotalAfterFilter() {
        return totalAfterFilter;
    }

    public void setTotalAfterFilter(long totalAfterFilter) {
        this.totalAfterFilter = totalAfterFilter;
    }

    public ArrayList<WarehouseMap> getWarehouseMapListByWarehouseId(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WarehouseMap.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WarehouseMap.class);
        crit.add(Restrictions.eq("warehouse.id", id));
        crit.add(Restrictions.eq("status", (byte) 1));
        crit.addOrder(Order.asc("name"));
        return (ArrayList<WarehouseMap>) crit.list();
    }

    public ArrayList<HashMap> getWarehouseMaps() {
        String sql = "select new map(w.id as id, w.name as name) from WarehouseMap w where w.status <> 0 order by w.name";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }

    public WarehouseMap getWarehouseMapById(int id) {
        return (WarehouseMap) sessionFactory.getCurrentSession().get(WarehouseMap.class, id);
    }

    public int createNewMap(WarehouseMap wm) {
        boolean isUpdated = false;
        if (wm.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(wm);
        if (isUpdated) {
            return 0;
        }
        return wm.getId();
    }

    public boolean checkDeletable(int map_id) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(wnr.id)\n"
                + "FROM weight_note_receipt wnr \n"
                + "LEFT JOIN warehouse_cell wc ON wnr.area_id = wc.id\n"
                + "LEFT JOIN warehouse_map wm ON wc.warehouse_map_id = wm.id\n"
                + "WHERE wm.id = :map_id AND wnr.`status` != 2 AND wnr.`status` != 3");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        query.setParameter("map_id", map_id);
        BigInteger countResult = (BigInteger) query.uniqueResult();
        if (countResult.intValue() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public int updateWarehouseMap(WarehouseMap wm) {
        boolean isUpdated = false;
        if (wm.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(wm);
        if (isUpdated) {
            return 0;
        }
        return wm.getId();
    }

    public long countRow() {
        String sql = "select count(id) from weight_note_receipt";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
    }

    public ArrayList<Map> searchWnrsByAreaId(String searchTerm, String order, int start, int amount, String colName, int grade, int client, int pledge, int area) {
        StringBuilder sql = new StringBuilder(""
                + "SELECT \n"
                + "wnr.id as wnr_id, \n"
                + "wnr.ref_number as ref_number, \n"
                + "Round((wnr.gross_weight - wnr.tare_weight)/1000,3) as net_weight, \n"
                + "gm.`name` as grade, \n"
                + "IFNULL(cm2.name,'') as pledge,\n"
                + "IFNULL(cm.name,'') as client,\n"
                + "gm.id as gmid,\n"
                + "cm.name as clientname,\n"
                + "cm2.name as pledgename,\n"
                + "wna.`status` as stt1, wnr.`status` as stt2 "
                + "FROM weight_note_receipt wnr\n"
                + "LEFT JOIN grade_master gm ON wnr.grade_id = gm.id\n"
                + "LEFT JOIN company_master cm ON wnr.client_id = cm.id\n"
                + "LEFT JOIN company_master cm2 ON wnr.pledge_id = cm2.id\n"
                + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "	LEFT JOIN weight_note wn ON wnr.wn_id = wn.id\n"
                + "WHERE wnr.area_id = :area\n"
                + "AND (wnr.pledge_id = :pledge or :pledge = -1)\n"
                + "AND (wnr.client_id = :client or :client = -1)\n"
                + "AND (wnr.grade_id = :grade or :grade = -1)\n"
                + "AND (wnr.`status` != 2) AND (wn.type = 'IM' OR wn.type = 'XP') \n"
                + "HAVING (stt2 = 3 and stt1 <> 1) or stt1 is null\n"
                );
        if (!searchTerm.equals("")) {
            sql.append("AND (ref_number like :searchTerm \n"
                    + "OR net_weight like :searchTerm \n"
                    + "OR grade like :searchTerm \n"
                    + "OR clientname like :searchTerm \n"
                    + "OR pledgename like :searchTerm)");
        }
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ").append((order.equals("desc") ? "desc" : "asc"));
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("wnr_id")
                .addScalar("ref_number")
                .addScalar("net_weight")
                .addScalar("grade")
                .addScalar("pledge")
                .addScalar("client")
                .addScalar("stt2");
        if (!searchTerm.equals("")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        query.setParameter("area", area);
        query.setParameter("grade", grade);
        query.setParameter("pledge", pledge);
        query.setParameter("client", client);
        this.setTotalAfterFilter(query.list().size());
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<Map> wnrs = new ArrayList<>();
            for (Object[] obj : obj_list) {
                Map wnr = new HashMap();
                wnr.put("wnr_id", obj[0].toString());
                wnr.put("ref_number", obj[1].toString());
                wnr.put("net_weight", obj[2].toString());
                wnr.put("grade", obj[3].toString());
                wnr.put("pledge", obj[4].toString());
                wnr.put("client", obj[5].toString());
                wnr.put("status", obj[6].toString());
                wnrs.add(wnr);
            }
            return wnrs;
        }
        return null;
    }
}
