package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.AuctionSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionSettingsRepository extends JpaRepository<AuctionSettings, Long> {

    @Query(value = "select * from sds_auction_settings", nativeQuery = true)
    AuctionSettings getAuctionSettings();

    @Query(value = "select * from sds_auction_settings LIMIT 1", nativeQuery = true)
    AuctionSettings getFirstAuctionSettingRecord();
}
