package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.StockReportHistory;

/**
 * Created by macOS on 2/14/17.
 */
public interface StockReportHistoryRepository extends JpaRepository<StockReportHistory, Integer> {
    public List<StockReportHistory> getDeliveryStockReport(int clientId);
    public List<StockReportHistory> getShippingStockReport(int clientId);

    public List<StockReportHistory> getDeliveryStockReportInMonth(int clientId);
    public List<StockReportHistory> getShippingStockReportInMonth(int clientId);
}
