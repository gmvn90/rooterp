package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.LocationCompany;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface LocationCompanyRepository extends JpaRepository<LocationCompany, Long> {
    List<LocationCompany> findByCompanyMaster_Name(String name);
}