package com.oms.sdsauctionbid.service;

import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.domain.UserAccountTransaction;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import com.oms.sdsauctionbid.utils.TransactionType;
import org.joda.time.DateTime;
import static com.oms.sdsauctionbid.utils.DoubleFormatter.round;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserAccountTransactionService {

        private AccountTransactionRepository accountTransactionRepository;

    public UserAccountTransactionService(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public void processAccountTransactionForUser(User user, String transactionId, Double transactionAmount,
                                                 Boolean transactionStatus, String transactionDetails, TransactionType transactionType) {
        UserAccountTransaction userAccountTransaction = new UserAccountTransaction();
        userAccountTransaction.setUser(user);
        DateTime dateTime = new DateTime(); // Initializes with the current date and time
        DateTimeFormatter customFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        userAccountTransaction.setTransactionDateTime(customFormatter.print(dateTime));
        userAccountTransaction.setTransactionEpochTime(Instant.now().toEpochMilli());
        userAccountTransaction.setTransactionId(transactionId);
        userAccountTransaction.setTransactionAmount(transactionAmount);
        userAccountTransaction.setTransactionStatus(transactionStatus);
        userAccountTransaction.setTransactionDetails(transactionDetails);
        Double availableBalance = Optional.ofNullable(accountTransactionRepository
                .getAvailableBalance(user.getId())).orElse(0.0)+transactionAmount;
        userAccountTransaction.setAvailableBalance(round(availableBalance,2));
        userAccountTransaction.setTransactionType(transactionType);
        accountTransactionRepository.save(userAccountTransaction);
    }
}


