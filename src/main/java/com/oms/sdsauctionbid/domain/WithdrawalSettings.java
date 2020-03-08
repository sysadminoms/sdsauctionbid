package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_WithdrawalSettings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class WithdrawalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne()
    @JoinColumn(name = "userTypeId")
    private UserType userType;

    @Column(name = "min_withdrawal_amount")
    private Double minWithdrawalAmount;

    @Column(name = "max_withdrawal_amount")
    private Double maxWithdrawalAmount;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private boolean status;

}
