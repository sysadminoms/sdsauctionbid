package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SDS_TokenCacheDetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Id"})
})

@Setter
@Getter
public class TokenCacheDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name="iat_user_id")
    private String iatUserId;

    @Column(name="user_id")
    private String userId;

    @Column(name="creation_epoch_time")
    private Long creationEpochTime;
}
