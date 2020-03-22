package com.oms.sdsauctionbid.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionTotalsResponse {
   private int status;
   private AllBidsResponse allBidsResponse;
}
