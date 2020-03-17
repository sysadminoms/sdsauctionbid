package com.oms.sdsauctionbid.domain;

import com.oms.sdsauctionbid.utils.TransactionType;
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

    private String transactionDetail;

    private TransactionType transactionType;

    private Boolean transactionStatus;
}
