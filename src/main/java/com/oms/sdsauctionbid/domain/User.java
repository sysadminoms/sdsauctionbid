package com.oms.sdsauctionbid.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oms.sdsauctionbid.utils.Identifiable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "SDS_UserDetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        }),
        @UniqueConstraint(columnNames = {
                "user_id"
        })
})

@Setter
@Getter
public class User implements Serializable, Identifiable<String> {
    @Id
    @GeneratedValue(
            generator = "assigned-sequence",
            strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "assigned-sequence",
            strategy = "com.oms.sdsauctionbid.utils.StringSequenceIdentifier",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_prefix", value = "A")
            }
    )
    private String Id;

    @ManyToOne()
    @JoinColumn(name = "userTypeId")
    private UserType userType;

    @OneToMany()
    @JoinColumn(name = "accountTransactionId")
    private List<UserAccountTransaction> userAccountTransaction = new ArrayList<>();

    @NotBlank
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @NotBlank
    private String lastName;

    @Column(name="agency_name")
    private String agencyName;

    @Column(name="mobile_number")
    private String mobileNo;

    @Column(name="user_id")
    private String userId;

    @Column(name="password")
    private String password;

    @Column(name="address")
    private String address;

    @Column(name="village")
    private String village;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="zip_code")
    private String zipCode;

    @Column(name="aadhar_no")
    private String aadharNo;

    @Column(name="aadhar_file_name")
    private String aadharFileName;

    @Column(name="aadhar_file_path")
    private String aadharFilePath;

    @Column(name="pan_no")
    private String panNo;

    @Column(name="pan_file_name")
    private String panFileName;

    @Column(name="pan_file_path")
    private String panFilePath;

    @Column(name="gst_no")
    private String gstNo;

    @Column(name="gst_file_name")
    private String gstFileName;

    @Column(name="gst_file_path")
    private String gstFilePath;

    @Column(name="contact_person_name")
    private String contactPersonName;

    @Column(name="contact_person_mobile")
    private String contactPersonMobile;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "commissionId")
    private UserComission userCommissionId;

    @Column(name="minimum_balance")
    private Double minimumBalance;

    @Column(name="profile_image")
    private String profileImage;

    @Column(name="email_address")
    private String emailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User businessDeveloper;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private Boolean status;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User parent;

    @OneToMany(mappedBy="parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<User> children;

    public String getName() {
        return this.firstName + " " + this.secondName + " " + this.lastName;
    }
}
