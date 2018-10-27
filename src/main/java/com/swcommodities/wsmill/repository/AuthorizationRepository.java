package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization, Integer> {}
