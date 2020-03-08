package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.WithdrawalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalSettingsRepository extends JpaRepository<WithdrawalSettings,Long> {

}
