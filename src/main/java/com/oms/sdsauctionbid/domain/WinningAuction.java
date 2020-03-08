package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@NoArgsConstructor
@Getter
@Setter
public class WinningAuction {
    public String winningPerson;
    public int totalLots;
    public int winningLotSize;
    public double commission;
    public double adminBoughtForwardAmount;
    public DateTime winningDateTime;
    public Long epochDateTime;
}
