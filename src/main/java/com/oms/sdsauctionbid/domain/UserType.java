package com.oms.sdsauctionbid.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oms.sdsauctionbid.utils.UserTypeName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SDS_UserType", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "userTypeId"
        }),
        @UniqueConstraint(columnNames = {
                "UserTypeName"
        })
})

@Setter
@Getter
public class UserType {
        @Id
        private String userTypeId;

        @Enumerated(EnumType.STRING)
        private UserTypeName userTypeName;

        private Long userTypeLevel;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String typeDescription;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank
        private Boolean status = false;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "Id")
        private List<User> users = new ArrayList<>();

}
