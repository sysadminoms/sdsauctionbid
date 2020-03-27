package com.oms.sdsauctionbid.logic;

import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.domain.response.AllBidsResponse;
import com.oms.sdsauctionbid.domain.response.EachBidResponse;
import com.oms.sdsauctionbid.repository.*;
import com.oms.sdsauctionbid.service.UserAccountTransactionService;
import com.oms.sdsauctionbid.service.UserCommissionService;
import com.oms.sdsauctionbid.service.UserService;
import com.oms.sdsauctionbid.utils.TicketStatus;
import com.oms.sdsauctionbid.utils.TransactionType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.oms.sdsauctionbid.utils.TransactionType.BROKERAGE;


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
    UserAccountTransactionService userAccountTransactionService;

    @Autowired
    public SdsAuctionDelegate(AuctionRepository auctionRepository, ProductRepository productRepository,
                              AuctionBidRepository auctionBidRepository,
                              AuctionSettingsRepository auctionSettingsRepository,
                              AuctionWinnerRepository auctionWinnerRepository,
                              UserRepository userRepository,
                              UserService userService,
                              UserCommissionService userCommissionService,
                              UserAccountTransactionService userAccountTransactionService) {
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
        this.auctionBidRepository = auctionBidRepository;
        this.auctionSettingsRepository = auctionSettingsRepository;
        this.auctionWinnerRepository = auctionWinnerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userCommissionService = userCommissionService;
        this.userAccountTransactionService = userAccountTransactionService;
    }

    public AllBidsResponse submitAuctionBid(Bids bids, User dealer) throws Exception {

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

        Double userBalance = Optional.ofNullable(this.userService.findUserBalance(dealer.getId()))
                .orElse(Double.parseDouble("0"));

        int noOfBids = bids.getBids().stream().collect(Collectors.summingInt(Bid::getTotalBids));

        if ((userBalance - noOfBids * Optional.ofNullable(auctionSettings.getBidAmount()).orElse(0)) < 0) {
            throw new Exception("Balance is Low, please increase balance");
        }
        Map<Integer, User> getCommissionMapForUser = userService
                .getCommissionMapForUser(dealer, (int)(auctionSettings.getCommission() * 100));


        List<EachBidResponse> bidList =  bids.getBids().stream().map(bid -> {
            try {
                return this.processBid(bid, trader, auction, dealer, getCommissionMapForUser, auctionSettings);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }).collect(Collectors.toList());

        double userAccountBalance = Optional.ofNullable(this.userService.findUserBalance(dealer.getId())).orElse(0.0);
        return new AllBidsResponse(userAccountBalance, bidList);
    }

    private EachBidResponse processBid(Bid bid, User user, Auction auction, User dealer,
                                       Map<Integer, User> getCommissionMapForUser, AuctionSettings auctionSettings)
            throws Exception {
            Optional<Product> product = auction.getProducts().stream().filter(prod -> prod.getProductId()
                    == bid.getProductId()).findFirst();
            Product prod = Optional.ofNullable(product).get().orElseThrow(() -> new Exception("Product Not Found"));
            AuctionBid  auctionBid = new AuctionBid();
            auctionBid.setAuction(auction);
            auctionBid.setTicketStatus(TicketStatus.NOT_CLAIMED);
            auctionBid.setUser(user);
            auctionBid.setBidTime(Instant.now().toEpochMilli());
            auctionBid.setProduct(prod);
            DateTime dateTime = new DateTime(); // Initializes with the current date and time
            DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

            auctionBid.setBidDateTime(customFormatter.print(dateTime));
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
        userAccountTransactionService.processAccountTransactionForUser(dealer, auctionBid.getBidId(),
                ((double) (-1*(auctionBid.calculateTotalDownCount()+auctionBid.calculateTotalUpCount())*
                        Optional.ofNullable(auctionSettings.getBidAmount()).orElse(0))), true,
                "Commission", BROKERAGE);

        userCommissionService.assignUserCommissionForOneTransaction(getCommissionMapForUser, auctionBid.getBidId(),
                (double) ((auctionBid.calculateTotalDownCount()+auctionBid.calculateTotalUpCount())*
                        Optional.ofNullable(auctionSettings.getBidAmount()).orElse(0)));

        EachBidResponse eachBidResponse = new EachBidResponse();
        eachBidResponse.setBarCode(auctionBid.getBidId());
        eachBidResponse.setProductId(prod.getProductId());
        eachBidResponse.setProductName(prod.getProductName());
        return eachBidResponse;
    }


    public String claimTicket(String bidId, String type, User dealer) throws Exception {
        AuctionBid winningBid;
        if("Sell".equalsIgnoreCase(type) || "Delivery".equalsIgnoreCase(type)) {
            User sundryUser = userRepository.getUserByUserId("SUNDRY");
            Optional<AuctionBid> auctionBid = auctionBidRepository.findById(bidId);
            if (auctionBid.isPresent()) {
                winningBid = auctionBid.get();
            } else {
                throw new Exception("Auction Bid is not present for the id provided");
            }
            if (sundryUser != null) {
                if (winningBid != null) {
                    if (winningBid.getTicketStatus() != TicketStatus.NOT_CLAIMED) {
                        return getTicketStatusMessage(winningBid);
                    }
                    AuctionWinner winner = auctionWinnerRepository
                            .getAuctionWinners(winningBid.getAuction().getAuctionID(),
                                    winningBid.getProduct().getProductId());
                    return processClaimTicket(winningBid,
                            winningBid.calculateWinningBidExist(winner.getAuctionWinningPercentage()),
                            dealer, sundryUser, winner, type);
                    }
                else {
                    throw new Exception("Bid not valid");
                }
            }
            else {
                throw new Exception("Sundry User is not present");
            }
        }
        else {
            throw new Exception("Please provide if you want to Sell your ticket or Claim it");
        }
    }

    private String getTicketStatusMessage(AuctionBid bid) {
        if(bid.getTicketStatus() == TicketStatus.NOT_WINNING) {
            return "Oops ! Sorry , This is not Winning Ticket Number";
        } else if(bid.getTicketStatus() == TicketStatus.CLAIMED_AS_SELL ) {
            return "This Ticket is already Claimed as Sell";
        }
        else if(bid.getTicketStatus() == TicketStatus.CLAIMED_AS_DELIVERY) {
            return "This Ticket is already Claimed as Delivery";
        }
        else {
            return bid.getTicketStatus().toString();
        }
    }

    private String processClaimTicket(AuctionBid ticket, int value, User dealer, User sundryUser,
                                      AuctionWinner winner, String claimType) throws Exception {
        if (ticket.getTicketStatus() == TicketStatus.NOT_CLAIMED) {
            if(value > 0) {
                double valueOfTicket = value * winner.getAuctionLotSize();
                if("Sell".equalsIgnoreCase(claimType)) {
                    userAccountTransactionService.processAccountTransactionForUser(sundryUser, ticket.getBidId(),
                            -1*valueOfTicket, true,
                            "Auction Ticket Claim",
                            TransactionType.SELL_CLAIM);

                    userAccountTransactionService.processAccountTransactionForUser(dealer, ticket.getBidId(),
                            valueOfTicket, true,
                            "Auction Ticket Claim", TransactionType.SELL_CLAIM);

                    ticket.setTicketStatus(TicketStatus.CLAIMED_AS_SELL);
                    ticket.setDealerId(dealer.getId());
                    auctionBidRepository.save(ticket);

                    return "Ticket is winning and " + valueOfTicket + " amount credited to your account";
                }
                else if("Delivery".equalsIgnoreCase(claimType)) {
                     User admin = Optional.ofNullable(userService.getAdmin()).map(list -> list.get(0))
                             .orElse(null);
                     Optional.ofNullable(admin).orElseThrow(() -> {
                         new Exception("Admin User Not Defined");
                         return null;
                     });
                     double sellValue = value * winner.getOpenPrice() - valueOfTicket;
                    Double userBalance = Optional.ofNullable(this.userService.findUserBalance(dealer.getId()))
                            .orElse(Double.parseDouble("0"));
                    if ((userBalance - sellValue) < 0) {
                        throw new Exception("Balance is Low, please increase balance");
                    }else{
                        userAccountTransactionService.processAccountTransactionForUser(dealer, ticket.getBidId(),
                                -1*sellValue, true,
                                "Auction Ticket Delivery", TransactionType.DELIVERY);

                        userAccountTransactionService.processAccountTransactionForUser(sundryUser, ticket.getBidId(),
                                -1*valueOfTicket, true,
                                "Auction Ticket Claim",
                                TransactionType.DELIVERY);

                        userAccountTransactionService.processAccountTransactionForUser(admin, ticket.getBidId(),
                                sellValue+valueOfTicket, true,
                                "Auction Ticket Delivery", TransactionType.DELIVERY);

                        ticket.setDealerId(dealer.getId());
                        auctionBidRepository.save(ticket);
                        return "Ticket is winning and you selected claim for delivery, so" + sellValue +
                                " amount debited from your account";
                    }
                }
        } else {
                ticket.setTicketStatus(TicketStatus.NOT_WINNING);
                auctionBidRepository.save(ticket);
                return "Oops ! Sorry , This is not Winning Ticket Number";
            }
        } else {
            return getTicketStatusMessage(ticket);
        }
        return null;
    }


}
