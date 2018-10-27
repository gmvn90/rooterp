package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.hibernate.dto.LuCafeHistory;
import com.swcommodities.wsmill.hibernate.dto.query.result.LuCafeHistoryAggregateInfo;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gmvn on 10/23/18.
 */

@Component
@Transactional
public class LuCafeHistoryRepositoryImpl extends BaseSearchRepository{
    @PersistenceContext private EntityManager entityManager;

    public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM LuCafeHistory lm ORDER BY refNumber asc";
        Query q = getQuery(queryString, "lm", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public List<LuCafeHistory> getResult(String whereClause, int offset, int limit) {
        return getResult(whereClause, offset, limit, " order by si.id desc");
    }

    public List<LuCafeHistory> getResult(String whereClause, int offset, int limit, String orderBy) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM LuCafeHistory si where 1=1 " + whereClause + orderBy;
        Query q = session.createQuery(queryString);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return (List<LuCafeHistory>) q.list();
    }

    public Long countResult(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM LuCafeHistory si where 1=1 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }

    public LuCafeHistoryAggregateInfo findAggregateInfo() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.LuCafeHistoryAggregateInfo(SUM(lch.beginExpense) as beginExpense, SUM(lch.income) as income, SUM(lch.indayExpense) as indayExpense, SUM(lch.total) as total) FROM LuCafeHistory lch";
        Query q = session.createQuery(queryString);
        return (LuCafeHistoryAggregateInfo) q.uniqueResult();
    }

    public LuCafeHistoryAggregateInfo findAggregateInfo(String whereClauseWithAnd) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.LuCafeHistoryAggregateInfo(SUM(lch.beginExpense) as beginExpense, SUM(lch.income) as income, SUM(lch.indayExpense) as indayExpense, SUM(lch.total) as total) FROM LuCafeHistory lch where 1=1 " + whereClauseWithAnd;
        Query q = session.createQuery(queryString);
        return (LuCafeHistoryAggregateInfo) q.uniqueResult();
    }

    public LuCafeHistoryAggregateInfo findAggregateInfo(List<SearchCriteria> criteriaList) {
        String whereClause = "";
        String databaseReplacement = "lch";
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
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.LuCafeHistoryAggregateInfo(SUM(lch.beginExpense) as beginExpense, SUM(lch.income) as income, SUM(lch.indayExpense) as indayExpense, SUM(lch.total) as total) FROM LuCafeHistory lch where 1=1 and " + whereClause;
        System.out.println(queryString);
        Query q = session.createQuery(queryString);
        for(SearchCriteria criteria: criteriaList) {
            if(criteria.getValue() instanceof Date) {
                q.setTimestamp(criteria.sanitizeKey(), (Date) criteria.getValue());
            } else {
                q.setParameter(criteria.sanitizeKey(), criteria.getValue());
            }
        }
        return (LuCafeHistoryAggregateInfo) q.uniqueResult();
    }
}
