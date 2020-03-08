package com.oms.sdsauctionbid.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SDS_State", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="state_name")
    private String stateName;

}
