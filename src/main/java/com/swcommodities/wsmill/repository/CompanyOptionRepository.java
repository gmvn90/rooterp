/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.CompanyOption;

/**
 * Created by dunguyen on 8/8/16.
 */
public interface CompanyOptionRepository extends JpaRepository<CompanyOption, Long> {
    List<CompanyOption> findTop10ByName(String name, Pageable pageable);
    CompanyOption findFirstByName(String name);
}
