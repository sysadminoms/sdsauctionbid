package com.oms.sdsauctionbid.service;

import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import com.oms.sdsauctionbid.utils.TransactionType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class UserCommissionService {

        private AccountTransactionRepository accountTransactionRepository;

    public UserCommissionService(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public void calculateCommissionForTransaction(Map<Integer,User> userCommissionMap, Map<String, Integer> transactionAmountMap) {
      transactionAmountMap.forEach((k,v) -> {
          userCommissionMap.forEach((commissionK,commissionV) -> {
              UserAccountTransaction userAccountTransaction = new UserAccountTransaction();
              userAccountTransaction.setTransactionStatus(true);
              userAccountTransaction.setTransactionAmount((double)(commissionK*v)/100);
              userAccountTransaction.setTransactionDetails("commission");
              userAccountTransaction.setTransactionId(k);
              userAccountTransaction.setTransactionType(TransactionType.BROKERAGE);
              DateTime dateTime = new DateTime(); // Initializes with the current date and time
              DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
              userAccountTransaction.setTransactionDateTime(customFormatter.print(dateTime));
              userAccountTransaction.setTransactionEpochTime(Instant.now().toEpochMilli());
          });
        });
    }
}


