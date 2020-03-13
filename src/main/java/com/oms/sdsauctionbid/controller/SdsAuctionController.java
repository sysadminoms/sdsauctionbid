package com.oms.sdsauctionbid.controller;


import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.domain.response.BidResponse;
import com.oms.sdsauctionbid.logic.SdsAuctionDelegate;
import com.oms.sdsauctionbid.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
public class SdsAuctionController {

    private final SdsAuctionDelegate sdsAuctionDelegate;
    private final UserService userService;

    public SdsAuctionController(SdsAuctionDelegate sdsAuctionDelegate, UserService userService) {
        this.sdsAuctionDelegate = sdsAuctionDelegate;
        this.userService = userService;
    }

    @PostMapping(value = "/submitBid")
    public ResponseEntity<?> submitBid(@RequestBody Bids bids) {
/*        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User userFromToken = userService.getUserDetailsByUserId(username);*/
        try {
            return new ResponseEntity<BidResponse>(new BidResponse(0,sdsAuctionDelegate.submitAuctionBid(bids)), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    , -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO - ADD LOGGING
}
