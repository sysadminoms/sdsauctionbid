package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query(value = "select Count(*) from sds_auction where auctionid = ? and currently_active=true", nativeQuery = true)
    Integer checkActiveAuctionExists(Long auctionId);

    @Query(value = "select products_product_id from sds_auction_products where auctionid = ?", nativeQuery = true)
    Integer getLinkedProductId(Long auctionId);

    @Query(value = "select * from sds_auction where currently_active=true LIMIT 1", nativeQuery = true)
    Auction getLiveAuctionDetails();
}
