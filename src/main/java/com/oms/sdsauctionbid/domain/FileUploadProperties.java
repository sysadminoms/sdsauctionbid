package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {
    private String uploadDir;
}
