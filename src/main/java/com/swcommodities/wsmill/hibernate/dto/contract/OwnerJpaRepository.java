package com.swcommodities.wsmill.hibernate.dto.contract;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;

public interface OwnerJpaRepository<T, ID extends Serializable> extends JpaRepository<T, Serializable> {
	List<T> findByCompanyMasterByClientId_Id(Integer companyId);
	List<RefList> findRefList();
	List<RefList> findOwnerRefList(int company_id);
}
