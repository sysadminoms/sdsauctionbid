package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SDS_LoggedInUserDetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Id"})
})

@Setter
@Getter
public class LoggedInUserDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name="login_id")
    private String loginId;

    @Column(name="mac_address")
    private String macAddress;

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="login_date_and_time")
    private Date loginDateAndTime;
}
