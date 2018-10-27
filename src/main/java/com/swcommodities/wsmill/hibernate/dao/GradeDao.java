/*
 * To change this template, choose Tools | Templates
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
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class GradeDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public GradeDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<GradeMaster> getAllGradeNames() {
        //DetachedCriteria crit = DetachedCriteria.forClass(GradeMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(GradeMaster.class);
        crit.add(Restrictions.eq("active", Constants.ACTIVE));
        crit.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("name"), "name"))
                .setResultTransformer(Transformers.aliasToBean(GradeMaster.class));
        return (ArrayList<GradeMaster>) crit.list();
    }

    public ArrayList<HashMap> getAllGradeNamesMap() {
        String sql = "select new map (id as id, name as name) from GradeMaster where active = 1 order by name asc";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }

    public GradeMaster getGradeById(int id) {
        return (GradeMaster) sessionFactory.getCurrentSession().load(GradeMaster.class, id);
    }

    public String getGradeNameById(int id) {
        String sql = "select name from grade_master where id =:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", id);
        return query.uniqueResult().toString();
    }

    public ArrayList<HashMap> getGradeInStock() {
        String sql = "select distinct new map (gm.id as id, gm.name as name) "
                + "from WeightNoteReceipt as wnr left join wnr.weightNote wn "
                + "left join wn.gradeMaster as gm "
                + "where (wn.type = 'IM' or wn.type='XP') and wnr.status <> 2"
                + " order by gm.name";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }

    public ArrayList<GradeMaster> getGradeInStockObj() {
        String sql = "select distinct gm.id, gm.name "
                + "from WeightNoteReceipt as wnr left join wnr.weightNote wn "
                + "left join wn.gradeMaster as gm "
                + "where (wn.type = 'IM' or wn.type='XP') and wnr.status <> 2 and wnr.status <> 3";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<GradeMaster>) query.list();
    }

    public ArrayList<HashMap> getGradeInMovement() {
        String sql = "select distinct new map (gm.id as id, gm.name as name) from Movement as m left join m.grade gm order by gm.name asc";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }

    public ArrayList<HashMap> getGradeInWeightNote(String type) {
        String sql = "select distinct new map (gm.id as id, gm.name as name) from WeightNote as wn left join wn.gradeMaster gm where wn.type =:type order by gm.name asc";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("type", type);
        return (ArrayList<HashMap>) query.list();
    }

    public ArrayList<HashMap> getGradeInStock(int map_id, int client_id, int pledge_id) {
        String sql = "SELECT DISTINCT(gm.id) as id, gm.`name` as name\n"
                + "FROM weight_note_receipt wnr\n"
                + "LEFT JOIN weight_note wn ON wn.id = wnr.wn_id\n"
                + "LEFT JOIN warehouse_cell wc ON wnr.area_id = wc.id\n"
                + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "LEFT JOIN grade_master gm ON gm.id = wnr.grade_id\n"
                + "WHERE wnr.status <> 2 and (wn.type = 'IM' OR wn.type = 'XP')\n"
                + "AND wnr.area_id IS NOT NULL and wn.`status` <> 2\n"
                + "AND (wna.status <> 1 or wna.status is null)\n"
                + "AND (wnr.client_id = :client_id OR :client_id = -1)\n"
                + "AND (wnr.pledge_id = :pledge_id OR :pledge_id = -1)\n"
                + "AND (wc.warehouse_map_id = :map_id OR :map_id = -1)\n"
                + "ORDER BY gm.`name`";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id")
                .addScalar("name");
        query.setParameter("client_id", client_id);
        query.setParameter("pledge_id", pledge_id);
        query.setParameter("map_id", map_id);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<HashMap> grs = new ArrayList<>();
            for (Object[] obj : obj_list) {
                HashMap gr = new HashMap();
                gr.put("id", obj[0].toString());
                gr.put("name", obj[1].toString());
                grs.add(gr);
            }
            return grs;
        }
        return null;
    }

    public ArrayList<HashMap> allocatedGrade(int id) {
        ArrayList<HashMap> res = new ArrayList<>();
        String sql = "select distinct gm.id, gm.name from weight_note wn "
                + "left join wnr_allocation wna on wna.wn_id = wn.id "
                + "left join grade_master gm on gm.id = wn.grade_id "
                + "where wna.inst_id = :id and wna.inst_type = 'P'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id", IntegerType.INSTANCE)
                .addScalar("name", StringType.INSTANCE)
                .setParameter("id", id);
        ArrayList<Object[]> objs = (ArrayList<Object[]>) query.list();
        if (objs != null && !objs.isEmpty()) {
            for (Object[] obj : objs) {
                HashMap map = new HashMap();
                map.put("id", obj[0]);
                map.put("name", obj[1]);
                res.add(map);
            }
        }
        return res;
    }
}
