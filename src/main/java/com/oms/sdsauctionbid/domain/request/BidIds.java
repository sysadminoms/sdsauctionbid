package com.oms.sdsauctionbid.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class BidIds {
   private List<BidId> bidIds;
}
