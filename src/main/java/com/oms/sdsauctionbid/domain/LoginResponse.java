package com.oms.sdsauctionbid.domain;


public class LoginResponse {

    private static final long serialVersionUID = -5657587897979324L;

    private final String jwtToken;

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return this.jwtToken;
    }
}
