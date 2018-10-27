/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfoImpl;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author macOS
 */
public interface BaseUtility {
    
    static RefNumberCurrentInfoImpl findMaxNumberFromString(EntityManager entityManager, String klass) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT max(refNumber) FROM " + klass;
        Query q = session.createQuery(queryString);
        Object obj = q.uniqueResult();
        if(obj == null) {
            return null;
        }
        String[] numbers = ((String) obj).split("-");
        Validate.isTrue(numbers.length == 3);
        int maxYear = Integer.parseInt(numbers[numbers.length - 2]);
        int maxNumber = Integer.parseInt(numbers[numbers.length - 1]);
        RefNumberCurrentInfoImpl result = new RefNumberCurrentInfoImpl(maxYear, maxNumber);
        return result;
    }
    
}
