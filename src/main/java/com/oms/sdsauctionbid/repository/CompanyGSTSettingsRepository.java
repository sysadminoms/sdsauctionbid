package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.CompanyGSTSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyGSTSettingsRepository extends JpaRepository<CompanyGSTSettings,Long> {


    @Query(value = "select * from sds_companygstsettings cgst where cgst.id = ?",
            nativeQuery = true)
    CompanyGSTSettings getCompanyGSTSettingsById(Long id);
}
