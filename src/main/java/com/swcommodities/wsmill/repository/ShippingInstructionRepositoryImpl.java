package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfoImpl;
import com.swcommodities.wsmill.hibernate.dto.cache.SICache;
import com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionListViewResult;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 10/19/16.
 */
@Component
@Transactional
public class ShippingInstructionRepositoryImpl extends BaseSearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private boolean hasWhere = false;

    private boolean isEmpty(Integer value) {
        return value == -1 || value == null;
    }

    public InstructionResult findTotalInfo(List<SearchCriteria> criteriaList) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(quantity) as tons) FROM ShippingInstruction si WHERE si.status <> 2 ";
        Query q = getQuery(queryString, "si", criteriaList, session);
        return (InstructionResult) q.uniqueResult();
    }

    public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM ShippingInstruction si WHERE si.status <> 2 ORDER BY refNumber DESC ";
        Query q = getQuery(queryString, "si", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public List<RefList> findOwnerRefList(int company_id) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM ShippingInstruction si WHERE si.status <> 2 and companyMasterByClientId.id="+company_id+" ORDER BY refNumber DESC ";
        SearchCriteria searchCriteria = new SearchCriteria("companyMasterByClientId.id", "=", company_id);
        List<SearchCriteria> criterias = new ArrayList<>();
        criterias.add(searchCriteria);
        Query q = getQuery(queryString, "si", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public List<ShippingInstructionListViewResult> findOwnerListview(int company_id) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.ShippingInstructionListViewResult(id as id, refNumber as refNumber) FROM ShippingInstruction si WHERE si.status <> 2 and companyMasterByClientId.id="+company_id+" ORDER BY refNumber DESC ";
        SearchCriteria searchCriteria = new SearchCriteria("companyMasterByClientId.id", "=", company_id);
        List<SearchCriteria> criterias = new ArrayList<>();
        criterias.add(searchCriteria);
        Query q = getQuery(queryString, "si", new ArrayList<SearchCriteria>(), session);
        return (List<ShippingInstructionListViewResult>) q.list();
    }

    public InstructionResult getAggregateInfo(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(total) as tons) FROM SICache si where 1=1 and statusInt != 2 " + whereClause;
        Query q = session.createQuery(queryString);
        return (InstructionResult) q.uniqueResult();
    }

    public Long countResult(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM SICache si where 1=1 and statusInt != 2 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }

    public List<SICache> getResult(String whereClause, int offset, int limit) {
        return getResult(whereClause, offset, limit, " order by si.id desc");
    }
    
    public List<SICache> getResult(String whereClause, int offset, int limit, String orderBy) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM SICache si where 1=1 and statusInt != 2 " + whereClause + orderBy;
        Query q = session.createQuery(queryString);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return (List<SICache>) q.list();
    }
    
    
    
    public RefNumberCurrentInfoImpl findMaxShippingAdviceNumber() {
        return BaseUtility.findMaxNumberFromString(entityManager, "ShippingAdvice");
    }
    
    public RefNumberCurrentInfoImpl findMaxSampleSentNumber() {
        RefNumberCurrentInfoImpl ref1 = BaseUtility.findMaxNumberFromString(entityManager, "SampleSent");
        RefNumberCurrentInfoImpl ref2 = BaseUtility.findMaxNumberFromString(entityManager, "SampleType");
        // fix null pointer, bc sampletype might be empty
        if(ref2 == null) {
            return ref1;
        }
        return ref1.compareTo(ref2) == 1 ? ref1 : ref2;
    }
    
    public RefNumberCurrentInfoImpl findMaxShippingInstructionNumber() {
        return BaseUtility.findMaxNumberFromString(entityManager, "ShippingInstruction");
    }
    
    public RefNumberCurrentInfoImpl findMaxClaimNumber() {
        return BaseUtility.findMaxNumberFromString(entityManager, "Claim");
    }
    
}
