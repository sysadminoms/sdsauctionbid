package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImmediateParentOptions {
    private String Id;
    private String name;

    public ImmediateParentOptions(User user) {
        this.Id = user.getId();
        this.name = user.getAgencyName();
  }
}
