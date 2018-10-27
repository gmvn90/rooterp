package com.swcommodities.wsmill.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.cache.SampleSentCache;

/**
 * Created by macOS on 1/19/17.
 */
@Component
@Transactional
public class SampleSentRepositoryImpl extends BaseSearchRepository  {
    @PersistenceContext
    private EntityManager entityManager;

    private boolean hasWhere = false;

    private boolean isEmpty(Integer value) {
        return value == -1 || value == null;
    }

    public Long countResult(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM SampleSentCache ss where 1=1 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }

    public List<SampleSentCache> getResult(String whereClause, int offset, int limit) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM SampleSentCache ss where 1=1 " + whereClause + " order by firstDate ASC, etaDate ASC";
        System.out.println(queryString);
        Query q = session.createQuery(queryString);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return (List<SampleSentCache>) q.list();
    }
}
