package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.GSTSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTSettingsRepository extends JpaRepository<GSTSettings,Long> {


}
