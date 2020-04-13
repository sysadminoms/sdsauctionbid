package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_AuctionSettings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Id"})
})

@Setter
@Getter
public class AuctionSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="auction_days") //Not Used Yet
    private String auctionDays;

    @Column(name="delivery_only_auction_time") //Time for Deliveery only Auction
    private String deliveryOnlyAuctionTime;

    @Column(name="auction_start_time")
    private String auctionStartTime;

    @Column(name="auction_end_time")
    private String auctionEndTime;

    @Column(name="auction_lot_size") //The Lot Size for each product(eg 200Kg)
    private Integer auctionLotSize;

    @Column(name="max_auction_win_amount_per_lot") //The Win Amount to be credited for each Lot Won
    private Double maxAuctionWinAmountPerLot;

    @Column(name="auction_brokerage")  //Bid Amount for Each Bid
    private Integer auctionBrokerage;

    @Column(name="delivery_only_max_lot") //The Max Lots Allowed for Delivery Only Auction, the rest are paid out
    private Integer deliveryOnlyMaxLot;

    @Column(name="min_account_balance_day")
    private String minAccountBalanceDay;

    @Column(name="min_account_balance_amount")
    private Double minAccountBalanceAmount;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="commission") //Total Commission (0.2 for 20%)
    private Double commission;

    @Column(name="tds_percentage") //Tds deducted in percentage(eg. 20 for 20%)
    private Double tdsPercentage;

    @Column(name="mobile_auction_duration_time") //Time in minutes
    private String mobileAuctionDurationTime;

    @Column(name="desktop_auction_duration_time") //Time in minutes
    private String desktopAuctionDurationTime;

    @Column(name="disable_scheduled_auction") //Backend field to disable Auction Scheduler
    private boolean disableScheduledAuction;

    @Column(name="status")
    private boolean status;

}
