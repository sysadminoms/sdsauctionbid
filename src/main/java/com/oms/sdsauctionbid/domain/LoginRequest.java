package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private static final long serialVersionUID = 1276564664634646747L;

    private String username;

    private String password;

    public LoginRequest()
    {

    }

    public LoginRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }


}
