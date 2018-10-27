/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DateType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.json.JSONArray;
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;
import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.hibernate.dto.view.WnrAllocationView;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class WeightNoteReceiptDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WeightNoteReceiptDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public ArrayList<WeightNoteReceipt> getWNRByWN(int wn_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.add(Restrictions.ne("status", Constants.DELETED));
        crit.add(Restrictions.eq("weightNote.id", wn_id));
        return (ArrayList<WeightNoteReceipt>) crit.list();
    }

    public ArrayList<WeightNoteReceipt> getAvailableWNR(int wn_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.add(Restrictions.eq("weightNote.id", wn_id));
        crit.add(Restrictions.or(
                Restrictions.eq("status", Constants.AVAILABLE),
                Restrictions.eq("status", Constants.MOVED)
        ));
//        crit.add(Restrictions.eq("status", Constants.AVAILABLE));
        return (ArrayList<WeightNoteReceipt>) crit.list();
    }

    public ArrayList<WeightNoteReceipt> getAllocatedWNR(int wn_id, int inst_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.createAlias("wnrAllocationsForWnrId", "wna");
//        crit.createAlias("wna.instId", "instId");
        //crit.createCriteria("wnrAllocationsForWnrId","wna");
        crit.add(Restrictions.eq("weightNote.id", wn_id));
        crit.add(Restrictions.eq("status", Constants.ALLOCATED));
        crit.add(Restrictions.eq("wna.instId", inst_id));
        return (ArrayList<WeightNoteReceipt>) crit.list();
    }

    public WeightNoteReceipt getWNRById(int id) {
        //return (WeightNoteReceipt) getHibernateTemplate().load(WeightNoteReceipt.class, id);
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.add(Restrictions.eq("id", id));
        return (WeightNoteReceipt) crit.uniqueResult();
    }

    public int updateWNR(WeightNoteReceipt wnr) {
        boolean isUpdated = false;
        if (wnr.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(wnr);
        if (isUpdated) {
            return 0;
        }
        return wnr.getId();
    }

    public String getNewWnrRef(String wn_ref) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.createAlias("weightNote", "wn");
        crit.add(Restrictions.eq("wn.refNumber", wn_ref));
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        if (cur_ref != null) {
            int new_ref = Integer.parseInt(cur_ref.substring(cur_ref.lastIndexOf("-") + 1)) + 1;
            return wn_ref + "-" + ((new_ref < 10) ? "0" + new_ref : new_ref);
        } else {
            return wn_ref + "-01";
        }
    }

    public int countNumberOfWnr(int wn_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.createAlias("weightNote", "wn");
        crit.add(Restrictions.eq("wn.id", wn_id));
        crit.setProjection(Projections.property("id"));
        return crit.list().size();
    }

    public Float getTotalByArea(int areaId, int clientid, int pledgeid, int gradeid) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("call getTotalStockTonsByAreaId(:cellid, :clientid, :pledgeid, :gradeid)");
        query.setParameter("cellid", areaId);
        query.setParameter("clientid", clientid);
        query.setParameter("pledgeid", pledgeid);
        query.setParameter("gradeid", gradeid);
        Double x = (Double) query.uniqueResult();
        if (x == null) {
            return null;
        } else {
            return new Float(x);
        }

    }

    public ArrayList<Map> getWeightNoteReceiptsByArea(int area, int companyId) {
        String sql = "SELECT wnr.ref_number, (wnr.gross_weight - wnr.tare_weight), gm.`name`\n"
                + "FROM weight_note_receipt wnr \n"
                + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "LEFT JOIN weight_note wn ON wnr.wn_id = wn.id\n"
                + "LEFT JOIN grade_master gm ON wn.grade_id = gm.id\n"
                + "LEFT JOIN delivery_instruction dinst ON (wn.inst_id = dinst.id AND wn.type = 'IM')\n"
                + "LEFT JOIN processing_instruction pinst ON (wn.inst_id = pinst.id AND wn.type = 'XP')\n"
                + "LEFT JOIN movement mvm ON wnr.movement_id = mvm.id\n"
                + "WHERE (wnr.area_id = :area) \n"
                + "AND ((dinst.client_id = :comid OR :comid = -1) OR (pinst.client_id = :comid OR :comid = -1) OR (mvm.pledge_id = :comid OR :comid = -1))\n"
                + "AND (wnr.`status` != 2) AND (wna.`status` != 1 OR wna.`status` IS NULL OR wnr.`status` = 4)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("area", area);
        query.setParameter("comid", companyId);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        ArrayList<Map> map_list = new ArrayList<>();
        if (obj_list != null || !obj_list.isEmpty()) {
            for (Object[] obj : obj_list) {
                Map m = new HashMap<>();
                m.put("ref", obj[0].toString());
                m.put("net", obj[1].toString());
                m.put("grade", obj[2].toString());
                map_list.add(m);
            }
        }
        return map_list;
    }

    public Number countNumberOf(int wn_id, String field_name) {
        String sql = "select sum(" + field_name + ") from weight_note_receipt where wn_id =:wn_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("wn_id", wn_id);
        return (Number) query.uniqueResult();
    }

    public Float countProcessingAllocated(int inst_id, String type) {
        String sql = "SELECT SUM(wnr.gross_weight - wnr.tare_weight) "
                + "FROM weight_note_receipt wnr LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id "
                + "WHERE wna.inst_id =:inst_id and wna.inst_type =:type";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("inst_id", inst_id);
        query.setParameter("type", type);
        Double temp = Double.parseDouble("0");
        if (query.uniqueResult() != null) {
            temp = (Double) query.uniqueResult();
        }
        return new Float(temp);
    }

    public ArrayList<WnrAllocation> getAllocationByOutWnr(int wnr_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WnrAllocation.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WnrAllocation.class);
        crit.add(Restrictions.eq("weightNoteReceiptByOutWnrId.id", wnr_id));
        Object check = crit.list();
        if (check != null) {
            return (ArrayList<WnrAllocation>) check;
        }
        return null;
    }

    public int saveWnrAllocation(WnrAllocation wnrA) {
        sessionFactory.getCurrentSession().saveOrUpdate(wnrA);
        return wnrA.getId();
    }

    public WeightNoteReceipt getWNRByRef(String ref_number) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNoteReceipt.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNoteReceipt.class);
        crit.add(Restrictions.eq("refNumber", ref_number));
        ArrayList check = (ArrayList) crit.list();
        if (check != null) {
            return (WeightNoteReceipt) check.get(0);
        }
        return null;
    }

    public JSONObject getWNRByMovId(int id) {
        String sql = "select "
                + " wnr.id as id, "
                + "wnr.ref_number as ref_number, "
                + "wnr.date as date,"
                + "wnr.packing_id as packing,"
                + "wnr.no_of_bags as bags,"
                + "pm.weight as kgPerBag,"
                + "wnr.gross_weight as gross,"
                + "wnr.tare_weight as tare,"
                + "wnr.gross_weight - wnr.tare_weight as net,"
                + "wnr.pallet_name as pallet,"
                + "wnr.pallet_weight as pallet_weight,"
                + "IF(wnr.options is null, '', wnr.options) "
                + "from wnr_movement wm "
                + "left join weight_note_receipt wnr on wm.wnr_id = wnr.id "
                + "left join packing_master pm on pm.id = wnr.packing_id "
                + "where wm.movement_id =:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id", IntegerType.INSTANCE)
                .addScalar("ref_number", StringType.INSTANCE)
                .addScalar("date", DateType.INSTANCE)
                .addScalar("packing", IntegerType.INSTANCE)
                .addScalar("bags", IntegerType.INSTANCE)
                .addScalar("kgPerBag", FloatType.INSTANCE)
                .addScalar("gross", FloatType.INSTANCE)
                .addScalar("tare", FloatType.INSTANCE)
                .addScalar("net", FloatType.INSTANCE)
                .addScalar("pallet_name", StringType.INSTANCE)
                .addScalar("pallet_weight", FloatType.INSTANCE)
                .addScalar("options", StringType.INSTANCE);
        query.setParameter("id", id);
        ArrayList<Object[]> objs = (ArrayList<Object[]>) query.list();
        Map map = new HashMap();
        int count = 1;

        ArrayList<Map> wnr_map_list = new ArrayList<>();
        for (Object[] obj : objs) {
            Map wnr_map = new HashMap();
            wnr_map.put("no", count);
            wnr_map.put("id", Common.getIntegerValue(obj[0]));
            wnr_map.put("ref_number", Common.getStringValue(obj[1]));
            wnr_map.put("date", Common.getDateValue(obj[2], Common.date_format));
            wnr_map.put("packing", Common.getIntegerValue(obj[3]));
            wnr_map.put("bags", Common.getIntegerValue(obj[4]));
            wnr_map.put("kgPerBag", Common.getFloatValue(obj[5]));
            wnr_map.put("gross", Common.getFloatValue(obj[6]));
            wnr_map.put("tare", Common.getFloatValue(obj[7]));
            wnr_map.put("net", Common.getFloatValue(obj[8]));
            wnr_map.put("pallet", Common.getStringValue(obj[9]));
            wnr_map.put("pallet_weight", Common.getFloatValue(obj[10]));
            wnr_map.put("options", Common.getFloatValue(obj[11]));
            wnr_map_list.add(wnr_map);
            count++;
        }
        map.put("total", getWnrTotalByMov(id));
        map.put("count", count);
        JSONArray json_array = new JSONArray(wnr_map_list);
        map.put("wnrs", json_array);
        JSONObject json = new JSONObject(map);
        return json;
    }

    public Map getWnrTotalByMov(int mov_id) {
        String sql = "select new map"
                + "("
                + "sum(wnr.palletWeight) as pallet, sum(wnr.packingMaster.weight) as total_bags_weight,"
                + "sum(wnr.noOfBags) as quantity, sum(wnr.grossWeight) as gross,"
                + "sum(wnr.tareWeight) as tare,"
                + "sum(wnr.grossWeight - wnr.tareWeight) as net"
                + ") "
                + "from WeightNoteReceipt as wnr left join wnr.movements as m where m.id = :id and wnr.status = 4";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", mov_id);
        return (Map) query.uniqueResult();
    }

    public boolean updateWnrInfo(int client, int pledge, int area, String wns, String username, boolean isUpdateById) {
        String updateByField = "id";
        if (!isUpdateById) {
            updateByField = "wn_id";
        }
        String sql = "update weight_note_receipt set ";
        HashMap element = new HashMap();
        HashMap map = new HashMap();
        map.put("type", "update");
        map.put("user", username);
        map.put("date", Common.getDateFromDatabase(new Date(), Common.date_format_ddMMyyyy_dash));
        int count = 0;
        boolean exc = false;
        if (client >= 0) {
            if (client == 0) {
                element.put("client", "none");
                sql += "client_id = null ";
            } else {
                element.put("client", client);
                sql += "client_id =:client ";
            }
            exc = true;
            count++;
        }

        if (pledge >= 0) {
            if (count > 0) {
                sql += ",";
            }
            if (pledge == 0) {
                element.put("pledge", "none");
                sql += "pledge_id = null ";
            } else {
                element.put("pledge", pledge);
                sql += "pledge_id =:pledge ";
            }
            exc = true;
            count++;
        }
        if (area > 0) {
            if (count > 0) {
                sql += ",";
            }
            element.put("area", area);
            sql += "area_id =:area ";
            exc = true;
            count++;
        }
        if (exc) {
            map.put("element", element);
            String log = (new JSONObject(map)).toString();
            if (count > 0) {
                sql += ",";
            }
            sql += " log = CONCAT(log,:log) ";
            sql += " where (";
            String[] wn = wns.split(",");
            for (int i = 0; i < wn.length; i++) {
                if (i == 0) {
                    sql += (updateByField + " =:wn" + i);
                } else {
                    sql += (" or " + updateByField + " =:wn" + i);
                }
            }
            sql += ") and status <> 2";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            if (client > 0) {
                query.setParameter("client", client);
            }
            if (pledge > 0) {
                query.setParameter("pledge", pledge);
            }
            if (area > 0) {
                query.setParameter("area", area);
            }
            for (int i = 0; i < wn.length; i++) {
                query.setParameter("wn" + i, wn[i]);
            }
            query.setParameter("log", log);
            return query.executeUpdate() > 0;
        } else {
            return false;
        }
    }

    public int saveWnaView(WnrAllocationView wnav) {
        if (wnav.getId() != null) {
            sessionFactory.getCurrentSession().update(wnav);
        } else {
            sessionFactory.getCurrentSession().save(wnav);
        }
        return wnav.getId();
    }

    //true : is weighted out
    //false: otherwise
    public boolean checkWnrIsWeightedOut(int wnr_id) {
        String sql = "select COUNT(`status`)\n"
                + "from wnr_allocation where `status` = 1 and wnr_id = " + wnr_id;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        int temp = Integer.parseInt(query.uniqueResult().toString());
        return temp != 0;
    }
}
