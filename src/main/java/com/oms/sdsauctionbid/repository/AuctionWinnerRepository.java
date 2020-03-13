package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.AuctionWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionWinnerRepository extends JpaRepository<AuctionWinner, Long> {

    @Query(value = "select * from sds_auction_winner where auctionid = ? and product_id = ?", nativeQuery = true)
    AuctionWinner getAuctionWinners(Long auctionId, Long productId);

}
