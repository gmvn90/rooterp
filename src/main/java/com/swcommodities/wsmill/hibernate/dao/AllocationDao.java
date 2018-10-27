/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.swcommodities.wsmill.hibernate.dto.WnrAllocation;
import com.swcommodities.wsmill.object.AllocationList;
import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author kiendn
 */
public class AllocationDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public AllocationDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    public long totalAfterFilter;

    public long getTotalAfterFilter() {
        return totalAfterFilter;
    }

    public void setTotalAfterFilter(long totalAfterFilter) {
        this.totalAfterFilter = totalAfterFilter;
    }

    public int updateAllocation(WnrAllocation allocation) {
        sessionFactory.getCurrentSession().saveOrUpdate(allocation);
        return allocation.getId();
    }

    public void delete(WnrAllocation allocation) {
        sessionFactory.getCurrentSession().delete(allocation);
    }

    public WnrAllocation findByWnrId(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WnrAllocation.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WnrAllocation.class);
        crit.add(Restrictions.eq("weightNoteReceiptByWnrId.id", id));
        return (WnrAllocation) crit.uniqueResult();
    }

    public WnrAllocation findByWnrRef(String ref_number, int inst_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WnrAllocation.class, "wna");
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WnrAllocation.class, "wna");
        crit.createAlias("weightNoteReceiptByWnrId", "wnr");
        crit.add(Restrictions.eq("wnr.refNumber", ref_number));
        crit.add(Restrictions.eq("wna.instId", inst_id));
        ArrayList<WnrAllocation> list = (ArrayList<WnrAllocation>) crit.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public int isAllocated(String ref_number, int inst_id, String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WnrAllocation.class, "wna");
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WnrAllocation.class, "wna");
        crit.createAlias("weightNoteReceiptByWnrId", "wnr");
        crit.add(Restrictions.eq("wnr.refNumber", ref_number));
        crit.add(Restrictions.eq("wna.instId", inst_id));
        crit.add(Restrictions.eq("wna.instType", type));
        crit.setProjection(Projections.property("wnr.id"));
        Integer result = (Integer) crit.uniqueResult();
        return result != null ? result : 0;
    }

    public ArrayList<AllocationList> searchGlobe(String searchTerm, String order, int start, int amount, String colName, int grade_id, String type, int status, int inst_id, String from_date, String to_date) throws ParseException {
        String table = "";
        switch (type) {
            case "P":
                table = "processing_instruction";
                break;
            case "E":
                table = "shipping_instruction";
                break;
        }
        StringBuilder sql = new StringBuilder("select inst.id as id,inst.ref_number as ref_number, gm.name as grade, IFNULL(pm.name, '') as packing, IFNULL(inst.quantity, 0) as quantity, "
                + "IFNULL(b.allocated,0) as allocated, "
                + "IFNULL(b.delivered,0) as delivered, "
                //+ "(select IFNULL(sum(IFNULL(wnr.gross_weight - wnr.tare_weight,0))/1000,0) from wnr_allocation wna left join weight_note_receipt wnr on wna.wnr_id = wnr.id where wna.inst_id = inst.id and wna.inst_type = :type) as allocated,"
                //+ "(select IFNULL(sum(IFNULL(wnr.gross_weight - wnr.tare_weight,0))/1000,0) from wnr_allocation wna left join weight_note_receipt wnr on wna.wnr_id = wnr.id where wna.inst_id = inst.id and wna.inst_type = :type and wna.status = 1) as delivered,"
                + "inst.from_date as from_date,"
                + "inst.to_date as to_date,inst.status,"
                + "gm.id "
                + "from " + table + " inst "
                + "left join grade_master gm on inst.grade_id = gm.id "
                + "left join packing_master pm on inst.packing_id = pm.id "
                + "left join ("
                + "select instruction.id as instruction_id, IFNULL(sum(IFNULL(wnr.gross_weight - wnr.tare_weight,0))/1000,0) as allocated,\n"
                + "	   IFNULL(sum(IF(wna.`status` <> 1, 0, IFNULL(wnr.gross_weight - wnr.tare_weight,0)))/1000,0) as delivered\n"
                + "from wnr_allocation wna left join weight_note_receipt wnr on wna.wnr_id = wnr.id "
                + "left join " + table + " instruction on wna.inst_id = instruction.id\n"
                + "where wna.inst_id = instruction.id and wna.inst_type = :type and instruction.`status` <> 2 group by instruction.id "
                + ") b on b.instruction_id = inst.id \n"
                + "where (gm.id = :grade_id or :grade_id = -1) "
                + "and (inst.status = :status or :status = -1) "
                + "and (inst.status <> 2)");
        sql.append(" and (inst.id = :inst_id or :inst_id = -1) ");

        StringBuilder getSizeSql = new StringBuilder("select inst.id as id,inst.ref_number, gm.`name` as gmName, IFNULL(pm.`name`, '') as pmName, IFNULL(inst.quantity,0) ,"
                + "inst.from_date,"
                + "inst.to_date,inst.status,"
                + "gm.id as gmId "
                + "from " + table + " inst "
                + "left join grade_master gm on inst.grade_id = gm.id "
                + "left join packing_master pm on inst.packing_id = pm.id "
                + "where (gm.id = :grade_id or :grade_id = -1) "
                + "and (inst.status = :status or :status = -1) "
                + "and (inst.status <> 2)");
        getSizeSql.append(" and (inst.id = :inst_id or :inst_id = -1) ");
        if (!searchTerm.equals("")) {
            sql.append("and (inst.ref_number like :searchTerm "
                    + "or DATE_FORMAT(inst.from_date,'%d-%b-%y') like :searchTerm "
                    + "or DATE_FORMAT(inst.to_date,'%d-%b-%y') like :searchTerm "
                    + "or gm.`name` like :searchTerm "
                    + "or inst.quantity like :searchTerm) ");

            getSizeSql.append("and (inst.ref_number like :searchTerm "
                    + "or DATE_FORMAT(inst.from_date,'%d-%b-%y') like :searchTerm "
                    + "or DATE_FORMAT(inst.to_date,'%d-%b-%y') like :searchTerm "
                    + "or gm.`name` like :searchTerm "
                    + "or inst.quantity like :searchTerm) ");
        }
        if (!from_date.equals("")) {
            sql.append("and (STR_TO_DATE(:from_date ,'%d-%c-%Y') <= inst.from_date)");
            getSizeSql.append("and (STR_TO_DATE(:from_date ,'%d-%c-%Y') <= inst.from_date)");
        }
        if (!to_date.equals("")) {
            sql.append("and (STR_TO_DATE(:to_date ,'%d-%c-%Y') >= inst.to_date)");
            getSizeSql.append("and (STR_TO_DATE(:to_date ,'%d-%c-%Y') >= inst.to_date)");
        }
        sql.append(" group by inst.id ");
        getSizeSql.append(" group by inst.id ");

        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ").append((order.equals("desc") ? "desc" : "asc"));
        }

        Query getSizeQuery = sessionFactory.getCurrentSession().createSQLQuery(getSizeSql.toString());
        if (!searchTerm.equals("")) {
            getSizeQuery.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        if (!from_date.equals("")) {
            getSizeQuery.setParameter("from_date", from_date);
        }
        if (!to_date.equals("")) {
            getSizeQuery.setParameter("to_date", to_date);
        }
        getSizeQuery.setParameter("grade_id", grade_id);
        //getSizeQuery.setParameter("type", type);
        getSizeQuery.setParameter("status", status);
        getSizeQuery.setParameter("inst_id", inst_id);

        this.setTotalAfterFilter(getSizeQuery.list().size());

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("id")
                .addScalar("ref_number")
                .addScalar("grade")
                .addScalar("packing")
                .addScalar("quantity")
                .addScalar("allocated")
                .addScalar("delivered")
                .addScalar("from_date")
                .addScalar("to_date")
                .addScalar("inst.status")
                .addScalar("gm.id");
        if (!searchTerm.equals("")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        if (!from_date.equals("")) {
            query.setParameter("from_date", from_date);
        }
        if (!to_date.equals("")) {
            query.setParameter("to_date", to_date);
        }
        query.setParameter("grade_id", grade_id);
        query.setParameter("type", type);
        query.setParameter("status", status);
        query.setParameter("inst_id", inst_id);
        //this.setTotalAfterFilter(query.list().size());
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        return convertToAllocationList(obj_list);
    }

    public ArrayList<AllocationList> convertToAllocationList(ArrayList<Object[]> obj_list) {
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<AllocationList> allocs = new ArrayList<>();
            for (Object[] obj : obj_list) {
                AllocationList alloc = new AllocationList();
                alloc.setId(Integer.parseInt(obj[0].toString()));
                alloc.setRefNumber(obj[1].toString());
                alloc.setGrade((obj[2] != null) ? obj[2].toString() : "");
                alloc.setPacking(obj[3] != null ? obj[3].toString() : "");
                alloc.setTotal(Float.parseFloat(obj[4].toString()));
                alloc.setAllocated(Float.parseFloat(obj[5].toString()));
                alloc.setDelivered(Float.parseFloat(obj[6].toString()));
                alloc.setFrom_date(Common.getDateFromDatabase((Date) obj[7], Common.date_format_a));
                alloc.setTo_date(Common.getDateFromDatabase((Date) obj[8], Common.date_format_a));
                allocs.add(alloc);
            }
            return allocs;
        }
        return null;
    }

    public long countRow(String type) {
        String table = "";
        switch (type) {
            case "P":
                table = "processing_instruction";
                break;
            case "E":
                table = "shipping_instruction";
                break;
        }
        String sql = "select count(id) from " + table;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
    }

    public Map countTotals(String searchTerm, int grade_id, String type, int status, int inst_id, String from_date, String to_date) throws ParseException {
        String sql = "";
        switch (type) {
            case "P":
                sql = "call getTotalAllocationListProcessing(:searchTerm,:grade,:status,:inst_id,:from_date,:to_date)";
                break;
            case "E":
                sql = "call getTotalAllocationListShipping(:searchTerm,:grade,:status,:inst_id,:from_date,:to_date)";
                break;
        }
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setParameter("grade", grade_id);
        query.setParameter("status", status);
        query.setParameter("inst_id", inst_id);
        query.setParameter("from_date", (!from_date.equals("")) ? from_date : "");
        query.setParameter("to_date", (!to_date.equals("")) ? to_date : "");
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("tons", obj[0]);
        m.put("allocated", obj[1]);
        m.put("delivered", obj[2]);
        return m;
    }

    public Map getInstInfo(int inst_id, String type) {
        String table = "";
        switch (type) {
            case "P":
                table = "processing_instruction";
                break;
            case "E":
                table = "shipping_instruction";
                break;
            case "M":
                table = "movement";
                break;
        }
        String sql = "select gm.name, inst.quantity, "
                + "(select IFNULL(sum(IFNULL(wnr.gross_weight - wnr.tare_weight,0))/1000,0) from wnr_allocation wna left join weight_note_receipt wnr on wna.wnr_id = wnr.id where wna.inst_id = inst.id and wna.inst_type = :type) as allocated,\n"
                + "inst.quantity - (select IFNULL(sum(IFNULL(wnr.gross_weight - wnr.tare_weight,0))/1000,0) from wnr_allocation wna left join weight_note_receipt wnr on wna.wnr_id = wnr.id where wna.inst_id = inst.id and wna.inst_type = :type) as pending "
                + "from " + table + " inst "
                + "left join grade_master gm on inst.grade_id = gm.id "
                + "where inst.id = :inst_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("type", type);
        query.setParameter("inst_id", inst_id);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("grade", obj[0]);
        m.put("quantity", obj[1]);
        m.put("allocated", obj[2]);
        m.put("pending", obj[3]);
        return m;
    }

    public Byte checkAllocateStatus(int wnr_id) {
        String sql = "select status from wnr_allocation where wnr_id =:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", wnr_id);
        return (Byte) query.uniqueResult();
    }
}
