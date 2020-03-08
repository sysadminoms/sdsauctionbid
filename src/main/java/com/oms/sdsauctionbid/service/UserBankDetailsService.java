package com.oms.sdsauctionbid.service;

import com.oms.sdsauctionbid.domain.FileUploadResponse;
import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.domain.UserBankDetails;
import com.oms.sdsauctionbid.repository.BankRepository;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserBankDetailsService {
    private BankRepository bankRepository;

    private static Timestamp timestamp;

    public UserBankDetailsService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    public void createUserBankDetails(User createdUser, JSONObject bankDetailsObj, FileUploadResponse chequeUploadResponse) {
        timestamp = new Timestamp(System.currentTimeMillis());

        UserBankDetails userBankDetails = new UserBankDetails();
        userBankDetails.setBankName((String) bankDetailsObj.get("bankName"));
        userBankDetails.setUserDetails(createdUser);
        userBankDetails.setAccountHolderName((String) bankDetailsObj.get("accountHolderName"));
        userBankDetails.setAccountType((String) bankDetailsObj.get("accountType"));
        userBankDetails.setAccountNumber((String) bankDetailsObj.get("accountNumber"));
        userBankDetails.setBankIfscCode((String) bankDetailsObj.get("bankIfscCode"));
        userBankDetails.setUploadCancelledCheck(chequeUploadResponse.getFileName());
        userBankDetails.setCancelledCheckImage(chequeUploadResponse.getFilePath());
        userBankDetails.setLastUpdatedBy((String) bankDetailsObj.get("lastUpdatedBy"));
        userBankDetails.setUpdateDateTime(timestamp);
        userBankDetails.setStatus((Boolean) bankDetailsObj.get("status"));

        bankRepository.save(userBankDetails);
    }

    public void updateUserBankDetails(User updatedUser, JSONObject bankDetailsObj, FileUploadResponse chequeUploadResponse) {
        timestamp = new Timestamp(System.currentTimeMillis());
        UserBankDetails userBankDetails = bankRepository.getBankDetailsByUserId(updatedUser.getId());

        if (userBankDetails!= null) {
            userBankDetails.setBankName((String) bankDetailsObj.get("bankName"));
            userBankDetails.setUserDetails(updatedUser);
            userBankDetails.setAccountHolderName((String) bankDetailsObj.get("accountHolderName"));
            userBankDetails.setAccountType((String) bankDetailsObj.get("accountType"));
            userBankDetails.setAccountNumber((String) bankDetailsObj.get("accountNumber"));
            userBankDetails.setBankIfscCode((String) bankDetailsObj.get("bankIfscCode"));
            userBankDetails.setUploadCancelledCheck(chequeUploadResponse.getFileName());
            userBankDetails.setCancelledCheckImage(chequeUploadResponse.getFilePath());
            userBankDetails.setLastUpdatedBy((String) bankDetailsObj.get("lastUpdatedBy"));
            userBankDetails.setUpdateDateTime(timestamp);
            userBankDetails.setStatus((Boolean) bankDetailsObj.get("status"));

            bankRepository.save(userBankDetails);
        }
    }

    public UserBankDetails getBankDetailsByUserId(String userId){
        return  bankRepository.getBankDetailsByUserId(userId);
    }

    public void deleteUserBankDetails(String userId){

        UserBankDetails userBankDetails = bankRepository.getBankDetailsByUserId(userId);
        bankRepository.deleteById(userBankDetails.getId());

    }
    public UserBankDetails getUserBankDetailsByAccountNumber(String accountNumber) {
        return  bankRepository.getBankDetailsByAccountNumber(accountNumber);
    }
}
