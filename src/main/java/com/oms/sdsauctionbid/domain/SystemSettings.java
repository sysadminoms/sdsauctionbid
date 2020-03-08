package com.oms.sdsauctionbid.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SDS_SystemSettings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "Id"
        })
})

@Setter
@Getter
public class SystemSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="SMS_API_Name")
    private String smsApiName;

    @Column(name="SMS_API_Key")
    private String smsApiKey;

    @Column(name="SMS_API_PWD")
    private String smsApiPwd;

    @Column(name="SMS_API_SenderID")
    private String smsApiSenderId;

    @Column(name="SMS_Status")
    private boolean smsStatus;

    @Column(name="Email_Host")
    private String emailHost;

    @Column(name="Email_Id")
    private String emailId;

    @Column(name="Email_PWD")
    private String emailPwd;

    @Column(name="Email_Smtp_Port")
    private String emailSmtpPort;

    @Column(name="Email_Sender_Name")
    private String emailSenderName;

    @Column(name="Email_Status")
    private boolean emailStatus;

    @Column(name="Payment_Gateway_Name")
    private String paymentGatewayName;

    @Column(name="Payment_API")
    private String paymentApi;

    @Column(name="Payment_API_key")
    private String paymentApikey;

    @Column(name="Payment_API_Status")
    private boolean paymentApiStatus;

    @Column(name="Last_Updated_By")
    private String lastUpdatedBy;

    @Column(name="Update_Date_Time")
    private Timestamp updateDateTime;

}
