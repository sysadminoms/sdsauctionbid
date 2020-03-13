package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductData {

    private String productId;
    private String productName;
    private String productImg;
    private Double productPrice;
    private Integer lotSize;
}
