package com.oms.sdsauctionbid.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oms.sdsauctionbid.utils.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SDS_PrintRollOrder", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "orderId"
        })
})

@Setter
@Getter
public class PrintRollOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User userId;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private PrinterRoll productId;

    @Column(name = "quantity")
    private Long quantity;

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

    @Column(name = "comment")
    private String comment;

    @Column(name = "dispatch_date")
    private String dispatchDate;

    @Column(name = "courier_date")
    private String courierDate;

    public String getUserName() {
        return this.userId.getName();
    }

    public String getEmail() {
        return this.userId.getEmailAddress();
    }

    public String getPhone() {
        return this.userId.getMobileNo();
    }


}
