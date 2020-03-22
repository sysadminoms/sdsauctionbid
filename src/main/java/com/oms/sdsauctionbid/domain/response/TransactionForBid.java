package com.oms.sdsauctionbid.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionForBid {
    private String userId;
    private double available_balance;
    private double running_balance;
    private double transaction_amount;
    private String transaction_type;
}
