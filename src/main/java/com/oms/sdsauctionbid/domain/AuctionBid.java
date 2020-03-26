package com.oms.sdsauctionbid.domain;

import com.oms.sdsauctionbid.utils.TicketStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "SDS_Auction_Bid", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"bidId"})
})

@Setter
@Getter
public class AuctionBid {
    @Id
    @GeneratedValue(
            generator = "product-sequence",
            strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "product-sequence",
            strategy = "com.oms.sdsauctionbid.utils.AuctionBidSequenceIdentifier"
    )
    private String bidId;

    @ManyToOne
    @JoinColumn(name = "auctionId")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "Id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Column(name="bid_date_time")
    private String bidDateTime;

    @Column(name="claim_status")
    private Boolean claimStatus;

    @Column(name="claim_type")
    private Boolean claimType;

    @Column(name = "sell_or_delivery")
    private Boolean sellDelivery;

    @Column(name="auction_bid_time")
    private Long bidTime;

    @Column(name="claimDateTime")
    private DateTime claimDateTime;

    @Column(name="bids_one_up")
    private int bidOneUp;

    @Column(name="bids_two_up")
    private int bidTwoUp;

    @Column(name="bids_three_up")
    private int bidThreeUp;

    @Column(name="bids_four_up")
    private int bidFourUp;

    @Column(name="bids_five_up")
    private int bidFiveUp;

    @Column(name="bids_six_up")
    private int bidSixUp;

    @Column(name="bids_seven_up")
    private int bidSevenUp;

    @Column(name="bids_eight_up")
    private int bidEightUp;

    @Column(name="bids_nine_up")
    private int bidNineUp;

    @Column(name="bids_Ten_up")
    private int bidTenUp;

    @Column(name="bids_one_down")
    private int bidOneDown;

    @Column(name="bids_two_down")
    private int bidTwoDown;

    @Column(name="bids_three_down")
    private int bidThreeDown;

    @Column(name="bids_four_down")
    private int bidFourDown;

    @Column(name="bids_five_down")
    private int bidFiveDown;

    @Column(name="bids_six_down")
    private int bidSixDown;

    @Column(name="bids_seven_down")
    private int bidSevenDown;

    @Column(name="bids_eight_down")
    private int bidEightDown;

    @Column(name="bids_nine_down")
    private int bidNineDown;

    @Column(name="bids_ten_down")
    private int bidTenDown;

    @Column(name="total_count_up")
    private int totalCountUp;

    @Column(name="total_count_down")
    private int totalCountDown;

    @Column(name="dealer_id")
    private String dealerId;

    @Column(name="ticket_status")
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    public int calculateTotalUpCount() {
        return Optional.ofNullable(this.bidOneUp).orElse(0)
                + Optional.ofNullable(this.bidTwoUp).orElse(0)
                + Optional.ofNullable(this.bidThreeUp).orElse(0)
                + Optional.ofNullable(this.bidFourUp).orElse(0)
                + Optional.ofNullable(this.bidFiveUp).orElse(0)
                + Optional.ofNullable(this.bidSixUp).orElse(0)
                + Optional.ofNullable(this.bidSevenUp).orElse(0)
                + Optional.ofNullable(this.bidEightUp).orElse(0)
                + Optional.ofNullable(this.bidNineUp).orElse(0)
                + Optional.ofNullable(this.bidTenUp).orElse(0);
    }

    public int calculateTotalDownCount() {
        return Optional.ofNullable(this.bidOneDown).orElse(0)
                + Optional.ofNullable(this.bidTwoDown).orElse(0)
                + Optional.ofNullable(this.bidThreeDown).orElse(0)
                + Optional.ofNullable(this.bidFourDown).orElse(0)
                + Optional.ofNullable(this.bidFiveDown).orElse(0)
                + Optional.ofNullable(this.bidSixDown).orElse(0)
                + Optional.ofNullable(this.bidSevenDown).orElse(0)
                + Optional.ofNullable(this.bidEightDown).orElse(0)
                + Optional.ofNullable(this.bidNineDown).orElse(0)
                + Optional.ofNullable(this.bidTenDown).orElse(0);
    }
}
