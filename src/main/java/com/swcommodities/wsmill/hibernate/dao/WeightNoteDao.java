/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.ByteType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.json.JSONException;
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.object.Allocation;
import com.swcommodities.wsmill.object.PiReportAllocatedWn;
import com.swcommodities.wsmill.object.PiReportAllocatedWnr;
import com.swcommodities.wsmill.object.StockReportInprocess;
import com.swcommodities.wsmill.object.StockReportObj;
import com.swcommodities.wsmill.object.WeighingObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class WeightNoteDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public WeightNoteDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    private long totalAfterFilter;
    private long totalAvailable;
    private long totalAllocated;

    public long getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(long totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public long getTotalAllocated() {
        return totalAllocated;
    }

    public void setTotalAllocated(long totalAllocated) {
        this.totalAllocated = totalAllocated;
    }

    public long getTotalAfterFilter() {
        return totalAfterFilter;
    }

    public void setTotalAfterFilter(long totalAfterFilter) {
        this.totalAfterFilter = totalAfterFilter;
    }

    public String getNewWnRef(String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("type", type));
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        String prefix = "WN-" + type + "-";
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, prefix) : Common
                .getNewRefNumber(null, prefix);
    }

    public int updateWN(WeightNote wn) {
        boolean isUpdated = false;
        if (wn.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(wn);
        if (isUpdated) {
            return 0;
        }
        return wn.getId();
    }

    public WeightNote getWnById(int id) {
        // DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        // crit.add(Restrictions.eq("id", id));
        // return (WeightNote)
        // getHibernateTemplate().findByCriteria(crit).get(0);
        String sql = "from WeightNote as wn left outer join fetch wn.weightNoteReceipts as weightNoteReceipts where wn.id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", id);
        return (WeightNote) query.uniqueResult();
    }

    public WeightNote getWnByQrId(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("qualityReport.id", id));
        return (WeightNote) crit.uniqueResult();
    }

    public ArrayList<WeightNote> getWeightNoteFromInst(int id, int type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("instId", id));
        crit.add(Restrictions.ne("status", Constants.DELETED));
        switch (type) {
            case 1:// di
                crit.add(Restrictions.eq("type", "IM"));
                break;
            case 2:// ip or xp
                Criterion criterion = Restrictions.or(Restrictions.eq("type", "IP"),
                        Restrictions.eq("type", "XP"));
                crit.add(criterion);
                break;
            case 4:// EX
                crit.add(Restrictions.eq("type", "EX"));
                break;
            default:
                crit.add(Restrictions.eq("type", ""));
        }
        return (ArrayList<WeightNote>) crit.list();
    }

    public ArrayList<Object[]> getWeightNoteRefListByDI(String searchString, int ins_id,
            int grade_id, byte status, int supplier_id, String type, int from) {
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery(
                        "call GetWnRefListByDI(:search,:ins_id,:grade_id,:status,:supplier_id,:wnType,:fromRow)");
        query.setParameter("search", "%" + searchString + "%");
        query.setParameter("ins_id", ins_id);
        query.setParameter("grade_id", grade_id);
        query.setParameter("status", status);
        query.setParameter("supplier_id", supplier_id);
        query.setParameter("wnType", type);
        query.setParameter("fromRow", from);
        return (ArrayList<Object[]>) query.list();
    }

    public ArrayList<Object[]> getWeightNoteRefListByPI(String searchString, int ins_id,
            int grade_id, byte status, int supplier_id, String type) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "call GetWnRefListByPI(:search,:ins_id,:grade_id,:status,:supplier_id,:wnType)");
        query.setParameter("search", "%" + searchString + "%");
        query.setParameter("ins_id", ins_id);
        query.setParameter("grade_id", grade_id);
        query.setParameter("status", status);
        query.setParameter("supplier_id", supplier_id);
        query.setParameter("wnType", type);
        return (ArrayList<Object[]>) query.list();
    }

    public ArrayList<Object[]> getWeightNoteRefListBySI(String searchString, int ins_id,
            int grade_id, byte status, int supplier_id, String type) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "call GetWnRefListBySI(:search,:ins_id,:grade_id,:status,:supplier_id,:wnType)");
        query.setParameter("search", "%" + searchString + "%");
        query.setParameter("ins_id", ins_id);
        query.setParameter("grade_id", grade_id);
        query.setParameter("status", status);
        query.setParameter("supplier_id", supplier_id);
        query.setParameter("wnType", type);
        return (ArrayList<Object[]>) query.list();
    }

    public ArrayList<Object[]> getMovementRefList(String searchString, int grade_id, byte status,
            int client_id, int pledge_id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "call getMovementRefList(:search,:grade_id,:status,:client_id,:pledge_id)");
        query.setParameter("search", "%" + searchString + "%");
        query.setParameter("grade_id", grade_id);
        query.setParameter("status", status);
        query.setParameter("client_id", client_id);
        query.setParameter("pledge_id", pledge_id);
        return (ArrayList<Object[]>) query.list();
    }

    public ArrayList<WeightNote> getWeightNotesByArea(int area) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("warehouseCell.id", area));
        return (ArrayList<WeightNote>) crit.list();
    }

    public ArrayList<WeightNote> getWeightNotesByWrcId(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("wrcId", id));
        crit.add(Restrictions.ne("status", Constants.DELETED));
        return (ArrayList<WeightNote>) crit.list();
    }

    public Map getWnrTotal(int wn_id) {
        String sql = "select new map"
                + "("
                + "sum(wnr.packingMaster.weight) as total_bags_weight,"
                + "sum(wnr.noOfBags) as quantity, sum(wnr.grossWeight) as gross,"
                + "sum(wnr.tareWeight) as tare,"
                + "sum(wnr.grossWeight - wnr.tareWeight) as net"
                + ") "
                + "from WeightNote as wn inner join wn.weightNoteReceipts as wnr where wn.id = :id and wnr.status <> 2";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", wn_id);
        return (Map) query.uniqueResult();
    }

    public ArrayList<Allocation> searchAvailableWN(String searchTerm, String order, int start,
            int amount, String colName, int grade) {
        StringBuilder sql = new StringBuilder("select wn.ref_number,")
                .append("sum(ifnull(wnr.gross_weight - wnr.tare_weight,0)/1000) as stock_tons,")
                .append("wn.`created_date` as stock_date, gm.name as grade,")
                .append("qr.above_sc20,qr.sc20,qr.sc19,qr.sc18,qr.sc17,qr.sc16,qr.sc15,qr.sc14,qr.sc13,qr.sc12,qr.below_sc12,qr.black,qr.broken,qr.black_broken,qr.brown,qr.moisture,qr.old_crop,qr.excelsa,qr.foreign_matter,qr.worm,qr.moldy ")
                .append(",wn.id,gm.id,wn.type, wn.status ").append("from weight_note wn ")
                .append("left join quality_report qr ON wn.qr_id = qr.id ")
                .append("left join grade_master gm on wn.grade_id = gm.id "
                        + " left join weight_note_receipt wnr on wnr.wn_id = wn.id\n"
                        + " where wnr.status <> 2 and wnr.status <> 3 and wn.status <> 2 and (wn.type = 'IM' or wn.type = 'XP') and (gm.id = :grade or :grade = -1) ");
        StringBuilder sqlGetSize = new StringBuilder("select wn.ref_number,")
                .append("wn.`created_date`, gm.name,")
                .append("wn.id as wnId,gm.id,wn.type, wn.status ").append("from weight_note wn ")
                .append("left join grade_master gm on wn.grade_id = gm.id ")
                .append("where (gm.id = :grade or :grade = -1) and (wn.type = \"IM\" or wn.type = \"XP\") and wn.status <> 2 ");

        if (!searchTerm.equals("")) {
            sql.append(" and (wn.ref_number like :searchTerm")
                    .append(" or DATE_FORMAT(wn.`created_date`,'%d-%b-%y') like :searchTerm")
                    .append(" or gm.name like :searchTerm) ");
            sqlGetSize.append(" and (wn.ref_number like :searchTerm")
                    .append(" or DATE_FORMAT(wn.`created_date`,'%d-%b-%y') like :searchTerm")
                    .append(" or gm.name like :searchTerm) ");
        }
        sql.append("group by wn.id ");
        sqlGetSize.append("group by wn.id ");
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ")
                    .append((order.equals("desc") ? "desc" : "asc"));
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn.ref_number")
                .addScalar("stock_tons").addScalar("stock_date").addScalar("grade")
                .addScalar("qr.above_sc20").addScalar("qr.sc20").addScalar("qr.sc19")
                .addScalar("qr.sc18").addScalar("qr.sc17").addScalar("qr.sc16")
                .addScalar("qr.sc15").addScalar("qr.sc14").addScalar("qr.sc13")
                .addScalar("qr.sc12").addScalar("qr.below_sc12").addScalar("qr.black")
                .addScalar("qr.broken").addScalar("qr.black_broken").addScalar("qr.brown")
                .addScalar("qr.moisture").addScalar("qr.old_crop").addScalar("qr.excelsa")
                .addScalar("qr.foreign_matter").addScalar("qr.worm").addScalar("qr.moldy")
                .addScalar("wn.id");
        Query queryGetSize = sessionFactory.getCurrentSession().createSQLQuery(sqlGetSize.toString());

        if (!searchTerm.equals("")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            queryGetSize.setParameter("searchTerm", "%" + searchTerm + "%");

        }
        query.setParameter("grade", grade);
        queryGetSize.setParameter("grade", grade);
        this.setTotalAvailable(queryGetSize.list().size());
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        // convert them to allocation object

        return convertToAllocationObject(obj_list);
    }

    public Map countTotalAvailableWn(String searchTerm, int grade) {
        String sql = "call total_available_wn(:searchTerm,:grade);";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setParameter("grade", grade);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap();
        m.put("stocks", obj[0]);
        m.put("above_sc20", obj[1]);
        m.put("sc20", obj[2]);
        m.put("sc19", obj[3]);
        m.put("sc18", obj[4]);
        m.put("sc17", obj[5]);
        m.put("sc16", obj[6]);
        m.put("sc15", obj[7]);
        m.put("sc14", obj[8]);
        m.put("sc13", obj[9]);
        m.put("sc12", obj[10]);
        m.put("below_sc12", obj[11]);
        m.put("black", obj[12]);
        m.put("broken", obj[13]);
        m.put("black_broken", obj[14]);
        m.put("brown", obj[15]);
        m.put("moisture", obj[16]);
        m.put("old_crop", obj[17]);
        m.put("excelsa", obj[18]);
        m.put("foreign_matter", obj[19]);
        m.put("worm", obj[20]);
        m.put("moldy", obj[21]);
        return m;
    }

    public Map countTotalAllocatedWn(String searchTerm, int inst_id, String type) {
        String sql = "call total_allocated_wn(:searchTerm,:instId,:type);";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setParameter("instId", inst_id);
        query.setParameter("type", type);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap();
        m.put("stocks", obj[0]);
        m.put("above_sc20", obj[1]);
        m.put("sc20", obj[2]);
        m.put("sc19", obj[3]);
        m.put("sc18", obj[4]);
        m.put("sc17", obj[5]);
        m.put("sc16", obj[6]);
        m.put("sc15", obj[7]);
        m.put("sc14", obj[8]);
        m.put("sc13", obj[9]);
        m.put("sc12", obj[10]);
        m.put("below_sc12", obj[11]);
        m.put("black", obj[12]);
        m.put("broken", obj[13]);
        m.put("black_broken", obj[14]);
        m.put("brown", obj[15]);
        m.put("moisture", obj[16]);
        m.put("old_crop", obj[17]);
        m.put("excelsa", obj[18]);
        m.put("foreign_matter", obj[19]);
        m.put("worm", obj[20]);
        m.put("moldy", obj[21]);
        return m;
    }

    public ArrayList<Allocation> searchAllocatedWn(String searchTerm, String order, int start,
            int amount, String colName, int inst_id, String type) {
        StringBuilder sql = new StringBuilder("select wn.ref_number,")
                .append("(sum(wnr.gross_weight - wnr.tare_weight)) as allocated_tons,")
                .append("wna.allocated_date allocated_date, gm.name as grade,")
                .append("qr.above_sc20,qr.sc20,qr.sc19,qr.sc18,qr.sc17,qr.sc16,qr.sc15,qr.sc14,qr.sc13,qr.sc12,qr.below_sc12,qr.black,qr.broken,qr.black_broken,qr.brown,qr.moisture,qr.old_crop,qr.excelsa,qr.foreign_matter,qr.worm,qr.moldy,")
                .append("wn.id,gm.id,wn.type,wna.inst_id,wna.inst_type")
                .append(" from wnr_allocation wna ")
                .append("left join weight_note wn on wna.wn_id = wn.id "
                        + " left join weight_note_receipt wnr on wnr.wn_id = wn.id and wna.wnr_id = wnr.id ")
                .append("left join quality_report qr on qr.id = wn.qr_id ")
                .append("left join grade_master gm on gm.id = wn.grade_id ")
                .append(" where wna.inst_id = :inst_id and wna.inst_type =:type ");
        StringBuilder sqlGetSize = new StringBuilder("select wn.ref_number,")
                .append("wna.allocated_date, gm.name,")
                .append("wn.id as wnId,gm.id,wn.type,wna.inst_id,wna.inst_type")
                .append(" from wnr_allocation wna ")
                .append("left join weight_note wn on wna.wn_id = wn.id ")
                .append("left join quality_report qr on qr.id = wn.qr_id ")
                .append("left join grade_master gm on gm.id = wn.grade_id ")
                .append(" where wna.inst_id = :inst_id and wna.inst_type =:type ");
        if (!searchTerm.equals("")) {
            sql.append(" and (wn.ref_number like :searchTerm")
                    .append(" or DATE_FORMAT(wna.allocated_date,'%d-%b-%y') like :searchTerm")
                    .append(" or gm.name like :searchTerm) ");
            sqlGetSize.append(" and (wn.ref_number like :searchTerm")
                    .append(" or DATE_FORMAT(wna.allocated_date,'%d-%b-%y') like :searchTerm")
                    .append(" or gm.name like :searchTerm) ");
        }
        sql.append(" group by wna.inst_id,wn.id");
        sqlGetSize.append(" group by wna.inst_id,wn.id");
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ")
                    .append((order.equals("desc") ? "desc" : "asc"));
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn.ref_number")
                .addScalar("allocated_tons").addScalar("allocated_date").addScalar("grade")
                .addScalar("qr.above_sc20").addScalar("qr.sc20").addScalar("qr.sc19")
                .addScalar("qr.sc18").addScalar("qr.sc17").addScalar("qr.sc16")
                .addScalar("qr.sc15").addScalar("qr.sc14").addScalar("qr.sc13")
                .addScalar("qr.sc12").addScalar("qr.below_sc12").addScalar("qr.black")
                .addScalar("qr.broken").addScalar("qr.black_broken").addScalar("qr.brown")
                .addScalar("qr.moisture").addScalar("qr.old_crop").addScalar("qr.excelsa")
                .addScalar("qr.foreign_matter").addScalar("qr.worm").addScalar("qr.moldy")
                .addScalar("wn.id").addScalar("gm.id");
        Query queryGetSize = sessionFactory.getCurrentSession().createSQLQuery(sqlGetSize.toString());

        if (!searchTerm.equals("")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            queryGetSize.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        query.setParameter("inst_id", inst_id);
        query.setParameter("type", type);
        queryGetSize.setParameter("inst_id", inst_id);
        queryGetSize.setParameter("type", type);
        this.setTotalAllocated(queryGetSize.list().size());
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        // convert them to allocation object

        return convertToAllocationObject(obj_list);
    }

    public ArrayList<Allocation> convertToAllocationObject(ArrayList<Object[]> obj_list) {
        // make sure the length obj is 26 [0-->25]
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<Allocation> allocations = new ArrayList<>();
            for (Object[] obj : obj_list) {
                Allocation allocation = new Allocation();
                allocation.setWn_ref(obj[0].toString());
                allocation.setStock_tons(Float.parseFloat(obj[1].toString()));
                allocation.setStock_date((Date) obj[2]);
                allocation.setGrade(obj[3].toString());
                allocation.setAboveSc20((obj[4] != null) ? Float.parseFloat(obj[4].toString()) : 0);
                allocation.setSc20((obj[5] != null) ? Float.parseFloat(obj[5].toString()) : 0);
                allocation.setSc19((obj[5] != null) ? Float.parseFloat(obj[6].toString()) : 0);
                allocation.setSc18((obj[7] != null) ? Float.parseFloat(obj[7].toString()) : 0);
                allocation.setSc17((obj[8] != null) ? Float.parseFloat(obj[8].toString()) : 0);
                allocation.setSc16((obj[9] != null) ? Float.parseFloat(obj[9].toString()) : 0);
                allocation.setSc15((obj[10] != null) ? Float.parseFloat(obj[10].toString()) : 0);
                allocation.setSc14((obj[11] != null) ? Float.parseFloat(obj[11].toString()) : 0);
                allocation.setSc13((obj[12] != null) ? Float.parseFloat(obj[12].toString()) : 0);
                allocation.setSc12((obj[13] != null) ? Float.parseFloat(obj[13].toString()) : 0);
                allocation.setBelowSc12((obj[14] != null) ? Float.parseFloat(obj[14].toString())
                        : 0);
                allocation.setBlack((obj[15] != null) ? Float.parseFloat(obj[15].toString()) : 0);
                allocation.setBroken((obj[16] != null) ? Float.parseFloat(obj[16].toString()) : 0);
                allocation.setBlackBroken((obj[17] != null) ? Float.parseFloat(obj[17].toString())
                        : 0);
                allocation.setBrown((obj[18] != null) ? Float.parseFloat(obj[18].toString()) : 0);
                allocation
                        .setMoisture((obj[19] != null) ? Float.parseFloat(obj[19].toString()) : 0);
                allocation.setOldCrop((obj[20] != null) ? Float.parseFloat(obj[20].toString()) : 0);
                allocation.setExcelsa((obj[21] != null) ? Float.parseFloat(obj[21].toString()) : 0);
                allocation
                        .setForeignMatter((obj[22] != null) ? Float.parseFloat(obj[22].toString())
                                        : 0);
                allocation.setWorm((obj[23] != null) ? Float.parseFloat(obj[23].toString()) : 0);
                allocation.setMoldy((obj[24] != null) ? Float.parseFloat(obj[24].toString()) : 0);
                allocation.setWn_id(Integer.parseInt(obj[25].toString()));
                allocations.add(allocation);
            }
            return allocations;
        }
        return null;
    }

    public long countRow() {
        String sql = "select count(id) from weight_note";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
    }

    public ArrayList<Object[]> getAvailableWeightNoteForWR(int inst_id, String inst_type) {
        String inst = "";
        switch (inst_type) {
            case "IM":
                inst = "delivery_instruction";
                break;
            case "EX":
                inst = "shipping_instruction";
                break;
        }
        String sql = "select distinct wn.id, wn.ref_number as refNumber,wn.status, qr.ref_number as quality_ref, IFNULL(qr.status,0) as qr_status, gm.name, wn.wrc_id, "
                + "IFNULL((sum(wnr.gross_weight) - sum(wnr.tare_weight)),0) as net_weight,"
                + "IFNULL(sum(wnr.no_of_bags),0) as bags "
                + " from weight_note wn "
                + "left join weight_note_receipt wnr on wnr.wn_id = wn.id "
                + "left join "
                + inst
                + " inst on inst.id = wn.inst_id "
                + "left join quality_report qr on qr.id = wn.qr_id "
                + "left join grade_master gm on gm.id = wn.grade_id "
                + "where wn.wrc_id is NULL and wn.type =:inst_type and wn.inst_id = :inst_id and wnr.status <> 2 "
                + "group by wn.ref_number";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("wn.id", IntegerType.INSTANCE)
                .addScalar("refNumber").addScalar("wn.status", ByteType.INSTANCE)
                .addScalar("quality_ref").addScalar("qr_status", ByteType.INSTANCE)
                .addScalar("gm.name").addScalar("wn.wrc_id", IntegerType.INSTANCE)
                .addScalar("net_weight", FloatType.INSTANCE).addScalar("bags", IntegerType.INSTANCE);

        query.setParameter("inst_type", inst_type);
        query.setParameter("inst_id", inst_id);

        return (ArrayList<Object[]>) query.list();
    }

    public ArrayList<Object[]> getListInstructionByType(String inst_type) {
        String inst = "";
        switch (inst_type) {
            case "IM":
                inst = "delivery_instruction";
                break;
            case "IP":
                inst = "processing_instruction";
                break;
            case "XP":
                inst = "processing_instruction";
                break;
            case "EX":
                inst = "shipping_instruction";
                break;
        }

        String sql = "SELECT id, ref_number\n" + "FROM " + inst + "\n" + "WHERE `status` <> 2\n"
                + "ORDER BY ref_number";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id").addScalar("ref_number");

        return (ArrayList<Object[]>) query.list();

    }

    public ArrayList<WeighingObj> searchWeightNote(String searchTerm, String order, int start,
            int amount, String colName, int grade, int inst_id, String type, Byte status) {
        String inst = "";
        switch (type) {
            case "IM":
                inst = "delivery_instruction";
                break;
            case "IP":
                inst = "processing_instruction";
                break;
            case "XP":
                inst = "processing_instruction";
                break;
            case "EX":
                inst = "shipping_instruction";
                break;
        }
        StringBuilder sql = new StringBuilder(
                "select qr.ref_number as qr_ref, gm.id, wn.type, wn.inst_id, wn.`status`, wn.id as wn_id, wn.ref_number as wn_ref, inst.ref_number as inst_ref, gm.`name` as grade_name, ")
                .append("		wn.created_date as wn_date, pm.`name` as packing_name ")
                //				.append(",(SELECT IFNULL(sum(wnr.no_of_bags),0)")
                //				.append(" FROM weight_note wn2 LEFT JOIN weight_note_receipt wnr on wnr.wn_id = wn2.id")
                //				.append(" WHERE wn2.id = wn.id AND wnr.status <> 2) as num,")
                //				.append("		(SELECT IFNULL(sum(wnr.gross_weight),0)")
                //				.append("		FROM weight_note wn2 LEFT JOIN weight_note_receipt wnr on wnr.wn_id = wn2.id")
                //				.append("		WHERE wn2.id = wn.id AND wnr.status <> 2) as gross_weight,")
                //				.append("		(SELECT IFNULL(sum(wnr.tare_weight),0)")
                //				.append("		FROM weight_note wn2 LEFT JOIN weight_note_receipt wnr on wnr.wn_id = wn2.id")
                //				.append("		WHERE wn2.id = wn.id AND wnr.status <> 2) as tare_weight,")
                //				.append("		((SELECT IFNULL(sum(wnr.gross_weight),0)")
                //				.append("		FROM weight_note wn2 LEFT JOIN weight_note_receipt wnr on wnr.wn_id = wn2.id")
                //				.append("		WHERE wn2.id = wn.id and wnr.status <> 2) - (SELECT IFNULL(sum(wnr.tare_weight),0)")
                //				.append("								FROM weight_note wn2 LEFT JOIN weight_note_receipt wnr on wnr.wn_id = wn2.id")
                //				.append("								WHERE wn2.id = wn.id AND wnr.status <> 2)) as net_weight")
                .append(" from weight_note wn")
                .append(" left join grade_master gm on wn.grade_id = gm.id")
                .append(" left join " + inst + " inst on wn.inst_id = inst.id")
                .append(" left join quality_report qr on wn.qr_id = qr.id")
                .append(" left join packing_master pm on wn.packing_id = pm.id")
                .append(" where wn.`status` != 2 AND wn.type = :type and (wn.inst_id = :inst_id or :inst_id = -1) and (gm.id = :grade or :grade = -1) and (wn.`status`= :status or :status = -1)")
                .append(" GROUP BY wn.id")
                .append(" having wn.ref_number like :searchTerm")
                .append(" or DATE_FORMAT(wn.created_date,'%d-%b-%y') like :searchTerm")
                .append(" or gm.`name` like :searchTerm")
                .append(" or pm.`name` like :searchTerm")
                .append(" or qr.ref_number like :searchTerm")
                .append(" or inst.ref_number like :searchTerm");
//				.append(" or (SELECT sum(wnr.gross_weight - wnr.tare_weight)")
//				.append("	FROM weight_note wn2 LEFT JOIN weight_note_receipt wnr on wnr.wn_id = wn2.id")
//				.append("	WHERE wn2.id = wn.id) like :searchTerm");
        String column = !colName.equals("0") ? colName : "id";
        // if (!colName.equals("0")) {
        sql.append(" order by ").append(column).append(" ")
                .append((order.equals("desc") ? "desc" : "asc"));
        // }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn_id")
                .addScalar("wn_ref").addScalar("qr_ref").addScalar("inst_ref")
                .addScalar("grade_name").addScalar("wn_date").addScalar("packing_name");
//				.addScalar("num").addScalar("gross_weight").addScalar("tare_weight")
//				.addScalar("net_weight");

        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setParameter("type", type);
        query.setParameter("grade", grade);
        query.setParameter("inst_id", inst_id);
        query.setParameter("status", status);
        this.setTotalAfterFilter(query.list().size());
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        // convert them to allocation object

        return convertToWeighingObject(obj_list);
    }

    public ArrayList<WeighingObj> convertToWeighingObject(ArrayList<Object[]> obj_list) {
        // make sure the length obj is 26 [0-->25]
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<WeighingObj> wos = new ArrayList<>();
            for (Object[] obj : obj_list) {
                try {
                    WeighingObj wo = new WeighingObj();
                    wo.setWn_id(Integer.parseInt(obj[0].toString()));
                    wo.setWn_ref(obj[1].toString());
                    wo.setQr_ref(obj[2].toString());
                    wo.setInst_ref(obj[3].toString());
                    wo.setGrade_name(Common.getStringValue(obj[4]));
                    wo.setWn_date((Date) obj[5]);
                    wo.setPacking_name(obj[6].toString());
//					wo.setNum(Integer.parseInt(obj[7].toString()));
//					wo.setGross_weight(Float.parseFloat(obj[8].toString()));
//					wo.setTare_weight(Float.parseFloat(obj[9].toString()));
//					wo.setNet_weight(Float.parseFloat(obj[10].toString()));
                    wos.add(wo);
                } catch (Exception e) {
                    System.out.println("error on converting weighing object");
                }
            }
            return wos;
        }
        return null;
    }

    public HashMap getWNSum(int id) {
        HashMap map = new HashMap();
        String sql = "call getWNSum(:id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("num", IntegerType.INSTANCE)
                .addScalar("gross_weight", FloatType.INSTANCE)
                .addScalar("tare_weight", FloatType.INSTANCE)
                .addScalar("net_weight", FloatType.INSTANCE)
                .setParameter("id", id);
        Object[] object = (Object[]) (query.uniqueResult());
        if (object != null && object.length > 0) {
            map.put("num", object[0]);
            map.put("gross_weight", object[1]);
            map.put("tare_weight", object[2]);
            map.put("net_weight", object[3]);
        }
        return map;
    }

    public Map countTotals(String searchTerm, int grade, int inst_id, String type, Byte status) {
        String sql = "call getTotalWeighing(:grade,:inst_id,:type,:istatus,:searchTerm)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("grade", grade);
        query.setParameter("inst_id", inst_id);
        query.setParameter("type", type);
        query.setParameter("istatus", status);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("num", obj[0]);
        m.put("gross_weight", obj[1]);
        m.put("tare_weight", obj[2]);
        m.put("net_weight", obj[3]);
        return m;
    }

    public boolean checkWnDeletable(int id) {
        String sql = "call checkWnDeletable(:wnid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("wnid", id);
        BigInteger countResult = (BigInteger) query.uniqueResult();
        return countResult.intValue() <= 0;
    }

    public ArrayList<GradeMaster> getAllGradesByType(String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class).setResultTransformer(
        //        Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.createAlias("gradeMaster", "gm");
        crit.add(Restrictions.lt("status", Constants.DELETED));
        if (type.equals("STORAGE")) {
            crit.add(Restrictions.or(Restrictions.eq("type", "IM"), Restrictions.eq("type", "XP")));
        } else {
            crit.add(Restrictions.eq("type", type));
        }

        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("gm.id"), "id")
                .add(Projections.property("gm.name"), "name");
        crit.addOrder(Order.asc("name"));
        crit.setProjection(Projections.distinct(projList));
        crit.setResultTransformer(Transformers.aliasToBean(GradeMaster.class));
        return (ArrayList<GradeMaster>) crit.list();
    }

    public ArrayList<HashMap> getGradeInStock2(int map_id, int client_id, int pledge_id) {
        String sql = "SELECT DISTINCT(x.id) as id, x.name as name\n"
                + "FROM\n"
                + "(SELECT DISTINCT(wnr.grade_id) as id, gm.`name` as name, wnr.status, wna.status as stt2, wnr.id as wnid\n"
                + "FROM grade_master gm\n"
                + "LEFT JOIN weight_note_receipt wnr ON wnr.grade_id = gm.id\n"
                + "LEFT JOIN warehouse_cell wc ON wnr.area_id = wc.id\n"
                + "LEFT JOIN weight_note wn ON wnr.wn_id = wn.id\n"
                + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "WHERE wnr.status <> 2 and (wn.type = 'IM' OR wn.type = 'XP')\n"
                + "and (wnr.client_id = :client_id or :client_id = -1)\n"
                + "and (wnr.pledge_id = :pledge_id or :pledge_id = -1)\n"
                + "and (wc.warehouse_map_id = :map_id or :map_id = -1)\n"
                + "HAVING id IS NOT NULL AND (wnr.status = 3 and wna.status <> 1) or wna.status is null \n"
                + "ORDER BY gm.`name`) x";
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

    //For import report in detail
    public ArrayList<HashMap> getGradeImportExportInStockOnSpecificDate(String from, String to, String type) {
        String sql = "SELECT DISTINCT(wnr.grade_id) as id, gm.`name` as name\n"
                + "FROM grade_master gm\n"
                + "LEFT JOIN weight_note_receipt wnr ON wnr.grade_id = gm.id\n"
                + "LEFT JOIN weight_note wn ON wn.id = wnr.wn_id\n"
                + "WHERE wnr.status <> 2 and wn.type = '" + type + "'\n"
                + "and STR_TO_DATE(wnr.date,'%Y-%m-%d') >= STR_TO_DATE(:period_from,'%Y-%m-%d')\n"
                + "and STR_TO_DATE(wnr.date,'%Y-%m-%d') <= STR_TO_DATE(:period_to,'%Y-%m-%d')\n"
                + "ORDER BY gm.`name`";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id")
                .addScalar("name");
        query.setParameter("period_from", from);
        query.setParameter("period_to", to);
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

    public ArrayList<GradeMaster> getAllGrades() {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class).setResultTransformer(
        //        Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.createAlias("gradeMaster", "gm");
        crit.add(Restrictions.lt("status", Constants.DELETED));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("gm.id"), "id")
                .add(Projections.property("gm.name"), "name");
        crit.setProjection(Projections.distinct(projList));
        crit.setResultTransformer(Transformers.aliasToBean(GradeMaster.class));
        return (ArrayList<GradeMaster>) crit.list();
    }

    public ArrayList<Integer> getAllIdByType(String type, int si_id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(WeightNote.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(WeightNote.class);
        crit.add(Restrictions.eq("type", type));
        crit.add(Restrictions.eq("instId", si_id));
        crit.add(Restrictions.lt("status", Constants.DELETED));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("id"), "id");
        crit.setProjection(Projections.distinct(projList));
        return (ArrayList<Integer>) crit.list();
    }

    public JSONObject getContainerByWnId(int id) throws JSONException {
        JSONObject jContainer = new JSONObject();
        String sql = "SELECT IFNULL(wn.container_no, ' '), IFNULL(wn.ico_no, ' '), IFNULL(wn.seal_no, ' '), IFNULL(SUM(wnr.gross_weight - wnr.tare_weight)/1000,0) as net, IFNULL(SUM(wnr.no_of_bags),0) as bag, IFNULL(SUM(wnr.tare_weight)/1000,0) as tare, IFNULL(SUM(wnr.gross_weight)/1000,0) as gross\n"
                + "FROM weight_note wn\n"
                + "LEFT JOIN weight_note_receipt wnr ON wn.id = wnr.wn_id\n"
                + "WHERE wn.id = :wnid and wn.`status` != 2 and wnr.`status` != 2";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("wnid", id);
        Object[] obj = (Object[]) query.uniqueResult();
        jContainer.put("con", obj[0]);
        jContainer.put("icon", obj[1]);
        jContainer.put("seal", obj[2]);
        jContainer.put("net", obj[3]);
        jContainer.put("bag", obj[4]);
        jContainer.put("tare", obj[5]);
        jContainer.put("gross", obj[6]);
        return jContainer;
    }

    public ArrayList<StockReportObj> getReportObject(int grade_id, String date, int map_id,
            int client_id) {
        StringBuilder sql1 = new StringBuilder("select\n"
                + "sh.id as id,\n" // 0
                + "sh.wn_ref as wn_ref,\n"
                + "sh.allocation_ref as inst_ref,\n" // 2
                + "sh.tons as tons,\n"
                + "sh.num as num,\n"// 4
                + "pm.`name` as packing,\n"
                + "sh.in_date as in_date,\n"// 6
                + "sh.area as area,\n"
                + "sh.blacks as black,\n"// 8
                + "sh.brown as brown,\n"
                + "sh.fm as fm,\n"// 10
                + "sh.broken as broken,\n"
                + "sh.moist as moist,\n"// 12
                + "sh.ocrop as ocrop,\n"
                + "sh.moldy as moldy,\n"// 14
                + "sh.cup as cup,\n"
                + "sh.asc20 as asc20,\n"// 16
                + "sh.sc20 as sc20,\n"
                + "sh.sc19 as sc19,\n"// 18
                + "sh.sc18 as sc18,\n"
                + "sh.sc17 as sc17,\n"// 20
                + "sh.sc16 as sc16,\n"
                + "sh.sc15 as sc15,\n"// 22
                + "sh.sc14 as sc14,\n"
                + "sh.sc13 as sc13,\n"// 24
                + "sh.sc12 as sc12,\n"
                + "sh.bsc12 as bsc12,\n"// 26
                + "cm.`name` as client\n" + "from stock_historical sh\n"
                + "left join packing_master pm on sh.packing_id = pm.id\n"
                + "left join grade_master gm on sh.grade_id = gm.id\n"
                + "left join company_master cm on sh.client_id = cm.id\n"
                + "where sh.grade_id = :grade_id\n" + "and (sh.map_id = :map_id or :map_id = -1)\n"
                + "and (sh.type = 'IM' or sh.type = 'XP')\n"
                + "and (sh.wna_id = 0 or sh.wna_stt <> 1 OR sh.wna_type = 'M')\n"
                + "and sh.created_date = STR_TO_DATE('").append(date).append(
                        "' ,'%Y-%m-%d %H:%i:%s')\n");
        if (client_id != -1) {
            sql1.append("and sh.client_id = ").append(client_id).append(" \n");
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1.toString()).addScalar("id")
                .addScalar("wn_ref").addScalar("inst_ref").addScalar("tons").addScalar("num")
                .addScalar("packing").addScalar("in_date").addScalar("area").addScalar("black")
                .addScalar("brown").addScalar("fm").addScalar("broken").addScalar("moist")
                .addScalar("ocrop").addScalar("moldy").addScalar("cup").addScalar("asc20")
                .addScalar("sc20").addScalar("sc19").addScalar("sc18").addScalar("sc17")
                .addScalar("sc16").addScalar("sc15").addScalar("sc14").addScalar("sc13")
                .addScalar("sc12").addScalar("bsc12").addScalar("client");

        query.setParameter("grade_id", grade_id);
        query.setParameter("map_id", map_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        return convertToStockReportObject(obj_list);
    }

    public ArrayList<StockReportObj> convertToStockReportObject(ArrayList<Object[]> obj_list) {
        // make sure the length obj is 26 [0-->25]
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportObj> sros = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportObj sro = new StockReportObj();
                sro.setWn_id(Integer.parseInt(obj[0].toString()));
                sro.setWn_ref(obj[1].toString());
                sro.setInst_ref((obj[2] != null) ? obj[2].toString() : "");
                sro.setTons((obj[3] != null) ? Float.parseFloat(obj[3].toString()) : 0);
                sro.setNum(Integer.parseInt(obj[4].toString()));
                sro.setPacking_name(obj[5].toString());
                sro.setDate(Common.getDateFromDatabase((Date) obj[6], Common.date_format_a));
                sro.setArea(obj[7].toString());
                sro.setBlack(Float.parseFloat(obj[8].toString()));
                sro.setBrown(Float.parseFloat(obj[9].toString()));
                sro.setFm(Float.parseFloat(obj[10].toString()));
                sro.setBroken(Float.parseFloat(obj[11].toString()));
                sro.setMoist(Float.parseFloat(obj[12].toString()));
                sro.setOcrop(Float.parseFloat(obj[13].toString()));
                sro.setMoldy(Float.parseFloat(obj[14].toString()));
                sro.setCup(obj[15].toString());
                sro.setAsc20(Float.parseFloat(obj[16].toString()));
                sro.setSc20(Float.parseFloat(obj[17].toString()));
                sro.setSc19(Float.parseFloat(obj[18].toString()));
                sro.setSc18(Float.parseFloat(obj[19].toString()));
                sro.setSc17(Float.parseFloat(obj[20].toString()));
                sro.setSc16(Float.parseFloat(obj[21].toString()));
                sro.setSc15(Float.parseFloat(obj[22].toString()));
                sro.setSc14(Float.parseFloat(obj[23].toString()));
                sro.setSc13(Float.parseFloat(obj[24].toString()));
                sro.setSc12(Float.parseFloat(obj[25].toString()));
                sro.setBsc12(Float.parseFloat(obj[26].toString()));
                sro.setClient(obj[27].toString());
                sros.add(sro);
            }
            return sros;
        }
        return null;
    }

    public ArrayList<StockReportInprocess> getReportObject_Inprocess(String date) {
        StringBuilder sql1 = new StringBuilder(
                "SELECT sh.pi_id as id, inst.ref_number as ref_number, sum(sh.tons) as tons, cm.`name` as client\n"
                + "FROM stock_historical sh\n"
                + "LEFT JOIN processing_instruction inst ON sh.pi_id = inst.id\n"
                + "LEFT JOIN company_master cm ON inst.client_id = cm.id\n"
                + "WHERE sh.pi_id != 0\n" + "AND inst.`status` = 0\n"
                + "AND sh.type = 'IP'\n" + "AND sh.created_date = STR_TO_DATE('").append(
                        date).append("' ,'%Y-%m-%d %H:%i:%s')\n" + "GROUP BY sh.pi_id");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1.toString()).addScalar("id")
                .addScalar("ref_number").addScalar("tons").addScalar("client");

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        return convertToStockReportObject_inprocess(obj_list, date);
    }

    public ArrayList<StockReportInprocess> convertToStockReportObject_inprocess(
            ArrayList<Object[]> obj_list, String date) {
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportInprocess> sris = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportInprocess sri = new StockReportInprocess();
                sri.setId(Integer.parseInt(obj[0].toString()));
                sri.setRef_number(obj[1].toString());
                sri.setInprocess_tons(Float.parseFloat(obj[2].toString()));
                sri.setClient(obj[3].toString());

                // get grade list of exprocess
                StringBuilder sql1 = new StringBuilder(
                        "SELECT sh.grade_id as gr_id, gm.`name` as gr_name, sum(sh.tons) as gr_tons\n"
                        + "FROM stock_historical sh\n"
                        + "LEFT JOIN grade_master gm ON sh.grade_id = gm.id\n"
                        + "WHERE sh.type = 'XP'\n" + "AND sh.pi_id = " + sri.getId() + "\n"
                        + "AND sh.created_date = STR_TO_DATE('").append(date).append(
                                "' ,'%Y-%m-%d %H:%i:%s')\n" + "GROUP BY sh.grade_id\n"
                                + "ORDER BY gm.`name`");

                Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1.toString()).addScalar("gr_id")
                        .addScalar("gr_name").addScalar("gr_tons");

                ArrayList<Object[]> obj_list2 = (ArrayList<Object[]>) query.list();

                if (obj_list2 != null || !obj_list2.isEmpty()) {
                    ArrayList<StockReportInprocess.ExprocessGrade> grades = new ArrayList<>();
                    for (Object[] obj2 : obj_list2) {
                        StockReportInprocess.ExprocessGrade grade = new StockReportInprocess.ExprocessGrade();
                        grade.setGr_id(Integer.parseInt(obj2[0].toString()));
                        grade.setGr_name(obj2[1].toString());
                        grade.setGr_tons(Float.parseFloat(obj2[2].toString()));
                        grades.add(grade);
                    }
                    sri.setGrades(grades);
                }
                sris.add(sri);
            }
            return sris;
        }
        return null;
    }

    public ArrayList<StockReportObj> getTodayReportObject(int grade_id, int map_id, int client_id) {
        StringBuilder sql = new StringBuilder(
                "SELECT\n"
                + "wn.id as wn_id, \n" // 0
                + "wn.ref_number as wn_ref, \n" // 1
                + "IFNULL(IF (wna.inst_type = 'E',(SELECT si.ref_number\n"
                + "					FROM shipping_instruction si \n"
                + "					WHERE si.id = wna.inst_id),\n"
                + "					(SELECT inst.ref_number\n"
                + "					FROM processing_instruction inst\n"
                + "					WHERE inst.id = wna.inst_id)), '') as inst_ref,\n" // 2
                + "round(sum(wnr.gross_weight - wnr.tare_weight)/1000,3) as tons,\n"
                + "IFNULL(SUM(wnr.no_of_bags),0) as num,\n" // 4
                + "pm.`name` as packing,\n" // 5
                + "wn.created_date as date,\n" // 6
                + "wm.`name` as map_name,\n" // 7
                + "wc.ordinate_x,\n" // 8
                + "wc.ordinate_y,\n" // 9
                + "wm.wall_vertical,\n" // 10
                + "wm.wall_horizontal,\n" // 11
                + "wm.width,\n" // 12
                + "wm.height,\n" // 13
                + "IFNULL(qr.black,0) as black,\n" // 14
                + "IFNULL(qr.brown,0) as brown,\n"
                + "IFNULL(qr.foreign_matter,0) as fm,\n"
                + "IFNULL(qr.broken,0) as broken,\n"
                + "IFNULL(qr.moisture,0) as moist,\n"
                + "IFNULL(qr.old_crop,0) as ocrop,\n"
                + "IFNULL(qr.moldy,0) as moldy,\n" // 20
                + "IFNULL(qr.rejected_cup,0) as cup,\n"
                + "IFNULL((SELECT COUNT(*) FROM cup_test WHERE qr_id = qr.id), 0) as number,\n"
                + "IFNULL(qr.above_sc20,0) as asc20,\n" // 23
                + "IFNULL(qr.sc20,0) as sc20,\n"
                + "IFNULL(qr.sc19,0) as sc19,\n"
                + "IFNULL(qr.sc18,0) as sc18,\n"
                + "IFNULL(qr.sc17,0) as sc17,\n"
                + "IFNULL(qr.sc16,0) as sc16,\n"
                + "IFNULL(qr.sc15,0) as sc15,\n"
                + "IFNULL(qr.sc14,0) as sc14,\n"
                + "IFNULL(qr.sc13,0) as sc13,\n"
                + "IFNULL(qr.sc12,0) as sc12,\n"
                + "IFNULL(qr.below_sc12,0) as bsc12,\n" // 33
                + "IFNULL(wn.area_id,0) as area,\n" // 34
                + "IFNULL(IF (wn.type = 'IM',(IF (wnr.`status` != 4,(SELECT di.client_id\n"
                + "					FROM delivery_instruction di\n"
                + "					WHERE di.id = wn.inst_id),\n"
                + "					(SELECT move.pledge_id\n"
                + "					FROM weight_note_receipt wnr3 LEFT JOIN movement move ON wnr3.movement_id = move.id\n"
                + "					WHERE wnr3.id = wnr.id))),\n"
                + "					(IF (wnr.`status` != 4,(SELECT cm.id\n"
                + "					FROM processing_instruction inst\n"
                + "					LEFT JOIN company_master cm ON inst.client_id = cm.id\n"
                + "					WHERE inst.id = wn.inst_id),(SELECT move.pledge_id\n"
                + "					FROM weight_note_receipt wnr3 LEFT JOIN movement move ON wnr3.movement_id = move.id\n"
                + "					WHERE wnr3.id = wnr.id)))),'') AS clientid,\n" // 35
                + "IFNULL(IF (wn.type = 'IM',(IF (wnr.`status` != 4,(SELECT cm.`name`\n"
                + "					FROM delivery_instruction di\n"
                + "                                     LEFT JOIN company_master cm ON di.client_id = cm.id\n"
                + "					WHERE di.id = wn.inst_id),\n"
                + "					(SELECT cm.`name`\n"
                + "					FROM weight_note_receipt wnr3 "
                + "                                     LEFT JOIN movement move ON wnr3.movement_id = move.id\n"
                + "                                     LEFT JOIN company_master cm ON move.pledge_id = cm.id\n"
                + "					WHERE wnr3.id = wnr.id))),\n"
                + "					(IF (wnr.`status` != 4,(SELECT cm.`name`\n"
                + "					FROM processing_instruction inst\n"
                + "					LEFT JOIN company_master cm ON inst.client_id = cm.id\n"
                + "					WHERE inst.id = wn.inst_id),(SELECT cm.`name`\n"
                + "					FROM weight_note_receipt wnr3 "
                + "                                     LEFT JOIN movement move ON wnr3.movement_id = move.id\n"
                + "                                     LEFT JOIN company_master cm ON move.pledge_id = cm.id\n"
                + "					WHERE wnr3.id = wnr.id)))),'') AS client,\n" // 36
                + "wn.grade_id as grade,\n" // 37
                + "wn.type as type,\n" // 38
                + "IFNULL(wm.id,0) as map,\n"
                + "wna.id,\n"
                + "wna.inst_type,\n"
                + "wna.status\n" // 42
                + "FROM weight_note wn\n"
                + "left join weight_note_receipt wnr on wnr.wn_id = wn.id\n"
                + "left join wnr_allocation wna ON wna.wnr_id = wnr.id\n"
                + "LEFT JOIN warehouse_cell wc ON wn.area_id = wc.id\n"
                + "LEFT JOIN warehouse_map wm ON wm.id = wc.warehouse_map_id\n"
                + "LEFT JOIN packing_master pm ON wn.packing_id = pm.id\n"
                + "LEFT JOIN grade_master gm ON wn.grade_id = gm.id\n"
                + "LEFT JOIN quality_report qr ON wn.qr_id = qr.id\n"
                + "WHERE wn.status <> 2\n"
                + "AND wnr.status <> 2\n"
                + "AND (wn.type = 'IM' or wn.type = 'XP')\n"
                + "GROUP BY wn.id, wnr.area_id\n"
                + "HAVING ((wnr.status = 3 and wna.status <> 1) or wna.status is null) and tons > 0\n"
                + "AND (clientid = :client_id or :client_id = -1)\n"
                + "AND (map = :map_id or :map_id = -1)\n" + "AND (grade = :grade_id)\n"
                + "ORDER BY wn.id");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn_id") // 0
                .addScalar("wn_ref").addScalar("inst_ref") // 2
                .addScalar("tons").addScalar("num") // 4
                .addScalar("packing").addScalar("date") // 6
                .addScalar("map_name").addScalar("ordinate_x") // 8
                .addScalar("ordinate_y").addScalar("wall_vertical") // 10
                .addScalar("wall_horizontal").addScalar("width") // 12
                .addScalar("height").addScalar("black") // 14
                .addScalar("brown").addScalar("fm") // 16
                .addScalar("broken").addScalar("moist") // 18
                .addScalar("ocrop").addScalar("moldy") // 20
                .addScalar("cup").addScalar("number") // 22
                .addScalar("asc20").addScalar("sc20") // 24
                .addScalar("sc19").addScalar("sc18") // 26
                .addScalar("sc17").addScalar("sc16") // 28
                .addScalar("sc15").addScalar("sc14") // 30
                .addScalar("sc13").addScalar("sc12") // 32
                .addScalar("bsc12").addScalar("area") // 34
                .addScalar("clientid").addScalar("client") // 36
                .addScalar("grade").addScalar("type") // 38
                .addScalar("map"); // 39
        query.setParameter("client_id", client_id);
        query.setParameter("map_id", map_id);
        query.setParameter("grade_id", grade_id);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportObj> sros = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportObj sro = new StockReportObj();
                sro.setWn_id(Integer.parseInt(obj[0].toString()));
                sro.setWn_ref(obj[1].toString());
                sro.setInst_ref((obj[2] != null) ? obj[2].toString() : "");
                sro.setTons((obj[3] != null) ? Float.parseFloat(obj[3].toString()) : 0);
                sro.setNum(Integer.parseInt(obj[4].toString()));
                sro.setPacking_name(obj[5].toString());
                sro.setDate(Common.getDateFromDatabase((Date) obj[6], Common.date_format_a));
                sro.setArea((!obj[34].toString().equals("0")) ? Common.convertIdIntoCode(
                        obj[7].toString(), Integer.parseInt(obj[8].toString()),
                        Integer.parseInt(obj[9].toString()), obj[10].toString(),
                        obj[11].toString(), Integer.parseInt(obj[12].toString()),
                        Integer.parseInt(obj[13].toString())) : "");
                sro.setBlack(Float.parseFloat(obj[14].toString()));
                sro.setBrown(Float.parseFloat(obj[15].toString()));
                sro.setFm(Float.parseFloat(obj[16].toString()));
                sro.setBroken(Float.parseFloat(obj[17].toString()));
                sro.setMoist(Float.parseFloat(obj[18].toString()));
                sro.setOcrop(Float.parseFloat(obj[19].toString()));
                sro.setMoldy(Float.parseFloat(obj[20].toString()));
                sro.setCup(obj[21].toString() + "/" + obj[22].toString());
                sro.setAsc20(Float.parseFloat(obj[23].toString()));
                sro.setSc20(Float.parseFloat(obj[24].toString()));
                sro.setSc19(Float.parseFloat(obj[25].toString()));
                sro.setSc18(Float.parseFloat(obj[26].toString()));
                sro.setSc17(Float.parseFloat(obj[27].toString()));
                sro.setSc16(Float.parseFloat(obj[28].toString()));
                sro.setSc15(Float.parseFloat(obj[29].toString()));
                sro.setSc14(Float.parseFloat(obj[30].toString()));
                sro.setSc13(Float.parseFloat(obj[31].toString()));
                sro.setSc12(Float.parseFloat(obj[32].toString()));
                sro.setBsc12(Float.parseFloat(obj[33].toString()));
                sro.setClient(obj[36].toString());
                sros.add(sro);
            }
            return sros;
        }
        return null;

    }

    public ArrayList<StockReportObj> getImportReportObject(int supplier_id, String from,
            String to, int grade_id) {
        StringBuilder sql = new StringBuilder(
                "SELECT\n"
                + "wn.id as wn_id, \n" // 0
                + "wn.ref_number as wn_ref, \n" // 1
                + "IFNULL(di.ref_number,'') as inst_ref,\n" // 2
                + "round(sum(wnr.gross_weight - wnr.tare_weight)/1000,3) as tons,\n"
                + "IFNULL(SUM(wnr.no_of_bags),0) as num,\n" // 4
                + "pm.`name` as packing,\n" // 5
                + "wn.created_date as date,\n" // 6
                + "wm.`name` as map_name,\n" // 7
                + "wc.ordinate_x,\n" // 8
                + "wc.ordinate_y,\n" // 9
                + "wm.wall_vertical,\n" // 10
                + "wm.wall_horizontal,\n" // 11
                + "wm.width,\n" // 12
                + "wm.height,\n" // 13
                + "IFNULL(qr.black,0) as black,\n" // 14
                + "IFNULL(qr.brown,0) as brown,\n"
                + "IFNULL(qr.foreign_matter,0) as fm,\n"
                + "IFNULL(qr.broken,0) as broken,\n"
                + "IFNULL(qr.moisture,0) as moist,\n"
                + "IFNULL(qr.old_crop,0) as ocrop,\n"
                + "IFNULL(qr.moldy,0) as moldy,\n" // 20
                + "IFNULL(qr.rejected_cup,0) as cup,\n"
                + "IFNULL((SELECT COUNT(*) FROM cup_test WHERE qr_id = qr.id), 0) as number,\n"
                + "IFNULL(qr.above_sc20,0) as asc20,\n" // 23
                + "IFNULL(qr.sc20,0) as sc20,\n"
                + "IFNULL(qr.sc19,0) as sc19,\n"
                + "IFNULL(qr.sc18,0) as sc18,\n"
                + "IFNULL(qr.sc17,0) as sc17,\n"
                + "IFNULL(qr.sc16,0) as sc16,\n"
                + "IFNULL(qr.sc15,0) as sc15,\n"
                + "IFNULL(qr.sc14,0) as sc14,\n"
                + "IFNULL(qr.sc13,0) as sc13,\n"
                + "IFNULL(qr.sc12,0) as sc12,\n"
                + "IFNULL(qr.below_sc12,0) as bsc12,\n" // 33
                + "IFNULL(wn.area_id,0) as area,\n" // 34
                + "IFNULL(cm.`name`,'') AS client,\n" // 36
                + "IFNULL(wm.id,0) as map\n"
                + "FROM weight_note wn\n"
                + "left join delivery_instruction di on di.id = wn.inst_id\n"
                + "left join company_master cm on di.supplier_id = cm.id\n"
                + "left join weight_note_receipt wnr on wnr.wn_id = wn.id\n"
                + "LEFT JOIN warehouse_cell wc ON wn.area_id = wc.id\n"
                + "LEFT JOIN warehouse_map wm ON wm.id = wc.warehouse_map_id\n"
                + "LEFT JOIN packing_master pm ON wn.packing_id = pm.id\n"
                + "LEFT JOIN grade_master gm ON wn.grade_id = gm.id\n"
                + "LEFT JOIN quality_report qr ON wn.qr_id = qr.id\n"
                + "WHERE wn.status <> 2\n"
                + "AND wnr.status <> 2\n"
                + "AND wnr.grade_id = :grade_id\n"        
                + "AND wn.type = 'IM' AND (di.supplier_id = :supplier_id or :supplier_id = -1)\n"
                + "and STR_TO_DATE(wnr.date,'%Y-%m-%d') >= STR_TO_DATE(:period_from,'%Y-%m-%d')\n"
                + "and STR_TO_DATE(wnr.date,'%Y-%m-%d') <= STR_TO_DATE(:period_to,'%Y-%m-%d')\n"
                + "GROUP BY wn.id, wnr.area_id\n"
                + "ORDER BY wn.id");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn_id") // 0
                .addScalar("wn_ref").addScalar("inst_ref") // 2
                .addScalar("tons").addScalar("num") // 4
                .addScalar("packing").addScalar("date") // 6
                .addScalar("map_name").addScalar("ordinate_x") // 8
                .addScalar("ordinate_y").addScalar("wall_vertical") // 10
                .addScalar("wall_horizontal").addScalar("width") // 12
                .addScalar("height").addScalar("black") // 14
                .addScalar("brown").addScalar("fm") // 16
                .addScalar("broken").addScalar("moist") // 18
                .addScalar("ocrop").addScalar("moldy") // 20
                .addScalar("cup").addScalar("number") // 22
                .addScalar("asc20").addScalar("sc20") // 24
                .addScalar("sc19").addScalar("sc18") // 26
                .addScalar("sc17").addScalar("sc16") // 28
                .addScalar("sc15").addScalar("sc14") // 30
                .addScalar("sc13").addScalar("sc12") // 32
                .addScalar("bsc12").addScalar("area") // 34
                .addScalar("client"); // 35
        query.setParameter("supplier_id", supplier_id);
        query.setParameter("grade_id", grade_id);
        query.setParameter("period_from", from);
        query.setParameter("period_to", to);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportObj> sros = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportObj sro = new StockReportObj();
                sro.setWn_id(Integer.parseInt(obj[0].toString()));
                sro.setWn_ref(obj[1].toString());
                sro.setInst_ref((obj[2] != null) ? obj[2].toString() : "");
                sro.setTons((obj[3] != null) ? Float.parseFloat(obj[3].toString()) : 0);
                sro.setNum(Integer.parseInt(obj[4].toString()));
                sro.setPacking_name(obj[5].toString());
                sro.setDate(Common.getDateFromDatabase((Date) obj[6], Common.date_format_a));
                sro.setArea((!obj[34].toString().equals("0")) ? Common.convertIdIntoCode(
                        obj[7].toString(), Integer.parseInt(obj[8].toString()),
                        Integer.parseInt(obj[9].toString()), obj[10].toString(),
                        obj[11].toString(), Integer.parseInt(obj[12].toString()),
                        Integer.parseInt(obj[13].toString())) : "");
                sro.setBlack(Float.parseFloat(obj[14].toString()));
                sro.setBrown(Float.parseFloat(obj[15].toString()));
                sro.setFm(Float.parseFloat(obj[16].toString()));
                sro.setBroken(Float.parseFloat(obj[17].toString()));
                sro.setMoist(Float.parseFloat(obj[18].toString()));
                sro.setOcrop(Float.parseFloat(obj[19].toString()));
                sro.setMoldy(Float.parseFloat(obj[20].toString()));
                sro.setCup(obj[21].toString() + "/" + obj[22].toString());
                sro.setAsc20(Float.parseFloat(obj[23].toString()));
                sro.setSc20(Float.parseFloat(obj[24].toString()));
                sro.setSc19(Float.parseFloat(obj[25].toString()));
                sro.setSc18(Float.parseFloat(obj[26].toString()));
                sro.setSc17(Float.parseFloat(obj[27].toString()));
                sro.setSc16(Float.parseFloat(obj[28].toString()));
                sro.setSc15(Float.parseFloat(obj[29].toString()));
                sro.setSc14(Float.parseFloat(obj[30].toString()));
                sro.setSc13(Float.parseFloat(obj[31].toString()));
                sro.setSc12(Float.parseFloat(obj[32].toString()));
                sro.setBsc12(Float.parseFloat(obj[33].toString()));
                sro.setClient(obj[35].toString());
                sros.add(sro);
            }
            return sros;
        }
        return null;

    }
    
    public ArrayList<StockReportObj> getExportReportObject(int client_id, String from,
            String to, int grade_id) {
        StringBuilder sql = new StringBuilder(
                "SELECT\n"
                + "wn.id as wn_id, \n" // 0
                + "wn.ref_number as wn_ref, \n" // 1
                + "IFNULL(si.ref_number,'') as inst_ref,\n" // 2
                + "round(sum(wnr.gross_weight - wnr.tare_weight)/1000,3) as tons,\n"
                + "IFNULL(SUM(wnr.no_of_bags),0) as num,\n" // 4
                + "pm.`name` as packing,\n" // 5
                + "wn.created_date as date,\n" // 6
                + "IFNULL(qr.black,0) as black,\n" // 7
                + "IFNULL(qr.brown,0) as brown,\n"
                + "IFNULL(qr.foreign_matter,0) as fm,\n"
                + "IFNULL(qr.broken,0) as broken,\n"
                + "IFNULL(qr.moisture,0) as moist,\n"
                + "IFNULL(qr.old_crop,0) as ocrop,\n"
                + "IFNULL(qr.moldy,0) as moldy,\n" // 13
                + "IFNULL(qr.rejected_cup,0) as cup,\n"
                + "IFNULL((SELECT COUNT(*) FROM cup_test WHERE qr_id = qr.id), 0) as number,\n"
                + "IFNULL(qr.above_sc20,0) as asc20,\n" // 16
                + "IFNULL(qr.sc20,0) as sc20,\n"
                + "IFNULL(qr.sc19,0) as sc19,\n"
                + "IFNULL(qr.sc18,0) as sc18,\n"
                + "IFNULL(qr.sc17,0) as sc17,\n"
                + "IFNULL(qr.sc16,0) as sc16,\n"
                + "IFNULL(qr.sc15,0) as sc15,\n"
                + "IFNULL(qr.sc14,0) as sc14,\n"
                + "IFNULL(qr.sc13,0) as sc13,\n"
                + "IFNULL(qr.sc12,0) as sc12,\n"
                + "IFNULL(qr.below_sc12,0) as bsc12,\n" // 26
                + "IFNULL(cm.`name`,'') AS client\n" // 27
                + "FROM weight_note wn\n"
                + "left join shipping_instruction si on si.id = wn.inst_id\n"
                + "left join company_master cm on si.client_id = cm.id\n"
                + "left join weight_note_receipt wnr on wnr.wn_id = wn.id\n"
                + "LEFT JOIN packing_master pm ON wn.packing_id = pm.id\n"
                + "LEFT JOIN grade_master gm ON wn.grade_id = gm.id\n"
                + "LEFT JOIN quality_report qr ON wn.qr_id = qr.id\n"
                + "WHERE wn.status <> 2\n"
                + "AND wnr.status <> 2\n"
                + "AND wnr.grade_id = :grade_id\n"        
                + "AND wn.type = 'EX' AND (si.client_id = :client_id or :client_id = -1)\n"
                + "and STR_TO_DATE(wnr.date,'%Y-%m-%d') >= STR_TO_DATE(:period_from,'%Y-%m-%d')\n"
                + "and STR_TO_DATE(wnr.date,'%Y-%m-%d') <= STR_TO_DATE(:period_to,'%Y-%m-%d')\n"
                + "GROUP BY wn.id\n"
                + "ORDER BY wn.id");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn_id") // 0
                .addScalar("wn_ref").addScalar("inst_ref") // 2
                .addScalar("tons").addScalar("num") // 4
                .addScalar("packing").addScalar("date") // 6
                .addScalar("black") // 7
                .addScalar("brown").addScalar("fm") // 9
                .addScalar("broken").addScalar("moist") // 11
                .addScalar("ocrop").addScalar("moldy") // 13
                .addScalar("cup").addScalar("number") // 15
                .addScalar("asc20").addScalar("sc20") // 17
                .addScalar("sc19").addScalar("sc18") // 19
                .addScalar("sc17").addScalar("sc16") // 21
                .addScalar("sc15").addScalar("sc14") // 23
                .addScalar("sc13").addScalar("sc12") // 25
                .addScalar("bsc12") // 26
                .addScalar("client"); // 27
        query.setParameter("client_id", client_id);
        query.setParameter("grade_id", grade_id);
        query.setParameter("period_from", from);
        query.setParameter("period_to", to);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportObj> sros = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportObj sro = new StockReportObj();
                sro.setWn_id(Integer.parseInt(obj[0].toString()));
                sro.setWn_ref(obj[1].toString());
                sro.setInst_ref((obj[2] != null) ? obj[2].toString() : "");
                sro.setTons((obj[3] != null) ? Float.parseFloat(obj[3].toString()) : 0);
                sro.setNum(Integer.parseInt(obj[4].toString()));
                sro.setPacking_name(obj[5].toString());
                sro.setDate(Common.getDateFromDatabase((Date) obj[6], Common.date_format_a));
                sro.setBlack(Float.parseFloat(obj[7].toString()));
                sro.setBrown(Float.parseFloat(obj[8].toString()));
                sro.setFm(Float.parseFloat(obj[9].toString()));
                sro.setBroken(Float.parseFloat(obj[10].toString()));
                sro.setMoist(Float.parseFloat(obj[11].toString()));
                sro.setOcrop(Float.parseFloat(obj[12].toString()));
                sro.setMoldy(Float.parseFloat(obj[13].toString()));
                sro.setCup(obj[14].toString() + "/" + obj[15].toString());
                sro.setAsc20(Float.parseFloat(obj[16].toString()));
                sro.setSc20(Float.parseFloat(obj[17].toString()));
                sro.setSc19(Float.parseFloat(obj[18].toString()));
                sro.setSc18(Float.parseFloat(obj[19].toString()));
                sro.setSc17(Float.parseFloat(obj[20].toString()));
                sro.setSc16(Float.parseFloat(obj[21].toString()));
                sro.setSc15(Float.parseFloat(obj[22].toString()));
                sro.setSc14(Float.parseFloat(obj[23].toString()));
                sro.setSc13(Float.parseFloat(obj[24].toString()));
                sro.setSc12(Float.parseFloat(obj[25].toString()));
                sro.setBsc12(Float.parseFloat(obj[26].toString()));
                sro.setClient(obj[27].toString());
                sros.add(sro);
            }
            return sros;
        }
        return null;

    }

    public ArrayList<StockReportInprocess> getTodayReportObject_Inprocess() {
        StringBuilder sql1 = new StringBuilder(
                "SELECT x.pi_id as id, inst.ref_number as ref_number, sum(x.tons) as tons, cm.`name` as client\n"
                + "FROM\n" + "(SELECT\n" + "wn.id as wn_id, \n"
                + "wn.ref_number as wn_ref,\n"
                + "sum(wnr.gross_weight - wnr.tare_weight)/1000 as tons ,\n"
                + "wn.grade_id as grade,\n" + "wn.type as type,\n"
                + "wn.inst_id AS pi_id\n" + "FROM weight_note wn\n"
                + "left join weight_note_receipt wnr on wnr.wn_id = wn.id\n"
                + "WHERE (wn.status <> 2)\n" + "AND wnr.status <> 2\n"
                + "AND (wn.type = 'XP' OR wn.type = 'IP') \n" + "GROUP BY wn.id\n"
                + "ORDER BY wn.id) x\n"
                + "LEFT JOIN processing_instruction inst ON x.pi_id = inst.id\n"
                + "LEFT JOIN company_master cm ON inst.client_id = cm.id\n"
                + "WHERE x.type = 'IP'\n" + "AND inst.status = 0\n" + "GROUP BY x.pi_id");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1.toString()).addScalar("id")
                .addScalar("ref_number").addScalar("tons", FloatType.INSTANCE).addScalar("client");

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        return convertToTodayStockReportObject_inprocess(obj_list);
    }

    public ArrayList<StockReportInprocess> convertToTodayStockReportObject_inprocess(
            ArrayList<Object[]> obj_list) {
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportInprocess> sris = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportInprocess sri = new StockReportInprocess();
                sri.setId(Integer.parseInt(obj[0].toString()));
                sri.setRef_number(obj[1].toString());
                sri.setInprocess_tons(Float.parseFloat(obj[2].toString()));
                sri.setClient(obj[3].toString());

                // get grade list of exprocess
                StringBuilder sql1 = new StringBuilder(
                        "SELECT x.grade as gr_id, gm.`name` as gr_name, IFNULL(sum(x.tons),0) as gr_tons\n"
                        + "FROM\n" + "(SELECT\n" + "wn.id as wn_id, \n"
                        + "wn.ref_number as wn_ref,\n"
                        + "sum(wnr.gross_weight - wnr.tare_weight)/1000 as tons ,\n"
                        + "wn.grade_id as grade,\n" + "wn.type as type,\n"
                        + "wn.inst_id AS pi_id\n" + "FROM weight_note wn\n"
                        + "left join weight_note_receipt wnr on wnr.wn_id = wn.id\n"
                        + "WHERE (wn.status <> 2)\n" + "AND wnr.status <> 2\n"
                        + "AND (wn.type = 'XP' OR wn.type = 'IP') \n" + "GROUP BY wn.id\n"
                        + "ORDER BY wn.id) x\n"
                        + "LEFT JOIN grade_master gm ON x.grade = gm.id\n"
                        + "WHERE x.type = 'XP'\n" + "AND x.pi_id = " + sri.getId() + "\n"
                        + "GROUP BY x.grade\n" + "ORDER BY gm.`name`");

                Query query = sessionFactory.getCurrentSession().createSQLQuery(sql1.toString()).addScalar("gr_id")
                        .addScalar("gr_name").addScalar("gr_tons");

                ArrayList<Object[]> obj_list2 = (ArrayList<Object[]>) query.list();

                if (obj_list2 != null || !obj_list2.isEmpty()) {
                    ArrayList<StockReportInprocess.ExprocessGrade> grades = new ArrayList<>();
                    for (Object[] obj2 : obj_list2) {
                        StockReportInprocess.ExprocessGrade grade = new StockReportInprocess.ExprocessGrade();
                        grade.setGr_id(Integer.parseInt(obj2[0].toString()));
                        grade.setGr_name(obj2[1].toString());
                        grade.setGr_tons(Float.parseFloat(obj2[2].toString()));
                        if (grade.getGr_tons() != 0) {
                            grades.add(grade);
                        }
                    }
                    if (!grades.isEmpty()) {
                        sri.setGrades(grades);
                    }
                }
                sris.add(sri);
            }
            return sris;
        }
        return null;
    }

    public QualityReport getweightedAverageFromQr(String wn_id) {
        String sql = "CALL weighted_QR(:wn_id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("wn_id", wn_id);
        Object[] obj = (Object[]) query.uniqueResult();
        if (obj != null) {
            QualityReport qr = new QualityReport();
            qr.setAboveSc20((obj[0] != null) ? Float.parseFloat(obj[0].toString()) : 0);
            qr.setSc20((obj[1] != null) ? Float.parseFloat(obj[1].toString()) : 0);
            qr.setSc19((obj[2] != null) ? Float.parseFloat(obj[2].toString()) : 0);
            qr.setSc18((obj[3] != null) ? Float.parseFloat(obj[3].toString()) : 0);
            qr.setSc17((obj[4] != null) ? Float.parseFloat(obj[4].toString()) : 0);
            qr.setSc16((obj[5] != null) ? Float.parseFloat(obj[5].toString()) : 0);
            qr.setSc15((obj[6] != null) ? Float.parseFloat(obj[6].toString()) : 0);
            qr.setSc14((obj[7] != null) ? Float.parseFloat(obj[7].toString()) : 0);
            qr.setSc13((obj[8] != null) ? Float.parseFloat(obj[8].toString()) : 0);
            qr.setSc12((obj[9] != null) ? Float.parseFloat(obj[9].toString()) : 0);
            qr.setBelowSc12((obj[10] != null) ? Float.parseFloat(obj[10].toString()) : 0);
            qr.setBlack((obj[11] != null) ? Float.parseFloat(obj[11].toString()) : 0);
            qr.setBroken((obj[12] != null) ? Float.parseFloat(obj[12].toString()) : 0);
            qr.setBlackBroken((obj[13] != null) ? Float.parseFloat(obj[13].toString()) : 0);
            qr.setBrown((obj[14] != null) ? Float.parseFloat(obj[14].toString()) : 0);
            qr.setMoisture((obj[15] != null) ? Float.parseFloat(obj[15].toString()) : 0);
            qr.setOldCrop((obj[16] != null) ? Float.parseFloat(obj[16].toString()) : 0);
            qr.setExcelsa((obj[17] != null) ? Float.parseFloat(obj[17].toString()) : 0);
            qr.setForeignMatter((obj[18] != null) ? Float.parseFloat(obj[18].toString()) : 0);
            qr.setWorm((obj[19] != null) ? Float.parseFloat(obj[19].toString()) : 0);
            qr.setMoldy((obj[20] != null) ? Float.parseFloat(obj[20].toString()) : 0);
            qr.setOtherBean((obj[21] != null) ? Float.parseFloat(obj[21].toString()) : 0);
            return qr;
        }
        return null;
    }

    public ArrayList<PiReportAllocatedWn> getPiReportAllocatedWn(int pi_id) {
        String sql = "CALL getWnAllocatedPiReport(:pi_id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("wn_id").addScalar("wn_ref")
                .addScalar("client").addScalar("supplier").addScalar("grade").addScalar("tons")
                .addScalar("black").addScalar("brown").addScalar("fm").addScalar("broken")
                .addScalar("moist").addScalar("ocrop").addScalar("moldy").addScalar("cup")
                .addScalar("asc20").addScalar("sc20").addScalar("sc19").addScalar("sc18")
                .addScalar("sc17").addScalar("sc16").addScalar("sc15").addScalar("sc14")
                .addScalar("sc13").addScalar("sc12").addScalar("bsc12").addScalar("number");
        query.setParameter("pi_id", pi_id);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<PiReportAllocatedWn> wns = new ArrayList<>();
            for (Object[] obj : obj_list) {
                PiReportAllocatedWn wn = new PiReportAllocatedWn();
                wn.setWn_id(Integer.parseInt(obj[0].toString()));
                wn.setRef_number(obj[1].toString());
                wn.setClient((obj[2] != null) ? obj[2].toString() : "");
                wn.setSupplier((obj[3] != null) ? obj[3].toString() : "");
                wn.setGrade((obj[4] != null) ? obj[4].toString() : "");
                wn.setTons((obj[5] != null) ? Float.parseFloat(obj[5].toString()) : (float) 0);
                wn.setBlack((obj[6] != null) ? Float.parseFloat(obj[6].toString()) : (float) 0);
                wn.setBrown((obj[7] != null) ? Float.parseFloat(obj[7].toString()) : (float) 0);
                wn.setFm((obj[8] != null) ? Float.parseFloat(obj[8].toString()) : (float) 0);
                wn.setBroken((obj[9] != null) ? Float.parseFloat(obj[9].toString()) : (float) 0);
                wn.setMoist((obj[10] != null) ? Float.parseFloat(obj[10].toString()) : (float) 0);
                wn.setOcrop((obj[11] != null) ? Float.parseFloat(obj[11].toString()) : (float) 0);
                wn.setMoldy((obj[12] != null) ? Float.parseFloat(obj[12].toString()) : (float) 0);
                wn.setCup((obj[13] != null) ? obj[13].toString() + "/" + obj[25].toString() : "");
                wn.setAsc20((obj[14] != null) ? Float.parseFloat(obj[14].toString()) : (float) 0);
                wn.setSc20((obj[15] != null) ? Float.parseFloat(obj[15].toString()) : (float) 0);
                wn.setSc19((obj[16] != null) ? Float.parseFloat(obj[16].toString()) : (float) 0);
                wn.setSc18((obj[17] != null) ? Float.parseFloat(obj[17].toString()) : (float) 0);
                wn.setSc17((obj[18] != null) ? Float.parseFloat(obj[18].toString()) : (float) 0);
                wn.setSc16((obj[19] != null) ? Float.parseFloat(obj[19].toString()) : (float) 0);
                wn.setSc15((obj[20] != null) ? Float.parseFloat(obj[20].toString()) : (float) 0);
                wn.setSc14((obj[21] != null) ? Float.parseFloat(obj[21].toString()) : (float) 0);
                wn.setSc13((obj[22] != null) ? Float.parseFloat(obj[22].toString()) : (float) 0);
                wn.setSc12((obj[23] != null) ? Float.parseFloat(obj[23].toString()) : (float) 0);
                wn.setBsc12((obj[24] != null) ? Float.parseFloat(obj[24].toString()) : (float) 0);
                wns.add(wn);
            }
            return wns;
        }
        return null;
    }

    public ArrayList<PiReportAllocatedWnr> getPiReportAllocatedWnr(int po_id) {
        String sql = "CALL getWnrAllocatedPiReport(:instid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("wnr_id").addScalar("wnr_ref")
                .addScalar("allocated_tons").addScalar("inprocess_wnr_ref").addScalar("new_tons")
                .addScalar("balance").addScalar("new_grade").addScalar("packing")
                .addScalar("wn_ref").addScalar("black").addScalar("brown").addScalar("fm")
                .addScalar("broken").addScalar("moist").addScalar("ocrop").addScalar("moldy")
                .addScalar("cup").addScalar("asc20").addScalar("sc20").addScalar("sc19")
                .addScalar("sc18").addScalar("sc17").addScalar("sc16").addScalar("sc15")
                .addScalar("sc14").addScalar("sc13").addScalar("sc12").addScalar("bsc12")
                .addScalar("number");
        query.setParameter("instid", po_id);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<PiReportAllocatedWnr> wnrs = new ArrayList<>();
            for (Object[] obj : obj_list) {
                PiReportAllocatedWnr wnr = new PiReportAllocatedWnr();
                wnr.setWnr_id(Integer.parseInt(obj[0].toString()));
                wnr.setWnr_ref((obj[1] != null) ? obj[1].toString() : "");
                wnr.setAllocated_tons((obj[2] != null) ? Float.parseFloat(obj[2].toString()) : 0);
                wnr.setInprocess_wnr_ref((obj[3] != null) ? obj[3].toString() : "");
                wnr.setNew_tons((obj[4] != null) ? Float.parseFloat(obj[4].toString()) : 0);
                wnr.setBalance((obj[5] != null) ? Float.parseFloat(obj[5].toString()) : 0);
                wnr.setNew_grade((obj[6] != null) ? obj[6].toString() : "");
                wnr.setPacking((obj[7] != null) ? obj[7].toString() : "");
                wnr.setWn_ref((obj[8] != null) ? obj[8].toString() : "");
                wnr.setBlack(Float.parseFloat(obj[9].toString()));
                wnr.setBrown(Float.parseFloat(obj[10].toString()));
                wnr.setFm(Float.parseFloat(obj[11].toString()));
                wnr.setBroken(Float.parseFloat(obj[12].toString()));
                wnr.setMoist(Float.parseFloat(obj[13].toString()));
                wnr.setOcrop(Float.parseFloat(obj[14].toString()));
                wnr.setMoldy(Float.parseFloat(obj[15].toString()));
                wnr.setCup(obj[16].toString() + "/" + obj[28].toString());
                wnr.setAsc20(Float.parseFloat(obj[17].toString()));
                wnr.setSc20(Float.parseFloat(obj[18].toString()));
                wnr.setSc19(Float.parseFloat(obj[19].toString()));
                wnr.setSc18(Float.parseFloat(obj[20].toString()));
                wnr.setSc17(Float.parseFloat(obj[21].toString()));
                wnr.setSc16(Float.parseFloat(obj[22].toString()));
                wnr.setSc15(Float.parseFloat(obj[23].toString()));
                wnr.setSc14(Float.parseFloat(obj[24].toString()));
                wnr.setSc13(Float.parseFloat(obj[25].toString()));
                wnr.setSc12(Float.parseFloat(obj[26].toString()));
                wnr.setBsc12(Float.parseFloat(obj[27].toString()));
                wnrs.add(wnr);
            }
            return wnrs;
        }
        return null;
    }

    public ArrayList<StockReportObj> getWnExprocessPiReport(int inst_id, int grade_id) {
        String sql = "CALL getWnExprocessPiReport(:instid,:gradeid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("wn_id") // 0
                .addScalar("wn_ref").addScalar("inst_ref") // 2
                .addScalar("tons").addScalar("num") // 4
                .addScalar("packing").addScalar("date") // 6
                .addScalar("map_name").addScalar("ordinate_x") // 8
                .addScalar("ordinate_y").addScalar("wall_vertical") // 10
                .addScalar("wall_horizontal").addScalar("width") // 12
                .addScalar("height").addScalar("black") // 14
                .addScalar("brown").addScalar("fm") // 16
                .addScalar("broken").addScalar("moist") // 18
                .addScalar("ocrop").addScalar("moldy") // 20
                .addScalar("cup").addScalar("number") // 22
                .addScalar("asc20").addScalar("sc20") // 24
                .addScalar("sc19").addScalar("sc18") // 26
                .addScalar("sc17").addScalar("sc16") // 28
                .addScalar("sc15").addScalar("sc14") // 30
                .addScalar("sc13").addScalar("sc12") // 32
                .addScalar("bsc12").addScalar("area") // 34
                .addScalar("client");
        query.setParameter("instid", inst_id);
        query.setParameter("gradeid", grade_id);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportObj> sros = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportObj sro = new StockReportObj();
                sro.setWn_id(Integer.parseInt(obj[0].toString()));
                sro.setWn_ref(obj[1].toString());
                sro.setInst_ref((obj[2] != null) ? obj[2].toString() : "");
                sro.setTons((obj[3] != null) ? Float.parseFloat(obj[3].toString()) : 0);
                sro.setNum(Integer.parseInt(obj[4].toString()));
                sro.setPacking_name(obj[5].toString());
                sro.setDate(Common.getDateFromDatabase((Date) obj[6], Common.date_format_a));
                sro.setArea((!obj[34].toString().equals("0")) ? Common.convertIdIntoCode(
                        obj[7].toString(), Integer.parseInt(obj[8].toString()),
                        Integer.parseInt(obj[9].toString()), obj[10].toString(),
                        obj[11].toString(), Integer.parseInt(obj[12].toString()),
                        Integer.parseInt(obj[13].toString())) : "");
                sro.setBlack(Float.parseFloat(obj[14].toString()));
                sro.setBrown(Float.parseFloat(obj[15].toString()));
                sro.setFm(Float.parseFloat(obj[16].toString()));
                sro.setBroken(Float.parseFloat(obj[17].toString()));
                sro.setMoist(Float.parseFloat(obj[18].toString()));
                sro.setOcrop(Float.parseFloat(obj[19].toString()));
                sro.setMoldy(Float.parseFloat(obj[20].toString()));
                sro.setCup(obj[21].toString() + "/" + obj[22].toString());
                sro.setAsc20(Float.parseFloat(obj[23].toString()));
                sro.setSc20(Float.parseFloat(obj[24].toString()));
                sro.setSc19(Float.parseFloat(obj[25].toString()));
                sro.setSc18(Float.parseFloat(obj[26].toString()));
                sro.setSc17(Float.parseFloat(obj[27].toString()));
                sro.setSc16(Float.parseFloat(obj[28].toString()));
                sro.setSc15(Float.parseFloat(obj[29].toString()));
                sro.setSc14(Float.parseFloat(obj[30].toString()));
                sro.setSc13(Float.parseFloat(obj[31].toString()));
                sro.setSc12(Float.parseFloat(obj[32].toString()));
                sro.setBsc12(Float.parseFloat(obj[33].toString()));
                sro.setClient(obj[35].toString());
                sros.add(sro);
            }
            return sros;
        }
        return null;

    }

    public ArrayList<StockReportObj> getInprocessPiReport(int pi_id) {
        String sql = "CALL getWnInprocessPiReport(:instid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("wn_id").addScalar("wn_ref")
                .addScalar("grade").addScalar("num").addScalar("date").addScalar("packing_name")
                .addScalar("cup").addScalar("number").addScalar("black").addScalar("brown")
                .addScalar("fm").addScalar("broken").addScalar("moist").addScalar("ocrop")
                .addScalar("moldy").addScalar("asc20").addScalar("sc20").addScalar("sc19")
                .addScalar("sc18").addScalar("sc17").addScalar("sc16").addScalar("sc15")
                .addScalar("sc14").addScalar("sc13").addScalar("sc12").addScalar("bsc12")
                .addScalar("tons");
        query.setParameter("instid", pi_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<StockReportObj> sros = new ArrayList<>();
            for (Object[] obj : obj_list) {
                StockReportObj sro = new StockReportObj();
                sro.setWn_id(Integer.parseInt(obj[0].toString()));
                sro.setWn_ref(obj[1].toString());
                sro.setGrade(obj[2].toString());
                sro.setNum(Integer.parseInt(obj[3].toString()));
                sro.setDate(Common.getDateFromDatabase((Date) obj[4], Common.date_format_a));
                sro.setPacking_name(obj[5].toString());
                sro.setCup(obj[6].toString() + "/" + obj[7].toString());
                sro.setBlack(Float.parseFloat(obj[8].toString()));
                sro.setBrown(Float.parseFloat(obj[9].toString()));
                sro.setFm(Float.parseFloat(obj[10].toString()));
                sro.setBroken(Float.parseFloat(obj[11].toString()));
                sro.setMoist(Float.parseFloat(obj[12].toString()));
                sro.setOcrop(Float.parseFloat(obj[13].toString()));
                sro.setMoldy(Float.parseFloat(obj[14].toString()));
                sro.setAsc20(Float.parseFloat(obj[15].toString()));
                sro.setSc20(Float.parseFloat(obj[16].toString()));
                sro.setSc19(Float.parseFloat(obj[17].toString()));
                sro.setSc18(Float.parseFloat(obj[18].toString()));
                sro.setSc17(Float.parseFloat(obj[19].toString()));
                sro.setSc16(Float.parseFloat(obj[20].toString()));
                sro.setSc15(Float.parseFloat(obj[21].toString()));
                sro.setSc14(Float.parseFloat(obj[22].toString()));
                sro.setSc13(Float.parseFloat(obj[23].toString()));
                sro.setSc12(Float.parseFloat(obj[24].toString()));
                sro.setBsc12(Float.parseFloat(obj[25].toString()));
                sro.setTons(Float.parseFloat(obj[26].toString()));
                sros.add(sro);
            }
            return sros;
        }
        return null;
    }

    public CompanyMaster getClientFromInst(int instId, String type) {
        String table = "";
        String client = "companyMasterByClientId";
        switch (type) {
            case "IM":
                table = "DeliveryInstruction";
                break;
            case "IP":
            case "XP":
                table = "ProcessingInstruction";
                break;
            case "EX":
                table = "ShippingInstruction";
                break;
        }
        String sql = "select client from " + table + " inst left join inst." + client
                + " client where inst.id =:inst";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("inst", instId);
        Object obj = query.uniqueResult();
        return (obj != null) ? (CompanyMaster) obj : null;
    }

    public CompanyMaster getPledgeFromInst(int instId) {
        String sql = "select pledge from DeliveryInstruction inst left join inst.companyMasterByPledger pledge where inst.id =:inst";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("inst", instId);
        Object obj = query.uniqueResult();
        return (obj != null) ? (CompanyMaster) obj : null;
    }
}
