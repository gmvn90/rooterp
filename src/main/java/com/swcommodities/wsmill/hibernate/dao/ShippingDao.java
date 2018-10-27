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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;
import com.swcommodities.wsmill.hibernate.dto.ShippingInstruction;
import com.swcommodities.wsmill.object.ShippingInstructionObj;
import com.swcommodities.wsmill.utils.Common;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author duhc
 */
public class ShippingDao {
    public SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public ShippingDao setSessionFactory(SessionFactory sessionFactory) {
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

    public ArrayList<ShippingInstruction> getShippingRefList(String searchString) {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingInstruction.class);
        crit.add(Restrictions.like("refNumber", "%" + searchString + "%"));
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.addOrder(Order.desc("refNumber"));
        return (ArrayList<ShippingInstruction>) crit.list();
    }

    public ShippingInstruction getSiById(int id) {
        return (ShippingInstruction) sessionFactory.getCurrentSession().get(ShippingInstruction.class, id);
    }

    public ShippingInstruction getFullSiById(int id) {
        String sql = "from ShippingInstruction as si\n"
                + "left outer join fetch si.companyMasterByClientId as companyMasterByClientId\n"
                + "left outer join fetch si.companyMasterByShipperId as companyMasterByShipperId\n"
                + "left outer join fetch si.portMasterByDischargePortId as portMasterByDischargePortId\n"
                + "left outer join fetch si.companyMasterByQualityCertId as companyMasterByQualityCertId\n"
                + "left outer join fetch si.packingMaster as packingMaster\n"
                + "left outer join fetch si.portMasterByLoadingPortId as portMasterByLoadingPortId\n"
                + "left outer join fetch si.companyMasterByWeightCertId as companyMasterByWeightCertId\n"
                + "left outer join fetch si.companyMasterByConsigneeId as companyMasterByConsigneeId\n"
                + "left outer join fetch si.companyMasterBySupplierId as companyMasterBySupplierId\n"
                + "left outer join fetch si.gradeMaster as gradeMaster\n"
                + "left outer join fetch si.shippingLineMaster as shippingLineMaster\n"
                //+ "left outer join fetch si.user as user\n"
                + "where si.id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("id", id);
        return (ShippingInstruction) query.uniqueResult();
    }

