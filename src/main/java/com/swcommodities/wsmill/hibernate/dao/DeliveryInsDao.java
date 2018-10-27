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
import org.hibernate.type.StringType;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.view.DeliveryView;
import com.swcommodities.wsmill.object.DeliveryInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author duhc
 */
public class DeliveryInsDao {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public DeliveryInsDao setSessionFactory(SessionFactory sessionFactory) {
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

    public DeliveryInstruction getDiByRef(String ref) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from DeliveryInstruction di where di.refNumber =:ref").setParameter("ref", ref);
        return (DeliveryInstruction) query.uniqueResult();
    }

    public String getRefById(int id) {
        String sql = "select ref_number from delivery_instruction where id =:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("ref_number", StringType.INSTANCE);
        query.setParameter("id", id);
        return (String) query.uniqueResult();
    }

    public String getNewContractRef() {
        //DetachedCriteria crit = DetachedCriteria.forClass(DeliveryInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeliveryInstruction.class);
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "DI-") : Common.getNewRefNumber(
                null, "DI-");
    }

    public int updateDeliveryIns(DeliveryInstruction deliveryIns) {
        boolean isUpdated = false;
        if (deliveryIns.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(deliveryIns);
        if (isUpdated) {
            return 0;
        }
        return deliveryIns.getId();
    }

    public ArrayList<DeliveryInstruction> getDeliveryInsRefList(String searchString) {
        //DetachedCriteria crit = DetachedCriteria.forClass(DeliveryInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeliveryInstruction.class);
        crit.add(Restrictions.like("refNumber", "%" + searchString + "%"));
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.addOrder(Order.desc("refNumber"));
        // return (ArrayList<DeliveryInstruction>)
        // getHibernateTemplate().findByCriteria(crit, firstResult, maxResults);
        return (ArrayList<DeliveryInstruction>) crit.list();
    }

    public ArrayList<DeliveryInstruction> getAllDIRefList() {
        //DetachedCriteria crit = DetachedCriteria.forClass(DeliveryInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeliveryInstruction.class);
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.setProjection(
                Projections.projectionList().add(Projections.property("id"), "id")
                .add(Projections.property("refNumber"), "refNumber")).setResultTransformer(
                Transformers.aliasToBean(DeliveryInstruction.class));
        return (ArrayList<DeliveryInstruction>) crit.list();
    }

    public DeliveryInstruction getDiById(int id) {
        return (DeliveryInstruction) sessionFactory.getCurrentSession().get(DeliveryInstruction.class, id);
    }

    public DeliveryView getLazyDiById(int id) {
        // String sql =
        // "select di.id as id, user.id as userId, client.id as clientId, controller.id as controllerId,\n"
        // +
        // "        supplier.id as supplierId, grade.id as gradeId, packing.id as packingId, warehouse.id as warehouseId,\n"
        // + "        qualityController.id as qualityControllerId,\n"
        // + "        pledger.id as pledgerId,\n"
        // + "        di.ref_number as refNumber,\n"
        // + "        di.client_ref as clientRef,\n"
        // + "        di.date as date,\n"
        // + "        di.supplier_ref as supplierRef,\n"
        // + "        di.tons as tons,\n"
        // + "        di.kg_per_bag as kgPerBag,\n"
        // + "        di.no_of_bags as noOfBags,\n"
        // + "        di.delivery_date as deliveryDate,\n"
        // + "        di.from_time as fromTime,\n"
        // + "        di.to_time as toTime,\n"
        // + "        di.marking_on_bags as markingOnBags,\n"
        // + "        di.origin_id as originId,\n"
        // + "        di.quality_id as qualityId,\n"
        // + "        di.remark as remark,\n"
        // + "        di.status as status,\n"
        // + "        di.log as log \n"
        // + "        from delivery_instruction di \n"
        // + "        left join user on user.id = di.user_id \n"
        // +
        // "        left join company_master client on client.id = di.client_id \n"
        // +
        // "        left join company_master controller on controller.id = di.weight_controller_id \n"
        // +
        // "        left join company_master supplier on supplier.id = di.supplier_id \n"
        // + "        left join grade_master grade on grade.id = di.grade_id \n"
        // +
        // "        left join packing_master packing on packing.id = di.packing_id \n"
        // +
        // "        left join company_master qualityController on qualityController.id = di.quality_controller_id \n"
        // +
        // "        left join company_master pledger on pledger.id = di.pledger \n"
        // + "        left join warehouse on warehouse.id = di.warehouse_id\n"
        // + "where di.id =:id";
        // Query query = getSession().createSQLQuery(sql)
        // .addScalar("id", IntegerType.INSTANCE)
        // .addScalar("userId", IntegerType.INSTANCE)
        // .addScalar("clientId", IntegerType.INSTANCE)
        // .addScalar("controllerId", IntegerType.INSTANCE)
        // .addScalar("supplierId", IntegerType.INSTANCE)
        // .addScalar("gradeId", IntegerType.INSTANCE)
        // .addScalar("packingId", IntegerType.INSTANCE)
        // .addScalar("warehouseId", IntegerType.INSTANCE)
        // .addScalar("qualityControllerId", IntegerType.INSTANCE)
        // .addScalar("pledgerId", IntegerType.INSTANCE)
        // .addScalar("refNumber", StringType.INSTANCE)
        // .addScalar("clientRef", StringType.INSTANCE)
        // .addScalar("date", DateType.INSTANCE)
        // .addScalar("supplierRef", StringType.INSTANCE)
        // .addScalar("tons", Hibernate.DOUBLE)
        // .addScalar("kgPerBag", FloatType.INSTANCE)
        // .addScalar("noOfBags", IntegerType.INSTANCE)
        // .addScalar("deliveryDate", DateType.INSTANCE)
        // .addScalar("fromTime", StringType.INSTANCE)
        // .addScalar("toTime", StringType.INSTANCE)
        // .addScalar("markingOnBags", StringType.INSTANCE)
        // .addScalar("originId", IntegerType.INSTANCE)
        // .addScalar("qualityId", IntegerType.INSTANCE)
        // .addScalar("remark", StringType.INSTANCE)
        // .addScalar("status", ByteType.INSTANCE)
        // .addScalar("log", StringType.INSTANCE);
        // query.setParameter("id", id);
        // return (DeliveryView) query.setResultTransformer(new
        // AliasToBeanResultTransformer(DeliveryView.class)).uniqueResult();
        return (DeliveryView) sessionFactory.getCurrentSession().get(DeliveryView.class, id);
    }

    public long countRow() {
        String sql = "select count(id) from delivery_instruction";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
    }

    public ArrayList<CompanyMaster> getCompanyInDi(String companyType) {
        String cmAlias = "";
        switch (companyType) {
            case "supplier":
                cmAlias = "companyMasterBySupplierId";
                break;
            case "buyer":
                cmAlias = "companyMasterByClientId";
                break;
        }

//        DetachedCriteria crit = DetachedCriteria.forClass(DeliveryInstruction.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeliveryInstruction.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.createAlias(cmAlias, "cm");
        crit.add(Restrictions.lt("status", Constants.DELETED));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("cm.id"), "id")
                .add(Projections.property("cm.name"), "name");
        crit.setProjection(Projections.distinct(projList));
        crit.setResultTransformer(Transformers.aliasToBean(CompanyMaster.class));
        return (ArrayList<CompanyMaster>) crit.list();
    }

    public ArrayList<GradeMaster> getAllGrades() {
//        DetachedCriteria crit = DetachedCriteria.forClass(DeliveryInstruction.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeliveryInstruction.class)
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

    //old
//	public ArrayList<DeliveryInstructionObj> searchDeliveryIns(String searchTerm, String order,
//			int start, int amount, String colName, int grade, int sup, int buy, Byte status) {
//		StringBuilder sql = new StringBuilder(
//				"select gm.id, cm1.id, cm.id, di.id, di.ref_number, cm.`name` as buyer_name, cm1.`name` as supp_name, di.origin_id, di.quality_id, gm.`name` as grade_name, pm.`name` as packing_name, di.tons, di.`status` as isDelivered, di.delivery_date, di.from_time, di.to_time,")
//				.append("	(select sum(wnr.gross_weight - wnr.tare_weight)/1000 from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = di.id and wn.type = 'IM' and wn.`status` != 2 and wnr.`status` != 2) as net_weight,")
//				.append("	(di.tons - (select sum(wnr.gross_weight - wnr.tare_weight)/1000 from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = di.id and wn.type = 'IM' and wn.`status` != 2 and wnr.`status` != 2)) as pending  ")
//				.append("from delivery_instruction di ")
//				.append("left join grade_master gm on di.grade_id = gm.id ")
//				.append("left join company_master cm on di.client_id = cm.id ")
//				.append("left join packing_master pm on di.packing_id = pm.id ")
//				.append("left join company_master cm1 on di.supplier_id = cm1.id ")
//				.append("where di.`status` != 2 ").append("GROUP BY di.id ")
//				.append("having (gm.id = :grade or :grade = -1) ")
//				.append("and (cm1.id = :supplier or :supplier = -1) ")
//				.append("and (cm.id = :buyer or :buyer = -1) ")
//				.append("and (di.`status` = :status or :status = -1) ");
//		if (!searchTerm.equals("")) {
//			sql.append(" and( di.ref_number like :searchTerm")
//					.append(" or DATE_FORMAT(di.delivery_date,'%d-%b-%y') like :searchTerm")
//					.append(" or di.tons like :searchTerm")
//					.append(" or gm.`name` like :searchTerm")
//					.append(" or cm.`name` like :searchTerm")
//					.append(" or cm1.`name` like :searchTerm)");
//		}
//		if (!colName.equals("0")) {
//			sql.append(" order by ").append(colName).append(" ")
//					.append((order.equals("desc") ? "desc" : "asc"));
//		}
//
//		Query query = getSession().createSQLQuery(sql.toString()).addScalar("di.id")
//				.addScalar("di.ref_number").addScalar("buyer_name").addScalar("supp_name")
//				.addScalar("di.origin_id").addScalar("di.quality_id").addScalar("grade_name")
//				.addScalar("packing_name").addScalar("di.tons").addScalar("isDelivered")
//				.addScalar("di.delivery_date").addScalar("di.from_time").addScalar("di.to_time")
//				.addScalar("net_weight").addScalar("pending");
//		if (!searchTerm.equals("")) {
//			query.setParameter("searchTerm", "%" + searchTerm + "%");
//		}
//		query.setParameter("grade", grade);
//		query.setParameter("supplier", sup);
//		query.setParameter("buyer", buy);
//		query.setParameter("status", status);
//		this.setTotalAfterFilter(query.list().size());
//		query.setFirstResult(start);
//		query.setMaxResults(amount);
//
//		ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
//		// convert them to allocation object
//
//		return convertToDeliveryObject(obj_list);
//	}
    public ArrayList<DeliveryInstructionObj> searchDeliveryIns(String searchTerm, String order,
            int start, int amount, String colName, int grade, int sup, int buy, Byte status) {
        StringBuilder sqlGetSize = new StringBuilder(
                "select gm.id, cm1.id, cm.id, di.id, di.ref_number, cm.`name` as buyer_name, cm1.`name` as supp_name, di.origin_id, di.quality_id, gm.`name` as grade_name, pm.`name` as packing_name, di.tons, di.`status` as isDelivered, di.delivery_date, di.from_time, di.to_time, ")
                .append("'net_weight', ")
                .append("'pending' ")
                .append("FROM delivery_instruction di ")
                .append("left join grade_master gm on di.grade_id = gm.id ")
                .append("left join company_master cm on di.client_id = cm.id ")
                .append("left join packing_master pm on di.packing_id = pm.id ")
                .append("left join company_master cm1 on di.supplier_id = cm1.id ")
                .append("where di.`status` != 2 ").append("GROUP BY di.id ")
                .append("having (gm.id = :grade or :grade = -1) ")
                .append("and (cm1.id = :supplier or :supplier = -1) ")
                .append("and (cm.id = :buyer or :buyer = -1) ")
                .append("and (di.`status` = :status or :status = -1) ");

        StringBuilder sql = new StringBuilder(
                "select gm.id, cm1.id, cm.id, di.id, di.ref_number, cm.`name` as buyer_name, cm1.`name` as supp_name, di.origin_id, di.quality_id, gm.`name` as grade_name, pm.`name` as packing_name, di.tons, di.`status` as isDelivered, di.delivery_date, di.from_time, di.to_time, ")
                .append("b.net_weight, ")
                .append("b.pending ")
                .append("from delivery_instruction di ")
                .append("left join grade_master gm on di.grade_id = gm.id ")
                .append("left join company_master cm on di.client_id = cm.id ")
                .append("left join packing_master pm on di.packing_id = pm.id ")
                .append("left join company_master cm1 on di.supplier_id = cm1.id ")
                .append("left join (select di.id as delivery_instruction_id, sum(wnr.gross_weight - wnr.tare_weight)/1000 as net_weight, (di.tons - (sum(wnr.gross_weight - wnr.tare_weight)/1000)) as pending")
                .append("              from weight_note_receipt wnr")
                .append("              left join weight_note wn on wnr.wn_id = wn.id")
                .append("              left join delivery_instruction di on wn.inst_id = di.id")
                .append("              where wn.type = 'IM' and wn.`status` != 2 and wnr.`status` != 2 group by di.id) b on di.id = b.delivery_instruction_id ")
                .append("where di.`status` != 2 ").append("GROUP BY di.id ")
                .append("having (gm.id = :grade or :grade = -1) ")
                .append("and (cm1.id = :supplier or :supplier = -1) ")
                .append("and (cm.id = :buyer or :buyer = -1) ")
                .append("and (di.`status` = :status or :status = -1) ");

        /* create query for get size of DI pending*/
        Query query = setQuery(sqlGetSize, searchTerm, order, colName, grade, sup, buy, status);

        /* get size of DI pending */
        this.setTotalAfterFilter(query.list().size());

        /* create query for get table DI pending*/
        query = setQuery(sql, searchTerm, order, colName, grade, sup, buy, status);
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        // convert them to allocation object

        return convertToDeliveryObject(obj_list);
    }

    private Query setQuery(StringBuilder sql, String searchTerm, String order, String colName,
            int grade, int sup, int buy, Byte status) {
        if (!searchTerm.equals("")) {
            sql.append(" and( di.ref_number like :searchTerm")
                    .append(" or DATE_FORMAT(di.delivery_date,'%d-%b-%y') like :searchTerm")
                    .append(" or di.tons like :searchTerm")
                    .append(" or gm.`name` like :searchTerm")
                    .append(" or cm.`name` like :searchTerm")
                    .append(" or cm1.`name` like :searchTerm)");
        }
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ")
                    .append((order.equals("desc") ? "desc" : "asc"));
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("di.id")
                .addScalar("di.ref_number").addScalar("buyer_name").addScalar("supp_name")
                .addScalar("di.origin_id").addScalar("di.quality_id").addScalar("grade_name")
                .addScalar("packing_name").addScalar("di.tons").addScalar("isDelivered")
                .addScalar("di.delivery_date").addScalar("di.from_time").addScalar("di.to_time")
                .addScalar("net_weight").addScalar("pending");
        if (!searchTerm.equals("")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        query.setParameter("grade", grade);
        query.setParameter("supplier", sup);
        query.setParameter("buyer", buy);
        query.setParameter("status", status);

        return query;
    }

    public ArrayList<DeliveryInstructionObj> convertToDeliveryObject(ArrayList<Object[]> obj_list) {
        // make sure the length obj is 26 [0-->25]
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<DeliveryInstructionObj> dis = new ArrayList<>();
            for (Object[] obj : obj_list) {
                DeliveryInstructionObj di = new DeliveryInstructionObj();
                di.setDi_id(Integer.parseInt(obj[0].toString()));
                di.setRef_number(obj[1].toString());
                di.setBuyer_name(obj[2].toString());
                di.setSupp_name(obj[3].toString());
                di.setOrigin((Integer.parseInt(obj[4].toString()) == 1) ? "VietNam" : "");
                di.setQuality((Integer.parseInt(obj[5].toString()) == 1) ? "Robusta" : "Arabica");
                di.setGrade_name(obj[6].toString());
                di.setPacking_name(obj[7].toString());
                di.setTons(Float.parseFloat((obj[8] != null) ? obj[8].toString() : "0"));
                di.setIsDelivery(Byte.parseByte(obj[9].toString()));
                di.setDelivery_date((Date) obj[10]);
                di.setFrom_time((obj[11] != null) ? obj[11].toString() : "");
                di.setTo_time((obj[12] != null) ? obj[12].toString() : "");
                di.setNet_weight(Float.parseFloat((obj[13] != null) ? obj[13].toString() : "0"));
                di.setPendding(Float.parseFloat((obj[14] != null) ? obj[14].toString() : "0"));
                dis.add(di);
            }
            return dis;
        }
        return null;
    }

    public Map countTotals(String searchTerm, int grade, int sup, int buy, Byte status) {
        String sql = "call getTotalDelivery(:grade,:supplier,:buyer,:istatus,:searchTerm)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("grade", grade);
        query.setParameter("supplier", sup);
        query.setParameter("buyer", buy);
        query.setParameter("istatus", status);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("total", obj[0]);
        m.put("delivered", obj[1]);
        m.put("pending", obj[2]);
        return m;
    }

    public Map countDelivered(int id, Byte status) {
        String sql = "SELECT\n"
                + "IFNULL((SELECT SUM(wnr.gross_weight - wnr.tare_weight)/1000 \n"
                + "FROM weight_note_receipt wnr \n"
                + "LEFT JOIN weight_note wn on wnr.wn_id = wn.id \n"
                + "WHERE wn.inst_id = di.id AND wn.type = 'IM' AND wn.`status` <> 2 AND wnr.`status` <> 2), 0) as delivered,\n"
                + "(di.tons - (SELECT IFNULL(SUM(wnr.gross_weight - wnr.tare_weight)/1000,0) \n"
                + "FROM weight_note_receipt wnr \n"
                + "LEFT JOIN weight_note wn on wnr.wn_id = wn.id \n"
                + "WHERE wn.inst_id = di.id AND wn.type = 'IM' AND wn.`status` <> 2 AND wnr.`status` <> 2))  as pend_or_can \n"
                + "FROM delivery_instruction di\n" + "WHERE di.id = :inst_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("inst_id", id);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("delivered", obj[0]);
        if (status == Constants.COMPLETE) {
            m.put("pending", 0);
            if (Float.parseFloat(obj[1] + "") > 0) {
                m.put("canceled", obj[1]);
            } else {
                m.put("canceled", 0);
            }
        } else {
            m.put("pending", obj[1]);
            m.put("canceled", 0);
        }
        return m;
    }

    public boolean checkInstDeletable(int id, String type) {
        String inst_table = "";
        switch (type) {
            case "IM":
                inst_table = "delivery_instruction";
                break;
            case "IP":
            case "XP":
                inst_table = "processing_instruction";
                break;
            case "EX":
                inst_table = "shipping_instruction";
                break;
        }
        StringBuilder sql = new StringBuilder("SELECT  count(inst.id)\n"
                + "FROM weight_note_receipt wnr\n"
                + "LEFT JOIN weight_note wn ON wn.id = wnr.wn_id\n" + "LEFT JOIN " + inst_table
                + " inst ON wn.inst_id = inst.id\n"
                + "WHERE inst.id = :instid AND wn.type = :insttype \n" + "AND \n"
                + "(inst.`status` = 1\n" + "OR wn.`status` = 1\n" + "OR wnr.`status` = 3\n"
                + "OR wn.wrc_id != null)");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        query.setParameter("instid", id);
        query.setParameter("insttype", type);
        BigInteger countResult = (BigInteger) query.uniqueResult();
        if (countResult.intValue() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<DeliveryInstructionObj> getImportReportInfo(int client_id, String from,
            String to) {
        String sql = "CALL getImportReportInfo(:client_id,:period_from,:period_to)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("di_id").addScalar("ref_number")
                .addScalar("client").addScalar("supplier").addScalar("origin").addScalar("quality")
                .addScalar("grade").addScalar("packing").addScalar("tons").addScalar("delivered")
                .addScalar("pending").addScalar("delivery_date").addScalar("from_time")
                .addScalar("to_time").addScalar("ct_ref");
        query.setParameter("client_id", client_id);
        query.setParameter("period_from", from);
        query.setParameter("period_to", to);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        if (obj_list != null || !obj_list.isEmpty()) {
            ArrayList<DeliveryInstructionObj> dis = new ArrayList<>();
            for (Object[] obj : obj_list) {
                DeliveryInstructionObj di = new DeliveryInstructionObj();
                di.setDi_id(Integer.parseInt(obj[0].toString()));
                di.setRef_number(obj[1].toString());
                di.setBuyer_name(obj[2].toString());
                di.setSupp_name(obj[3].toString());
                di.setOrigin(obj[4].toString());
                di.setQuality(obj[5].toString());
                di.setGrade_name(obj[6].toString());
                di.setPacking_name(obj[7].toString());
                di.setTons(Float.parseFloat((obj[8] != null) ? obj[8].toString() : "0"));
                di.setNet_weight(Float.parseFloat((obj[9] != null) ? obj[9].toString() : "0"));
                di.setPendding(Float.parseFloat((obj[10] != null) ? obj[10].toString() : "0"));
                di.setDelivery_date((obj[11] != null) ? (Date) obj[11] : null);
                di.setFrom_time(obj[12] != null ? obj[12].toString() : "");
                di.setTo_time(obj[13] != null ? obj[13].toString() : "");
                di.setCt_ref(obj[14] != null ? obj[14].toString() : "");
                dis.add(di);
            }
            return dis;
        }
        return null;
    }

    public ArrayList<HashMap> getPendingDi(int clientid) {
        String str = "SELECT\n"
                + "di.id as id,\n"
                + "di.ref_number as ref_number,\n"
                + "IFNULL(cm.`name`, '') as supplier,\n"
                + "IFNULL(di.origin_id, 1) as origin,\n"
                + "IFNULL(di.quality_id, 1) as quality,\n"
                + "gm.`name` as grade,\n"
                + "IFNULL(pm.`name`, '') as packing,\n"
                + "di.tons as tons,\n"
                + "IFNULL(SUM(wnr.gross_weight - wnr.tare_weight)/1000,0) as delivered,\n"
                + "(di.tons - IFNULL(SUM(wnr.gross_weight - wnr.tare_weight)/1000,0)) as pending,\n"
                + "di.date as created_date,\n"
                + "IFNULL(di.from_time, '') as from_time,\n"
                + "IFNULL(di.to_time,'') as to_time\n"
                + "FROM delivery_instruction di \n"
                + "LEFT JOIN company_master cm ON cm.id = di.supplier_id\n"
                + "LEFT JOIN grade_master gm ON gm.id = di.grade_id\n"
                + "LEFT JOIN packing_master pm ON pm.id = di.packing_id\n"
                + "LEFT JOIN weight_note wn ON wn.inst_id = di.id AND wn.type = 'IM' AND wn.`status` <> 2\n"
                + "LEFT JOIN weight_note_receipt wnr ON wnr.wn_id = wn.id AND wnr.`status` <> 2\n"
                + "WHERE di.`status` = 0 AND (di.client_id = :clientid or :clientid = -1)\n"
                + "GROUP BY di.id\n" + "ORDER BY di.ref_number DESC";

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id")
                .addScalar("ref_number").addScalar("supplier").addScalar("origin")
                .addScalar("quality").addScalar("grade").addScalar("packing").addScalar("tons")
                .addScalar("delivered").addScalar("pending").addScalar("created_date")
                .addScalar("from_time").addScalar("to_time");
        query.setParameter("clientid", clientid);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> dis = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap di = new HashMap();
            di.put("id", obj[0].toString());
            di.put("ref_number", obj[1].toString());
            di.put("supplier", obj[2].toString());
            di.put("origin", (obj[3].toString().equals("1")) ? "Vietnam" : "");
            di.put("quality", (obj[4].toString().equals("1")) ? "Robusta" : "Arabica");
            di.put("grade", obj[5].toString());
            di.put("packing", obj[6].toString());
            di.put("tons", Float.parseFloat(obj[7].toString()));
            di.put("delivered", Float.parseFloat(obj[8].toString()));
            di.put("pending", Float.parseFloat(obj[9].toString()));
            di.put("created_date",
                    Common.getDateFromDatabase((Date) obj[10], Common.date_format_ddMMyyyy_dash));
            di.put("from_time", obj[11].toString());
            di.put("to_time", obj[12].toString());
            dis.add(di);
        }
        return dis;

    }
}
