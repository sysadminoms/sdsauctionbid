package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Setter
@Getter
public class LegalDocumentsRequest {

    private Long id;

    private String documentName;

    private String uploadFile;

    private String lastUpdatedBy;

    private Timestamp updateDateTime;

    private boolean status;

    private MultipartFile file;
}
