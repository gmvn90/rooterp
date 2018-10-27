package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.hibernate.dto.LuCafeHistory;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.LuCafeHistoryAggregateInfo;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gmvn on 10/23/18.
 */
@Transactional
public interface LuCafeHistoryRepository extends ClientRefJpaRepository<LuCafeHistory, Integer>, JpaSpecificationExecutor<LuCafeHistory>{
    public List<RefList> findRefList();

    List<LuCafeHistory> getResult(String whereClause, int offset, int limit);
    Long countResult(String whereClause);

    LuCafeHistoryAggregateInfo findAggregateInfo();
    LuCafeHistoryAggregateInfo findAggregateInfo(List<SearchCriteria> list);
    LuCafeHistoryAggregateInfo findAggregateInfo(String whereClauseWithAnd);
}
