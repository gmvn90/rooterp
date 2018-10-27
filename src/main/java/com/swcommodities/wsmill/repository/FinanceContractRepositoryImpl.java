package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.facade.FinanceContractDTO;
import com.swcommodities.wsmill.hibernate.dto.facade.assembler.FinanceContractAssembler;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;
import com.swcommodities.wsmill.utils.Common;


@Component
@org.springframework.transaction.annotation.Transactional
public class FinanceContractRepositoryImpl extends BaseSearchRepository {
    
    @Autowired FinanceContractRepository financeContractRepository;
    
    private int defaultEmpty = -1;
    
	@PersistenceContext private EntityManager entityManager;
	
	public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM FinanceContract lm ORDER BY refNumber asc";
        Query q = getQuery(queryString, "lm", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public String getNewFinanceContractRef() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT max(refNumber) FROM FinanceContract ";
        Query q = session.createQuery(queryString);
        String cur_ref = (String) q.uniqueResult();
        return (cur_ref != null) ? Common.getNewRefNumber(cur_ref, "FC-") : Common.getNewRefNumber(
                null, "FC-");
    }
    
    public Long countResult(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM FinanceContract si where 1=1 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }
    
    public List<FinanceContractDTO> getResult(String whereClause, int offset, int limit) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM FinanceContract si where 1=1 " + whereClause;
        Query q = session.createQuery(queryString);
        FinanceContractAssembler assembler = new FinanceContractAssembler();
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        List<FinanceContract> financeContracts = (List<FinanceContract>) q.list();
        List<FinanceContractDTO> financeContractDTOs = new ArrayList<>();
        for(FinanceContract financeContract: financeContracts) {
            financeContractDTOs.add(assembler.toDto(financeContract));
        }
        return financeContractDTOs;
    }
    
    public FinanceContractDTO findOneDTO(int id) {
        FinanceContract financeContract = financeContractRepository.findOne(id);
        if(financeContract == null) {
            return null;
        }
        FinanceContractAssembler assembler = new FinanceContractAssembler();
        return assembler.toDto(financeContract);
    }
    
	
}