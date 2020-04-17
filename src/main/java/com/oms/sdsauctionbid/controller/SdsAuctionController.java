package com.oms.sdsauctionbid.controller;


import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.domain.request.BidIds;
import com.oms.sdsauctionbid.domain.response.BidResponse;
import com.oms.sdsauctionbid.domain.response.ClaimTicketAsDeliveryResponse;
import com.oms.sdsauctionbid.logic.SdsAccountDelegate;
import com.oms.sdsauctionbid.logic.SdsAuctionDelegate;
import com.oms.sdsauctionbid.service.UserAccountTransactionService;
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
    private final SdsAccountDelegate sdsAccountDelegate;
    private final UserService userService;

    public SdsAuctionController(SdsAuctionDelegate sdsAuctionDelegate, UserService userService,
                                SdsAccountDelegate sdsAccountDelegate) {
        this.sdsAuctionDelegate = sdsAuctionDelegate;
        this.userService = userService;
        this.sdsAccountDelegate = sdsAccountDelegate;
    }

    @PostMapping(value = "/submitBid")
    public ResponseEntity<?> submitBid(@RequestBody Bids bids) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User userFromToken = userService.getUserDetailsByUserId(username);
        try {
            return new ResponseEntity<BidResponse>(new BidResponse(0,
                    sdsAuctionDelegate.submitAuctionBid(bids, userFromToken)), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    , -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/addAccountTransaction")
    public ResponseEntity<?> addAccountTransaction(@RequestBody AccountTransaction accountTransaction) {
        try {
            return new ResponseEntity<>(new CustomMessageResponse("Success", 0), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    , -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/getAccountTransactionsForBids")
    public ResponseEntity<?> getAccountTransactionsForBids(@RequestBody BidIds bidIds) {
        try {
            return new ResponseEntity<>(sdsAccountDelegate.getAccountTransactionsForBids(bidIds), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    , -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUserBalance")
    public ResponseEntity<?> getUserAccountBalance(@RequestParam String userId) {
        try {
            return new ResponseEntity<>(new CustomMessageResponse(Double.toString(sdsAccountDelegate
                    .getUserAccountBalance(userId)), 0), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    , -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/claimTicket")
    public ResponseEntity<?> claimTicket(@RequestParam String bidId, @RequestParam String sellOrDelivery) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            User dealer = userService.getUserDetailsByUserId(username);
            return new ResponseEntity<>(new CustomMessageResponse(sdsAuctionDelegate.claimTicket(bidId,
                    sellOrDelivery, dealer), 0), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomMessageResponse(e.getMessage()
                    , -1), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getDeliveryDetailsForTicket")
    public ResponseEntity<?> deliveryDetailsForTicket(@RequestParam String bidId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            User dealer = userService.getUserDetailsByUserId(username);
            ClaimTicketAsDeliveryResponse claimTicketAsDeliveryResponse =
                    sdsAuctionDelegate.getTicketDetailsForDeliveryClaim(bidId, dealer);
            claimTicketAsDeliveryResponse.setStatus(0);
            return new ResponseEntity<>(claimTicketAsDeliveryResponse, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ClaimTicketAsDeliveryResponse(e.getMessage(), null,
                     -1), HttpStatus.INTERNAL_SERVER_ERROR); }
    }

    //TODO - ADD LOGGING
}
