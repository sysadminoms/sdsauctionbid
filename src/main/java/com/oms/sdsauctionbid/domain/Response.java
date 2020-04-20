package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class Response {
    private String Id;

    private String name;

    private String email;

    private String contactNo;

    private String sbd;

    private String city;

    private Boolean status;

    public Response(User user) {
        this.Id = user.getId();
        this.name = user.getAgencyName();
        this.contactNo = user.getMobileNo();
        this.email = user.getEmailAddress();
        this.sbd = Optional.ofNullable(user).map(User::getName).orElse("N/A");
        if (user.getCity() != null) {
            this.city = user.getCity().getCityName();
        } else this.city = "N/A";
        this.status = user.getStatus();
    }
}
