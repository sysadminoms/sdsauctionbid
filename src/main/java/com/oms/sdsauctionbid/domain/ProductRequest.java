package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ProductRequest {

    private Long Id;

    private String productName;

    private String productDescription;

    private Double minimumPrice;

    private Double openPrice;

    private String lastUpdatedBy;

    private boolean status;

    private MultipartFile productImageFile;
}
