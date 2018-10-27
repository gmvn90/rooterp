/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID>, JpaSpecificationExecutor<T> {
    T findOne(ID id);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    Page<T> findAll(Specification<T> specification, Pageable pageable);
}
