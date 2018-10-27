/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dao;

import java.util.ArrayList;
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
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.view.CompanyView;
import com.swcommodities.wsmill.utils.Constants;

/**
 *
 * @author kiendn
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class CompanyDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public CompanyDao setSessionFactory(SessionFactory sessionFactory) {
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

    public ArrayList<CompanyMaster> getAllCompanyNames() {
        //DetachedCriteria crit = DetachedCriteria.forClass(CompanyMaster.class);
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(CompanyMaster.class);
        crit.setProjection(
                Projections.projectionList().add(Projections.property("id"), "id")
                .add(Projections.property("name"), "name")).addOrder(Order.asc("name"))
                .setResultTransformer(Transformers.aliasToBean(CompanyMaster.class));
        return (ArrayList<CompanyMaster>) crit.list();
    }

    public CompanyMaster getCompanyById(int id) {
        return (CompanyMaster) sessionFactory.getCurrentSession().get(CompanyMaster.class, id);
    }

    public void update(CompanyMaster company) {
        sessionFactory.getCurrentSession().saveOrUpdate(company);
    }

    public long countRow() {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(CompanyMaster.class);
        crit.setProjection(Projections.rowCount());
        long rowCount = (Long) crit.uniqueResult();
        return rowCount;

    }

    public ArrayList<CompanyMaster> searchGlobe(String searchTerm, String order, int start,
            int amount, String colName, Byte active, int company_type) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT company_master.* ")
                .append("FROM company_master ")
                .append("LEFT JOIN country ON company_master.country_id = country.id ")
                .append("left join company_role cr on company_master.id = cr.company_id ")
                .append("left join company_type type on cr.role_id = type.id ");
        sql.append("WHERE (type.id =:company_type OR :company_type = -1) ");
        if (!searchTerm.equals("")) {
            sql.append("AND (company_master.`name` LIKE :search_term ")
                    .append("OR company_master.name_vn LIKE :search_term ")
                    .append("OR company_master.full_name LIKE :search_term ")
                    .append("OR company_master.full_name_vn LIKE :search_term ")
                    .append("OR company_master.address1 LIKE :search_term ")
                    .append("OR company_master.address1_vn LIKE :search_term ")
                    .append("OR company_master.address2 LIKE :search_term ")
                    .append("OR company_master.address2_vn LIKE :search_term ")
                    .append("OR company_master.representative LIKE :search_term ")
                    .append("OR company_master.representative_role LIKE :search_term ")
                    .append("OR company_master.representative_role_vn LIKE :search_term ")
                    .append("OR company_master.tax_code LIKE :search_term ")
                    .append("OR company_master.email LIKE :search_term ")
                    .append("OR company_master.fax LIKE :search_term ")
                    .append("OR company_master.telephone LIKE :search_term ")
                    .append("OR country.short_name LIKE :search_term) ");
        }
        sql.append(" ORDER BY ").append(colName).append(" ")
                .append((order.equals("desc") ? "desc" : "asc"));

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addEntity(CompanyMaster.class);
        query.setParameter("company_type", company_type);
        if (!searchTerm.equals("")) {
            query.setParameter("search_term", "%" + searchTerm + "%");
        }

        totalAfterFilter = query.list().size();

        query.setFirstResult(start);

        if (amount != -1) {
            query.setMaxResults(amount);
        }
        ArrayList<CompanyMaster> result = (ArrayList<CompanyMaster>) query.list();

        // ArrayList<CompanyMaster> list = new ArrayList<CompanyMaster>();
        // for (int i = 0; i < result.size(); i++) {
        // GradeInReference grade = new GradeInReference();
        // grade.setId((Integer) result.get(i)[0]);
        // grade.setOrigin(result.get(i)[1] + "");
        // grade.setQuality(result.get(i)[2] + "");
        // grade.setName(result.get(i)[3] + "");
        // list.add(grade);
        // }
        return result;
    }

    public JSONObject validateCompanyMaster(String fullName, String fullNameVn, String name,
            String nameVn, int id) throws JSONException {

        String sql = "CALL validateCompanyMaster(:fullname_i,:fullnamevn_i,:name_i,:namevn_i,:cmid)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("full_stt")
                .addScalar("fullvn_stt").addScalar("name_stt").addScalar("namevn_stt");
        query.setParameter("fullname_i", fullName);
        query.setParameter("fullnamevn_i", fullNameVn);
        query.setParameter("name_i", name);
        query.setParameter("namevn_i", nameVn);
        query.setParameter("cmid", id);
        Object[] obj = (Object[]) query.uniqueResult();
        JSONObject json = new JSONObject();
        json.put("title", "Can not update these information because: ");
        boolean flag = true;
        JSONArray a_json = new JSONArray();
        if (Integer.parseInt(obj[0].toString()) > 0) {
            flag = false;
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "The fullname (EN) is already exist!");
            a_json.put(sub_json);
        }
        if (Integer.parseInt(obj[1].toString()) > 0) {
            flag = false;
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "The fullname (VN) is already exist!");
            a_json.put(sub_json);
        }
        if (Integer.parseInt(obj[2].toString()) > 0) {
            flag = false;
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "The name (EN) is already exist!");
            a_json.put(sub_json);
        }
        if (Integer.parseInt(obj[3].toString()) > 0) {
            flag = false;
            JSONObject sub_json = new JSONObject();
            sub_json.put("a_row", "The name (VN) is already exist!");
            a_json.put(sub_json);
        }
        json.put("isOke", flag);
        json.put("status", "unsuccess");
        json.put("msg", a_json);
        return json;
    }

    public String getCountryShortNameById(int id) {
        String sql = "select short_name from country where id =:id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("short_name", StringType.INSTANCE);
        query.setParameter("id", id);
        return (String) query.uniqueResult();
    }

    public CompanyView getLazyCompanyById(int id) {
        return (CompanyView) sessionFactory.getCurrentSession().get(CompanyView.class, id);
    }

    public ArrayList<HashMap> getCompanyInMovement() {
        String sql = "select distinct new map (pl.id as id, pl.name as name) from Movement as m left join m.pledge as pl";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }

    public ArrayList<CompanyMaster> getRefListCompanies() {
//        DetachedCriteria crit = DetachedCriteria.forClass(CompanyMaster.class)
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        Criteria crit = sessionFactory.getCurrentSession().createCriteria(CompanyMaster.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        crit.add(Restrictions.lt("active", Constants.DELETED));
        ProjectionList projList = Projections.projectionList()
                .add(Projections.property("id"), "id").add(Projections.property("name"), "name");
        crit.setProjection(Projections.distinct(projList));
        crit.setResultTransformer(Transformers.aliasToBean(CompanyMaster.class));
        return (ArrayList<CompanyMaster>) crit.list();
    }

    public ArrayList<HashMap> getCompaniesMap() {
        String sql = "select new map (id as id, name as name) from CompanyMaster where active <> 2 order by name";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<HashMap>) query.list();
    }

    public ArrayList<Map> getCompanyTypeList() {
        String sql = "select new map(com.id as id, com.name as name) from CompanyType com";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        return (ArrayList<Map>) query.list();
    }

    public ArrayList<Map> getTypes(int comp_id) {
        String sql = "select new map(cr.companyMaster.id as com, cr.companyType.id as role) from CompanyRole cr where cr.companyMaster.id = :comp_id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("comp_id", comp_id);
        return (ArrayList<Map>) query.list();
    }

    public void deleteCompanyRoles(int company_id, int role_id) {
        String sql = "DELETE FROM company_role WHERE company_id = :company_id AND role_id = :role_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("company_id", company_id);
        query.setParameter("role_id", role_id);
        query.executeUpdate();
    }

    public void updateCompanyRoles(int company_id, int role_id) {
        String sql = "INSERT INTO company_role VALUES (:company_id,:role_id)";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("company_id", company_id);
        query.setParameter("role_id", role_id);
        query.executeUpdate();
    }

    public boolean checkRole(int company_id, int role_id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "SELECT * FROM company_role WHERE company_id = :company_id AND role_id = :role_id");
        query.setParameter("company_id", company_id);
        query.setParameter("role_id", role_id);
        if (query.uniqueResult() != null) {
            return true;
        }
        return false;
    }

    public ArrayList<HashMap> getCompaniesOf(String type, int company_id) {
        String str = "SELECT DISTINCT(wnr.pledge_id) as id, cm.`name` as name\n"
                + "FROM weight_note_receipt wnr\n"
                + "LEFT JOIN company_master cm ON wnr.pledge_id = cm.id\n"
                + "WHERE wnr.client_id = :company_id\n" + "HAVING id IS NOT NULL\n"
                + "ORDER BY cm.`name`";
        if (type.equals("Pledge")) {
            str = "SELECT DISTINCT(wnr.client_id) as id, cm.`name` as name\n"
                    + "FROM weight_note_receipt wnr\n"
                    + "LEFT JOIN company_master cm ON wnr.client_id = cm.id\n"
                    + "WHERE wnr.pledge_id = :company_id\n" + "HAVING id IS NOT NULL\n"
                    + "ORDER BY cm.`name`";
        }

        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id").addScalar("name");
        query.setParameter("company_id", company_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> comps = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap comp = new HashMap();
            comp.put("id", obj[0].toString());
            comp.put("name", obj[1].toString());
            comps.add(comp);
        }
        return comps;

    }

    public String getClientFromPledgeId(int company_id) {
        String sql = "SELECT client_id FROM weight_note_receipt WHERE pledge_id = :company_id group by client_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("company_id", company_id);
        String result = "";
        ArrayList res = (ArrayList) query.list();
        if (res != null && !res.isEmpty()) {
            for (int i = 0; i < res.size(); i++) {
                if (i == 0) {
                    result = res.get(i).toString();
                } else {
                    result += "," + res.get(i).toString();
                }
            }
        }
        return result;
    }

    public ArrayList<HashMap> getGradesOfCompany(String type, int company_id) {
        ArrayList<HashMap> grs = new ArrayList<>();
        String sql = "SELECT wnr.grade_id as id, gm.`name` as name\n"
                + "FROM weight_note_receipt wnr\n"
                + "LEFT JOIN grade_master gm ON wnr.grade_id = gm.id\n"
                + "WHERE wnr.client_id = :company_id\n"
                + "AND wnr.status <> 2 AND wnr.grade_id is not null\n" + "GROUP BY gm.id\n"
                + "ORDER BY gm.`name`";
        if (type.equals("Pledge")) {
            String comp_list = getClientFromPledgeId(company_id);
            if (!comp_list.equals("")) {
                sql = "SELECT wnr.grade_id as id, gm.`name` as name\n"
                        + "FROM weight_note_receipt wnr\n"
                        + "LEFT JOIN grade_master gm ON wnr.grade_id = gm.id\n"
                        + "WHERE wnr.client_id IN (" + comp_list + " )\n"
                        + "AND wnr.status <> 2 AND wnr.grade_id is not null \n"
                        + "GROUP BY gm.id\n" + "ORDER BY gm.`name`";
            } else {
                sql = "";
            }
        }

        if (!sql.equals("")) {
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id").addScalar("name");
            if (type.equals("Client")) {
                query.setParameter("company_id", company_id);
            }

            ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

            for (Object[] obj : obj_list) {
                HashMap gr = new HashMap();
                gr.put("id", obj[0].toString());
                gr.put("name", obj[1].toString());
                grs.add(gr);
            }
        }
        return grs;
    }

    public ArrayList<HashMap> getCompaniesInStockByType(String type) {
        String str = "SELECT DISTINCT(cm.id) as id, cm.`name` as name\n"
                + "FROM weight_note_receipt wnr \n"
                + "LEFT JOIN weight_note wn ON wn.id = wnr.wn_id\n"
                + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "LEFT JOIN company_master cm ON cm.id = wnr.pledge_id\n"
                + "WHERE wnr.status != 2 AND (wn.type = 'IM' OR wn.type = 'XP') AND wnr.pledge_id IS NOT NULL\n"
                + "AND (wna.status <> 1 or wna.status is null)\n"
                + "ORDER BY cm.`name`";
        if (type.equals("Client")) {
            str = "SELECT DISTINCT(cm.id) as id, cm.`name` as name\n"
                    + "FROM weight_note_receipt wnr \n"
                    + "LEFT JOIN weight_note wn ON wn.id = wnr.wn_id\n"
                    + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                    + "LEFT JOIN company_master cm ON cm.id = wnr.client_id\n"
                    + "WHERE wnr.status != 2 AND (wn.type = 'IM' OR wn.type = 'XP') AND wnr.client_id IS NOT NULL\n"
                    + "AND (wna.status <> 1 or wna.status is null)\n"
                    + "ORDER BY cm.`name`";
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(str).addScalar("id").addScalar("name");

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> comps = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap comp = new HashMap();
            comp.put("id", obj[0].toString());
            comp.put("name", obj[1].toString());
            comps.add(comp);
        }
        return comps;
    }

    public ArrayList<HashMap> getCompaniesByType(int type) {
        String str = "SELECT cm.id as id, cm.`name` as name\n" + "FROM company_master cm\n"
                + "LEFT JOIN company_role cr ON cm.id = cr.company_id\n" + "WHERE cr.role_id = "
                + type + " ORDER BY cm.`name`";
        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id").addScalar("name");
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> comps = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap comp = new HashMap();
            comp.put("id", obj[0].toString());
            comp.put("name", obj[1].toString());
            comps.add(comp);
        }
        return comps;
    }

    public ArrayList<HashMap> getCompaniesByTypeNew(String localName) {
        String str = "Select cm.id as id, cm.`name` as name\n" +
                "from company_master cm\n" +
                "left join company_master_company_type_master cmctm On cm.id = cmctm.companies_id\n" +
                "left join company_type_master ctm on cmctm.companyTypes_id = ctm.id\n" +
                "where ctm.`local_name` like :localName";
        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id").addScalar("name");
        query.setParameter("localName", "%" + localName + "%");
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> comps = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap comp = new HashMap();
            comp.put("id", obj[0].toString());
            comp.put("name", obj[1].toString());
            comps.add(comp);
        }
        return comps;
    }

    public ArrayList<HashMap> getCompaniesByTypeMaster(Integer companyTypeMasterId) {
        String str = "select cm.id as id, cm.`name` as name\n" +
                "from company_master_company_type_master cmctm\n" +
                "left join company_master cm on cmctm.companies_id = cm.id\n" +
                "where cmctm.companyTypes_id = " + companyTypeMasterId;
        StringBuilder sql = new StringBuilder(str);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id").addScalar("name");
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> comps = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap comp = new HashMap();
            comp.put("id", obj[0].toString());
            comp.put("name", obj[1].toString());
            comps.add(comp);
        }
        return comps;
    }

    public ArrayList<HashMap> getCompaniesInStockByMapAndType(String type, int map_id) {
        String sql = "SELECT DISTINCT(x.id) as id, x.name as name\n"
                + "FROM\n"
                + "(SELECT DISTINCT(wnr.pledge_id) as id, cm.`name` as name, wnr.status, wna.status as stt2\n"
                + "FROM company_master cm\n"
                + "LEFT JOIN weight_note_receipt wnr ON wnr.pledge_id = cm.id\n"
                + "LEFT JOIN warehouse_cell wc ON wnr.area_id = wc.id\n"
                + "LEFT JOIN weight_note wn ON wnr.wn_id = wn.id\n"
                + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                + "WHERE wnr.status <> 2 and (wn.type = 'IM' OR wn.type = 'XP') and wc.warehouse_map_id = "
                + map_id
                + "\n"
                + "HAVING (wnr.status = 3 and wna.status <> 1) or wna.status is null AND id IS NOT NULL\n"
                + "ORDER BY cm.`name`) x";
        if (type.equals("Client")) {
            sql = "SELECT DISTINCT(x.id) as id, x.name as name\n"
                    + "FROM\n"
                    + "(SELECT DISTINCT(wnr.client_id) as id, cm.`name` as name, wnr.status, wna.status as stt2\n"
                    + "FROM company_master cm\n"
                    + "LEFT JOIN weight_note_receipt wnr ON wnr.client_id = cm.id\n"
                    + "LEFT JOIN warehouse_cell wc ON wnr.area_id = wc.id\n"
                    + "LEFT JOIN weight_note wn ON wnr.wn_id = wn.id\n"
                    + "LEFT JOIN wnr_allocation wna ON wnr.id = wna.wnr_id\n"
                    + "WHERE wnr.status <> 2 and (wn.type = 'IM' OR wn.type = 'XP') and (wc.warehouse_map_id = :map_id or :map_id = -1)\n"
                    + "HAVING (wnr.status = 3 and wna.status <> 1) or wna.status is null AND id IS NOT NULL\n"
                    + "ORDER BY cm.`name`) x";
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id").addScalar("name");
        query.setParameter("map_id", map_id);

        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();

        ArrayList<HashMap> comps = new ArrayList<>();
        for (Object[] obj : obj_list) {
            HashMap comp = new HashMap();
            comp.put("id", obj[0].toString());
            comp.put("name", obj[1].toString());
            comps.add(comp);
        }
        return comps;
    }

    public ArrayList<HashMap> getClientListInSystem() {
        ArrayList<HashMap> comps = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select cm.id as id, cm.name as name from weight_note_receipt wnr ")
                .append("left join weight_note wn on wnr.wn_id = wn.id ")
                .append("left join company_master cm on cm.id = wnr.client_id ")
                .append("where cm.id is not null ").append("group by wnr.client_id");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id").addScalar("name");
        ArrayList<Object[]> obj_list = (ArrayList<Object[]>) query.list();
        if (obj_list != null && !obj_list.isEmpty()) {
            for (Object[] obj : obj_list) {
                HashMap comp = new HashMap();
                comp.put("id", obj[0].toString());
                comp.put("name", obj[1].toString());
                comps.add(comp);
            }
        }
        return comps;
    }

    public ArrayList<HashMap> getTodayProcessingClient() {
        ArrayList<HashMap> result = new ArrayList<HashMap>();
        StringBuilder sql = new StringBuilder();
        sql.append("select pi.client_id as id, cm.name as name from weight_note_receipt wnr ")
                .append("left join weight_note wn on wnr.wn_id = wn.id ")
                .append("left join processing_instruction pi on pi.id = wn.inst_id ")
                .append("left join company_master cm on cm.id = pi.client_id ")
                .append("where wn.type = 'IP' or wn.type = 'XP' and DATE_FORMAT(wnr.date,'%d-%b-%y') = DATE_FORMAT(NOW(),'%d-%b-%y') ")
                .append("group by pi.client_id");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString()).addScalar("id", IntegerType.INSTANCE).addScalar("name", StringType.INSTANCE);
        ArrayList<Object[]> list = new ArrayList<Object[]>(query.list());
        if (!list.isEmpty()) {
            for (Object[] obj : list) {
                HashMap map = new HashMap();
                map.put("id", obj[0]);
                map.put("name", obj[1]);
                result.add(map);
            }
        }
        return result;
    }
}
