/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.utils.Common;

/**
 *
 * @author macOS
 */
@Component
@org.springframework.transaction.annotation.Transactional
public class PaymentRepositoryImpl {
    @PersistenceContext private EntityManager entityManager;
    
    public String getNewPaymentRef() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT max(refNumber) FROM Payment";
        Query q = session.createQuery(queryString);
        String cur_ref = (String) q.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "PAY-") : Common.getNewRefNumber(
                null, "PAY-");
    }
}
