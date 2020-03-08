package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_CompanySettings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class CompanySettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private String companyName;

    @Column(name="address")
    private String address;

    @Column(name="email")
    private String email;

    @Column(name="website")
    private String website;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="mobile_no")
    private String mobileNo;

    @Column(name="pan_no")
    private String panNo;

    @Column(name="tan_no")
    private String tanNo;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private boolean status;
}
