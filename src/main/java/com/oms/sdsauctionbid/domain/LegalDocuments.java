package com.oms.sdsauctionbid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_LegalDocuments")

@Setter
@Getter
public class LegalDocuments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="document_name")
    private String documentName;

    @Column(name="upload_file_name")
    private String uploadFileName;

    @Column(name="upload_file_path")
    private String uploadFilePath;

    @Column(name="last_updated_by")
    private String lastUpdatedBy;

    @Column(name="update_date_time")
    private Timestamp updateDateTime;

    @Column(name="status")
    private boolean status;

}
