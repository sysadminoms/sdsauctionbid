package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionBidRepository extends JpaRepository<AuctionBid, String> {

    @Query(value = "select * from sds_auction_bid where auction_id = ? and id = ? and product_id = ?", nativeQuery = true)
    AuctionBid getAuctionBid(Long auctionId, String userId, Long productId);

    @Query(value = "SELECT SUM(total_count_up) as UP,SUM(total_count_down) as DOWN FROM sds_auction_bid WHERE auction_id = ? and product_id = ?", nativeQuery = true)
    List<Integer[]> getUpAndDownValuesForAuction(Long auctionId, Long productId);

    @Query(value = "SELECT SUM(bids_one_up),SUM(bids_two_up),SUM(bids_three_up),SUM(bids_four_up),SUM(bids_five_up),SUM(bids_six_up),SUM(bids_seven_up),SUM(bids_eight_up),SUM(bids_nine_up),SUM(bids_ten_up),SUM(bids_one_down),SUM(bids_two_down),SUM(bids_three_down),SUM(bids_four_down),SUM(bids_five_down),SUM(bids_six_down),SUM(bids_seven_down),SUM(bids_eight_down),SUM(bids_nine_down),SUM(bids_ten_down) FROM sds_auction_bid WHERE auction_id = ? and product_id = ?", nativeQuery = true)
    List<Integer[]> getAllBidValuesForAuction(Long auctionId, Long productId);

    @Query(value = "select auction_bid_time from sds_auction_bid where auction_id = ? and product_id = ? and ? > 0 ORDER BY auction_bid_time ASC LIMIT 1", nativeQuery = true)
    Long getAuctionTimeForBid(Long auctionId, Long productId, String columnName);
}
