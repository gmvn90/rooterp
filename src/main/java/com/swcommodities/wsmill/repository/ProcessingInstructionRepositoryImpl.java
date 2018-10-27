package com.swcommodities.wsmill.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.domain.service.RefNumberCurrentInfoImpl;
import com.swcommodities.wsmill.hibernate.dto.TransactionItem;
import com.swcommodities.wsmill.hibernate.dto.cache.PICache;
import com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionListViewResult;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;
import com.swcommodities.wsmill.hibernate.specification.SearchCriteria;

/**
 * Created by dunguyen on 10/19/16.
 */
@Component
@Transactional
public class ProcessingInstructionRepositoryImpl extends BaseSearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private boolean hasWhere = false;

    private boolean isEmpty(Integer value) {
        return value == -1 || value == null;
    }

    public InstructionResult findTotalInfo(List<SearchCriteria> criteriaList) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(quantity) as tons, SUM(allocatedWeight) as allocated, SUM(inProcessWeight) as inprocess, SUM(exProcessWeight) as exprocess, SUM(inProcessWeight - exProcessWeight) as pending) FROM ProcessingInstruction pi WHERE pi.status <> 2 ";
        Query q = getQuery(queryString, "pi", criteriaList, session);
        return (InstructionResult) q.uniqueResult();

    }

    public List<RefList> findRefList() {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM ProcessingInstruction pi WHERE pi.status <> 2 ORDER BY refNumber DESC ";
        Query q = getQuery(queryString, "pi", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public List<RefList> findOwnerRefList(int company_id) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.RefList(id as id, refNumber as value) FROM ProcessingInstruction pi WHERE pi.status <> 2 and companyMasterByClientId.id="+company_id+" ORDER BY refNumber DESC ";
        SearchCriteria searchCriteria = new SearchCriteria("companyMasterByClientId.id", "=", company_id);
        List<SearchCriteria> criterias = new ArrayList<>();
        criterias.add(searchCriteria);
        Query q = getQuery(queryString, "pi", new ArrayList<SearchCriteria>(), session);
        return (List<RefList>) q.list();
    }

    public List<ProcessingInstructionListViewResult> findOwnerListview(int company_id) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.ProcessingInstructionListViewResult(id as id, refNumber as refNumber) FROM ProcessingInstruction pi WHERE pi.status <> 2 and companyMasterByClientId.id="+company_id+" ORDER BY refNumber DESC ";
        SearchCriteria searchCriteria = new SearchCriteria("companyMasterByClientId.id", "=", company_id);
        List<SearchCriteria> criterias = new ArrayList<>();
        criterias.add(searchCriteria);
        Query q = getQuery(queryString, "pi", new ArrayList<SearchCriteria>(), session);
        return (List<ProcessingInstructionListViewResult>) q.list();
    }

    public InstructionResult getAggregateInfo(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT new com.swcommodities.wsmill.hibernate.dto.query.result.InstructionResult(SUM(totalTons) as tons, SUM(totalTons) as allocated, SUM(debitTons) as inprocess, SUM(creditTons) as exprocess, SUM(balance) as pending) FROM PICache pi where 1=1 and statusInt != 2 " + whereClause;
        Query q = session.createQuery(queryString);
        return (InstructionResult) q.uniqueResult();
    }

    public Long countResult(String whereClause) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT count(id) FROM PICache di where 1=1 and statusInt != 2 " + whereClause;
        Query q = session.createQuery(queryString);
        return (Long) q.uniqueResult();
    }

    public List<PICache> getResult(String whereClause, int offset, int limit) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "FROM PICache pi where 1=1 and statusInt != 2 " + whereClause + " order by pi.id desc";
        Query q = session.createQuery(queryString);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return (List<PICache>) q.list();
    }

    public List<TransactionItem> getPiTransactionItemBasic(int piId) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "select new com.swcommodities.wsmill.hibernate.dto.TransactionItem(wnr.gradeMaster, (case when (wnr.weightNote.type = 'IP') then 1 else 2 end), coalesce(sum(wnr.grossWeight - wnr.tareWeight)/1000,0)) from WeightNoteReceipt wnr where wnr.status != 2 and wnr.weightNote.status != 2 and wnr.weightNote.processingInstruction.id=" + piId + " group by wnr.weightNote.type, wnr.gradeMaster order by wnr.weightNote.type, wnr.gradeMaster.name";
        Query q = session.createQuery(queryString);

        return (List<TransactionItem>) q.list();
    }
    
    private RefNumberCurrentInfoImpl findMaxNumberFromString(String klass) {
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
    
    public RefNumberCurrentInfoImpl findMaxProcessingInstructionNumber() {
        return findMaxNumberFromString("ProcessingInstruction");
    }

}
