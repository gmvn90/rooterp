package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.swcommodities.wsmill.hibernate.dto.User;

/**
 * Created by dunguyen on 9/24/16.
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    // escapse underscore
    User findByUserNameAndPassword(String username, String encodedPassword);
}