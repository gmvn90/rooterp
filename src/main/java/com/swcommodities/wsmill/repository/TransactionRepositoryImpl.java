package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;
import com.swcommodities.wsmill.utils.Common;

@Component
@org.springframework.transaction.annotation.Transactional
public class TransactionRepositoryImpl extends BaseSearchRepository {
	@PersistenceContext private EntityManager entityManager;
	
	public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM Transaction lm ORDER BY refNumber asc";
        Query q = getQuery(queryString, "lm", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public String getNewTransactionRef() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT max(refNumber) FROM Transaction";
        Query q = session.createQuery(queryString);
        String cur_ref = (String) q.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "TR-") : Common.getNewRefNumber(
                null, "TR-");
    }
	
}