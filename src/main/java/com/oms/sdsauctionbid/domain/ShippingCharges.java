package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SDS_ShippingCharges", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class ShippingCharges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "state_id")
    @ManyToOne
    private State stateId;

    @Column(name = "price")
    private double price;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "update_date_time")
    private String updateDateTime;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "type")
    private String type;

    @Column(name = "price_type")
    private String priceType;

    @Transient
    private String stateName;

}
