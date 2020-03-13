package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_PrinterRoll", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })})
@Getter
@Setter
public class PrinterRoll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "product_name")
    @NotNull
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "size")
    private int size;

    @Column(name = "product_pricing")
    private double productPricing;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "update_date_time")
    private Timestamp updateDateTime;

    @Column(name = "status")
    private boolean status;

}
