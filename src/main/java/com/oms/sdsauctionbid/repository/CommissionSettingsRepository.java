package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.UserComission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionSettingsRepository extends JpaRepository<UserComission,Long> {
    @Query(value = "select * from sds_user_commission_settings",
            nativeQuery = true)
    List<UserComission> getCommissionSettings();


}
