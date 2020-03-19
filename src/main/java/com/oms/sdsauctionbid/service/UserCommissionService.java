package com.oms.sdsauctionbid.service;

import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.oms.sdsauctionbid.utils.TransactionType.*;

@Service
public class UserCommissionService {

        private AccountTransactionRepository accountTransactionRepository;
        private UserAccountTransactionService userAccountTransactionService;

    public UserCommissionService(AccountTransactionRepository accountTransactionRepository, UserAccountTransactionService userAccountTransactionService) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.userAccountTransactionService = userAccountTransactionService;
    }

    public void assignCommissionForAllTransactions(Map<Integer,User> userCommissionMap,
                                                  Map<String, Double> transactionAmountMap) {
      transactionAmountMap.forEach((transactionId, transactionAmount) -> {
          assignUserCommissionForOneTransaction(userCommissionMap, transactionId, transactionAmount);
      });
    }

    public void assignUserCommissionForOneTransaction(Map<Integer, User> userCommissionMap, String transactionId,
                                                      Double transactionAmount) {
        userCommissionMap.forEach((commissionPercentage, user) -> {
            assignOneUserCommissionForOneTransaction(user, transactionId, transactionAmount, commissionPercentage);
        });
    }

    private void assignOneUserCommissionForOneTransaction(User user, String transactionId, double transactionAmount,
                                                          Integer commissionPercentage) {
        userAccountTransactionService.processAccountTransactionForUser(user, transactionId,
                (transactionAmount * commissionPercentage)/100, true,
                "Commission", COMMISSION);
    }


}


