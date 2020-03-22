package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.AuctionBid;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import com.oms.sdsauctionbid.domain.response.TransactionForBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AccountTransactionRepository extends JpaRepository<UserAccountTransaction, Long> {
    @Query(value = "select available_balance from sds_user_account_transaction where id = ? and transaction_status=true ORDER BY  transaction_epoch_time DESC LIMIT 1 ", nativeQuery = true)
    Double getAvailableBalance(String  userId);

    @Query(value = "select id, transaction_amount, " +
            "transaction_type from sds_user_account_transaction where transaction_id = ?", nativeQuery = true)
    List<?> getTransactionsForBid(String  bidId);
}
