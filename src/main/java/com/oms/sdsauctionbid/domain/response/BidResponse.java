package com.oms.sdsauctionbid.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BidResponse {
   private int status;
   private List<EachBidResponse> bidResults;
}
