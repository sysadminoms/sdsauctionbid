package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AuctionData {

    private String auctionId;
    private String auctionStartTime;
    private String auctionEndTime;
    private String auctionDate;
    private Long auctionStartEpochTime;
    private Long auctionEndEpochTime;
    private Long auctionServerTime;
    private Long auctionServerEndTime;
    private List<ProductData> products;
    private Boolean auctionMode;
    private Boolean auctionType;
    private Boolean deliveryOnlyAuction;
    private Integer auctionBrokerage;
    private Double minBalance;
}
