package com.oms.sdsauctionbid.repository;


import com.oms.sdsauctionbid.domain.UserBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository  extends JpaRepository<UserBankDetails, Long> {

    @Query(value = "select * from sds_user_bank_details where user_details_id = ?", nativeQuery = true)
    UserBankDetails getBankDetailsByUserId(String userId);

    @Query(value = "select * from sds_user_bank_details where account_number = ?", nativeQuery = true)
    UserBankDetails getBankDetailsByAccountNumber(String accountNumber);

}
