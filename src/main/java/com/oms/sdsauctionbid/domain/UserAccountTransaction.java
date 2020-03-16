package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "SDS_User_Account_Transaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accountTransactionId"})
})

@Setter
@Getter
public class UserAccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountTransactionId;

    @ManyToOne()
    @JoinColumn(name = "Id")
    private User user;

    @Column(name="transaction_amount")
    private Double transactionAmount;

    @Column(name="transaction_epoch_time")
    private Long transactionEpochTime;

    @Column(name="transaction_date_time")
    private String transactionDateTime;

    @Column(name="transaction_id")
    private String transactionId;

    @Column(name="transaction_type")
    private String transactionType;

    @Column(name="transaction_status")
    private Boolean transactionStatus;
}
