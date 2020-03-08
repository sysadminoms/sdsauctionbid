package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadResponse {
    private String fileName;
    private String filePath;
    private String fileType;

    public FileUploadResponse(String fileName, String filePath,String fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public FileUploadResponse() {

    }
}
