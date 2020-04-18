package com.oms.sdsauctionbid.domain.response;

import com.oms.sdsauctionbid.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryTicketResponse {
   private String imagePath;
   private String productName;
   private String bidWinner;
   private Double lotPrice;
   private Integer lotSize;
   private Long bidEpochDate;
   private Integer winningBid;
   private Double brokeragePaid;
   private Double dealerBalance;
   private Double amountPayable;
   private Double netFinalValue;
   private Integer totalKilograms;

}
