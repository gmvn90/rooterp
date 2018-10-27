package com.swcommodities.wsmill.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 10/20/16.
 */
public class BaseSearchRepository {
    public Query getQuery(String baseQuery, String databaseReplacement, List<SearchCriteria> criteriaList, Session session) {
        if(criteriaList.size() == 0) {
            return session.createQuery(baseQuery);
        }
        baseQuery += " and ";
        //Query q = session.createQuery(baseQuery);
        boolean isFirst = true;
        for(SearchCriteria criteria: criteriaList) {
            if(isFirst) {
                baseQuery += criteria.getHibernateCompare(databaseReplacement) + " ";
                isFirst = false;
            } else {
                baseQuery += "and " + criteria.getHibernateCompare(databaseReplacement) + " ";
            }

        }
        System.out.println("basequery " + baseQuery);
        Query q = session.createQuery(baseQuery);
        for(SearchCriteria criteria: criteriaList) {
            if(criteria.getValue() instanceof Date) {
                q.setTimestamp(criteria.sanitizeKey(), (Date) criteria.getValue());
            } else {
                q.setParameter(criteria.sanitizeKey(), criteria.getValue());
            }
        }
        return q;
    }
    
    public Query getQuery(String baseQuery, String databaseReplacement, String joinQuery, List<SearchCriteria> criteriaList, Session session) {
        baseQuery += joinQuery;
        return getQuery(baseQuery, databaseReplacement, criteriaList, session);
    }
}
