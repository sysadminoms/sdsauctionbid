package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.ShippingCharges;
import com.oms.sdsauctionbid.domain.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShippingChargesRepository extends CrudRepository<ShippingCharges, Long> {
    @Query(value = "SELECT SHIPPING_CHARGES.price,SHIPPING_CHARGES.type\n" +
            "FROM sds_shipping_charges AS SHIPPING_CHARGES\n" +
            "LEFT JOIN sds_state AS STATE\n" +
            "ON STATE.id = SHIPPING_CHARGES.state_id where STATE.state_name=?",nativeQuery = true)
    List<Object[]> findByStateIdAndType(String stateName);
}
