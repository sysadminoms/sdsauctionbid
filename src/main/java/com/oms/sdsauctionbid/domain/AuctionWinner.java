package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SDS_Auction_Winner", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"winnerID"})
})

@Setter
@Getter
public class AuctionWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long winnerID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auctionid")
    private Auction auction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name="auction_winning_percentage")
    private String auctionWinningPercentage;

    @Column(name="auction_total_comission_paid")
    private Double totalComissionPaid;

    @Column(name="admin_brought_forward_amount")
    private Double adminBroughtForwardAmount;

    @Column(name="total_lots")
    private Integer totalLots;

    @Column(name="auction_lot_size")
    private Integer auctionLotSize;

    @Column(name="winning_lot_size")
    private Integer WinningLotSize;

    @Column(name="open_price")
    private Double openPrice;

    @Column(name="minimum_price")
    private Double minimumPrice;

    @Column(name="epoch_dateTime_of_winning_bid")
    public Long epochDateTimeOfWinningBid;

    @Column(name="delivery_only_auction")
    private Boolean deliveryOnlyAuction;

    @Column(name="max_auction_win_amount_per_lot")
    private Double maxAuctionWinAmountPerLot;

}
