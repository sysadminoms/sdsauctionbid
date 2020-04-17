package com.oms.sdsauctionbid.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimTicketAsDeliveryResponse {
    String response;
    DeliveryTicketResponse deliveryTicketResponse;
    int status;
}
