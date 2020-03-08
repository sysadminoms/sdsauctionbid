package com.oms.sdsauctionbid.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomMessageResponse {
    String response;
    int status;
}
