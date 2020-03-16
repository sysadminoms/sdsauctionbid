package com.oms.sdsauctionbid.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountTransaction {

    private String userId;

    private Double transactionAmount;

    private String transactionId;

    private String transactionType;

    private Boolean transactionStatus;
}
