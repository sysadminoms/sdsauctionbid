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

    @Column(name="winning_lot_size")
    private Integer WinningLotSize;

    @Column(name="winning_person")
    private String winningPerson;

    @Column(name="Epoch_DateTime_Of_Winning_Bid")
    public Long epochDateTimeOfWinningBid;
}
