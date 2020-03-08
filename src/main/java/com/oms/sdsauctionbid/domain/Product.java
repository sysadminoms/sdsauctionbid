package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "SDS_ManageProduct", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productId"})
})

@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name="product_name")
    private String productName;

    @ManyToMany
    @JoinColumn(name = "auctionId")
    private Collection<Auction> auctions;

    @Column(name="product_description")
    private String productDescription;

    @Column(name="image")
    private String image;

    @Column(name="image_path")
    private String imagePath;

    @Column(name="minimum_price")
    private Double minimumPrice;

    @Column(name="open_price")
    private Double openPrice;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private boolean status;

    @Column(name="admin_brought_forward_amount")
    private double adminBroughtForwardAmount;
}
