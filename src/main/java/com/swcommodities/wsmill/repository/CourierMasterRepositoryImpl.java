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

@Component
@org.springframework.transaction.annotation.Transactional
public class CourierMasterRepositoryImpl extends BaseSearchRepository {
	@PersistenceContext private EntityManager entityManager;
	
	public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, name as value) FROM CourierMaster cm ORDER BY name asc";
        Query q = getQuery(queryString, "cm", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }
	
}