package com.oms.sdsauctionbid.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oms.sdsauctionbid.utils.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SDS_Deliveryorder", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "orderId"
        })
})

@Setter
@Getter
public class DeliveryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String userId;

    private String bidId;

    private Long productId;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "shipping_charges")
    private double shippingCharges;

    @Column(name = "price")
    private double price;

    @Column(name = "order_date_time")
    private String orderDateTime;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "courier_partner_name")
    private String courierPartnerName;

    @Column(name = "courier_tracking_url")
    private String courierTrackingUrl;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "courier_status")
    private String courierStatus;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "update_date")
    private String updateTime;

    @Column(name = "status")
    private Boolean status;

    @Column(name="epoch_creation_Date_Time")
    public Long epochCreationDateTime;

}
