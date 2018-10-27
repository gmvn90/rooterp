/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.contract.ClientRefJpaRepository;
import com.swcommodities.wsmill.hibernate.dto.query.result.RefList;

/**
 *
 * @author trung
 */


/**
 * A DAO for the entity User is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author trung
 */

@Transactional
public interface CompanyRepository extends ClientRefJpaRepository<CompanyMaster, Integer>, JpaSpecificationExecutor {
    List<CompanyMaster> findByName(String name);
    List<CompanyMaster> findAllByOrderByNameAsc();
    List<CompanyMaster> findByCompanyTypes_IdOrderByNameAsc(Integer id);
    List<RefList> findByLocalName(String localName);
    boolean isCompanyBeneficiary(CompanyMaster companyMaster);
}