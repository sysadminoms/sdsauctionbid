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

    @Autowired
    public SdsAuctionDelegate(AuctionRepository auctionRepository, ProductRepository productRepository,
                              AuctionBidRepository auctionBidRepository,
                              AuctionSettingsRepository auctionSettingsRepository,
                              AuctionWinnerRepository auctionWinnerRepository) {
        this.auctionRepository = auctionRepository;
        this.productRepository = productRepository;
        this.auctionBidRepository = auctionBidRepository;
        this.auctionSettingsRepository = auctionSettingsRepository;
        this.auctionWinnerRepository = auctionWinnerRepository;
    }

    public AuctionData getLiveAuctionDetails() throws Exception {
        AuctionData auctionData = new AuctionData();

        List<ProductData> productDataList = new ArrayList<>();
        Auction auction = this.auctionRepository.getLiveAuctionDetails();

        Optional.ofNullable(auction)
                .orElseThrow(() -> new Exception("No Active Auction Found"));
        AuctionSettings auctionSettings = this.auctionSettingsRepository.getFirstAuctionSettingRecord();
        Optional.ofNullable(auctionSettings)
                .orElseThrow(() -> new Exception("Auction Settings Not Found"));
        productDataList =
        auction.getProducts().stream().map(product -> {
            ProductData productData = new ProductData();
            productData.setProductId(product.getProductId().toString());
            productData.setProductName(product.getProductName());
            productData.setProductImg(product.getImage() + "/" + product.getImagePath());
            productData.setProductPrice(product.getOpenPrice());
            productData.setLotSize(auctionSettings.getAuctionLotSize());
            return productData;
        }).collect(Collectors.toList());


        auctionData.setAuctionId(auction.getAuctionID().toString());
        auctionData.setAuctionStartTime(auction.getAuctionStartTime().toString());
        auctionData.setAuctionEndTime(auction.getAuctionEndTime().toString());
        auctionData.setAuctionDate(auction.getAuctionDate().toString());
        auctionData.setAuctionMode(auction.getAuctionMode());
        auctionData.setAuctionType(auction.getAuctionType());
        auctionData.setProducts(productDataList);

        return auctionData;
    }

    public List<String> submitAuctionBid(Bids bids, User user) throws Exception {
        Auction auction = this.auctionRepository.findById(bids.getAuctionId()).get();
        Optional.ofNullable(auction).map(auc -> {if(!auc.getCurrentlyActive()) {
            throw new RuntimeException("Auction has Ended");}
        return auc;
        })
            .orElseThrow(() -> new Exception("Auction Not Found or has ended"));

        return bids.getBids().stream().map(bid -> {
            try {
                return this.processBid(bid, user, auction);
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



    public WinningAuction calculateActualAmount(LiveAuction liveAuction) {
        winningAuction = new WinningAuction();
        Map<String, Integer> bidMap = new HashMap<>();
        Map<String, Integer> bidMapUp = new HashMap<>();
        Map<String, DateTime> timeMap = new HashMap<>();
        double netValue = 0;
        minBidSizeSet = false;
        int winnerBidSize = 0;
        totalSize = 0;
        minBidSize = 0;
        populateMapUp(bidMap, bidMapUp,"1U", liveAuction.getOneUp(), timeMap, liveAuction.getOneUpDate());
        populateMap(bidMap,"1D", liveAuction.getOneDown(), timeMap, liveAuction.getOneDownDate());
        populateMapUp(bidMap, bidMapUp,"2U", liveAuction.getTwoUp(), timeMap, liveAuction.getTwoUpDate());
        populateMap(bidMap,"2D", liveAuction.getTwoDown(), timeMap, liveAuction.getTwoDownDate());
        populateMapUp(bidMap, bidMapUp,"3U", liveAuction.getThreeUp(), timeMap, liveAuction.getThreeUpDate());
        populateMap(bidMap,"3D", liveAuction.getThreeDown(), timeMap, liveAuction.getThreeDownDate());
        populateMapUp(bidMap, bidMapUp,"4U", liveAuction.getFourUp(), timeMap, liveAuction.getFourUpDate());
        populateMap(bidMap,"4D", liveAuction.getFourDown(), timeMap, liveAuction.getFourDownDate());
        populateMapUp(bidMap, bidMapUp,"5U", liveAuction.getFiveUp(), timeMap, liveAuction.getFiveUpDate());
        populateMap(bidMap,"5D", liveAuction.getFiveDown(), timeMap, liveAuction.getFiveDownDate());
        populateMapUp(bidMap, bidMapUp,"6U", liveAuction.getSixUp(), timeMap, liveAuction.getSixUpDate());
        populateMap(bidMap,"6D", liveAuction.getSixDown(), timeMap, liveAuction.getSixDownDate());
        populateMapUp(bidMap, bidMapUp,"7U", liveAuction.getSevenUp(), timeMap, liveAuction.getSevenUpDate());
        populateMap(bidMap,"7D", liveAuction.getSevenDown(), timeMap, liveAuction.getSevenDownDate());
        populateMapUp(bidMap, bidMapUp,"8U", liveAuction.getEightUp(), timeMap, liveAuction.getEightUpDate());
        populateMap(bidMap,"8D", liveAuction.getEightDown(), timeMap, liveAuction.getEightDownDate());
        populateMapUp(bidMap, bidMapUp,"9U", liveAuction.getNineUp(), timeMap, liveAuction.getNineUpDate());
        populateMap(bidMap,"9D", liveAuction.getNineDown(), timeMap, liveAuction.getNineDownDate());
        populateMapUp(bidMap, bidMapUp,"10U", liveAuction.getTenUp(), timeMap, liveAuction.getTenUpDate());
        populateMap(bidMap,"10D", liveAuction.getTenDown(), timeMap, liveAuction.getTenDownDate());
        winningAuction.setCommission((totalSize * liveAuction.getBidSize())*liveAuction.getCommission());
        netValue = (totalSize * liveAuction.getBidSize()) * (1-liveAuction.getCommission()) + liveAuction.getAdminBoughtForward();
        if(netValue < minBidSize*liveAuction.getLotValue()) {
           winnerBidSize = minBidSize;
        } else {
            winnerBidSize = (int) Math.floor(netValue/liveAuction.getLotValue());
        }
        winningAuction.setTotalLots(totalSize);
        int finalWinnerBidSize = winnerBidSize;
        int max = 0;
        Map<String, Integer> collect = bidMap.entrySet().stream().filter(x -> x.getValue() <= Integer.valueOf(finalWinnerBidSize))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(collect.size() > 0) {
            max = Collections.max(collect.values());
        }
        winningAuction.setWinningLotSize(max);
        winningAuction.setAdminBoughtForwardAmount(netValue - max*liveAuction.getLotValue());

        int finalMax = max;
        Map<String, Integer> finalCollect = bidMapUp.entrySet().stream().filter(x -> x.getValue() == Integer.valueOf(finalMax))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(finalCollect.size() < 1) {
            int finalMax1 = max;
            finalCollect = collect.entrySet().stream().filter(x -> x.getValue() == Integer.valueOf(finalMax1))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        finalCollect.forEach((k,v) -> {
            if (winningAuction.getWinningPerson() == null || (timeMap.get(k) != null
                    && (winningAuction.getWinningDateTime() == null ||
                    timeMap.get(k).isBefore(winningAuction.getWinningDateTime()))
            )) {
                winningAuction.setWinningPerson(k);
                winningAuction.setWinningDateTime(timeMap.get(k));}
        });
        return winningAuction;

    }

    void populateMapUp(Map bidMap, Map bidMapUp, String key, int value, Map timeMap, DateTime bidTime) {
            bidMapUp.put(key, value);
            populateMap(bidMap, key, value, timeMap, bidTime);
    }

    void populateMap(Map bidMap, String key, int value, Map timeMap, DateTime bidTime) {
            if((minBidSize > value || (minBidSize == 0 && !minBidSizeSet))){
                minBidSizeSet = true;
                minBidSize = value;
            }
            totalSize = totalSize + value;
            bidMap.put(key, value);
            if(bidTime != null) {
                timeMap.put(key, bidTime);
            }
    }
    public void createAuction(){
        Auction auction = new Auction();
        auction.setAuctionStartTime(new Time(System.currentTimeMillis()));
        auction.setAuctionDate(new java.sql.Date(System.currentTimeMillis()));
        auction.setAuctionMode(true);
        auction.setProducts(this.productRepository.getActiveProducts());
        auction.setAuctionEndTime(new Time(System.currentTimeMillis()+900000));
        auction.setCurrentlyActive(true);
        auctionRepository.save(auction);
    }




    public String getAuctionWinner(Long auctionId) throws Exception {
        Auction auction = this.auctionRepository.findById(auctionId).get();
        Optional.ofNullable(auction).map(auc -> {if(!auc.getCurrentlyActive()) {
            throw new RuntimeException("Auction has Ended");}
            return auc;
        })
                .orElseThrow(() -> new Exception("Auction Not Found or has ended"));
      AuctionSettings auctionSettings = this.auctionSettingsRepository.getFirstAuctionSettingRecord();
        Optional.ofNullable(auctionSettings)
                .orElseThrow(() -> new Exception("Auction Settings Not Found"));
        Set<Product> auctionProducts = auction.getProducts();
        Map<Long, Product> productIds = auctionProducts.stream().collect(Collectors.toMap(Product::getProductId, prod -> prod));
        Set<Long> productId = productIds.keySet();
        productId.forEach(id -> { try {
            List<Integer[]>  bidValuesForAuction = this.auctionBidRepository
                    .getAllBidValuesForAuction(auctionId,id);
            Integer[] bidValues = Optional.ofNullable(bidValuesForAuction)
                    .map(trend -> trend.get(0)).orElse(new Integer[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});

            this.processWinner(bidValues, auction, auctionId, id, auctionSettings, productIds);
        } catch (Exception e) {
            new RuntimeException(e);
        }});

      //  }
        return "Auction Update Successful";
    }

    public String processWinner(Integer[] bidValues, Auction auction, Long auctionId,
                                Long productId, AuctionSettings auctionSettings, Map<Long, Product> productIds) throws Exception {
        AuctionWinner auctionWinner = null;
        Product processProduct = productIds.get(productId);
        auctionWinner = this.auctionWinnerRepository.getAuctionWinners(auctionId, productId);
       if(auctionWinner == null){
           auctionWinner = new AuctionWinner();
           auctionWinner.setAuction(auction);
           auctionWinner.setProduct(processProduct);
       }


        minBidSizeSet = false;
        minBidSize = 0;
        totalSize = 0;
        int winnerBidSize = 0;
        Map<String, Integer> bidMap = new HashMap<>();
        Map<String, Integer> bidMapUp = new HashMap<>();

        populateUpMap(bidMap, bidMapUp,"bids_one_up", bidValues[0]);
        populateUpMap(bidMap, bidMapUp,"bids_two_up", bidValues[1]);
        populateUpMap(bidMap, bidMapUp,"bids_three_up", bidValues[2]);
        populateUpMap(bidMap, bidMapUp,"bids_four_up", bidValues[3]);
        populateUpMap(bidMap, bidMapUp,"bids_five_up", bidValues[4]);
        populateUpMap(bidMap, bidMapUp,"bids_six_up", bidValues[5]);
        populateUpMap(bidMap, bidMapUp,"bids_seven_up", bidValues[6]);
        populateUpMap(bidMap, bidMapUp,"bids_eight_up", bidValues[7]);
        populateUpMap(bidMap, bidMapUp,"bids_nine_up", bidValues[8]);
        populateUpMap(bidMap, bidMapUp,"bids_ten_up", bidValues[9]);
        populateBidMap(bidMap,"bids_one_down", bidValues[10]);
        populateBidMap(bidMap,"bids_two_down", bidValues[11]);
        populateBidMap(bidMap,"bids_three_down", bidValues[12]);
        populateBidMap(bidMap,"bids_four_down", bidValues[13]);
        populateBidMap(bidMap,"bids_five_down", bidValues[14]);
        populateBidMap(bidMap,"bids_six_down", bidValues[15]);
        populateBidMap(bidMap,"bids_seven_down", bidValues[16]);
        populateBidMap(bidMap,"bids_eight_down", bidValues[17]);
        populateBidMap(bidMap,"bids_nine_down", bidValues[18]);
        populateBidMap(bidMap,"bids_ten_down", bidValues[19]);

        auctionWinner.setTotalComissionPaid((totalSize
                * auctionSettings.getBidAmount())*auctionSettings.getCommission());
        auctionWinner.setTotalLots(totalSize);

        double netAuctionValue = (totalSize * auctionSettings.getBidAmount())
                * (1-auctionSettings.getCommission())
                + Optional.ofNullable(processProduct.getAdminBroughtForwardAmount()).orElse(0.00);

        if(netAuctionValue < minBidSize*auctionSettings.getAuctionLotSize()) {
            winnerBidSize = minBidSize;
        } else {
            winnerBidSize = (int) Math.floor(netAuctionValue/auctionSettings.getAuctionLotSize());
        }

        int finalWinnerBidSize = winnerBidSize;
        int max = 0;
        Map<String, Integer> collect = bidMap.entrySet().stream()
                .filter(x -> x.getValue() <= Integer.valueOf(finalWinnerBidSize))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if(collect.size() > 0) {
            max = Collections.max(collect.values());
        }

        auctionWinner.setWinningLotSize(max);

        double adminBroughtForwardAmount = netAuctionValue - max*auctionSettings.getAuctionLotSize();
        auctionWinner.setAdminBroughtForwardAmount(adminBroughtForwardAmount);
        processProduct.setAdminBroughtForwardAmount(adminBroughtForwardAmount);

        int finalMax = max;
        Map<String, Integer> finalCollect = bidMapUp.entrySet().stream().filter(x -> x.getValue()
                == Integer.valueOf(finalMax))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(finalCollect.size() < 1) {
            int finalMaxTotal = max;
            finalCollect = collect.entrySet().stream().filter(x -> x.getValue() == Integer.valueOf(finalMaxTotal))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        if(max == 0) {
            String key = finalCollect.entrySet().stream().findAny().get().getKey();
            auctionWinner.setWinningPerson(key);
        }else{
            List<String> keys = new ArrayList(finalCollect.keySet());
            Collections.shuffle(keys);
            Map<String, Integer> shuffleMap = new LinkedHashMap<>();
            Map<String, Integer> finalCollect1 = finalCollect;
            keys.forEach(k->shuffleMap.put(k, finalCollect1.get(k)));
            AuctionWinner finalAuctionWinner = auctionWinner;
            shuffleMap.forEach((k, v) -> {
                Long bidTime = Optional.ofNullable(this.auctionBidRepository.getAuctionTimeForBid(auctionId,
                        productId, k)).orElse((long) 0.00);

                if (finalAuctionWinner.getWinningPerson() == null || (
                        bidTime != (long) 0.00 ||
                                (finalAuctionWinner.getEpochDateTimeOfWinningBid() == null ||
                                        bidTime < (finalAuctionWinner.getEpochDateTimeOfWinningBid()))
                )) {
                    finalAuctionWinner.setWinningPerson(k);
                    if (bidTime == (long)0.00) {
                        finalAuctionWinner.setEpochDateTimeOfWinningBid(null);
                    } else {
                        finalAuctionWinner.setEpochDateTimeOfWinningBid(bidTime);
                    }
                }
            });
        }


        this.productRepository.save(processProduct);
        this.auctionWinnerRepository.save(auctionWinner);
        return "Winner is " + auctionWinner.getWinningPerson();
    }


    void populateUpMap(Map bidMap, Map bidMapUp, String key, int value) {
        bidMapUp.put(key, value);
        populateBidMap(bidMap, key, value);
    }

    void populateBidMap(Map bidMap, String key, int value) {
        if((minBidSize > value || (minBidSize == 0 && !minBidSizeSet))){
            minBidSizeSet = true;
            minBidSize = value;
        }
        totalSize = totalSize + value;
        bidMap.put(key, value);
    }

    public String getAuctionTrend(Long auctionId, Long productId) throws Exception {
        Integer getActiveAuctionCount = this.auctionRepository.checkActiveAuctionExists(auctionId);
        Optional.ofNullable(getActiveAuctionCount).map(auctionCount -> {if(auctionCount != 1) {
            throw new RuntimeException("Auction Not Found or has ended");}
            return auctionCount;
        }).orElseThrow(() -> new Exception("Auction Not Found or has ended"));
        List<Integer[]>  trendsForAuction= this.auctionBidRepository.getUpAndDownValuesForAuction(auctionId,productId);
        Integer trendUp = Optional.ofNullable(trendsForAuction)
                .map(trend -> trend.get(0)).map(val -> val[0]).orElse(0);
        Integer trendDown = Optional.ofNullable(trendsForAuction)
                .map(trend -> trend.get(0)).map(val -> val[1]).orElse(0);
        if(trendUp == trendDown){
            return "Both Equal";
        }else if(trendUp > trendDown){
            return "Green Is Up";
        }else{
            return "Red Is Up";
        }
    }
}
