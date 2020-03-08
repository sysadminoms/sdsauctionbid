package com.oms.sdsauctionbid.repository;


import com.oms.sdsauctionbid.domain.SystemSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings,Long> {

    @Query(value = "select * from sds_system_settings", nativeQuery = true)
    SystemSettings getSystemSettings();

}
