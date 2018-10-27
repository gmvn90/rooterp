package com.swcommodities.wsmill.hibernate.dto.contract;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;

public interface ClientRefJpaRepository <T, ID extends Serializable> extends JpaRepository<T, Serializable> {
	List<RefList> findRefList();
}
