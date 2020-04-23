package com.oms.sdsauctionbid.service;


import com.oms.sdsauctionbid.domain.*;
import com.oms.sdsauctionbid.repository.AccountTransactionRepository;
import com.oms.sdsauctionbid.repository.LoggedInUserDetailsRepository;
import com.oms.sdsauctionbid.repository.TokenCacheDetailsRepository;
import com.oms.sdsauctionbid.repository.UserRepository;
import com.oms.sdsauctionbid.utils.UserTypeName;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private AccountTransactionRepository accountTransactionRepository;

    private TokenCacheDetailsRepository tokenCacheDetailsRepository;

    private LoggedInUserDetailsRepository loggedInUserDetailsRepository;

    private UserTypeService userTypeService;
    private static final Logger LOG = LoggerFactory.getLogger(User.class);

    private static Timestamp timestamp;

/*
    public Double findUserBalance(String userId) {
        User user = this.userRepository.findById(userId).get();
        return Optional.ofNullable(user).map(actualUser -> findUserBalanceForUser(actualUser))
                .orElse(Double.parseDouble("0"));
    }
*/

/*    public Double findUserBalanceForUser(User user) {
        List<UserAccountTransaction> accountTransactionList = user.getUserAccountTransaction();
        return accountTransactionList
                .stream().map(accountTransaction -> accountTransaction.getTransactionAmount())
                .collect(Collectors.summingDouble(Double::doubleValue));
    }*/

    public Double findUserBalance(String userId) {

        return Optional.ofNullable(accountTransactionRepository.getAvailableBalance(userId))
                .orElse(Double.parseDouble("0"));
    }

    public UserService(UserRepository userRepository, AccountTransactionRepository accountTransactionRepository, TokenCacheDetailsRepository tokenCacheDetailsRepository, LoggedInUserDetailsRepository loggedInUserDetailsRepository, UserTypeService userTypeService) {
        this.userRepository = userRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.tokenCacheDetailsRepository = tokenCacheDetailsRepository;
        this.loggedInUserDetailsRepository = loggedInUserDetailsRepository;
        this.userTypeService = userTypeService;
    }

    public User getUserDetailsById(String id) {
        return userRepository.findById(id).get();
    }

    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllChildUsers(String id) {
        User currentUser = getUserDetailsById(id);
        return Optional.ofNullable(currentUser).map(User::getChildren).orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toList());
    }

    public User getParentUser(String id) {
        User currentUser = getUserDetailsById(id);
        return Optional.ofNullable(currentUser).map(User::getParent).orElse(null);
    }

    public UserDetails loadUserByUsername(String userId) {
        User providedUser = userRepository.getUserByUserId(userId);
        if (providedUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + userId);
        }
        return new org.springframework.security.core.userdetails.User(providedUser.getUserId(),
                providedUser.getPassword(),
                AuthorityUtils.createAuthorityList(providedUser.getUserType().getUserTypeLevel().toString()));
    }

    public User getUserDetailsByUserId(String userId) {
        return userRepository.getUserByUserId(userId);
    }

    public User createUser(User loggedInUser, JSONObject jsonObject, UserType userType, String encode,
                           FileUploadResponse adhaarUploadResponse,

                           FileUploadResponse panUploadResponse,
                           FileUploadResponse gstUploadResponse) {
        LOG.info("In UserService class to Create User Details");
        timestamp = new Timestamp(System.currentTimeMillis());

        User newUser = new User();
        newUser.setUserType(userType);
        newUser.setFirstName((String) jsonObject.get("firstName"));
        newUser.setSecondName((String) jsonObject.get("secondName"));
        newUser.setLastName((String) jsonObject.get("lastName"));
        newUser.setAgencyName((String) jsonObject.get("agencyName"));
        newUser.setMobileNo((String) jsonObject.get("mobileNo"));
        newUser.setAddress((String) jsonObject.get("address"));
        newUser.setVillage((String) jsonObject.get("village"));
//        newUser.setCity((String) jsonObject.get("city"));
//        newUser.setState((String) jsonObject.get("state"));
        newUser.setZipCode(jsonObject.get("zipCode").toString());
        newUser.setAadharNo(jsonObject.get("aadharNo").toString());
        newUser.setAadharFileName(adhaarUploadResponse.getFileName());
        newUser.setAadharFilePath(adhaarUploadResponse.getFilePath());
        newUser.setPanNo((String) jsonObject.get("panNo"));
        newUser.setPanFileName(panUploadResponse.getFileName());
        newUser.setPanFilePath(panUploadResponse.getFilePath());
//        newUser.setGstNo((String) jsonObject.get("gstNo"));
//        newUser.setGstFileName(gstUploadResponse.getFileName());
//        newUser.setGstFilePath(gstUploadResponse.getFilePath());
        newUser.setContactPersonName((String) jsonObject.get("contactPersonName"));
        newUser.setContactPersonMobile((String) jsonObject.get("contactPersonMobile"));
        newUser.setUserId(jsonObject.get("contactPersonMobile").toString() + "@sdstradeing.in");
        newUser.setEmailAddress((String) jsonObject.get("emailAddress"));
        newUser.setLastUpdatedBy((String) jsonObject.get("lastUpdatedBy"));
        newUser.setStatus((Boolean) jsonObject.get("status"));
        newUser.setPassword(encode);
        if (newUser.getUserType().getUserTypeLevel() == loggedInUser.getUserType().getUserTypeLevel() + 1) {
            newUser.setParent(loggedInUser);
        }
        newUser.setUpdateDateTime(timestamp);
        return userRepository.save(newUser);
    }

    public User updateUser(String id, JSONObject jsonObject, UserType userType, String encode,
                           FileUploadResponse adhaarUploadResponse,
                           FileUploadResponse panUploadResponse,
                           FileUploadResponse gstUploadResponse) {
        LOG.info("In UserService class to Update User Details");
        timestamp = new Timestamp(System.currentTimeMillis());

        User userUpdate = userRepository.findById(id).get();

        userUpdate.setUserType(userType);
        userUpdate.setUserType(userType);
        userUpdate.setFirstName((String) jsonObject.get("firstName"));
        userUpdate.setSecondName((String) jsonObject.get("secondName"));
        userUpdate.setLastName((String) jsonObject.get("lastName"));
        userUpdate.setAgencyName((String) jsonObject.get("agencyName"));
        userUpdate.setMobileNo((String) jsonObject.get("mobileNo"));
        userUpdate.setAddress((String) jsonObject.get("address"));
        userUpdate.setVillage((String) jsonObject.get("village"));
//        userUpdate.setCity((String) jsonObject.get("city"));
//        userUpdate.setState((String) jsonObject.get("state"));
        userUpdate.setZipCode(jsonObject.get("zipCode").toString());
        userUpdate.setAadharNo(jsonObject.get("aadharNo").toString());
        userUpdate.setAadharFileName(adhaarUploadResponse.getFileName());
        userUpdate.setAadharFilePath(adhaarUploadResponse.getFilePath());
        userUpdate.setPanNo((String) jsonObject.get("panNo"));
        userUpdate.setPanFileName(panUploadResponse.getFileName());
        userUpdate.setPanFilePath(panUploadResponse.getFilePath());
//        userUpdate.setGstNo((String) jsonObject.get("gstNo"));
//        userUpdate.setGstFileName(gstUploadResponse.getFileName());
//        userUpdate.setGstFilePath(gstUploadResponse.getFilePath());
        userUpdate.setContactPersonName((String) jsonObject.get("contactPersonName"));
        userUpdate.setContactPersonMobile((String) jsonObject.get("contactPersonMobile"));
        userUpdate.setUserId(jsonObject.get("contactPersonMobile").toString() + "@sdstradeing.in");
        userUpdate.setEmailAddress((String) jsonObject.get("emailAddress"));
        userUpdate.setLastUpdatedBy((String) jsonObject.get("lastUpdatedBy"));
        userUpdate.setStatus((Boolean) jsonObject.get("status"));
        userUpdate.setPassword(encode);
        userUpdate.setUpdateDateTime(timestamp);
        return userRepository.save(userUpdate);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(String id) {
        LOG.info("In UserService class to Delete User Details");
        userRepository.deleteById(id);

    }

    public List<User> getAdmin() {
        UserType superAdmin = userTypeService.getUserTypeByName(UserTypeName.SUPER_ADMIN.toString());
        return userRepository.getUsersByUserType(superAdmin.getUserTypeId());
    }

    public String changeUserStatus(String id, boolean status) {

        LOG.info("In UserService to changeUserStatus ");
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).get();
            user.setStatus(status);
            userRepository.save(user);
            return "ACCEPTED";
        } else
            return "NOT_FOUND";
    }

    public Map<String, User> getCommissionMapForUser(User user, Integer totalCommissionPercentage) {
        Map<String, User> commissionUserMap = new HashMap<>();
        int commission;
        while (user != null) {
            if (Optional.ofNullable(user).map(User::getParent).map(User::getId).orElse(null) == user.getId()) {
                commissionUserMap.put(totalCommissionPercentage + "#" + user.getId(), user);
                user = null;
            } else {
                commission = Optional.ofNullable(user.getUserCommissionPercentage()).orElse(0);
                commissionUserMap.put(commission + "#" + user.getId(), user);
                totalCommissionPercentage = totalCommissionPercentage - commission;
                user = user.getParent();
            }
        }

        return commissionUserMap;
    }

    public void createTokenCacheDetails(String userId,String tokenIat,String ip,String macAddress) {
        TokenCacheDetails tokenCacheDetails = getTokenCacheDetailsByUserId(userId);
        if(tokenCacheDetails == null) {
            tokenCacheDetails = new TokenCacheDetails();
        }
        // deleteTokenCacheDetailsByUserId(userId);
        tokenCacheDetails.setUserId(userId);
        tokenCacheDetails.setIatUserId(tokenIat);
        tokenCacheDetails.setCreationEpochTime(Instant.now().toEpochMilli());
        tokenCacheDetailsRepository.save(tokenCacheDetails);
        createLoggedUserDetails(userId,ip,macAddress);
    }

    public void createLoggedUserDetails(String userId,String ip,String macAddress) {
        LoggedInUserDetails loggedInUserDetails = new LoggedInUserDetails();
        loggedInUserDetails.setIpAddress(ip);
        loggedInUserDetails.setMacAddress(macAddress);
        loggedInUserDetails.setLoginId(userId);
        loggedInUserDetails.setLoginDateAndTime(new Date(System.currentTimeMillis()));
        loggedInUserDetailsRepository.save(loggedInUserDetails);
    }

    public void deleteTokenCacheDetailsByUserId(String userId) {
        TokenCacheDetails tokenCacheDetails = getTokenCacheDetailsByUserId(userId);
        if(tokenCacheDetails != null) {
            tokenCacheDetailsRepository.deleteById(tokenCacheDetails.getId());
        }
    }

    public TokenCacheDetails getTokenCacheDetailsByUserId(String userId) {
        return Optional.ofNullable(tokenCacheDetailsRepository.getTokenCacheDetailsByUserId(userId)).orElse(null);
    }

}


