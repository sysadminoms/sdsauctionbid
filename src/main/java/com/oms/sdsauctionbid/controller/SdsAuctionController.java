package com.oms.sdsauctionbid.controller;


import com.oms.sdsauctionbid.domain.*;
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
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User userFromToken = userService.getUserDetailsByUserId(username);
        try {
            return new ResponseEntity<>(sdsAuctionDelegate.submitAuctionBid(bids, userFromToken), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    ,-1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/createAuctionWinner")
    public ResponseEntity<?> createAuctionWinner(@RequestBody LiveAuction liveAuction) {

        WinningAuction winningAuctionsList = sdsAuctionDelegate.calculateActualAmount(liveAuction);

        if (winningAuctionsList == null) {
            return new ResponseEntity<>(
                    "No Winners Found",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                winningAuctionsList,
                OK);

        //TODO - ADD LOGGING
    }

    @GetMapping(value = "/createAuction")
    public ResponseEntity<?> createAuction() {

        sdsAuctionDelegate.createAuction();


        return new ResponseEntity<>(
                "ok",
                OK);

        //TODO - ADD LOGGING
    }

    @GetMapping(value = "/getLiveAuctionDetails")
    public ResponseEntity<?> getLiveAuctionDetails() throws Exception {
        AuctionData auctionDetails = sdsAuctionDelegate.getLiveAuctionDetails();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User userFromToken = userService.getUserDetailsByUserId(username);
       if(userFromToken.getUserType().getUserTypeLevel() != 4){
           return new ResponseEntity<>(new CustomMessageResponse("Only Dealers Are Allowed To Trade"
                   ,-1), HttpStatus.UNAUTHORIZED);
       }

        if (auctionDetails == null) {
            return new ResponseEntity<>(
                    "No Auction Details Found",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                auctionDetails,
                OK);

        //TODO - ADD LOGGING
    }

    @GetMapping(value = "/getAuctionTrend")
    public ResponseEntity<?> getAuctionTrend(@RequestParam Long auctionId, @RequestParam Long productId) throws Exception {

       String trendForAuction = sdsAuctionDelegate.getAuctionTrend(auctionId,productId);


        return new ResponseEntity<>(new CustomMessageResponse(trendForAuction
                ,0), OK);

        //TODO - ADD LOGGING
    }

    @GetMapping(value = "/getAuctionWinner")
    public ResponseEntity<?> getAuctionWinner(@RequestParam Long auctionId) throws Exception {
        try {
            String winner = sdsAuctionDelegate.getAuctionWinner(auctionId);
            return new ResponseEntity<>(new CustomMessageResponse(winner
                    ,0), OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    ,-1), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //TODO - ADD LOGGING
    }
}
