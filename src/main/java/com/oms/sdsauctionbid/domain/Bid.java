package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bid {
    public Long productId;
    private int bidOneUp;
    private int bidTwoUp;
    private int bidThreeUp;
    private int bidFourUp;
    private int bidFiveUp;
    private int bidSixUp;
    private int bidSevenUp;
    private int bidEightUp;
    private int bidNineUp;
    private int bidTenUp;
    private int bidOneDown;
    private int bidTwoDown;
    private int bidThreeDown;
    private int bidFourDown;
    private int bidFiveDown;
    private int bidSixDown;
    private int bidSevenDown;
    private int bidEightDown;
    private int bidNineDown;
    private int bidTenDown;
}
