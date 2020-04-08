package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "SDS_Auction", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"auctionID"})
})

@Setter
@Getter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionID;

    @Column(name="auction_days")
    private String auctionDays;


    @Column(name="auction_start_time")
    private Time auctionStartTime;


    @Column(name="auction_end_time")
    private Time auctionEndTime;

    @Column(name="auction_date")
    private Date auctionDate;

    @Column(name="auction_mode")
    private Boolean auctionMode;  //Mobile or Desktop

    @Column(name="auction_type")
    private Boolean auctionType;   //Delivery or Sell

    @Column(name="last_updated_by")
    private String lastUpdatedBy;


    @Column(name="update_date_time")
    private Date updateDateTime;

    @Column(name="currently_active")
    private Boolean currentlyActive; //If the Auction is currently Active

    @ManyToMany
    @JoinColumn(name = "productId")
    private Set<Product> products;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "winnerID")
    private Set<AuctionWinner> auctionWinners;

    @Column(name="auction_lot_size")
    private Integer auctionLotSize;

    @Column(name="bid_amount")
    private Integer bidAmount;

    @Column(name="commission")
    private Double commission;
}
