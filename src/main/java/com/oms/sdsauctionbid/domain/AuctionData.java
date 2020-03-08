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
    private List<ProductData> products;
    private boolean auctionMode;
    private boolean auctionType;
}
