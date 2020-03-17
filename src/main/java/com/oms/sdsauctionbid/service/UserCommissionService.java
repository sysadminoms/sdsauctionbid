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

import static com.oms.sdsauctionbid.utils.TransactionType.*;

@Service
public class UserCommissionService {

        private AccountTransactionRepository accountTransactionRepository;
        private UserAccountTransactionService userAccountTransactionService;

    public UserCommissionService(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public void assignCommissionForTransaction(Map<Integer,User> userCommissionMap,
                                                  Map<String, Integer> transactionAmountMap) {
      transactionAmountMap.forEach((k,v) -> {
          userCommissionMap.forEach((commissionK,commissionV) -> {
              userAccountTransactionService.processAccountTransactionForUser(commissionV, k,
                      (double)(commissionK*v)/100, true, "Commission",
                        BROKERAGE);
          });
        });
    }
}


