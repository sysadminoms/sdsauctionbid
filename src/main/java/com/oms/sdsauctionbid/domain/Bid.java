package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Bid {
    private Long productId;
    private int oneUp;
    private int twoUp;
    private int threeUp;
    private int fourUp;
    private int fiveUp;
    private int sixUp;
    private int sevenUp;
    private int eightUp;
    private int nineUp;
    private int tenUp;
    private int oneDown;
    private int twoDown;
    private int threeDown;
    private int fourDown;
    private int fiveDown;
    private int sixDown;
    private int sevenDown;
    private int eightDown;
    private int nineDown;
    private int tenDown;


public int getTotalBids() {
    return (Optional.ofNullable(this.getOneUp()).orElse(0) +
            Optional.ofNullable(this.getTwoUp()).orElse(0) +
            Optional.ofNullable(this.getThreeUp()).orElse(0) +
            Optional.ofNullable(this.getFourUp()).orElse(0) +
            Optional.ofNullable(this.getFiveUp()).orElse(0) +
            Optional.ofNullable(this.getSixUp()).orElse(0) +
            Optional.ofNullable(this.getSevenUp()).orElse(0) +
            Optional.ofNullable(this.getEightUp()).orElse(0) +
            Optional.ofNullable(this.getNineUp()).orElse(0) +
            Optional.ofNullable(this.getTenUp()).orElse(0) +
            Optional.ofNullable(this.getOneDown()).orElse(0) +
            Optional.ofNullable(this.getTwoDown()).orElse(0) +
            Optional.ofNullable(this.getThreeDown()).orElse(0) +
            Optional.ofNullable(this.getFourDown()).orElse(0) +
            Optional.ofNullable(this.getFiveDown()).orElse(0) +
            Optional.ofNullable(this.getSixDown()).orElse(0) +
            Optional.ofNullable(this.getSevenDown()).orElse(0) +
            Optional.ofNullable(this.getEightDown()).orElse(0) +
            Optional.ofNullable(this.getNineDown()).orElse(0) +
            Optional.ofNullable(this.getTenDown()).orElse(0));
}


}
