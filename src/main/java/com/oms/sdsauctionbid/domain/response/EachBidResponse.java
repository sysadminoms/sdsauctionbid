package com.oms.sdsauctionbid.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EachBidResponse {
    private Long productId;
    private String productName;
    private String barCode;
}
