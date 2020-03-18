package com.oms.sdsauctionbid.logic;

import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.domain.response.BidResponse;
import com.oms.sdsauctionbid.domain.response.EachBidResponse;
import com.oms.sdsauctionbid.repository.*;
import com.oms.sdsauctionbid.service.UserCommissionService;
import com.oms.sdsauctionbid.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
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
    UserService userService;
    UserCommissionService userCommissionService;

    @Autowired
    public SdsAuctionDelegate(AuctionRepository auctionRepository, ProductRepository productRepository,
                              AuctionBidRepository auctionBidRepository,
                              AuctionSettingsRepository auctionSettingsRepository,
                              AuctionWinnerRepository auctionWinnerRepository,
                              UserRepository userRepository,
                              UserService userService,
                              UserCommissionService userCommissionService) {
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
        this.auctionBidRepository = auctionBidRepository;
        this.auctionSettingsRepository = auctionSettingsRepository;
        this.auctionWinnerRepository = auctionWinnerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userCommissionService = userCommissionService;
    }

    public List<EachBidResponse> submitAuctionBid(Bids bids, User dealer) throws Exception {

        User trader = this.userRepository.findById(bids.getTraderId()).get();
       Optional.ofNullable(trader)
                        .orElseThrow(() -> {
                            new Exception("Trader id is not valid");
                            return null;
                        });
        Optional<Auction> optionalAuction = this.auctionRepository.findById(bids.getAuctionId());
       Auction auction =  Optional.ofNullable(optionalAuction).map(auc -> {if(!auc.get().getCurrentlyActive()) {
            throw new RuntimeException("Auction has Ended");}
        return auc.get();
        })
            .orElseThrow(() -> new Exception("Auction Not Found or has ended"));

        AuctionSettings auctionSettings = this.auctionSettingsRepository.getFirstAuctionSettingRecord();
        Optional.ofNullable(auctionSettings)
                .orElseThrow(() -> new Exception("Auction Settings Not Found"));

        Map<Integer, User> getCommissionMapForUser = userService
                .getCommissionMapForUser(dealer, (int)(auctionSettings.getCommission() * 100));


        return bids.getBids().stream().map(bid -> {
            try {
                return this.processBid(bid, trader, auction, dealer, getCommissionMapForUser, auctionSettings);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    private EachBidResponse processBid(Bid bid, User user, Auction auction, User dealer,
                                       Map<Integer, User> getCommissionMapForUser, AuctionSettings auctionSettings)
            throws Exception {

        Optional<Product> product = auction.getProducts().stream().filter(prod -> prod.getProductId()
                == bid.getProductId()).findFirst();
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

        userCommissionService.assignUserCommissionForOneTransaction(getCommissionMapForUser, auctionBid.getBidId(),
                (double) ((auctionBid.calculateTotalDownCount()+auctionBid.calculateTotalUpCount())*
                        Optional.ofNullable(auctionSettings.getBidAmount()).orElse(0)));

        EachBidResponse eachBidResponse = new EachBidResponse();
        eachBidResponse.setBarCode(auctionBid.getBidId());
        eachBidResponse.setProductId(prod.getProductId());
        eachBidResponse.setProductName(prod.getProductName());
        return eachBidResponse;
    }
}
