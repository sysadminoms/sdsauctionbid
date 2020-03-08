package com.oms.sdsauctionbid.logic;

import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.repository.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class SdsAuctionDelegate {

    int totalSize;
    int minBidSize;
    boolean minBidSizeSet;
    WinningAuction winningAuction;
    AuctionRepository auctionRepository;
    ProductRepository productRepository;
    AuctionBidRepository auctionBidRepository;
    AuctionSettingsRepository auctionSettingsRepository;
    AuctionWinnerRepository auctionWinnerRepository;
    UserRepository userRepository;

    @Autowired
    public SdsAuctionDelegate(AuctionRepository auctionRepository, ProductRepository productRepository,
                              AuctionBidRepository auctionBidRepository,
                              AuctionSettingsRepository auctionSettingsRepository,
                              AuctionWinnerRepository auctionWinnerRepository,
                              UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
        this.auctionBidRepository = auctionBidRepository;
        this.auctionSettingsRepository = auctionSettingsRepository;
        this.auctionWinnerRepository = auctionWinnerRepository;
        this.userRepository = userRepository;
    }

    public List<String> submitAuctionBid(Bids bids) throws Exception {

        User trader = this.userRepository.findById(bids.getTraderId()).get();
       Optional.ofNullable(trader)
                        .orElseThrow(() -> {
                            new Exception("Trader id is not valid");
                            return null;
                        });
        Auction auction = this.auctionRepository.findById(bids.getAuctionId()).get();
        Optional.ofNullable(auction).map(auc -> {if(!auc.getCurrentlyActive()) {
            throw new RuntimeException("Auction has Ended");}
        return auc;
        })
            .orElseThrow(() -> new Exception("Auction Not Found or has ended"));

        return bids.getBids().stream().map(bid -> {
            try {
                return this.processBid(bid, trader, auction);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    private String processBid(Bid bid, User user, Auction auction) throws Exception {
        Optional<Product> product = auction.getProducts().stream().filter(prod -> prod.getProductId() == bid.getProductId()).findFirst();
        Product prod = Optional.ofNullable(product).get().orElseThrow(() -> new Exception("Product Not Found"));

       // AuctionBid auctionBid = this.auctionBidRepository.getAuctionBid(auction.getAuctionID(), user.getId(), prod.getProductId());
      //  if (auctionBid == null) {
        AuctionBid  auctionBid = new AuctionBid();
            auctionBid.setAuction(auction);
            auctionBid.setUser(user);
            auctionBid.setBidTime(Instant.now().toEpochMilli());
            auctionBid.setProduct(prod);
            DateTime dateTime = new DateTime(); // Initializes with the current date and time
            DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

            auctionBid.setBidDateTime(customFormatter.print(dateTime));
        //}
        auctionBid.setBidOneUp(bid.getBidOneUp());
        auctionBid.setBidTwoUp(bid.getBidTwoUp());
        auctionBid.setBidThreeUp(bid.getBidThreeUp());
        auctionBid.setBidFourUp(bid.getBidFourUp());
        auctionBid.setBidFiveUp(bid.getBidFiveUp());
        auctionBid.setBidSixUp(bid.getBidSixUp());
        auctionBid.setBidSevenUp(bid.getBidSevenUp());
        auctionBid.setBidEightUp(bid.getBidEightUp());
        auctionBid.setBidNineUp(bid.getBidNineUp());
        auctionBid.setBidTenUp(bid.getBidTenUp());

        auctionBid.setBidOneDown(bid.getBidOneDown());
        auctionBid.setBidTwoDown(bid.getBidTwoDown());
        auctionBid.setBidThreeDown(bid.getBidThreeDown());
        auctionBid.setBidFourDown(bid.getBidFourDown());
        auctionBid.setBidFiveDown(bid.getBidFiveDown());
        auctionBid.setBidSixDown(bid.getBidSixDown());
        auctionBid.setBidSevenDown(bid.getBidSevenDown());
        auctionBid.setBidEightDown(bid.getBidEightDown());
        auctionBid.setBidNineDown(bid.getBidNineDown());
        auctionBid.setBidTenDown(bid.getBidTenDown());

        auctionBid.setTotalCountUp(auctionBid.calculateTotalUpCount());
        auctionBid.setTotalCountDown(auctionBid.calculateTotalDownCount());

        auctionBidRepository.save(auctionBid);

        return auctionBid.getBidId();
    }
}
