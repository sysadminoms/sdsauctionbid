package com.oms.sdsauctionbid.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="SDS_Vendor", uniqueConstraints ={
        @UniqueConstraint(columnNames ={
                "vendorID"
        })
})

@Getter
@Setter
public class Vendor {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long vendorID;

    @Column(name="vendor_Name")
    private String vendorName;

    @Column(name="vendor_Address")
    private String vendorAddress;

    @Column(name="GST_Number")
    private String gstNumber;

    @Column(name="Bank_Name")
    private String bankName;

    @Column(name="Bank_Account_Number")
    private String bankAccountNumber;

    @Column(name="Account_Type")
    private String accountType;

    @Column(name="IFSC_Code")
    private String ifscCode;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private boolean status;
}
