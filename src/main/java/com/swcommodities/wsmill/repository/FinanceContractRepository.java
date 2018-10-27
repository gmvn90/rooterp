package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;

/**
 * Created by gmvn on 11/30/16.
 */
@Transactional
public interface FinanceContractRepository extends ClientRefJpaRepository<FinanceContract, Integer>, JpaSpecificationExecutor<FinanceContract> {
    public List<RefList> findRefList();

    public String getNewFinanceContractRef();
    FinanceContractDTO findOneDTO(int id);
    List<FinanceContractDTO> getResult(String whereClause, int offset, int limit);
    Long countResult(String whereClause);
}
