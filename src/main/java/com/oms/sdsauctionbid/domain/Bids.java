package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Bids {
    public Long auctionId;
    public String traderId;
    public List<Bid> bids;
}
