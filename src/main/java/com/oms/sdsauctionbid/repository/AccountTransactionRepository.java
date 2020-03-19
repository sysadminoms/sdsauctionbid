package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.AuctionBid;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<UserAccountTransaction, Long> {
    @Query(value = "select available_balance from sds_user_account_transaction where id = ? and transaction_status=true ORDER BY  transaction_epoch_time DESC LIMIT 1 ", nativeQuery = true)
    Double getAvailableBalance(String  userId);
}
