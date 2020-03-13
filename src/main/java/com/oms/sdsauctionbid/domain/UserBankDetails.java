package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_UserBankDetails")

@Setter
@Getter
public class UserBankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User userDetails;

    @Column(name="account_holder_name")
    private String accountHolderName;

    @Column(name="account_type")
    private String accountType;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="bank_ifsc_code")
    private String bankIfscCode;

    @Column(name="account_number")
    private String accountNumber;

    @Column(name="upload_cancelled_check")
    private String uploadCancelledCheck;

    @Column(name="cancelled_check_image")
    private String cancelledCheckImage;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private Boolean status;
}
