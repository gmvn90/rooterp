package com.swcommodities.wsmill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swcommodities.wsmill.hibernate.dto.NotifyParty;

/**
 * Created by gmvn on 10/28/16.
 */
public interface NotifyPartyRepository extends JpaRepository<NotifyParty, Integer> {
    @Query("select distinct w.companyMaster.id from NotifyParty w")
    List<Integer> getBuyers();
}
