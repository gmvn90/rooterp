package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceAggregateInfo;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;
import com.swcommodities.wsmill.utils.Common;

@Component
@org.springframework.transaction.annotation.Transactional
public class InvoiceRepositoryImpl extends BaseSearchRepository {
	@PersistenceContext private EntityManager entityManager;
	
	public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM Invoice lm ORDER BY refNumber asc";
        Query q = getQuery(queryString, "lm", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public String getNewInvoiceRef() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT max(refNumber) FROM Invoice";
        Query q = session.createQuery(queryString);
        String cur_ref = (String) q.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "INV-") : Common.getNewRefNumber(
                null, "INV-");
    }
    
    public List<Invoice> getResult(String whereClause, int offset, int limit) {
        return getResult(whereClause, offset, limit, " order by si.id desc");
    }
    
    public List<Invoice> getResult(String whereClause, int offset, int limit, String orderBy) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM Invoice si where 1=1 " + whereClause + orderBy;
        Query q = session.createQuery(queryString);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return (List<Invoice>) q.list();
    }
    
    public Long countResult(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM Invoice si where 1=1 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }
    
    public InvoiceAggregateInfo findAggregateInfo() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceAggregateInfo(SUM(inv.amount) as amount, SUM(inv.balance) as balance, SUM(inv.paidAmount) as paid) FROM Invoice inv";
        Query q = session.createQuery(queryString);
        return (InvoiceAggregateInfo) q.uniqueResult();
    }
    
    public InvoiceAggregateInfo findAggregateInfo(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceAggregateInfo(SUM(inv.amount) as amount, SUM(inv.balance) as balance, SUM(inv.paidAmount) as paid) FROM Invoice inv where inv.client.id=" + clientId;
        Query q = session.createQuery(queryString);
        return (InvoiceAggregateInfo) q.uniqueResult();
    }
    
    public InvoiceAggregateInfo findAggregateInfo(String whereClauseWithAnd) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceAggregateInfo(SUM(inv.amount) as amount, SUM(inv.balance) as balance, SUM(inv.paidAmount) as paid) FROM Invoice inv where 1=1 " + whereClauseWithAnd;
        Query q = session.createQuery(queryString);
        return (InvoiceAggregateInfo) q.uniqueResult();
    }
    
    public InvoiceAggregateInfo findAggregateInfo(List<SearchCriteria> criteriaList) {
        String whereClause = "";
        String databaseReplacement = "inv";
        boolean isFirst = true;
        for(SearchCriteria criteria: criteriaList) {
            if(isFirst) {
                whereClause += criteria.getHibernateCompare(databaseReplacement) + " ";
                isFirst = false;
            } else {
                whereClause += "and " + criteria.getHibernateCompare(databaseReplacement) + " ";
            }
        }
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InvoiceAggregateInfo(SUM(inv.amount) as amount, SUM(inv.balance) as balance, SUM(inv.paidAmount) as paid) FROM Invoice inv where 1=1 and " + whereClause;
        System.out.println(queryString);
        Query q = session.createQuery(queryString);
        for(SearchCriteria criteria: criteriaList) {
            if(criteria.getValue() instanceof Date) {
                q.setTimestamp(criteria.sanitizeKey(), (Date) criteria.getValue());
            } else {
                q.setParameter(criteria.sanitizeKey(), criteria.getValue());
            }
        }
        return (InvoiceAggregateInfo) q.uniqueResult();
    }
	
}