    public ShippingInstruction getSiByRef(String ref) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ShippingInstruction si where si.refNumber =:ref").setParameter("ref", ref);
        return (ShippingInstruction) query.uniqueResult();
    }

    public String getNewContractRef() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingInstruction.class);
        crit.setProjection(Projections.max("refNumber"));
        String cur_ref = (String) crit.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "SI-") : Common.getNewRefNumber(null, "SI-");
    }

    public Integer getLatestId() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingInstruction.class);
        crit.setProjection(Projections.max("id"));
        Integer cur_ref = (Integer) crit.uniqueResult();
        return (cur_ref != null) ? cur_ref : -1;
    }

    public int updateShip(ShippingInstruction ship) {
        boolean isUpdated = false;
        if (ship.getId() != null) {
            isUpdated = true;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(ship);
        if (isUpdated) {
            return 0;
        }
        return ship.getId();
    }

    public long countRow() {
        String sql = "select count(id) from shipping_instruction";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return Long.parseLong(((BigInteger) query.uniqueResult()).toString());
    }

//    public ArrayList<ShippingInstructionObj> searchShippingIns(String searchTerm, String order, int start, int amount, String colName, int grade, int sup, Byte status) {
//        StringBuilder sql = new StringBuilder("select gm.id, cm.id, si.id, si.ref_number, cm.`name` as supplier_name, \n"
//                + "    si.origin_id, si.quality_id, gm.`name` as grade_name, si.`status`,\n"
//                + "	pm.`name` as packing_name, si.quantity as tons, \n"
//                + "	si.from_date as from_date, si.to_date as to_date,\n"
//                + "	(select sum(wnr.gross_weight - wnr.tare_weight) from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = si.id and wn.type = 'EX' AND wn.`status` <> 2 AND wnr.`status` <> 2) as delivered,\n"
//                + "	(si.quantity*1000 - (select sum(wnr.gross_weight - wnr.tare_weight) from weight_note_receipt wnr left join weight_note wn on wnr.wn_id = wn.id where wn.inst_id = si.id and wn.type = 'EX' AND wnr.`status` <> 2 AND wn.`status` <> 2)) as pending ")
//                .append("from shipping_instruction si ")
//                .append("left join grade_master gm on si.grade_id = gm.id ")
//                .append("left join packing_master pm on si.packing_id = pm.id ")
//                .append("left join company_master cm on si.supplier_id = cm.id ")
//                .append("where si.`status` != 2 ")
//                .append("GROUP BY si.id ")
//                .append("having (gm.id = :grade or :grade = -1) ")
//                .append("and (cm.id = :supplier or :supplier = -1) ")
//                .append("and (si.`status` = :status or :status = -1) ");
//        if (!searchTerm.equals("")) {
//            sql.append(" and (si.ref_number like :searchTerm")
//                    .append(" or DATE_FORMAT(si.from_date,'%d-%b-%y') like :searchTerm")
//                    .append(" or DATE_FORMAT(si.to_date,'%d-%b-%y') like :searchTerm")
//                    .append(" or si.quantity like :searchTerm")
//                    .append(" or gm.`name` like :searchTerm")
//                    .append(" or cm.`name` like :searchTerm")
//                    .append(" or pm.`name` like :searchTerm)");
//        }
//        if (!colName.equals("0")) {
//            sql.append(" order by ").append(colName).append(" ").append((order.equals("desc") ? "desc" : "asc"));
//        }
//
//        Query query = getSession().createSQLQuery(sql.toString())
//                .addScalar("si.id")
//                .addScalar("si.ref_number")
//                .addScalar("supplier_name")
//                .addScalar("si.origin_id")
//                .addScalar("si.quality_id")
//                .addScalar("grade_name")
//                .addScalar("packing_name")
//                .addScalar("tons")
//                .addScalar("delivered")
//                .addScalar("pending")
//                .addScalar("from_date")
//                .addScalar("to_date");
//        if (!searchTerm.equals("")) {
//            query.setParameter("searchTerm", "%" + searchTerm + "%");
//        }
//        query.setParameter("grade", grade);
//        query.setParameter("supplier", sup);
//        query.setParameter("status", status);
//        this.setTotalAfterFilter(query.list().size());
//        query.setFirstResult(start);
//        query.setMaxResults(amount);
//
//        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
//        //convert them to allocation object
//
//        return convertToShippingObject(obj_list);
//    }
    public ArrayList<ShippingInstructionObj> searchShippingIns(String searchTerm, String order,
            int start, int amount, String colName, int grade, int sup, Byte status) {
        StringBuilder sqlGetSize = new StringBuilder(
                "select gm.id, cm.id, si.id, si.ref_number, cm.`name` as supplier_name, \n"
                + "    si.origin_id, si.quality_id, gm.`name` as grade_name, si.`status`,\n"
                + "	pm.`name` as packing_name, si.quantity as tons, \n"
                + "	si.from_date as from_date, si.to_date as to_date,\n")
                .append("'net_weight', ")
                .append("'pending' ")
                .append("FROM shipping_instruction si ")
                .append("left join grade_master gm on si.grade_id = gm.id ")
                .append("left join packing_master pm on si.packing_id = pm.id ")
                .append("left join company_master cm on si.supplier_id = cm.id ")
                .append("where si.`status` != 2 ").append("GROUP BY si.id ")
                .append("having (gm.id = :grade or :grade = -1) ")
                .append("and (cm.id = :supplier or :supplier = -1) ")
                .append("and (si.`status` = :status or :status = -1) ");

        StringBuilder sql = new StringBuilder(
                "select gm.id, cm.id, si.id, si.ref_number, cm.`name` as supplier_name, \n"
                + "    si.origin_id, si.quality_id, gm.`name` as grade_name, si.`status`,\n"
                + "	pm.`name` as packing_name, si.quantity as tons, \n"
                + "	si.from_date as from_date, si.to_date as to_date,\n")
                .append("b.net_weight, ")
                .append("b.pending ")
                .append("FROM shipping_instruction si ")
                .append("left join grade_master gm on si.grade_id = gm.id ")
                .append("left join packing_master pm on si.packing_id = pm.id ")
                .append("left join company_master cm on si.supplier_id = cm.id ")
                .append("left join (select si.id as shipping_instruction_id, sum(wnr.gross_weight - wnr.tare_weight)/1000 as net_weight, (si.quantity - 'net_weight') as pending")
                .append("              from weight_note_receipt wnr")
                .append("              left join weight_note wn on wnr.wn_id = wn.id")
                .append("              left join shipping_instruction si on wn.inst_id = si.id")
                .append("              where wn.type = 'EX' and wn.`status` != 2 and wnr.`status` != 2 group by si.id) b on si.id = b.shipping_instruction_id ")
                .append("where si.`status` != 2 ").append("GROUP BY si.id ")
                .append("having (gm.id = :grade or :grade = -1) ")
                .append("and (cm.id = :supplier or :supplier = -1) ")
                .append("and (si.`status` = :status or :status = -1) ");

        /* create query for get size of SI pending*/
        Query query = setQuery(sqlGetSize, searchTerm, order, colName, grade, sup, status);

        /* get size of SI pending */
        this.setTotalAfterFilter(query.list().size());

        /* create query for get table SI pending*/
        query = setQuery(sql, searchTerm, order, colName, grade, sup, status);
        query.setFirstResult(start);
        query.setMaxResults(amount);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        // convert them to allocation object

        return convertToShippingObject(obj_list);
    }

    public ArrayList<HashMap> searchShippingIns_v2(String order, int start, int amount,
                                                String colName, int gradeId, int clientId, Byte status) throws ParseException {
        Map<String, Object> params = new HashMap<String,Object>();
        String sql = "select  DISTINCT si.id as siId,\n" +
                "si.ref_number as refNumber,\n" +
                "clientCm.`name` as client,\n" +
                "si.client_ref as clientRef,\n" +
                "buyerCm.`name` as buyer,\n" +
                "si.buyer_ref as buyerRef,\n" +
                "si.from_date as fromDate,\n" +
                "si.load_date as loadingDate,\n" +
                "gm.`name` as grade,\n" +
                "pm.`name` as packing,\n" +
                "portm.`name` as transitPort,\n" +
                "si.quantity as quantities,\n" +
                "si.weight_quality_certificate as wqCert,\n" +
                "slm.`name` as shippingLine,\n" +
                "si.booking_ref as bookingRef,\n" +
                "si.closing_date as closingDate,\n" +
                "si.closing_time as closingTime,\n" +
                "if( count(ss.id) = 0, 'Pending', \n" +
                "\tif( Sum(if(ss.sending_status = 1, 1, 0)) > 0, 'Sent', 'Pending')\n" +
                ") as sampleStatus,\n" +
                "if(si.`status` = 1, 'Completed', 'Pending') as status\n" +
                "from shipping_instruction si\n" +
                "left join company_master clientCm on si.client_id = clientCm.id\n" +
                "left join company_master buyerCm on si.buyer_id = buyerCm.id\n" +
                "left join grade_master gm on si.grade_id = gm.id\n" +
                "left join packing_master pm on si.packing_id = pm.id\n" +
                "left join port_master portm on si.transit_port_id = portm.id\n" +
                "left join shipping_line_master slm on si.shipping_line_id = slm.id\n" +
                "left join sample_sent ss on (si.id = ss.si_id and ss.approval_status <> 3)\n" +
                "where si.`status` <> 2\n" +
                "and (si.grade_id = :gradeId or :gradeId = -1)\n" +
                "and (si.`status` = :status or :status = -1)\n" +
                "and (si.client_id = :clientId or :clientId = -1)\n" +
                "GROUP BY si.id\n";
        params.put("gradeId", gradeId);
        params.put("status", status);
        params.put("clientId", clientId);
        if (colName.equals("0")) {
            colName = "si.id";
        }
        if (colName != null && !colName.equals("-1")) {
            sql += "order by " + colName;
            order = order.equals("desc") ? " asc " : " desc ";
            sql += order;
        }
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("siId").addScalar("refNumber")
                .addScalar("client").addScalar("clientRef")
                .addScalar("buyer").addScalar("buyerRef")
                .addScalar("fromDate").addScalar("loadingDate")
                .addScalar("grade").addScalar("packing")
                .addScalar("transitPort").addScalar("quantities").addScalar("wqCert")
                .addScalar("shippingLine").addScalar("bookingRef")
                .addScalar("closingDate").addScalar("closingTime")
                .addScalar("sampleStatus").addScalar("status");

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
            map.put("siId", Common.getStringValue(obj[0]));
            map.put("refNumber", Common.getStringValue(obj[1]));
            map.put("client", Common.getStringValue(obj[2]));
            map.put("clientRef", Common.getStringValue(obj[3]));
            map.put("buyer",Common.getStringValue(obj[4]));
            map.put("buyerRef",Common.getStringValue(obj[5]));
            map.put("fromDate", obj[6] == null ? "" : Common.getDateFromDatabase((Date) obj[6], Common.date_format_a) );
            map.put("loadingDate", obj[7] == null ? "" : Common.getDateFromDatabase((Date) obj[7], Common.date_format_a) );
            map.put("grade",Common.getStringValue(obj[8]));
            map.put("packing",Common.getStringValue(obj[9]));
            map.put("transitPort",Common.getStringValue(obj[10]));
            map.put("quantities",Common.getFloatValue(obj[11]));
            map.put("wqCert",Common.getStringValue(obj[12]));
            map.put("shippingLine",Common.getStringValue(obj[13]));
            map.put("bookingRef",Common.getStringValue(obj[14]));
            map.put("closingDate", obj[15] == null ? "" : Common.getDateFromDatabase((Date) obj[15], Common.date_format_a) );
            map.put("closingTime",Common.getStringValue(obj[16]));
            map.put("sampleStatus",Common.getStringValue(obj[17]));
            map.put("status",Common.getStringValue(obj[18]));

            list.add(map);
        }
        return list;
    }

    private Query setQuery(StringBuilder sql, String searchTerm, String order, String colName,
            int grade, int sup, Byte status) {
        if (!searchTerm.equals("")) {
            sql.append(" and (si.ref_number like :searchTerm")
                    .append(" or DATE_FORMAT(si.from_date,'%d-%b-%y') like :searchTerm")
                    .append(" or DATE_FORMAT(si.to_date,'%d-%b-%y') like :searchTerm")
                    .append(" or si.quantity like :searchTerm")
                    .append(" or gm.`name` like :searchTerm")
                    .append(" or cm.`name` like :searchTerm")
                    .append(" or pm.`name` like :searchTerm)");
        }
        if (!colName.equals("0")) {
            sql.append(" order by ").append(colName).append(" ")
                    .append((order.equals("desc") ? "desc" : "asc"));
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("si.id")
                .addScalar("si.ref_number")
                .addScalar("supplier_name")
                .addScalar("si.origin_id")
                .addScalar("si.quality_id")
                .addScalar("grade_name")
                .addScalar("packing_name")
                .addScalar("tons")
                .addScalar("net_weight")
                .addScalar("pending")
                .addScalar("from_date")
                .addScalar("to_date");
        if (!searchTerm.equals("")) {
            query.setParameter("searchTerm", "%" + searchTerm + "%");
        }
        query.setParameter("grade", grade);
        query.setParameter("supplier", sup);
        query.setParameter("status", status);

        return query;
    }

    public ArrayList<ShippingInstructionObj> convertToShippingObject(ArrayList<Object[]> obj_list) {
        //make sure the length obj is 26 [0-->25]
        if (obj_list != null && !obj_list.isEmpty()) {
            ArrayList<ShippingInstructionObj> sis = new ArrayList<>();
            for (Object[] obj : obj_list) {
                ShippingInstructionObj si = new ShippingInstructionObj();
                si.setSi_id(Integer.parseInt(obj[0].toString()));
                si.setRef_number((obj[1] != null) ? obj[1].toString() : "");
                si.setSupp_name((obj[2] != null) ? obj[2].toString() : "");
                si.setOrigin(Common.getIntegerValue(obj[3]).equals(1) ? "VietNam" : "");
                si.setQuality(Common.getIntegerValue(obj[4]).equals(1) ? "Robusta" : "Arabica");
                si.setGrade_name((obj[5] != null) ? obj[5].toString() : "");
                si.setPacking_name((obj[6] != null) ? obj[6].toString() : "");
                si.setTons(Float.parseFloat((obj[7] != null) ? obj[7].toString() : "0"));
                si.setDelivered(Float.parseFloat((obj[8] != null) ? obj[8].toString() : "0"));
                si.setPendding(Float.parseFloat((obj[9] != null) ? obj[9].toString() : "0"));
                si.setFrom_date((obj[10] != null) ? (Date) obj[10] : null);
                si.setTo_date((obj[11] != null) ? (Date) obj[11] : null);
                sis.add(si);
            }
            return sis;
        }
        return null;
    }

    public Map countTotals(String searchTerm, int grade, int sup, Byte status) {
        String sql = "call getTotalShipping(:grade,:supplier,:istatus,:searchTerm)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("grade", grade);
        query.setParameter("supplier", sup);
        query.setParameter("istatus", status);
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("total", obj[0]);
        m.put("delivered", obj[1]);
        m.put("pending", obj[2]);
        return m;
    }

    public Map getShippingFullInfo(int id) {
        String sql = "call getSIFullInfo(:siid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("id", StringType.INSTANCE)
                .addScalar("client", StringType.INSTANCE)
                .addScalar("client_ref", StringType.INSTANCE)
                .addScalar("si_ref", StringType.INSTANCE)
                .addScalar("created_date", StringType.INSTANCE)
                .addScalar("supplier", StringType.INSTANCE)
                .addScalar("origin", StringType.INSTANCE)
                .addScalar("quality", StringType.INSTANCE)
                .addScalar("grade", StringType.INSTANCE)
                .addScalar("contract_quantity", StringType.INSTANCE)
                .addScalar("shipment", StringType.INSTANCE)
                .addScalar("quantity", StringType.INSTANCE)
                .addScalar("loading_date", StringType.INSTANCE)
                .addScalar("from_date", StringType.INSTANCE)
                .addScalar("to_date", StringType.INSTANCE)
                .addScalar("contract_no", StringType.INSTANCE)
                .addScalar("shipper", StringType.INSTANCE)
                .addScalar("consignee", StringType.INSTANCE)
                .addScalar("shipping_line", StringType.INSTANCE)
                .addScalar("feeder", StringType.INSTANCE)
                .addScalar("feeder_ets", StringType.INSTANCE)
                .addScalar("feeder_eta", StringType.INSTANCE)
                .addScalar("ocean", StringType.INSTANCE)
                .addScalar("ocean_ets", StringType.INSTANCE)
                .addScalar("ocean_eta", StringType.INSTANCE)
                .addScalar("port_of_loading", StringType.INSTANCE)
                .addScalar("place_of_delivery", StringType.INSTANCE)
                .addScalar("port_of_discharge", StringType.INSTANCE)
                .addScalar("container_status", StringType.INSTANCE)
                .addScalar("weight_cert", StringType.INSTANCE)
                .addScalar("quality_cert", StringType.INSTANCE)
                .addScalar("marking", StringType.INSTANCE)
                .addScalar("freight", StringType.INSTANCE)
                .addScalar("lc_no", StringType.INSTANCE)
                .addScalar("lc_date", StringType.INSTANCE)
                .addScalar("invoice_no", StringType.INSTANCE)
                .addScalar("invoice_date", StringType.INSTANCE)
                .addScalar("bl_no", StringType.INSTANCE)
                .addScalar("bl_date", StringType.INSTANCE)
                .addScalar("remark", StringType.INSTANCE)
                .addScalar("status", StringType.INSTANCE)
                .addScalar("user", StringType.INSTANCE)
                .addScalar("buyer", StringType.INSTANCE)
                .addScalar("buyer_ref", StringType.INSTANCE);
        query.setParameter("siid", id);
        Object[] obj = (Object[]) query.uniqueResult();
        Map m = new HashMap<>();
        m.put("client", obj[1]);
        m.put("client_ref", obj[2]);
        m.put("si_ref", obj[3]);
        m.put("created_date", obj[4]);
        m.put("supplier", obj[5]);
        m.put("origin", obj[6]);
        m.put("quality", obj[7]);
        m.put("grade", obj[8]);
        m.put("contract_quantity", obj[9]);
        m.put("shipment", obj[10]);
        m.put("quantity", obj[11]);
        m.put("loading_date", obj[12]);
        m.put("from_date", obj[13]);
        m.put("to_date", obj[14]);
        m.put("contract_no", obj[15]);
        m.put("shipper", obj[16]);
        m.put("consignee", obj[17]);
        m.put("shipping_line", obj[18]);
        m.put("feeder", obj[19]);
        m.put("feeder_ets", obj[20]);
        m.put("feeder_eta", obj[21]);
        m.put("ocean", obj[22]);
        m.put("ocean_ets", obj[23]);
        m.put("ocean_eta", obj[24]);
        m.put("port_of_loading", obj[25]);
        m.put("place_of_delivery", obj[26]);
        m.put("port_of_discharge", obj[27]);
        m.put("container_status", obj[28]);
        m.put("weight_cert", obj[29]);
        m.put("quality_cert", obj[30]);
        m.put("marking", obj[31]);
        m.put("freight", obj[32]);
        m.put("lc_no", obj[33]);
        m.put("lc_date", obj[34]);
        m.put("invoice_no", obj[35]);
        m.put("invoice_date", obj[36]);
        m.put("bl_no", obj[37]);
        m.put("bl_date", obj[38]);
        m.put("remark", obj[39]);
        m.put("status", obj[40]);
        m.put("user", obj[41]);
        m.put("buyer", obj[42]);
        m.put("buyer_ref", obj[43]);

        return m;
    }

    public String getNotifyInString(int id) {
        String sql = "SELECT cm.`name`\n"
                + "FROM notify_party np\n"
                + "LEFT JOIN company_master cm ON np.company_id = cm.id\n"
                + "WHERE np.ins_id = " + id;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        ArrayList<String> obj_list = (ArrayList<String>) query.list();
        String result = "";
        for (String str : obj_list) {
            result += (str + "\n");
        }
        return result;
    }

    public Map getAccessoriesInMap(int id) {
        String sql = "SELECT\n"
                + "am.`name` as name,\n"
                + "acc.quantity as quantity,\n"
                + "IFNULL(cm.`name`,'') as supplier\n"
                + "FROM accessories acc \n"
                + "LEFT JOIN accessory_master am ON acc.accessory_id = am.id\n"
                + "LEFT JOIN company_master cm ON acc.supplier_id = cm.id\n"
                + "WHERE acc.inst_id = " + id + "\n"
                + "ORDER BY am.`name`";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("name", StringType.INSTANCE)
                .addScalar("quantity", StringType.INSTANCE)
                .addScalar("supplier", StringType.INSTANCE);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        Map result = new HashMap();
        for (Object[] str : obj_list) {
            result.put(((String) str[0]).replaceAll("\\s+", "") + "Quantity", str[1]);
            result.put(((String) str[0]).replaceAll("\\s+", "") + "Supplier", str[2]);
        }
        return result;
    }

    public String getDocumentInString(int id) {
        String sql = "SELECT \n"
                + "dc.`status`,\n"
                + "dm.`name`\n"
                + "FROM documents dc\n"
                + "LEFT JOIN document_master dm ON dc.document_id = dm.id\n"
                + "WHERE dc.type = 'S' AND dc.inst_id = " + id + "\n"
                + "ORDER BY dm.`name`";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        String result = "";
        for (Object[] str : obj_list) {
            String symbol = "  ";
            if (((byte) str[0]) == (byte) 1) {
                symbol = "✔";
            }
            result += (symbol + ((String) str[1]) + "\n");
        }
        return result;
    }

    public ArrayList<ShippingInstruction> getAllSIRefList() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingInstruction.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingInstruction.class);
        crit.add(Restrictions.lt("status", Constants.DELETED));
        crit.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("refNumber"), "refNumber"))
                .setResultTransformer(Transformers.aliasToBean(ShippingInstruction.class));
        return (ArrayList<ShippingInstruction>) crit.list();
    }

    public ArrayList<GradeMaster> getAllGrades() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingInstruction.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingInstruction.class)
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

    public ArrayList<CompanyMaster> getCompanyInSi() {
        //DetachedCriteria crit = DetachedCriteria.forClass(ShippingInstruction.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(ShippingInstruction.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.createAlias("companyMasterBySupplierId", "cm");
        crit.add(Restrictions.lt("status", Constants.DELETED));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("cm.id"), "id")
                .add(Projections.property("cm.name"), "name");
        crit.setProjection(Projections.distinct(projList));
        crit.setResultTransformer(Transformers.aliasToBean(CompanyMaster.class));
        return (ArrayList<CompanyMaster>) crit.list();
    }

    public ArrayList<ShippingInstructionObj> getExportReportInfo(int client_id, String from, String to) {
        String sql = "CALL getExportReportInfo(:client_id,:period_from,:period_to)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("si_id")
                .addScalar("ref_number")
                .addScalar("client")
                .addScalar("origin")
                .addScalar("quality")
                .addScalar("grade")
                .addScalar("packing")
                .addScalar("tons")
                .addScalar("delivered")
                .addScalar("pending")
                .addScalar("si_date")
                .addScalar("from_date")
                .addScalar("to_date");
        query.setParameter("client_id", client_id);
        query.setParameter("period_from", from);
        query.setParameter("period_to", to);
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        if (obj_list != null && !obj_list.isEmpty()) {
            ArrayList<ShippingInstructionObj> sis = new ArrayList<>();
            for (Object[] obj : obj_list) {
                ShippingInstructionObj si = new ShippingInstructionObj();
                si.setSi_id(Integer.parseInt(obj[0].toString()));
                si.setRef_number(obj[1].toString());
                si.setClient_name(obj[2].toString());
                si.setOrigin(obj[3].toString());
                si.setQuality(obj[4].toString());
                si.setGrade_name(obj[5].toString());
                si.setPacking_name(obj[6].toString());
                si.setTons(Float.parseFloat((obj[7] != null) ? obj[7].toString() : "0"));
                si.setDelivered(Float.parseFloat((obj[8] != null) ? obj[8].toString() : "0"));
                si.setPendding(Float.parseFloat((obj[9] != null) ? obj[9].toString() : "0"));
                si.setDate((obj[10] != null) ? (Date) obj[10] : null);
                si.setFrom_date((obj[11] != null) ? (Date) obj[11] : null);
                si.setTo_date((obj[12] != null) ? (Date) obj[12] : null);
                sis.add(si);
            }
            return sis;
        }
        return null;
    }

    public ArrayList<HashMap> getPendingSi(int clientid) {
        String str = "SELECT\n"
                + "di.id as id,\n"
                + "di.ref_number as ref_number,\n"
                + "IFNULL(cm.`name`, '') as buyer,\n"
                + "IFNULL(di.origin_id, 1) as origin,\n"
                + "IFNULL(di.quality_id, 1) as quality,\n"
                + "gm.`name` as grade,\n"
                + "IFNULL(pm.`name`, '') as packing,\n"
                + "IFNULL(di.quantity,0) as tons,\n"
                + "IFNULL(SUM(wnr.gross_weight - wnr.tare_weight)/1000,0) as delivered,\n"
                + "(IFNULL(di.quantity,0) - IFNULL(SUM(wnr.gross_weight - wnr.tare_weight)/1000,0)) as pending,\n"
                + "di.from_date as from_date,\n"
                + "di.to_date as to_date\n"
                + "FROM shipping_instruction di \n"
                + "LEFT JOIN company_master cm ON cm.id = di.client_id\n"
                + "LEFT JOIN grade_master gm ON gm.id = di.grade_id\n"
                + "LEFT JOIN packing_master pm ON pm.id = di.packing_id\n"
                + "LEFT JOIN weight_note wn ON wn.inst_id = di.id AND wn.type = 'EX' AND wn.`status` <> 2\n"
                + "LEFT JOIN weight_note_receipt wnr ON wnr.wn_id = wn.id AND wnr.`status` <> 2\n"
                + "WHERE di.`status` = 0 AND (di.client_id = :clientid or :clientid = -1)\n"
                + "GROUP BY di.id\n"
                + "ORDER BY di.ref_number DESC";

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("id")
                .addScalar("ref_number")
                .addScalar("buyer")
                .addScalar("origin")
                .addScalar("quality")
                .addScalar("grade")
                .addScalar("packing")
                .addScalar("tons")
                .addScalar("delivered")
                .addScalar("pending")
                .addScalar("from_date")
                .addScalar("to_date");
        query.setParameter("clientid", clientid);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> dis = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap di = new HashMap();
            di.put("id", obj[0].toString());
            di.put("ref_number", obj[1].toString());
            di.put("buyer", obj[2].toString());
            di.put("origin", (obj[3].toString().equals("1")) ? "Vietnam" : "");
            di.put("quality", (obj[4].toString().equals("1")) ? "Robusta" : "Arabica");
            di.put("grade", (obj[5] != null) ? obj[5].toString() : "");
            di.put("packing", obj[6].toString());
            di.put("tons", (obj[7] != null) ? Float.parseFloat(obj[7].toString()) : Float.parseFloat("0"));
            di.put("delivered", Float.parseFloat(obj[8].toString()));
            di.put("pending", Float.parseFloat(obj[9].toString()));
            di.put("from_date", (obj[10] != null) ? Common.getDateFromDatabase((Date) obj[10], Common.date_format_ddMMyyyy_dash) : "");
            di.put("to_date", (obj[11] != null) ? Common.getDateFromDatabase((Date) obj[11], Common.date_format_ddMMyyyy_dash) : "");
            dis.add(di);
        }
        return dis;
    }

    public ArrayList<HashMap> getShippingReport(int si_id) {
        String str = "select x.in_out as type, wn.ref_number as wn_ref, wn.created_date as date, gm.`name` as grade, x.net as tons, x.no_of_bag as bags, pm.`name` as packing,\n" // 0 - 6
                + "IFNULL(qr.black,0) as black,\n" //7
                + "IFNULL(qr.brown,0) as brown,\n"
                + "IFNULL(qr.foreign_matter,0) as fm,\n"
                + "IFNULL(qr.broken,0) as broken,\n" //10
                + "IFNULL(qr.moisture,0) as moist,\n"
                + "IFNULL(qr.old_crop,0) as ocrop,\n"
                + "IFNULL(qr.moldy,0) as moldy,\n"
                + "CONCAT(IFNULL(qr.rejected_cup,0),'/',(SELECT COUNT(*) FROM cup_test WHERE qr_id = qr.id)) as cup,\n" // 14
                + "IFNULL(qr.above_sc20,0) as asc20,\n"
                + "IFNULL(qr.sc20,0) as sc20,\n"
                + "IFNULL(qr.sc19,0) as sc19,\n"
                + "IFNULL(qr.sc18,0) as sc18,\n" //18
                + "IFNULL(qr.sc17,0) as sc17,\n"
                + "IFNULL(qr.sc16,0) as sc16,\n"
                + "IFNULL(qr.sc15,0) as sc15,\n"
                + "IFNULL(qr.sc14,0) as sc14,\n" //22
                + "IFNULL(qr.sc13,0) as sc13,\n"
                + "IFNULL(qr.sc12,0) as sc12,\n"
                + "IFNULL(qr.below_sc12,0) as bsc12\n" //25
                + "from\n"
                + "(\n"
                + "(select \n"
                + "wnr.wn_id as wnid, \n"
                + "wna.id as wnaid, \n"
                + "sum(wnr.gross_weight - wnr.tare_weight) as net,\n"
                + "sum(wnr.no_of_bags) as no_of_bag,\n"
                + "wnr.grade_id,\n"
                + "wna.`status`,\n"
                + "'Allocated' as in_out\n"
                + "from weight_note_receipt wnr\n"
                + "left join wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "left join weight_note wn ON wnr.wn_id = wn.id\n"
                + "where wna.inst_id = :si_id and wna.inst_type = 'E'\n"
                + "and wna.`status` <> 2 and wnr.`status` <> 2 group by wn.id)\n"
                + "UNION\n"
                + "(select \n"
                + "wnr.wn_id as wnid, \n"
                + "wna.id as wnaid, \n"
                + "sum(wnr.gross_weight - wnr.tare_weight) as net,\n"
                + "sum(wnr.no_of_bags) as no_of_bag,\n"
                + "wnr.grade_id,\n"
                + "wna.`status`,\n"
                + "'Export' as in_out\n"
                + "from weight_note_receipt wnr\n"
                + "left join wnr_allocation wna ON wnr.id = wna.out_wnr_id\n"
                + "left join weight_note wn ON wnr.wn_id = wn.id\n"
                + "where wna.inst_id = :si_id and wna.inst_type = 'E'\n"
                + "and wna.`status` <> 2 and wnr.`status` <> 2 group by wn.id, wna.wn_id)\n"
                + ") x\n"
                + "left join grade_master gm ON gm.id = x.grade_id\n"
                + "left join weight_note wn ON wn.id = x.wnid\n"
                + "left join quality_report qr ON qr.id = wn.qr_id\n"
                + "left join packing_master pm ON pm.id = wn.packing_id\n"
                + "group by wn.id, x.wnaid, x.in_out\n"
                + "order by x.wnaid, x.in_out";

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("type").addScalar("wn_ref")
                .addScalar("date").addScalar("grade")
                .addScalar("tons")
                .addScalar("bags").addScalar("packing")
                .addScalar("black")
                .addScalar("brown").addScalar("fm")
                .addScalar("broken").addScalar("moist")
                .addScalar("ocrop").addScalar("moldy")
                .addScalar("cup", StringType.INSTANCE)
                .addScalar("asc20").addScalar("sc20")
                .addScalar("sc19").addScalar("sc18")
                .addScalar("sc17").addScalar("sc16")
                .addScalar("sc15").addScalar("sc14")
                .addScalar("sc13").addScalar("sc12")
                .addScalar("bsc12");
        query.setParameter("si_id", si_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> sis = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap si = new HashMap();
            si.put("type", obj[0].toString());
            si.put("wn_ref", obj[1].toString());
            si.put("date", Common.getDateFromDatabase((Date) obj[2], Common.date_format_a));
            si.put("grade", obj[3].toString());
            si.put("tons", Float.parseFloat(obj[4].toString()));
            si.put("bags", obj[5].toString());
            si.put("packing", obj[6].toString());
            si.put("black", Float.parseFloat(obj[7].toString()));
            si.put("brown", Float.parseFloat(obj[8].toString()));
            si.put("fm", Float.parseFloat(obj[9].toString()));
            si.put("broken", Float.parseFloat(obj[10].toString()));
            si.put("moist", Float.parseFloat(obj[11].toString()));
            si.put("ocrop", Float.parseFloat(obj[12].toString()));
            si.put("moldy", Float.parseFloat(obj[13].toString()));
            si.put("cup", obj[14].toString());
            si.put("asc20", Float.parseFloat(obj[15].toString()));
            si.put("sc20", Float.parseFloat(obj[16].toString()));
            si.put("sc19", Float.parseFloat(obj[17].toString()));
            si.put("sc18", Float.parseFloat(obj[18].toString()));
            si.put("sc17", Float.parseFloat(obj[19].toString()));
            si.put("sc16", Float.parseFloat(obj[20].toString()));
            si.put("sc15", Float.parseFloat(obj[21].toString()));
            si.put("sc14", Float.parseFloat(obj[22].toString()));
            si.put("sc13", Float.parseFloat(obj[23].toString()));
            si.put("sc12", Float.parseFloat(obj[24].toString()));
            si.put("bsc12", Float.parseFloat(obj[25].toString()));

            sis.add(si);
        }
        return sis;
    }

    public ArrayList<HashMap> getShippingReportDetailWnr(int si_id) {
        String str = "select\n"
                + "wn.ref_number as wn_ref,\n" //0
                + "wnr.ref_number as wnr_ref,\n"
                + "IFNULL(wnr2.ref_number,'') as export_wnr_ref,\n" //2
                + "IFNULL((wnr.gross_weight - wnr.tare_weight),0)/1000 as allocated_tons,\n"
                + "IFNULL(gm.`name`,'') as new_grade,\n" //4
                + "IFNULL(pm.`name`,'') as packing,\n"
                + "IFNULL(wna.weight_out,0)/1000 as new_tons,\n" //6
                + "IFNULL(qr.black,0) as black,\n"
                + "IFNULL(qr.brown,0) as brown,\n" //8
                + "IFNULL(qr.foreign_matter,0) as fm,\n"
                + "IFNULL(qr.broken,0) as broken,\n" //10
                + "IFNULL(qr.moisture,0) as moist,\n"
                + "IFNULL(qr.old_crop,0) as ocrop,\n" //12
                + "IFNULL(qr.moldy,0) as moldy,\n"
                + "CONCAT(IFNULL(qr.rejected_cup,0),'/',(SELECT COUNT(*) FROM cup_test WHERE qr_id = qr.id)) as cup,\n" //14
                + "IFNULL(qr.above_sc20,0) as asc20,\n"
                + "IFNULL(qr.sc20,0) as sc20,\n" //16
                + "IFNULL(qr.sc19,0) as sc19,\n"
                + "IFNULL(qr.sc18,0) as sc18, \n" //18
                + "IFNULL(qr.sc17,0) as sc17,\n"
                + "IFNULL(qr.sc16,0) as sc16,\n" //20
                + "IFNULL(qr.sc15,0) as sc15,\n"
                + "IFNULL(qr.sc14,0) as sc14, \n" //22
                + "IFNULL(qr.sc13,0) as sc13,\n"
                + "IFNULL(qr.sc12,0) as sc12,\n" //24
                + "IFNULL(qr.below_sc12,0) as bsc12, \n" //25
                + "IFNULL(wnr2.date,'') as date \n" //26
                + "from wnr_allocation wna\n"
                + "left join weight_note_receipt wnr on wna.wnr_id = wnr.id\n"
                + "left join weight_note wn on wnr.wn_id = wn.id\n"
                + "left join weight_note_receipt wnr2 on wna.out_wnr_id = wnr2.id\n"
                + "left join grade_master gm on wnr2.grade_id = gm.id\n"
                + "left join packing_master pm on wn.packing_id = pm.id\n"
                + "left join quality_report qr on wn.qr_id = qr.id\n"
                + "where wna.inst_type = 'E' and wna.inst_id = :si_id and wna.`status` != 2\n"
                + "and wn.`status` != 2 and wnr.`status` != 2 and wnr2.`status` != 2";

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("wn_ref").addScalar("wnr_ref")
                .addScalar("export_wnr_ref").addScalar("allocated_tons")
                .addScalar("new_grade").addScalar("packing").addScalar("new_tons")
                .addScalar("black")
                .addScalar("brown").addScalar("fm")
                .addScalar("broken").addScalar("moist")
                .addScalar("ocrop").addScalar("moldy")
                .addScalar("cup", StringType.INSTANCE)
                .addScalar("asc20").addScalar("sc20")
                .addScalar("sc19").addScalar("sc18")
                .addScalar("sc17").addScalar("sc16")
                .addScalar("sc15").addScalar("sc14")
                .addScalar("sc13").addScalar("sc12")
                .addScalar("bsc12").addScalar("date");
        query.setParameter("si_id", si_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> sis = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap si = new HashMap();
            si.put("wn_ref", obj[0].toString());
            si.put("wnr_ref", obj[1].toString());
            si.put("export_wnr_ref", obj[2].toString());
            si.put("allocated_tons", Float.parseFloat(obj[3].toString()));
            si.put("new_grade", obj[4].toString());
            si.put("packing", obj[5].toString());
            si.put("new_tons", Float.parseFloat(obj[6].toString()));
            si.put("black", Float.parseFloat(obj[7].toString()));
            si.put("brown", Float.parseFloat(obj[8].toString()));
            si.put("fm", Float.parseFloat(obj[9].toString()));
            si.put("broken", Float.parseFloat(obj[10].toString()));
            si.put("moist", Float.parseFloat(obj[11].toString()));
            si.put("ocrop", Float.parseFloat(obj[12].toString()));
            si.put("moldy", Float.parseFloat(obj[13].toString()));
            si.put("cup", obj[14].toString());
            si.put("asc20", Float.parseFloat(obj[15].toString()));
            si.put("sc20", Float.parseFloat(obj[16].toString()));
            si.put("sc19", Float.parseFloat(obj[17].toString()));
            si.put("sc18", Float.parseFloat(obj[18].toString()));
            si.put("sc17", Float.parseFloat(obj[19].toString()));
            si.put("sc16", Float.parseFloat(obj[20].toString()));
            si.put("sc15", Float.parseFloat(obj[21].toString()));
            si.put("sc14", Float.parseFloat(obj[22].toString()));
            si.put("sc13", Float.parseFloat(obj[23].toString()));
            si.put("sc12", Float.parseFloat(obj[24].toString()));
            si.put("bsc12", Float.parseFloat(obj[25].toString()));
            si.put("balance", Float.parseFloat(obj[6].toString()) - Float.parseFloat(obj[3].toString()));
            si.put("date", obj[26].toString());
            sis.add(si);
        }
        return sis;
    }

    public ArrayList<HashMap> getShippingReportDetailWn(int si_id) {
        String str = "select\n"
                + "wn.created_date as date,\n" //0
                + "wn.ref_number as wn_ref,\n"
                + "IFNULL(wn.seal_no,'') as seal_no,\n"  //2
                + "IFNULL(wn.container_no,'') as container_no,\n"
                + "SUM(wnr.gross_weight - wnr.tare_weight)/1000 as tons,\n"  //4
                + "IFNULL(gm.`name`,'') as grade,\n"
                + "IFNULL(pm.`name`,'') as packing,\n" //6
                + "IFNULL(qr.black,0) as black,\n"
                + "IFNULL(qr.brown,0) as brown,\n" //8
                + "IFNULL(qr.foreign_matter,0) as fm,\n"
                + "IFNULL(qr.broken,0) as broken,\n"  //10
                + "IFNULL(qr.moisture,0) as moist,\n"
                + "IFNULL(qr.old_crop,0) as ocrop,\n" //12
                + "IFNULL(qr.moldy,0) as moldy,\n"
                + "CONCAT(IFNULL(qr.rejected_cup,0),'/',(SELECT COUNT(*) FROM cup_test WHERE qr_id = qr.id)) as cup,\n" //14
                + "IFNULL(qr.above_sc20,0) as asc20,\n"
                + "IFNULL(qr.sc20,0) as sc20,\n" //16
                + "IFNULL(qr.sc19,0) as sc19,\n"
                + "IFNULL(qr.sc18,0) as sc18,\n" //18
                + "IFNULL(qr.sc17,0) as sc17,\n"
                + "IFNULL(qr.sc16,0) as sc16,\n" //20
                + "IFNULL(qr.sc15,0) as sc15,\n"
                + "IFNULL(qr.sc14,0) as sc14,\n" //22
                + "IFNULL(qr.sc13,0) as sc13,\n"
                + "IFNULL(qr.sc12,0) as sc12,\n" //24
                + "IFNULL(qr.below_sc12,0) as bsc12\n"  //25
                + "from weight_note wn\n"
                + "left join weight_note_receipt wnr on wn.id = wnr.wn_id\n"
                + "left join quality_report qr on wn.qr_id = qr.id\n"
                + "left join grade_master gm on wn.grade_id = gm.id\n"
                + "left join packing_master pm on wn.packing_id = pm.id\n"
                + "where wn.inst_id = :si_id and wn.type = 'EX' \n"
                + "and wnr.`status` != 2 and wn.`status` != 2 \n"
                + "group by wn.id";

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString())
                .addScalar("date").addScalar("wn_ref").addScalar("seal_no")
                .addScalar("container_no").addScalar("tons")
                .addScalar("grade").addScalar("packing")
                .addScalar("black")
                .addScalar("brown").addScalar("fm")
                .addScalar("broken").addScalar("moist")
                .addScalar("ocrop").addScalar("moldy")
                .addScalar("cup", StringType.INSTANCE)
                .addScalar("asc20").addScalar("sc20")
                .addScalar("sc19").addScalar("sc18")
                .addScalar("sc17").addScalar("sc16")
                .addScalar("sc15").addScalar("sc14")
                .addScalar("sc13").addScalar("sc12")
                .addScalar("bsc12");
        query.setParameter("si_id", si_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> sis = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap si = new HashMap();
            si.put("wn_ref", obj[1].toString());
            si.put("seal_no", obj[2].toString());
            si.put("container_no", obj[3].toString());
            si.put("tons", Float.parseFloat(obj[4].toString()));
            si.put("grade", obj[5].toString());
            si.put("packing", obj[6].toString());
            si.put("black", Float.parseFloat(obj[7].toString()));
            si.put("brown", Float.parseFloat(obj[8].toString()));
            si.put("fm", Float.parseFloat(obj[9].toString()));
            si.put("broken", Float.parseFloat(obj[10].toString()));
            si.put("moist", Float.parseFloat(obj[11].toString()));
            si.put("ocrop", Float.parseFloat(obj[12].toString()));
            si.put("moldy", Float.parseFloat(obj[13].toString()));
            si.put("cup", obj[14].toString());
            si.put("asc20", Float.parseFloat(obj[15].toString()));
            si.put("sc20", Float.parseFloat(obj[16].toString()));
            si.put("sc19", Float.parseFloat(obj[17].toString()));
            si.put("sc18", Float.parseFloat(obj[18].toString()));
            si.put("sc17", Float.parseFloat(obj[19].toString()));
            si.put("sc16", Float.parseFloat(obj[20].toString()));
            si.put("sc15", Float.parseFloat(obj[21].toString()));
            si.put("sc14", Float.parseFloat(obj[22].toString()));
            si.put("sc13", Float.parseFloat(obj[23].toString()));
            si.put("sc12", Float.parseFloat(obj[24].toString()));
            si.put("bsc12", Float.parseFloat(obj[25].toString()));
            si.put("date", obj[0].toString());
            sis.add(si);
        }
        return sis;
    }
}
