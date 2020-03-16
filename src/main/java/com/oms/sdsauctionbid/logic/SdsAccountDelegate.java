package com.oms.sdsauctionbid.logic;

import com.oms.sdsauctionbid.domain.AccountTransaction;
import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import com.oms.sdsauctionbid.repository.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class SdsAccountDelegate {

    UserRepository userRepository;
    AccountTransactionRepository accountTransactionRepository;

    public SdsAccountDelegate(UserRepository userRepository, AccountTransactionRepository accountTransactionRepository) {
        this.userRepository = userRepository;
        this.accountTransactionRepository = accountTransactionRepository;
    }

   public void addAccountTransaction(AccountTransaction accountTransaction) {

       User user = this.userRepository.findById(accountTransaction.getUserId()).get();
       Optional.ofNullable(user)
               .orElseThrow(() -> {
                   new Exception("User id is not valid");
                   return null;
               });
       processAccountTransactionForUser(user, accountTransaction.getTransactionId(),
               accountTransaction.getTransactionAmount(), accountTransaction.getTransactionStatus(),
               accountTransaction.getTransactionType());
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
