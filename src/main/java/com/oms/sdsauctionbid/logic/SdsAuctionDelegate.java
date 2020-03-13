package com.oms.sdsauctionbid.logic;

import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.domain.response.BidResponse;
import com.oms.sdsauctionbid.domain.response.EachBidResponse;
import com.oms.sdsauctionbid.repository.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class SdsAuctionDelegate {
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

    public List<EachBidResponse> submitAuctionBid(Bids bids) throws Exception {

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

    private EachBidResponse processBid(Bid bid, User user, Auction auction) throws Exception {
        Optional<Product> product = auction.getProducts().stream().filter(prod -> prod.getProductId() == bid.getProductId()).findFirst();
        Product prod = Optional.ofNullable(product).get().orElseThrow(() -> new Exception("Product Not Found"));
        AuctionBid  auctionBid = new AuctionBid();
            auctionBid.setAuction(auction);
            auctionBid.setUser(user);
            auctionBid.setBidTime(Instant.now().toEpochMilli());
            auctionBid.setProduct(prod);
            DateTime dateTime = new DateTime(); // Initializes with the current date and time
            DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

            auctionBid.setBidDateTime(customFormatter.print(dateTime));
        //}
        auctionBid.setBidOneUp(bid.getOneUp());
        auctionBid.setBidTwoUp(bid.getTwoUp());
        auctionBid.setBidThreeUp(bid.getThreeUp());
        auctionBid.setBidFourUp(bid.getFourUp());
        auctionBid.setBidFiveUp(bid.getFiveUp());
        auctionBid.setBidSixUp(bid.getSixUp());
        auctionBid.setBidSevenUp(bid.getSevenUp());
        auctionBid.setBidEightUp(bid.getEightUp());
        auctionBid.setBidNineUp(bid.getNineUp());
        auctionBid.setBidTenUp(bid.getTenUp());

        auctionBid.setBidOneDown(bid.getOneDown());
        auctionBid.setBidTwoDown(bid.getTwoDown());
        auctionBid.setBidThreeDown(bid.getThreeDown());
        auctionBid.setBidFourDown(bid.getFourDown());
        auctionBid.setBidFiveDown(bid.getFiveDown());
        auctionBid.setBidSixDown(bid.getSixDown());
        auctionBid.setBidSevenDown(bid.getSevenDown());
        auctionBid.setBidEightDown(bid.getEightDown());
        auctionBid.setBidNineDown(bid.getNineDown());
        auctionBid.setBidTenDown(bid.getTenDown());

        auctionBid.setTotalCountUp(auctionBid.calculateTotalUpCount());
        auctionBid.setTotalCountDown(auctionBid.calculateTotalDownCount());

        auctionBidRepository.save(auctionBid);
        EachBidResponse eachBidResponse = new EachBidResponse();
        eachBidResponse.setBarCode(auctionBid.getBidId());
        eachBidResponse.setProductId(prod.getProductId());
        eachBidResponse.setProductName(prod.getProductName());
        return eachBidResponse;
    }
}
