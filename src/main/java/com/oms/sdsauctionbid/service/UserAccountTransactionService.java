package com.oms.sdsauctionbid.service;

import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserAccountTransactionService {

        private AccountTransactionRepository accountTransactionRepository;

    public UserAccountTransactionService(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public void processAccountTransactionForUser(User user, String transactionId, Double transactionAmount,
                                                 Boolean transactionStatus, String transactionType) {
        UserAccountTransaction userAccountTransaction = new UserAccountTransaction();
        userAccountTransaction.setUser(user);
        DateTime dateTime = new DateTime(); // Initializes with the current date and time
        DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        userAccountTransaction.setTransactionDateTime(customFormatter.print(dateTime));
        userAccountTransaction.setTransactionEpochTime(Instant.now().toEpochMilli());
        userAccountTransaction.setTransactionId(transactionId);
        userAccountTransaction.setTransactionAmount(transactionAmount);
        userAccountTransaction.setTransactionStatus(transactionStatus);
        userAccountTransaction.setTransactionType(transactionType);
        accountTransactionRepository.save(userAccountTransaction);
    }
}


