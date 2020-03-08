package com.oms.sdsauctionbid.service;


import com.oms.sdsauctionbid.domain.UserType;
import com.oms.sdsauctionbid.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {
    private final UserTypeRepository userTypeRepository;

    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAllUserTypes() {
        return userTypeRepository.findAll();
    }

    public UserType getUserTypeByName(String userTypeName){
        return userTypeRepository.getUserTypeByName(userTypeName);
    }

    public UserType getUserTypeByLevel(Long level){
        return userTypeRepository.findByUserTypeLevel(level);
    }

}
