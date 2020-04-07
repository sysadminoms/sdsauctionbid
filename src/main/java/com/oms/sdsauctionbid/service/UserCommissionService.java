package com.oms.sdsauctionbid.service;

import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import com.oms.sdsauctionbid.utils.TransactionType;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.oms.sdsauctionbid.utils.TransactionType.*;

@Service
public class UserCommissionService {

    private AccountTransactionRepository accountTransactionRepository;
    private UserAccountTransactionService userAccountTransactionService;

    public UserCommissionService(AccountTransactionRepository accountTransactionRepository,
                                 UserAccountTransactionService userAccountTransactionService) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.userAccountTransactionService = userAccountTransactionService;
    }

    public void assignCommissionForAllTransactions(Map<Integer,User> userCommissionMap,
                                                   Map<String, Double> transactionAmountMap) {
        transactionAmountMap.forEach((transactionId, transactionAmount) -> {
            assignUserCommissionForOneTransaction(userCommissionMap, transactionId, transactionAmount, 0.0);
        });
    }

    public void assignUserCommissionForOneTransaction(Map<Integer, User> userCommissionMap, String transactionId,
                                                      Double transactionAmount, double tdsPayable) {
        userCommissionMap.forEach((commissionPercentage, user) -> {
            assignOneUserCommissionForOneTransaction(user, transactionId, transactionAmount, commissionPercentage,
                    tdsPayable);
        });
    }

    public void assignUserCommissionForOneTransactionWithTDSPercentageForAllTransactions
            (Map<String, User> userCommissionMap, String transactionId,
                                                      Double transactionAmount, double tdsPayablePercentage) {
        userCommissionMap.forEach((commissionPercentage, user) -> {
            assignOneUserCommissionForOneTransactionWithTDSPercentageForAllTransactions(user, transactionId,
                    transactionAmount, commissionPercentage.split("#")[0],
                    tdsPayablePercentage);
        });
    }

    private void assignOneUserCommissionForOneTransaction(User user, String transactionId, double transactionAmount,
                                                          Integer commissionPercentage, double tdsPayable) {
        userAccountTransactionService.processAccountTransactionForUser(user, transactionId,
                (transactionAmount * commissionPercentage)/100, true,
                "Commission", TransactionType.COMMISSION, tdsPayable);
    }

    private void assignOneUserCommissionForOneTransactionWithTDSPercentageForAllTransactions
            (User user, String transactionId, double transactionAmount,
                                                          String commissionPercentage, double tdsPayable) {
        double transAmountForUserTransaction = (transactionAmount * Integer.parseInt(commissionPercentage))/100;
        userAccountTransactionService.processAccountTransactionForUser(user, transactionId,
                transAmountForUserTransaction * (100-tdsPayable)/100
                , true,
                "Commission", TransactionType.COMMISSION,
                transAmountForUserTransaction*tdsPayable/100);
    }


}



