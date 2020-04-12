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

    @Column(name="auction_days")
    private String auctionDays;

    @Column(name="delivery_only_auction_time")
    private String deliveryOnlyAuctionTime;

    @Column(name="auction_start_time")
    private String auctionStartTime;

    @Column(name="auction_end_time")
    private String auctionEndTime;

    @Column(name="auction_only_delivery_days")
    private String auctionOnlyDeliveryTime;

    @Column(name="auction_lot_size")
    private Integer auctionLotSize;

    @Column(name="max_auction_win_amount_per_lot")
    private Double maxAuctionWinAmountPerLot;

    @Column(name="auction_brokerage")
    private Integer auctionBrokerage;

    @Column(name="delivery_only_max_lot")
    private Integer deliveryOnlyMaxLot;

    @Column(name="min_account_balance_day")
    private String minAccountBalanceDay;

    @Column(name="min_account_balance_amount")
    private Integer minAccountBalanceAmount;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="bid_amount")
    private Integer bidAmount;

    @Column(name="commission")
    private Double commission;

    @Column(name="tds_percentage")
    private Double tdsPercentage;

    @Column(name="mobile_auction_duration_time")
    private String mobileAuctionDurationTime;

    @Column(name="desktop_auction_duration_time")
    private String desktopAuctionDurationTime;

    @Column(name="disable_scheduled_auction")
    private boolean disableScheduledAuction;

    @Column(name="status")
    private boolean status;

}
