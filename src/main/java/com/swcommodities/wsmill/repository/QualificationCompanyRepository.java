package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.QualificationCompany;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface QualificationCompanyRepository extends JpaRepository<QualificationCompany, Long> {
    List<QualificationCompany> findByCompanyMaster_Name(String name);
}