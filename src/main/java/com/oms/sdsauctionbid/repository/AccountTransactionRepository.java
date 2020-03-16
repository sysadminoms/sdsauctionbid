package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.AuctionBid;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<UserAccountTransaction, Long> {

}
