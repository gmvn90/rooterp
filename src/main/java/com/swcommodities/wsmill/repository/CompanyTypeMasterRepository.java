package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.CompanyTypeMaster;

/**
 * Created by dunguyen on 11/10/16.
 */
public interface CompanyTypeMasterRepository extends JpaRepository<CompanyTypeMaster, Integer> {
    CompanyTypeMaster findByLocalName(String localName);
}
