package com.oms.sdsauctionbid.logic;

import com.oms.sdsauctionbid.domain.AccountTransaction;
import com.oms.sdsauctionbid.domain.User;
import com.oms.sdsauctionbid.domain.request.BidIds;
import com.oms.sdsauctionbid.domain.response.TransactionForBid;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import com.oms.sdsauctionbid.repository.UserRepository;
import com.oms.sdsauctionbid.service.UserAccountTransactionService;
import com.oms.sdsauctionbid.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SdsAccountDelegate {

    UserRepository userRepository;
    UserService userService;
    AccountTransactionRepository accountTransactionRepository;
    UserAccountTransactionService userAccountTransactionService;

    public SdsAccountDelegate(UserRepository userRepository, AccountTransactionRepository accountTransactionRepository,
                              UserAccountTransactionService userAccountTransactionService, UserService userService) {
        this.userRepository = userRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.userAccountTransactionService = userAccountTransactionService;
        this.userService = userService;
    }

   public String addAccountTransaction(AccountTransaction accountTransaction) {

       User user = this.userRepository.findById(accountTransaction.getUserId()).get();
       Optional.ofNullable(user)
               .orElseThrow(() -> {
                   new Exception("User id is not valid");
                   return null;
               });
       userAccountTransactionService.processAccountTransactionForUser(user, accountTransaction.getTransactionId(),
               accountTransaction.getTransactionAmount(), accountTransaction.getTransactionStatus(),
               accountTransaction.getTransactionDetail(), accountTransaction.getTransactionType());
       return "Success";
    }

    public double getUserAccountBalance(String userId) {
        return this.userService.findUserBalance(userId);
    }

    public List<?> getAccountTransactionsForBids(BidIds bidIds) {
        return bidIds.getBidIds().stream().map(bidId ->
                this.accountTransactionRepository.getTransactionsForBid(bidId.getBidId())).collect(Collectors.toList());
    }


}
