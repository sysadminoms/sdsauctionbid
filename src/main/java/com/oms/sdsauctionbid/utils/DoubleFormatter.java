package com.oms.sdsauctionbid.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public  class DoubleFormatter {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
