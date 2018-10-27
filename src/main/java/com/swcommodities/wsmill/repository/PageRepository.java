package com.swcommodities.wsmill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swcommodities.wsmill.hibernate.dto.Page;
import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {
    Page findFirstByUrl(String url);
    List<Page> findByUrl(String url);
    List<Page> findByUrlIsNotNull();
    List<Page> findByPageIsNull();
    Page findTopByOrderByIdDesc();
}
