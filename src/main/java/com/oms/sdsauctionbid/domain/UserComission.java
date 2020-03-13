package com.oms.sdsauctionbid.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_UserCommissionSettings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class UserComission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne()
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @Column(name="min_comission")
    private Double minCommission;

    @Column(name="max_comission")
    private Double maxCommission;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private Boolean status;
}
