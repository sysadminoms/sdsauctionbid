package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.CompanySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySettingsRepository extends JpaRepository<CompanySettings, Long> {

    @Query(value = "select * from sds_company_settings", nativeQuery = true)
    CompanySettings getCompanySettings();

}
