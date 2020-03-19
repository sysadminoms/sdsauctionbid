package com.oms.sdsauctionbid.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllBidsResponse {
   private double balance;
   private List<EachBidResponse> bidResults;
}
