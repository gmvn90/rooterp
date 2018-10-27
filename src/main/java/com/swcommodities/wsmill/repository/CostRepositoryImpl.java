/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;

/**
 *
 * @author trung
 */
@Component
public class CostRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    

    public List<CompanyMaster> findCompaniesThatHasCost() throws Exception {
        Session session = entityManager.unwrap(Session.class);
        Query q = session.createQuery("select c.company from Cost c group by c.company");
        return q.list();
    }
    
}
