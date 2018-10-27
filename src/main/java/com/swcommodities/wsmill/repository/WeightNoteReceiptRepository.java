package com.swcommodities.wsmill.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swcommodities.wsmill.hibernate.dto.StockListClientViewHistory;
import com.swcommodities.wsmill.hibernate.dto.WeightNoteReceipt;

/**
 * Created by dunguyen on 11/10/16.
 */
public interface WeightNoteReceiptRepository extends JpaRepository<WeightNoteReceipt, Integer> {
    @Query("select distinct w.companyMasterByClientId.id from WeightNoteReceipt w")
    List<Integer> getClients();

    @Query("select distinct w.companyMasterByPledgeId.id from WeightNoteReceipt w")
    List<Integer> getPledgers();

    List<Integer> getWNInStock();
    
    List<WeightNoteReceipt> findByCompanyMasterByClientId_Id(Integer id);

    Double getTonForClient(int clientId, int gradeId);

    public Map<String, Double> getCurrentTonByClientAndGrade(int clientId);

    public List<StockListClientViewHistory> getCurrentStockListClientView(int clientId, int gradeId, int warehouseId);
    
    List<WeightNoteReceipt> findByIdIn(List<Integer> ids);
    List<WeightNoteReceipt> findByWeightNote_Id(Integer id);
}
