package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.CompanyTypeMaster;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

@Component
public class CompanyRepositoryImpl extends BaseSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, name as value) FROM CompanyMaster cm ORDER BY name DESC ";
        Query q = getQuery(queryString, "cm", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public List<RefList> findByLocalName(String localName) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(cm.id as id, cm.name as value) FROM CompanyMaster cm join cm.companyTypes ct where ct.localName = :name ORDER BY cm.name asc ";
        Query q = session.createQuery(queryString).setString("name", localName);
        return (List<RefList>) q.list();
    }

    public boolean isCompanyBeneficiary(CompanyMaster companyMaster) {
        List<CompanyTypeMaster> types = companyMaster.getCompanyTypes();
        boolean result = false;
        for (CompanyTypeMaster type : types) {
            if (type.getLocalName().equalsIgnoreCase("Beneficiary")) {
                result = true;
                break;
            }
        }
        return result;
    }
}
