package com.oms.sdsauctionbid.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BidResponse {
   private List<EachBidResponse> bidResults;
}
