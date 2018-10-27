package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.cache.DICache;
import com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 10/19/16.
 */
@Component
@Transactional
public class DeliveryInstructionRepositoryImpl extends BaseSearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private boolean hasWhere = false;

    private boolean isEmpty(Integer value) {
        return value == -1 || value == null;
    }

    public InstructionResult findTotalInfo(List<SearchCriteria> criteriaList) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(tons) as tons, SUM(deliverd) as deliverd, SUM(pending) as pending) FROM DeliveryInstruction di WHERE di.status <> 2 ";
        Query q = getQuery(queryString, "di", criteriaList, session);
        return (InstructionResult) q.uniqueResult();

    }

    public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM DeliveryInstruction di WHERE di.status <> 2 ORDER BY refNumber DESC ";
        Query q = getQuery(queryString, "di", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }
    
    public List<RefList> findOwnerRefList(int company_id) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM DeliveryInstruction di WHERE di.status <> 2 and companyMasterByClientId.id="+company_id+" ORDER BY refNumber DESC ";
        SearchCriteria searchCriteria = new SearchCriteria("companyMasterByClientId.id", "=", company_id);
        List<SearchCriteria> criterias = new ArrayList<>();
        criterias.add(searchCriteria);
        Query q = getQuery(queryString, "di", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }
    
    public InstructionResult getAggregateInfo(String whereClause) {
    	Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(totalTons) as tons, SUM(deliverd) as deliverd, SUM(balance) as pending) FROM DICache di where 1=1 and statusInt != 2 " + whereClause;
        Query q = session.createQuery(queryString);
        return (InstructionResult) q.uniqueResult();
    }
    
    public Long countResult(String whereClause) {
    	Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM DICache di where 1=1 and statusInt != 2 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }
    
    public List<DICache> getResult(String whereClause, int offset, int limit) {
    	Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM DICache di where 1=1 and statusInt != 2 " + whereClause + " order by di.id DESC";
        Query q = session.createQuery(queryString);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return (List<DICache>) q.list();
    }
}
