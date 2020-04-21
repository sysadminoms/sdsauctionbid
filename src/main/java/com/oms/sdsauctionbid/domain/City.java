package com.oms.sdsauctionbid.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SDS_City", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "cityId"
        })
})

@Setter
@Getter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(name="city_name")
    private String cityName;

    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "state_id")
    private State state;

}
