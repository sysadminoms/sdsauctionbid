package com.oms.sdsauctionbid.utils;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {

    T getId();
}
