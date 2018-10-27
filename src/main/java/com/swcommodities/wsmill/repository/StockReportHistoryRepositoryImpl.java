package com.swcommodities.wsmill.repository;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.StockReportHistory;

/**
 * Created by macOS on 2/14/17.
 */
@Component
@Transactional
public class StockReportHistoryRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<StockReportHistory> getDeliveryStockReport(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new StockReportHistory(\n" +
                "\t sum(balance),\n" +
                "\t firstDate,\n" +
                "\t gradeInt,\n" +
                "\t grade)\n" +
                "FROM\n" +
                "\t DICache\n" +
                "WHERE\n" +
                "\t statusInt = 0\n" +
                "\t and firstDate is not null\n" +
                "\t and (-1 = :clientId or clientInt = :clientId)\n" +
                "GROUP BY\n" +
                "\t firstDate,\n" +
                "\t gradeInt\n" +
                "ORDER BY firstDate";
        Query q = session.createQuery(queryString);
        q.setInteger("clientId", clientId);
        return (List<StockReportHistory>) q.list();
    }

    public List<StockReportHistory> getShippingStockReport(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new StockReportHistory(\n" +
                "\t sum(exported),\n" +
                "\t loadingDate,\n" +
                "\t grade,\n" +
                "\t gradeInt)\n" +
                "FROM\n" +
                "\t SICache \n" +
                "WHERE\n" +
                "\t statusInt = 0\n" +
                "\t and loadingDate is not null\n" +
                "\t and (-1 = :clientId or clientInt = :clientId)\n" +
                "GROUP BY\n" +
                "\t loadingDate,\n" +
                "\t gradeInt\n" +
                "ORDER BY loadingDate";
        Query q = session.createQuery(queryString);
        q.setInteger("clientId", clientId);
        return (List<StockReportHistory>) q.list();
    }

    public List<StockReportHistory> getDeliveryStockReportInMonth(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new StockReportHistory(\n" +
                "\t sum(balance),\n" +
                "\t firstDate,\n" +
                "\t gradeInt,\n" +
                "\t grade)\n" +
                "FROM\n" +
                "\t DICache\n" +
                "WHERE\n" +
                "\t statusInt = 0\n" +
                "\t and firstDate is not null\n" +
                "\t and (-1 = :clientId or clientInt = :clientId)\n" +
                "GROUP BY\n" +
                "\t month(firstDate),\n" +
                "\t gradeInt\n" +
                "ORDER BY firstDate";
        Query q = session.createQuery(queryString);
        q.setInteger("clientId", clientId);
        return (List<StockReportHistory>) q.list();
    }

    public List<StockReportHistory> getShippingStockReportInMonth(int clientId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new StockReportHistory(\n" +
                "\t sum(exported),\n" +
                "\t loadingDate,\n" +
                "\t grade,\n" +
                "\t gradeInt)\n" +
                "FROM\n" +
                "\t SICache \n" +
                "WHERE\n" +
                "\t statusInt = 0\n" +
                "\t and loadingDate is not null\n" +
                "\t and (-1 = :clientId or clientInt = :clientId)\n" +
                "GROUP BY\n" +
                "\t month(loadingDate),\n" +
                "\t gradeInt\n" +
                "ORDER BY loadingDate";
        Query q = session.createQuery(queryString);
        q.setInteger("clientId", clientId);
        return (List<StockReportHistory>) q.list();
    }

}
