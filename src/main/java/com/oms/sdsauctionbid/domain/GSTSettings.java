package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_GstSettings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class GSTSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="product_name")
    private String productName;

    @Column(name="hsn_code")
    private String hsnCode;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private Boolean status;

}
