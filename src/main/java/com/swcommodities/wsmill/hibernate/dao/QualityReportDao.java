/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

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
import org.hibernate.type.DateType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.json.JSONException;
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dto.CourierMaster;
import com.swcommodities.wsmill.hibernate.dto.QualityReport;
import com.swcommodities.wsmill.hibernate.dto.SampleSent;
import com.swcommodities.wsmill.hibernate.dto.WeightNote;
import com.swcommodities.wsmill.object.QualityReportObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class QualityReportDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public QualityReportDao setSessionFactory(SessionFactory sessionFactory) {
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

    public int updateQuality(QualityReport qr) {
        boolean isUpdated = false;
        if (qr.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(qr);
        if (isUpdated) {
            return 0;
        }
        return qr.getId();
    }

    public String getNewQRRef(String type) {
        //DetachedCriteria crit = DetachedCriteria.forClass(QualityReport.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(QualityReport.class);
        crit.add(Restrictions.eq("type", type));
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        String prefix = "QR-" + type + "-";
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, prefix) : Common.getNewRefNumber(null, prefix);
    }

    public CourierMaster getCourierById(int id) {
        return (CourierMaster) sessionFactory.getCurrentSession().get(CourierMaster.class, id);
    }

    public QualityReport getQualityReportByWeightnote(WeightNote wn) {
        //DetachedCriteria crit = DetachedCriteria.forClass(QualityReport.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(QualityReport.class);
        crit.add(Restrictions.eq("weightNote.id", wn.getId()));
        return (QualityReport) crit.uniqueResult();
    }

    public ArrayList<QualityReport> getQrRefList(String type) {
        String sql = "from QualityReport qr where qr.type = :type and status <" + Constants.DELETED + " order by refNumber desc";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("type", type);
        query.setMaxResults(5000);
        return (ArrayList<QualityReport>) query.list();
        //        return (ArrayList<QualityReport>)getHibernateTemplate().findByCriteria(crit);
    }

    public QualityReport getQualityReportById(int id) {
        return (QualityReport) sessionFactory.getCurrentSession().get(QualityReport.class, id);
    }

    public ArrayList<QualityReportObj> searchQualityReport(String searchTerm, String order, int start, int amount, String colName, int grade, int sup, int buyer, String type, Byte status, String from, String to) {
        String inst = "";
        String company_type = "";
        String wn_or_wr = "";
        switch (type) {
            case "IM":
                inst = "delivery_instruction";
                company_type = "supplier_id";
                wn_or_wr = "weight_note";
                break;
            case "WR":
                inst = "delivery_instruction";
                company_type = "supplier_id";
                wn_or_wr = "warehouse_receipt";
                break;
            case "IP":
            case "XP":
                inst = "processing_instruction";
                company_type = "client_id";
                wn_or_wr = "weight_note";
                break;
            case "EX":
                inst = "shipping_instruction";
                company_type = "client_id";
                wn_or_wr = "weight_note";
                break;
            case "WC":
                inst = "shipping_instruction";
                company_type = "client_id";
                wn_or_wr = "warehouse_receipt";
                break;
        }

        StringBuilder sql = new StringBuilder("SELECT qr.id as qr_id, qr.ref_number as qr_ref, wn.ref_number as wn_ref, cm.`name` as supplier_name,\n"
                + "		gm.`name` as grade_name, qr.date as qr_date, qr.black as black, qr.brown as brown,\n"
                + "		qr.foreign_matter as fm, qr.broken as broken, qr.moisture as moisture,\n"
                + "		qr.old_crop as ocrop, qr.moldy as moldy, qr.above_sc20 as asc20,\n"
                + "		qr.sc20 as sc20, qr.sc19 as sc19, qr.sc18 as sc18, qr.sc17 as sc17,\n"
                + "		 qr.sc16 as sc16, qr.sc15 as sc15, qr.sc14 as sc14, qr.sc13 as sc13,\n"
                + "		 qr.sc12 as sc12, qr.below_sc12 as bsc12, qr.type, qr.`status` as quality_status, cm.id, " + (type.equals("EX") ?  "cm2.id,": "") + " gm.id\n"
                + "FROM quality_report qr\n"
                + "LEFT JOIN " + wn_or_wr + " wn ON wn.qr_id = qr.id\n"
                + "LEFT JOIN " + inst + " inst ON inst.id = wn.inst_id\n");
        if (type.equals("WC") || type.equals("WR")) {
            sql.append("LEFT JOIN grade_master gm ON gm.id = inst.grade_id\n");
        } else {
            sql.append("LEFT JOIN grade_master gm ON gm.id = wn.grade_id\n");
        }
        if (type.equals("EX")) {
            sql.append("LEFT JOIN company_master cm2 ON cm2.id = inst.buyer_id\n");
        }
        sql.append("LEFT JOIN company_master cm ON cm.id = inst.").append(company_type).append(" \n"
                + "HAVING (qr.type = :type)\n"
                + "AND quality_status < 2 \n"
                + "AND (quality_status = :status or :status = -1)\n"
                + "AND (cm.id = :supplier or :supplier = -1)\n"
                + "AND (gm.id = :grade or :grade = -1)\n");
        if (type.equals("EX")) {
            sql.append("AND (cm2.id = :buyer or :buyer = -1)\n");
        }
        if (!from.equals("")) {
            sql.append("AND (qr.date >= STR_TO_DATE(:sFrom ,'%d-%m-%Y %H:%i:%s'))\n");
        }
        if (!to.equals("")) {
            sql.append("AND (qr.date <= STR_TO_DATE(:sTo ,'%d-%m-%Y %H:%i:%s'))\n");
        }
        sql.append("AND ("
                + "(qr.ref_number like :searchTerm) "
                + "OR (wn.ref_number like :searchTerm) "
                + "OR (cm.`name` like :searchTerm)\n"
                + "OR (gm.`name` like :searchTerm) "
                + "OR (DATE_FORMAT(qr.date, '%d-%b-%y') like :searchTerm)\n"
                + "OR (qr.black like :searchTerm) OR (qr.brown like :searchTerm) OR (qr.foreign_matter like :searchTerm) OR (qr.broken like :searchTerm) \n"
                + "OR (qr.moisture like :searchTerm) OR (qr.old_crop like :searchTerm) OR (qr.moldy like :searchTerm) \n"
                + "OR (qr.above_sc20 like :searchTerm) OR (qr.sc20 like :searchTerm) OR (qr.sc19 like :searchTerm) OR (qr.sc18 like :searchTerm) \n"
                + "OR (qr.sc17 like :searchTerm) OR (qr.sc16 like :searchTerm) OR (qr.sc15 like :searchTerm) OR (qr.sc14 like :searchTerm) \n"
                + "OR (qr.sc13 like :searchTerm) OR (qr.sc12 like :searchTerm) OR (qr.below_sc12 like :searchTerm)) \n");
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ").append((order.equals("desc") ? "desc" : "asc"));
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("qr_id")
                .addScalar("qr_ref")
                .addScalar("wn_ref")
                .addScalar("supplier_name")
                .addScalar("grade_name")
                .addScalar("qr_date")
                .addScalar("black")
                .addScalar("brown")
                .addScalar("fm")
                .addScalar("broken")
                .addScalar("moisture")
                .addScalar("ocrop")
                .addScalar("moldy")
                .addScalar("asc20")
                .addScalar("sc20")
                .addScalar("sc19")
                .addScalar("sc18")
                .addScalar("sc17")
                .addScalar("sc16")
                .addScalar("sc15")
                .addScalar("sc14")
                .addScalar("sc13")
                .addScalar("sc12")
                .addScalar("bsc12");
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        query.setParameter("grade", grade);
        query.setParameter("supplier", sup);
        if (type.equals("EX")) {
            query.setParameter("buyer", buyer);
        }
        query.setParameter("type", type);
        query.setParameter("status", status);
        if (!from.equals("")) {
            query.setParameter("sFrom", from + " 00:00:00");
        }
        if (!to.equals("")) {
            query.setParameter("sTo", to + " 23:59:59");
        }
        this.setTotalAfterFilter(query.list().size());
        if (start != 0) {
            query.setFirstResult(start);
        }
        if (amount != 0) {
            query.setMaxResults(amount);
        }
        

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        //convert them to allocation object

        return convertToQualityObject(obj_list);
    }

    public ArrayList<QualityReportObj> convertToQualityObject(ArrayList<Object[]> obj_list) {
        //make sure the length obj is 26 [0-->25]
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<QualityReportObj> qrs = new ArrayList<>();
            for (Object[] obj : obj_list) {
                QualityReportObj qr = new QualityReportObj();
                qr.setQr_id(Integer.parseInt(obj[0].toString()));
                qr.setQr_ref(obj[1].toString());
                qr.setWn_ref((obj[2] != null) ? obj[2].toString() : "");
                qr.setSuppplier_name((obj[3] != null) ? obj[3].toString() : "");
                qr.setGrade_name((obj[4] != null) ? obj[4].toString() : "");
                qr.setQr_date((obj[5] != null) ? (Date) obj[5] : new Date());
                qr.setBlack((obj[6] != null) ? Float.parseFloat(obj[6].toString()) : 0);
                qr.setBrown((obj[7] != null) ? Float.parseFloat(obj[7].toString()) : 0);
                qr.setFm((obj[8] != null) ? Float.parseFloat(obj[8].toString()) : 0);
                qr.setBroken((obj[9] != null) ? Float.parseFloat(obj[9].toString()) : 0);
                qr.setMoisture((obj[10] != null) ? Float.parseFloat(obj[10].toString()) : 0);
                qr.setOcrop((obj[11] != null) ? Float.parseFloat(obj[11].toString()) : 0);
                qr.setMoldy((obj[12] != null) ? Float.parseFloat(obj[12].toString()) : 0);
                qr.setAsc20((obj[13] != null) ? Float.parseFloat(obj[13].toString()) : 0);
                qr.setSc20((obj[14] != null) ? Float.parseFloat(obj[14].toString()) : 0);
                qr.setSc19((obj[15] != null) ? Float.parseFloat(obj[15].toString()) : 0);
                qr.setSc18((obj[16] != null) ? Float.parseFloat(obj[16].toString()) : 0);
                qr.setSc17((obj[17] != null) ? Float.parseFloat(obj[17].toString()) : 0);
                qr.setSc16((obj[18] != null) ? Float.parseFloat(obj[18].toString()) : 0);
                qr.setSc15((obj[19] != null) ? Float.parseFloat(obj[19].toString()) : 0);
                qr.setSc14((obj[20] != null) ? Float.parseFloat(obj[20].toString()) : 0);
                qr.setSc13((obj[21] != null) ? Float.parseFloat(obj[21].toString()) : 0);
                qr.setSc12((obj[22] != null) ? Float.parseFloat(obj[22].toString()) : 0);
                qr.setBsc12((obj[23] != null) ? Float.parseFloat(obj[23].toString()) : 0);
                qrs.add(qr);
            }
            return qrs;
        }
        return null;
    }

    public ArrayList<Map> getAllGrades(String type) {
        String sql;

        switch (type) {
            case "WR":
                sql = "SELECT DISTINCT IFNULL(gm.id, '') as id, IFNULL(gm.`name`, '') as grade_name\n"
                        + "FROM quality_report qr LEFT JOIN warehouse_receipt wr ON qr.id = wr.qr_id\n"
                        + "						LEFT JOIN delivery_instruction di ON wr.inst_id = di.id\n"
                        + "						LEFT JOIN grade_master gm ON di.grade_id = gm.id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY gm.`name`";
                break;
            case "WC":
                sql = "SELECT DISTINCT IFNULL(gm.id, '') as id, IFNULL(gm.`name`, '') as grade_name\n"
                        + "FROM quality_report qr LEFT JOIN warehouse_receipt wr ON qr.id = wr.qr_id\n"
                        + "						LEFT JOIN shipping_instruction si ON wr.inst_id = si.id\n"
                        + "						LEFT JOIN grade_master gm ON si.grade_id = gm.id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY gm.`name`";
                break;
            default:
                sql = "SELECT DISTINCT IFNULL(gm.id, '') as id, IFNULL(gm.`name`, '') as grade_name\n"
                        + "FROM quality_report qr LEFT JOIN weight_note wn ON qr.id = wn.qr_id\n"
                        + "						LEFT JOIN grade_master gm ON wn.grade_id = gm.id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY gm.`name`";
                break;
        }
        StringBuilder sqlb = new StringBuilder(sql);
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlb.toString())
                .addScalar("id")
                .addScalar("grade_name");
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        ArrayList<Map> list_map = new ArrayList<>();
        for (Object[] obj : obj_list) {
            Map temp = new HashMap<>();
            temp.put("id", obj[0]);
            temp.put("name", obj[1]);
            list_map.add(temp);
        }
        return list_map;
    }

    public ArrayList<Map> getCompany(String type) {
        String sql;

        switch (type) {
            case "IM":
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN weight_note wn ON wn.qr_id = qr.id\n"
                        + "						LEFT JOIN delivery_instruction inst ON inst.id = wn.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.supplier_id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY cm.`name`";
                break;
            case "IP":
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN weight_note wn ON wn.qr_id = qr.id\n"
                        + "						LEFT JOIN processing_instruction inst ON inst.id = wn.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.client_id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY cm.`name`";
                break;
            case "XP":
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN weight_note wn ON wn.qr_id = qr.id\n"
                        + "						LEFT JOIN processing_instruction inst ON inst.id = wn.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.client_id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY cm.`name`";
                break;
            case "EX":
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN weight_note wn ON wn.qr_id = qr.id\n"
                        + "						LEFT JOIN shipping_instruction inst ON inst.id = wn.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.client_id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY cm.`name`";
                break;
            case "buyer":
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN weight_note wn ON wn.qr_id = qr.id\n"
                        + "						LEFT JOIN shipping_instruction inst ON inst.id = wn.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.buyer_id\n"
                        + "WHERE qr.status != 2 AND qr.type = 'EX' ORDER BY cm.`name`";
                break;
            case "WR":
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN warehouse_receipt wr ON wr.qr_id = qr.id\n"
                        + "						LEFT JOIN delivery_instruction inst ON inst.id = wr.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.supplier_id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY cm.`name`";
                break;
            default:
                sql = "SELECT DISTINCT IFNULL(cm.id, '') as id, IFNULL(cm.`name`, '') as comp_name\n"
                        + "FROM quality_report qr LEFT JOIN warehouse_receipt wr ON wr.qr_id = qr.id\n"
                        + "						LEFT JOIN shipping_instruction inst ON inst.id = wr.inst_id\n"
                        + "						LEFT JOIN company_master cm ON cm.id = inst.client_id\n"
                        + "WHERE qr.status != 2 AND qr.type = '" + type + "' ORDER BY cm.`name`";
                break;

        }

        StringBuilder sqlb = new StringBuilder(sql);
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlb.toString())
                .addScalar("id")
                .addScalar("comp_name");
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        ArrayList<Map> list_map = new ArrayList<>();
        for (Object[] obj : obj_list) {
            Map temp = new HashMap<>();
            temp.put("id", obj[0]);
            temp.put("name", obj[1]);
            list_map.add(temp);
        }
        return list_map;
    }

    public JSONObject searchQualityReport_1(String type, int qr_id) throws JSONException {
        String inst = "";
        String company_type = "";
        String wn_or_wr = "";
        switch (type) {
            case "IM":
                inst = "delivery_instruction";
                company_type = "supplier_id";
                wn_or_wr = "weight_note";
                break;
            case "WR":
                inst = "delivery_instruction";
                company_type = "supplier_id";
                wn_or_wr = "warehouse_receipt";
                break;
            case "IP":
            case "XP":
                inst = "processing_instruction";
                company_type = "client_id";
                wn_or_wr = "weight_note";
                break;
            case "EX":
                inst = "shipping_instruction";
                company_type = "client_id";
                wn_or_wr = "weight_note";
                break;
            case "WC":
                inst = "shipping_instruction";
                company_type = "client_id";
                wn_or_wr = "warehouse_receipt";
                break;
        }

        StringBuilder sql = new StringBuilder("SELECT qr.id as qr_id, qr.ref_number as qr_ref, wn.ref_number as wn_ref, cm.`name` as supplier_name,\n"
                + "		gm.`name` as grade_name, qr.date as qr_date, round(qr.black,2) as black, round(qr.brown,2) as brown,\n"
                + "		round(qr.foreign_matter,2) as fm, round(qr.broken,2) as broken, round(qr.moisture,2) as moisture,\n"
                + "		round(qr.old_crop,2) as ocrop, round(qr.moldy,2) as moldy, round(qr.above_sc20,2) as asc20,\n"
                + "		round(qr.sc20,2) as sc20, round(qr.sc19,2) as sc19, round(qr.sc18,2) as sc18, round(qr.sc17,2) as sc17,\n"
                + "		round(qr.sc16,2) as sc16, round(qr.sc15,2) as sc15, round(qr.sc14,2) as sc14, round(qr.sc13,2) as sc13,\n"
                + "		round(qr.sc12,2) as sc12, round(qr.below_sc12,2) as bsc12, qr.`status` as status, qr.remark as remark, qr.black_broken as black_broken, qr.worm as worm,"
                + "             IFNULL(wn.truck_no,'') as truck_no, IFNULL(wn.container_no,'') as container_no, round(qr.other_bean,2) as obean, round(qr.cherry,2) as cherry, round(IFNULL(qr.defect,0),2) as defect \n"
                + "FROM quality_report qr\n"
                + "LEFT JOIN " + wn_or_wr + " wn ON wn.qr_id = qr.id\n"
                + "LEFT JOIN " + inst + " inst ON inst.id = wn.inst_id\n");

        if (type.equals("WC") || type.equals("WR")) {
            sql = new StringBuilder("SELECT qr.id as qr_id, qr.ref_number as qr_ref, wn.ref_number as wn_ref, cm.`name` as supplier_name,\n"
                    + "		gm.`name` as grade_name, qr.date as qr_date, round(qr.black,2) as black, round(qr.brown,2) as brown,\n"
                    + "		round(qr.foreign_matter,2) as fm, round(qr.broken,2) as broken, round(qr.moisture,2) as moisture,\n"
                    + "		round(qr.old_crop,2) as ocrop, round(qr.moldy,2) as moldy, round(qr.above_sc20,2) as asc20,\n"
                    + "		round(qr.sc20,2) as sc20, round(qr.sc19,2) as sc19, round(qr.sc18,2) as sc18, round(qr.sc17,2) as sc17,\n"
                    + "		round(qr.sc16,2) as sc16, round(qr.sc15,2) as sc15, round(qr.sc14,2) as sc14, round(qr.sc13,2) as sc13,\n"
                    + "		round(qr.sc12,2) as sc12, round(qr.below_sc12,2) as bsc12, qr.`status` as status, qr.remark as remark, qr.black_broken as black_broken, qr.worm as worm,\n"
                    + "             IFNULL(wn2.truck_no,'') as truck_no, IFNULL(wn2.container_no,'') as container_no, round(qr.other_bean,2) as obean, round(qr.cherry,2) as cherry, round(IFNULL(qr.defect,0),2) as defect \n"
                    + "FROM quality_report qr\n"
                    + "LEFT JOIN warehouse_receipt wn ON wn.qr_id = qr.id\n"
                    + "LEFT JOIN weight_note wn2 ON wn.id = wn2.wrc_id\n"
                    + "LEFT JOIN " + inst + " inst ON inst.id = wn.inst_id\n");
        }

        if (type.equals("WC") || type.equals("WR")) {
            sql.append("LEFT JOIN grade_master gm ON gm.id = inst.grade_id\n");
        } else {
            sql.append("LEFT JOIN grade_master gm ON gm.id = wn.grade_id\n");
        }
        sql.append("LEFT JOIN company_master cm ON cm.id = inst.").append(company_type).append(" \n"
                + "WHERE qr.id = :qrid");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("qr_id", IntegerType.INSTANCE)
                .addScalar("qr_ref", StringType.INSTANCE)
                .addScalar("wn_ref", StringType.INSTANCE)
                .addScalar("supplier_name", StringType.INSTANCE)
                .addScalar("grade_name", StringType.INSTANCE)
                .addScalar("qr_date", DateType.INSTANCE)
                .addScalar("black", FloatType.INSTANCE)
                .addScalar("brown", FloatType.INSTANCE)
                .addScalar("fm", FloatType.INSTANCE)
                .addScalar("broken", FloatType.INSTANCE)
                .addScalar("moisture", FloatType.INSTANCE)
                .addScalar("ocrop", FloatType.INSTANCE)
                .addScalar("moldy", FloatType.INSTANCE)
                .addScalar("asc20", FloatType.INSTANCE)
                .addScalar("sc20", FloatType.INSTANCE)
                .addScalar("sc19", FloatType.INSTANCE)
                .addScalar("sc18", FloatType.INSTANCE)
                .addScalar("sc17", FloatType.INSTANCE)
                .addScalar("sc16", FloatType.INSTANCE)
                .addScalar("sc15", FloatType.INSTANCE)
                .addScalar("sc14", FloatType.INSTANCE)
                .addScalar("sc13", FloatType.INSTANCE)
                .addScalar("sc12", FloatType.INSTANCE)
                .addScalar("bsc12", FloatType.INSTANCE)
                .addScalar("status", IntegerType.INSTANCE)
                .addScalar("remark", StringType.INSTANCE)
                .addScalar("black_broken", FloatType.INSTANCE)
                .addScalar("worm", FloatType.INSTANCE)
                .addScalar("truck_no", StringType.INSTANCE)
                .addScalar("container_no", StringType.INSTANCE)
                .addScalar("obean", FloatType.INSTANCE)
                .addScalar("cherry", FloatType.INSTANCE)
                .addScalar("defect", FloatType.INSTANCE);
        query.setParameter("qrid", qr_id);

        Object[] obj = (Object[]) query.uniqueResult();

        if (obj != null) {

            JSONObject qr = new JSONObject();
            qr.put("qr_id", Integer.parseInt(obj[0].toString()));
            qr.put("qr_ref", obj[1].toString());
            qr.put("wn_ref", (obj[2] != null) ? obj[2].toString() : "");
            qr.put("supplier", (obj[3] != null) ? obj[3].toString() : "");
            qr.put("grade", (obj[4] != null) ? obj[4].toString() : "");
            qr.put("date", (obj[5] != null) ? (Date) obj[5] : new Date());
            qr.put("black", (obj[6] != null) ? (obj[6].toString()) : 0);
            qr.put("brown", (obj[7] != null) ? (obj[7].toString()) : 0);
            qr.put("fm", (obj[8] != null) ? (obj[8].toString()) : 0);
            qr.put("broken", (obj[9] != null) ? (obj[9].toString()) : 0);
            qr.put("moisture", (obj[10] != null) ? (obj[10].toString()) : 0);
            qr.put("old_crop", (obj[11] != null) ? (obj[11].toString()) : 0);
            qr.put("other_bean", (obj[30] != null) ? (obj[30].toString()) : 0);
            qr.put("cherry", (obj[31] != null) ? (obj[31].toString()) : 0);
            qr.put("defect", (obj[32] != null) ? (obj[32].toString()) : 0);
            qr.put("moldy", (obj[12] != null) ? (obj[12].toString()) : 0);
            qr.put("above_sc20", (obj[13] != null) ? (obj[13].toString()) : 0);
            qr.put("sc20", (obj[14] != null) ? (obj[14].toString()) : 0);
            qr.put("sc19", (obj[15] != null) ? (obj[15].toString()) : 0);
            qr.put("sc18", (obj[16] != null) ? (obj[16].toString()) : 0);
            qr.put("sc17", (obj[17] != null) ? (obj[17].toString()) : 0);
            qr.put("sc16", (obj[18] != null) ? (obj[18].toString()) : 0);
            qr.put("sc15", (obj[19] != null) ? (obj[19].toString()) : 0);
            qr.put("sc14", (obj[20] != null) ? (obj[20].toString()) : 0);
            qr.put("sc13", (obj[21] != null) ? (obj[21].toString()) : 0);
            qr.put("sc12", (obj[22] != null) ? (obj[22].toString()) : 0);
            qr.put("below_sc12", (obj[23] != null) ? (obj[23].toString()) : 0);
            qr.put("status", Integer.parseInt(obj[24].toString()));
            qr.put("remark", (obj[25] != null) ? obj[25].toString() : "");
            qr.put("black_broken", (obj[26] != null) ? (obj[26].toString()) : 0);
            qr.put("worm", (obj[27] != null) ? (obj[27].toString()) : 0);
            qr.put("truck_no", obj[28].toString());
            qr.put("container_no", obj[29].toString());

            return qr;
        } else {
            return null;
        }

    }

    public SampleSent getSampleSentById(int id) {
        return (SampleSent) sessionFactory.getCurrentSession().get(SampleSent.class, id);
    }

    public String getNewSampleSentRef() {
        //DetachedCriteria crit = DetachedCriteria.forClass(SampleSent.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(SampleSent.class);
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "SS-") : Common.getNewRefNumber(null, "SS-");
    }

    public String getNewSampleSentTypeRef() {
        //DetachedCriteria crit = DetachedCriteria.forClass(SampleSent.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(SampleSent.class);
        crit.setProjection(Projections.max("typeSampleRef"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "TS-") : Common.getNewRefNumber(null, "TS-");
    }

    public SampleSent newSampleSent(SampleSent obj) {
        sessionFactory.getCurrentSession().save(obj);
        return obj;
    }

    public SampleSent updateSampleSent(SampleSent obj) {
        sessionFactory.getCurrentSession().update(obj);
        return obj;
    }

    public ArrayList<HashMap> getSampleSentList(String order, int start, int amount,
                                          String colName, int clientId, Byte sentStatus, Byte approvalStatus) throws ParseException {
        Map<String, Object> params = new HashMap<String,Object>();
        String sql = "select DISTINCT ss.id as id,\n" +
                "ss.ref_number as refNumber,\n" +
                "si.ref_number as siRef,\n" +
                "ifnull(client_cm.name,'') as client,\n" +
                "ifnull(si.client_ref,'') as clientRef,\n" +
                "ifnull(buyer_cm.name,'') as buyer,\n" +
                "si.from_date as firstDate,\n" +
                "if(si.origin_id = 1, 'Vietnam', '') as origin,\n" +
                "if(si.quality_id = 1, 'Robusta', '') as quality,\n" +
                "ifnull(grade_gm.name,'') as grade,\n" +
                "ifnull(cour_cm.name,'') as courier,\n" +
                "ss.tracking_no as awbNo,\n" +
                "ss.sent_date as sentDate,\n" +
                "ss.sending_status as sentStatus,\n" +
                "ss.approval_status as approvalStatus\n" +
                "from sample_sent ss \n" +
                "left join shipping_instruction si on ss.si_id = si.id\n" +
                "left join company_master cour_cm on ss.courier_id = cour_cm.id\n" +
                "left join company_master client_cm on si.client_id = client_cm.id\n" +
                "left join company_master buyer_cm on si.buyer_id = buyer_cm.id\n" +
                "left join grade_master grade_gm on si.grade_id = grade_gm.id\n" +
                "where ss.approval_status <> 3\n" +
                "and (si.client_id = :clientId or :clientId = -1)\n" +
                "and (ss.sending_status = :sentStatus or :sentStatus = -1)\n" +
                "and (ss.approval_status = :approvalStatus or :approvalStatus = -1)\n";
        params.put("sentStatus", sentStatus);
        params.put("approvalStatus", approvalStatus);
        params.put("clientId", clientId);
        if (colName.equals("0")) {
            colName = "id";
        }
        if (colName != null && !colName.equals("-1")) {
            sql += "order by " + colName;
            order = order.equals("desc") ? " asc " : " desc ";
            sql += order;
        }
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id").addScalar("refNumber")
                .addScalar("siRef").addScalar("client")
                .addScalar("clientRef").addScalar("buyer")
                .addScalar("firstDate").addScalar("origin")
                .addScalar("quality").addScalar("grade")
                .addScalar("courier").addScalar("awbNo")
                .addScalar("sentDate").addScalar("sentStatus").addScalar("approvalStatus");

        for (String str : query.getNamedParameters()) {
            query.setParameter(str, params.get(str));
        }

        if (start != 0) {
            query.setFirstResult(start);
        }
        if (amount != 0) {
            query.setMaxResults(amount);
        }

        ArrayList<Object[]> result = (ArrayList<Object[]>) query.list();
        ArrayList<HashMap> list = new ArrayList<>();
        for (Object[] obj : result) {
            HashMap map = new HashMap();
            map.put("id", Common.getStringValue(obj[0]));
            map.put("refNumber", Common.getStringValue(obj[1]));
            map.put("siRef", Common.getStringValue(obj[2]));
            map.put("client", Common.getStringValue(obj[3]));
            map.put("clientRef", Common.getStringValue(obj[4]));
            map.put("buyer", Common.getStringValue(obj[5]));
            map.put("firstDate", obj[6] == null ? "" : Common.getDateFromDatabase((Date) obj[6], Common.date_format_a) );
            map.put("origin", Common.getStringValue(obj[7]));
            map.put("quality", Common.getStringValue(obj[8]));
            map.put("grade", Common.getStringValue(obj[9]));
            map.put("courier", Common.getStringValue(obj[10]));
            map.put("awbNo", Common.getStringValue(obj[11]));
            map.put("sentDate", obj[12] == null ? Common.getDateFromDatabase(new Date(), Common.date_format_a) : Common.getDateFromDatabase((Date) obj[12], Common.date_format_a) );
            Byte sStatus = Byte.parseByte(obj[13].toString());
            map.put("sentStatus", sStatus.equals(Byte.parseByte("0")) ? "Pending" : "Sent");
            Byte aStatus = Byte.parseByte(obj[14].toString());
            map.put("approvalStatus", aStatus.equals(Byte.parseByte("0")) ? "Pending" : (aStatus.equals(Byte.parseByte("1")) ? "Approved" : "Rejected"));
            list.add(map);
        }
        return list;
    }

    public JSONObject getSampleSentFullDetailById(int id) throws JSONException {

        String sql = "select ss.id as id,\n" +
                "ss.ref_number as refNumber,\n" +
                "ss.created_date as createdDate,\n" +
                "si.ref_number as siRef,\n" +
                "si.date as siDate,\n" +
                "ifnull(client_cm.name,'') as client,\n" +
                "ifnull(si.client_ref,'') as clientRef,\n" +
                "ifnull(supplier_cm.name,'') as supplier,\n" +
                "ifnull(si.supplier_ref,'') as supplierRef,\n" +
                "ifnull(shipper_cm.name,'') as shipper,\n" +
                "ifnull(si.shipper_ref,'') as shipperRef,\n" +
                "ifnull(buyer_cm.name,'') as buyer,\n" +
                "ifnull(si.buyer_ref,'') as buyerRef,\n" +
                "si.from_date as firstDate,\n" +
                "si.to_date as lastDate,\n" +
                "if(si.origin_id = 1, 'Vietnam', '') as origin,\n" +
                "if(si.quality_id = 1, 'Robusta', '') as quality,\n" +
                "ifnull(grade_gm.name,'') as grade,\n" +
                "ifnull(ss.courier_id,-1) as courier,\n" +
                "ss.tracking_no as awbNo,\n" +
                "ss.sent_date as sentDate,\n" +
                "ss.eta_date as etaDate,\n" +
                "ss.sending_status as sentStatus,\n" +
                "ss.approval_status as approvalStatus,\n" +
                "us.user_name as userName,\n" +
                "ss.updated_date as updatedDate,\n" +
                "si.weight_quality_certificate as qualityWeightCerti,\n" +
                "si.fumigation as fumigation\n" +
                "from sample_sent ss\n" +
                "left join shipping_instruction si on ss.si_id = si.id\n" +
                "left join company_master client_cm on si.client_id = client_cm.id\n" +
                "left join company_master buyer_cm on si.buyer_id = buyer_cm.id\n" +
                "left join company_master shipper_cm on si.buyer_id = shipper_cm.id\n" +
                "left join company_master supplier_cm on si.buyer_id = supplier_cm.id\n" +
                "left join grade_master grade_gm on si.grade_id = grade_gm.id\n" +
                "left join user us on ss.user_id = us.id\n" +
                "where ss.approval_status <> 3 and ss.id = :ssId";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id").addScalar("refNumber").addScalar("createdDate")
                .addScalar("siRef").addScalar("siDate").addScalar("client")
                .addScalar("clientRef").addScalar("supplier").addScalar("supplierRef")
                .addScalar("shipper").addScalar("shipperRef").addScalar("buyer")
                .addScalar("buyerRef").addScalar("firstDate").addScalar("lastDate")
                .addScalar("origin").addScalar("quality").addScalar("grade")
                .addScalar("courier").addScalar("awbNo").addScalar("sentDate")
                .addScalar("etaDate").addScalar("sentStatus").addScalar("approvalStatus")
                .addScalar("userName").addScalar("updatedDate").addScalar("qualityWeightCerti").addScalar("fumigation");
        query.setParameter("ssId", id);

        Object[] obj = (Object[]) query.uniqueResult();

        JSONObject ss = new JSONObject();

        ss.put("id", obj[0]);
        ss.put("refNumber", obj[1]);
        ss.put("createdDate", Common.getDateFromDatabase((Date) obj[2], Common.date_format));
        ss.put("siRef", obj[3]);
        ss.put("siDate", Common.getDateFromDatabase((Date) obj[4], Common.date_format));
        ss.put("client", obj[5]);
        ss.put("clientRef", obj[6]);
        ss.put("supplier", obj[7]);
        ss.put("supplierRef", obj[8]);
        ss.put("shipper", obj[9]);
        ss.put("shipperRef", obj[10]);
        ss.put("buyer", obj[11]);
        ss.put("buyerRef", obj[12]);
        ss.put("firstDate", obj[13] == null ? "" : Common.getDateFromDatabase((Date) obj[13], Common.date_format_a));
        ss.put("lastDate", obj[14] == null ? "" : Common.getDateFromDatabase((Date) obj[14], Common.date_format_a));
        ss.put("origin", obj[15]);
        ss.put("quality", obj[16]);
        ss.put("grade", obj[17]);
        ss.put("courier", obj[18]);
        ss.put("awbNo", obj[19]);
        ss.put("sentDate",obj[20] == null ? "" : Common.getDateFromDatabase((Date) obj[20], Common.date_format_a));
        ss.put("etaDate", obj[21] == null ? "" : Common.getDateFromDatabase((Date) obj[21], Common.date_format_a));
        Byte sStatus = Byte.parseByte(obj[22].toString());
        ss.put("sentStatus", sStatus);
        Byte aStatus = Byte.parseByte(obj[23].toString());
        ss.put("approvalStatus", aStatus);
        ss.put("userName", obj[24]);
        ss.put("updatedDate", Common.getDateFromDatabase((Date) obj[25], Common.date_format));
        ss.put("qualityWeightCerti", Common.getStringValue(obj[26]));
        ss.put("fumigation", Common.getStringValue(obj[27]));

        return ss;
    }
}
