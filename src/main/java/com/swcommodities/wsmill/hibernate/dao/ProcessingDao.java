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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ProcessingInstruction;
import com.swcommodities.wsmill.hibernate.dto.ProcessingType;
import com.swcommodities.wsmill.object.ProcessingInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
public class ProcessingDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public ProcessingDao setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    private final int reweighing = 10;
    private final int rebagging = 1;
    private long totalAfterFilter;

    public long getTotalAfterFilter() {
        return totalAfterFilter;
    }

    public void setTotalAfterFilter(long totalAfterFilter) {
        this.totalAfterFilter = totalAfterFilter;
    }

    public ArrayList<ProcessingType> getAllProcessTypes() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ProcessingType.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingType.class);
        crit.setProjection(
                Projections.projectionList().add(Projections.property("id"), "id")
                .add(Projections.property("name"), "name")).setResultTransformer(
                Transformers.aliasToBean(ProcessingType.class));
        return (ArrayList<ProcessingType>) crit.list();
    }

    public GradeMaster getPIGrades(int id) {
        //DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class);
        crit.createAlias("ProcessingInstruction", "pi");
        crit.createAlias("pi.gradeMaster", "gm");
        crit.add(Restrictions.eq("pi.id", id));
        crit.setProjection(
                Projections.projectionList().add(Projections.property("gm.id"), "id")
                .add(Projections.property("gm.name"), "name")).setResultTransformer(
                Transformers.aliasToBean(GradeMaster.class));
        return (GradeMaster) crit.uniqueResult();
    }

    public ProcessingType getTypeById(int id) {
        return (ProcessingType) sessionFactory.getCurrentSession().load(ProcessingType.class, id);
    }

    public ProcessingInstruction getPiByRef(String ref) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from ProcessingInstruction pi where pi.refNumber =:ref").setParameter("ref", ref);
        return (ProcessingInstruction) query.uniqueResult();
    }

    public ProcessingInstruction getPiById(int id) {
        return (ProcessingInstruction) sessionFactory.getCurrentSession().get(ProcessingInstruction.class, id);
    }

    public String getNewContractRef() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class);
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "PI-") : Common.getNewRefNumber(
                null, "PI-");
    }

    public Integer getLastestId() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class);
        crit.setProjection(Projections.max("id"));
        Integer cur_ref = (Integer) crit.uniqueResult();
        return (cur_ref != null) ? cur_ref : -1;
    }

    public int updateProcess(ProcessingInstruction process) {
        boolean isUpdated = false;
        if (process.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(process);
        if (isUpdated) {
            return 0;
        }
        return process.getId();
    }

    public ArrayList<ProcessingInstruction> getProcessingRefList(String searchString) {
        //DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class);
        crit.add(Restrictions.like("refNumber", "%" + searchString + "%"));
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.addOrder(Order.desc("refNumber"));
        return (ArrayList<ProcessingInstruction>) crit.list();
    }

    public ArrayList<Map> getPendingProcessingMap() {
        String sql = "select new map" + "(" + "id as id, refNumber as ref_number" + ") "
                + "from ProcessingInstruction as pi where pi.status = 0";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<Map>) query.list();
    }

    public ArrayList<ProcessingInstruction> getAllPIRefList() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class);
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.setProjection(
                Projections.projectionList().add(Projections.property("id"), "id")
                .add(Projections.property("refNumber"), "refNumber")).setResultTransformer(
                Transformers.aliasToBean(ProcessingInstruction.class));
        return (ArrayList<ProcessingInstruction>) crit.list();
    }

    public long countRow() {
        String sql = "select count(id) from processing_instruction";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
    }

    public ArrayList<ProcessingInstructionObj> searchProcessingIns(String searchTerm, String order,
            int start, int amount, String colName, int grade, int client, Byte status) {
        StringBuilder getSizeSql = new StringBuilder(
                "select cm.id as cmId, gm.id as gmId, pinst.id as pinstId, pinst.`status`, pinst.ref_number, pinst.from_date, pinst.to_date, gm.`name` as gmName, cm.`name` as cmName \n")
                .append("from processing_instruction pinst ")
                .append("left join grade_master gm on pinst.grade_id = gm.id ")
                .append("left join company_master cm on pinst.client_id = cm.id ")
                .append("where pinst.`status` != 2 "
                        + "and (gm.id = :grade or :grade = -1) "
                        + "and (cm.id = :cli or :cli = -1) "
                        + "and (pinst.`status` = :status or :status = -1) ");
        if (!searchTerm.equals("")) {
            getSizeSql.append(" and (pinst.ref_number like :searchTerm ")
                    .append(" or DATE_FORMAT(pinst.from_date,'%d-%b-%y') like :searchTerm ")
                    .append(" or DATE_FORMAT(pinst.to_date,'%d-%b-%y') like :searchTerm ")
                    .append(" or gm.`name` like :searchTerm ")
                    .append(" or cm.`name` like :searchTerm) ");
        }
        getSizeSql.append("GROUP BY pinst.id ");
        
        
        
        StringBuilder sql = new StringBuilder(
                "select cm.id, gm.id, pinst.id, pinst.ref_number, pinst.quality_id, pinst.origin_id, pinst.from_date, pinst.to_date, pinst.status, gm.`name` as grade_name, pm.`name` as packing_name, cm.`name` as client_name,\n"
                + "	(select ifnull(sum(wnr.gross_weight - wnr.tare_weight),0) from weight_note_receipt wnr left join wnr_allocation wna on wnr.id = wna.wnr_id where wna.inst_id = pinst.id and wna.inst_type = 'P' and wnr.`status` = 3) as allocated,\n"
                + "	(select ifnull(sum(wnr.gross_weight - wnr.tare_weight),0) from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = pinst.id and wn.type = 'IP' and wnr.`status` != 2 and wn.`status` != 2) as in_process,\n"
                + "	(select ifnull(sum(wnr.gross_weight - wnr.tare_weight),0) from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = pinst.id and wn.type = 'XP' and wnr.`status` != 2 and wn.`status` != 2) as ex_process,\n"
                + "	((select ifnull(sum(wnr.gross_weight - wnr.tare_weight),0) from weight_note_receipt wnr left join wnr_allocation wna on wnr.id = wna.wnr_id where wna.inst_id = pinst.id and wna.inst_type = 'P' and wnr.`status` = 3) - \n"
                + "	(select ifnull(sum(wnr.gross_weight - wnr.tare_weight),0) from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = pinst.id and wn.type = 'IP' and wnr.`status` != 2 and wn.`status` != 2)) as pending ")
                .append("from processing_instruction pinst ")
                .append("left join grade_master gm on pinst.grade_id = gm.id ")
                .append("left join packing_master pm on pinst.packing_id = pm.id ")
                .append("left join company_master cm on pinst.client_id = cm.id ")
                .append("where pinst.`status` != 2 "
                        + "and (gm.id = :grade or :grade = -1) "
                        + "and (cm.id = :cli or :cli = -1) "
                        + "and (pinst.`status` = :status or :status = -1) ");
        if (!searchTerm.equals("")) {
            sql.append(" and (pinst.ref_number like :searchTerm ")
                    .append(" or DATE_FORMAT(pinst.from_date,'%d-%b-%y') like :searchTerm ")
                    .append(" or DATE_FORMAT(pinst.to_date,'%d-%b-%y') like :searchTerm ")
                    .append(" or gm.`name` like :searchTerm ")
                    .append(" or cm.`name` like :searchTerm) ");
        }
        sql.append("GROUP BY pinst.id ");
        
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ")
                    .append((order.equals("desc") ? "desc" : "asc"));
        }
        
        Query getSizeQuery = sessionFactory.getCurrentSession().createSQLQuery(getSizeSql.toString());
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("pinst.id")
                .addScalar("pinst.ref_number").addScalar("pinst.origin_id")
                .addScalar("pinst.quality_id").addScalar("grade_name").addScalar("packing_name")
                .addScalar("allocated").addScalar("in_process").addScalar("ex_process")
                .addScalar("pending").addScalar("pinst.from_date").addScalar("pinst.to_date").addScalar("pinst.status");
        if (!searchTerm.equals("")) {
            getSizeQuery.setParameter("searchTerm", "%" + searchTerm + "%");
            query.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        
        
        getSizeQuery.setParameter("grade", grade);
        getSizeQuery.setParameter("cli", client);
        getSizeQuery.setParameter("status", status);
        
        
        this.setTotalAfterFilter(getSizeQuery.list().size());
        
        query.setParameter("grade", grade);
        query.setParameter("cli", client);
        query.setParameter("status", status);
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        // convert them to allocation object

        return convertToProcessingObject(obj_list);
    }

    public ArrayList<ProcessingInstructionObj> convertToProcessingObject(
            ArrayList<Object[]> obj_list) {
        // make sure the length obj is 26 [0-->25]
        if (obj_list != null && !obj_list.isEmpty()) {
            ArrayList<ProcessingInstructionObj> pis = new ArrayList<>();
            for (Object[] obj : obj_list) {
                ProcessingInstructionObj pi = new ProcessingInstructionObj();
                pi.setPi_id(Integer.parseInt(obj[0].toString()));
                pi.setRef_number(obj[1].toString());
                pi.setOrigin((Common.getIntegerValue(obj[2]).equals(1)) ? "VietNam" : "");
                pi.setQuality((Common.getIntegerValue(obj[3]).equals(1)) ? "Robusta" : "Arabica");
                pi.setGrade_name(obj[4].toString());
                pi.setPacking_name(obj[5].toString());
                pi.setAllocated(Float.parseFloat((obj[6] != null) ? obj[6].toString() : "0"));
                pi.setIn_process(Float.parseFloat((obj[7] != null) ? obj[7].toString() : "0"));
                pi.setEx_process(Float.parseFloat((obj[8] != null) ? obj[8].toString() : "0"));
                pi.setPending(Float.parseFloat((obj[9] != null) ? obj[9].toString() : "0"));
                pi.setFrom_date((Date) obj[10]);
                pi.setTo_date((Date) obj[11]);
                pi.setStatus(Byte.valueOf(obj[12].toString()));
                pis.add(pi);
            }
            return pis;
        }
        return null;
    }

    public Map countTotals(String searchTerm, int grade, int client, Byte status) {
        String sql = "call getTotalProcessing(:grade,:cli,:istatus,:searchTerm)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("grade", grade);
        query.setParameter("cli", client);
        query.setParameter("istatus", status);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("allocated", obj[0]);
        m.put("inprocess", obj[1]);
        m.put("exprocess", obj[2]);
        m.put("pending", obj[3]);
        return m;
    }

    public ArrayList<GradeMaster> getAllGrades() {
//        DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class)
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

    public ArrayList<CompanyMaster> getCompanyInPi() {
//        DetachedCriteria crit = DetachedCriteria.forClass(ProcessingInstruction.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ProcessingInstruction.class);
        crit.createAlias("companyMasterByClientId", "cm");
        crit.add(Restrictions.lt("status", Constants.DELETED));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("cm.id"), "id")
                .add(Projections.property("cm.name"), "name");
        crit.setProjection(Projections.distinct(projList));
        crit.setResultTransformer(Transformers.aliasToBean(CompanyMaster.class));
        return (ArrayList<CompanyMaster>) crit.list();
    }

    public Map countAllocated(int id) {
        String sql = "select \n"
                + "ifnull((select format(sum(wnr.gross_weight - wnr.tare_weight)/1000,3) \n"
                + "		from weight_note_receipt wnr \n"
                + "		left join wnr_allocation wna on wnr.id = wna.wnr_id \n"
                + "		where wna.inst_id = pinst.id and wna.inst_type = 'P' and wnr.`status` = 3),0) as allocated,\n"
                + "ifnull((select format(sum(wnr.gross_weight - wnr.tare_weight)/1000,3) \n"
                + "		from weight_note_receipt wnr \n"
                + "		left join weight_note wn on wnr.wn_id = wn.id \n"
                + "		where wn.inst_id = pinst.id and wn.type = 'IP' and wn.`status` <> 2 and wnr.`status` <> 2),0) as in_process,\n"
                + "ifnull((select format(sum(wnr.gross_weight - wnr.tare_weight)/1000,3) \n"
                + "		from weight_note_receipt wnr \n"
                + "		left join weight_note wn on wnr.wn_id = wn.id \n"
                + "		where wn.inst_id = pinst.id and wn.type = 'XP' and wn.`status` <> 2 and wnr.`status` <> 2),0) as ex_process,\n"
                + "format((ifnull((select sum(wnr.gross_weight - wnr.tare_weight)/1000 \n"
                + "		from weight_note_receipt wnr \n"
                + "		left join wnr_allocation wna on wnr.id = wna.wnr_id \n"
                + "		where wnr.`status` = 3 and wna.inst_id = pinst.id and wna.inst_type = 'P'),0) - ifnull((select sum(wnr.gross_weight - wnr.tare_weight)/1000 \n"
                + "																			from weight_note_receipt wnr \n"
                + "																			left join weight_note wn on wnr.wn_id = wn.id \n"
                + "																			where wn.inst_id = pinst.id and wn.type = 'IP' and wn.`status` <> 2 and wnr.`status` <> 2),0)),3) as pending,\n"
                + "format((ifnull((select sum(wnr.gross_weight - wnr.tare_weight)/1000 \n"
                + "		from weight_note_receipt wnr \n"
                + "		left join weight_note wn on wnr.wn_id = wn.id \n"
                + "		where wn.inst_id = pinst.id and wn.type = 'IP' and wn.`status` <> 2 and wnr.`status` <> 2),0) - \n"
                + "ifnull((select sum(wnr.gross_weight - wnr.tare_weight)/1000 \n"
                + "		from weight_note_receipt wnr \n"
                + "		left join weight_note wn on wnr.wn_id = wn.id \n"
                + "		where wn.inst_id = pinst.id and wn.type = 'XP' and wn.`status` <> 2 and wnr.`status` <> 2),0)),3) as weight_loss\n"
                + "from processing_instruction pinst\n" + "where pinst.id = :inst_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("inst_id", id);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("allocated", obj[0]);
        m.put("in_process", obj[1]);
        m.put("ex_process", obj[2]);
        m.put("pending", obj[3]);
        m.put("weight_loss", obj[4]);

        return m;
    }

    public Map countTotalAllocatedPiReport(int instid) {
        String sql = "call countTotalWnAllocatedPiReport(:instid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("instid", instid);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("tons", obj[0]);
        m.put("black", obj[1]);
        m.put("brown", obj[2]);
        m.put("fm", obj[3]);
        m.put("broken", obj[4]);
        m.put("moist", obj[5]);
        m.put("ocrop", obj[6]);
        m.put("moldy", obj[7]);
        m.put("asc20", obj[8]);
        m.put("sc20", obj[9]);
        m.put("sc19", obj[10]);
        m.put("sc18", obj[11]);
        m.put("sc17", obj[12]);
        m.put("sc16", obj[13]);
        m.put("sc15", obj[14]);
        m.put("sc14", obj[15]);
        m.put("sc13", obj[16]);
        m.put("sc12", obj[17]);
        m.put("bsc12", obj[18]);
        return m;
    }

    public ArrayList<Object[]> getPendingPiFromClient(int id) {
        ArrayList<HashMap> res = new ArrayList<>();
        String sql = "select pi.id as id, pi.ref_number as refNumber, client.name as client, client.id as clientId, pi.type_id as typeId from processing_instruction pi "
                + "left join company_master client on client.id = pi.client_id "
                + "where (client.id =:id or :id = -1) and pi.completionStatus1 = 'PENDING' ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", IntegerType.INSTANCE)
                .addScalar("refNumber", StringType.INSTANCE).addScalar("client", StringType.INSTANCE)
                .addScalar("clientId", IntegerType.INSTANCE).addScalar("typeId", IntegerType.INSTANCE);
        query.setParameter("id", id);
        ArrayList<Object[]> objs = (ArrayList<Object[]>) query.list();
        return objs;
    }

    public ArrayList<HashMap> getProcessTons(String type, int id) {
        ArrayList<HashMap> res = new ArrayList<>();
        String sql = "call TonsByPI(:type,:id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", IntegerType.INSTANCE)
                .addScalar("grade", StringType.INSTANCE).addScalar("tons", FloatType.INSTANCE)
                .addScalar("processingType", IntegerType.INSTANCE).setParameter("id", id)
                .setParameter("type", type);
        ArrayList<Object[]> objs = (ArrayList<Object[]>) query.list();
        if (objs != null && !objs.isEmpty()) {
            for (Object[] obj : objs) {
                HashMap map = new HashMap();
                map.put("grade_id", Common.getIntegerValue(obj[0]));
                map.put("grade", Common.getStringValue(obj[1]));
                map.put("tons", Common.getFloatValue(obj[2]));
                map.put("processingType", Common.getIntegerValue(obj[3]));
                res.add(map);
            }
        }
        return res;
    }

    public float getInprocessTonsByPi(int id) {
        String sql = "call InProcessTonsByPi(:id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", id);
        return Common.getFloatValue(query.uniqueResult());
    }

    public ArrayList<HashMap> getXprocessFromPi(int id) {
        ArrayList<HashMap> res = new ArrayList<>();
        String sql = "call XProcessTonsByPi(:id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("name", StringType.INSTANCE).addScalar("tons", FloatType.INSTANCE);
        query.setParameter("id", id);
        ArrayList<Object[]> objs = (ArrayList<Object[]>) query.list();
        if (objs != null && !objs.isEmpty()) {
            for (Object[] obj : objs) {
                HashMap map = new HashMap();
                map.put("pi_id", id);
                map.put("grade", Common.getStringValue(obj[0]));
                map.put("tons", Common.getFloatValue(obj[1]));
                res.add(map);
            }
        }
        return res;
    }

    public ArrayList<HashMap> getPendingPi(int clientid) {
        String str = "SELECT\n"
                + "di.id as id,\n"
                + "di.ref_number as ref_number,\n"
                + "IFNULL(di.origin_id, 1) as origin,\n"
                + "IFNULL(di.quality_id, 1) as quality,\n"
                + "gm.`name` as grade,\n"
                + "IFNULL(pm.`name`, '') as packing,\n"
                + "(\n"
                + "SELECT SUM(wnr2.gross_weight - wnr2.tare_weight)/1000\n"
                + "FROM weight_note_receipt wnr2 LEFT JOIN wnr_allocation wna ON wnr2.id = wna.wnr_id\n"
                + "WHERE wna.inst_id = di.id and wna.inst_type = 'P'\n"
                + ") as allocated,\n"
                + "SUM(CASE WHEN wn.type = 'IP'\n"
                + "THEN IFNULL(wnr.gross_weight - wnr.tare_weight,0) ELSE 0 END )/1000\n"
                + "as in_process,\n"
                + "SUM(CASE WHEN wn.type = 'XP'\n"
                + "THEN IFNULL(wnr.gross_weight - wnr.tare_weight,0) ELSE 0 END )/1000\n"
                + "as ex_process,\n"
                + "((\n"
                + "SELECT SUM(wnr2.gross_weight - wnr2.tare_weight)/1000\n"
                + "FROM weight_note_receipt wnr2 LEFT JOIN wnr_allocation wna ON wnr2.id = wna.wnr_id\n"
                + "WHERE wna.inst_id = di.id and wna.inst_type = 'P'\n"
                + ") - SUM(CASE WHEN wn.type = 'IP'\n"
                + "THEN IFNULL(wnr.gross_weight - wnr.tare_weight,0) ELSE 0 END )/1000) as pending,\n"
                + "di.from_date as from_date,\n"
                + "di.to_date as to_date\n"
                + "FROM processing_instruction di \n"
                + "LEFT JOIN grade_master gm ON gm.id = di.grade_id\n"
                + "LEFT JOIN packing_master pm ON pm.id = di.packing_id\n"
                + "LEFT JOIN weight_note wn ON wn.inst_id = di.id AND (wn.type = 'IP' OR wn.type = 'XP') AND wn.`status` <> 2\n"
                + "LEFT JOIN weight_note_receipt wnr ON wnr.wn_id = wn.id AND wnr.`status` <> 2\n"
                + "WHERE di.`status` = 0 AND (di.client_id = :clientid or :clientid = -1)\n"
                + "GROUP BY di.id\n" + "ORDER BY di.ref_number DESC";

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id")
                .addScalar("ref_number").addScalar("origin").addScalar("quality")
                .addScalar("grade").addScalar("packing").addScalar("allocated")
                .addScalar("in_process").addScalar("ex_process").addScalar("pending")
                .addScalar("from_date").addScalar("to_date");
        query.setParameter("clientid", clientid);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> dis = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap di = new HashMap();
            di.put("id", obj[0].toString());
            di.put("ref_number", obj[1].toString());
            di.put("origin", (obj[2].toString().equals("1")) ? "Vietnam" : "");
            di.put("quality", (obj[3].toString().equals("1")) ? "Robusta" : "");
            di.put("grade", obj[4].toString());
            di.put("packing", obj[5].toString());
            di.put("allocated",
                    (obj[6] != null) ? Float.parseFloat(obj[6].toString()) : Float.parseFloat("0"));
            di.put("in_process",
                    (obj[7] != null) ? Float.parseFloat(obj[7].toString()) : Float.parseFloat("0"));
            di.put("ex_process",
                    (obj[8] != null) ? Float.parseFloat(obj[8].toString()) : Float.parseFloat("0"));
            di.put("pending",
                    (obj[9] != null) ? Float.parseFloat(obj[9].toString()) : Float.parseFloat("0"));
            di.put("from_date",
                    (obj[10] != null) ? Common.getDateFromDatabase((Date) obj[10],
                    Common.date_format_ddMMyyyy_dash) : "");
            di.put("to_date",
                    (obj[11] != null) ? Common.getDateFromDatabase((Date) obj[11],
                    Common.date_format_ddMMyyyy_dash) : "");
            dis.add(di);
        }
        return dis;

    }

    public ArrayList<HashMap<String, Object>> searchProccessingInstruction(int client,
            String fromDate, String toDate) {
        ArrayList<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
        String sql = "select id,ref_number, created_date, client_id, type_id from processing_instruction "
                + "where status <> 2 and created_date < STR_TO_DATE(:toDate,'%d-%m-%Y') and created_date > STR_TO_DATE(:fromDate,'%d-%m-%Y') "
                + "and client_id = :clientId";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id", IntegerType.INSTANCE)
                .addScalar("ref_number", StringType.INSTANCE)
                .addScalar("created_date", DateType.INSTANCE)
                .addScalar("client_id", IntegerType.INSTANCE)
                .addScalar("type_id", IntegerType.INSTANCE)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("clientId", client);
        ArrayList<Object[]> object = new ArrayList<Object[]>(query.list());
        if (!object.isEmpty()) {
            for (Object[] obj : object) {
                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put("id", Common.getIntegerValue(obj[0]));
                map.put("refNumber", obj[1]);
                map.put("date", (Date) obj[2]);
                map.put("client", Common.getIntegerValue(obj[3]));
                int type = Common.getIntegerValue(obj[4]);
                map.put("type", (type == reweighing || type == rebagging) ? 1 : 0);

                res.add(map);
            }
        }
        return res;
    }
}
