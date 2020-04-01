package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.DeliveryOrder;
import com.oms.sdsauctionbid.domain.ShippingCharges;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryOrderRepository extends CrudRepository<DeliveryOrder, Long> {

}
