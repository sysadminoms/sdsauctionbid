package com.oms.sdsauctionbid.repository;



import com.oms.sdsauctionbid.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, String> {

    @Query(value = "select * from sds_user_type where user_type_name = ?1", nativeQuery = true)
    UserType getUserTypeByName(String userTypeName);

    @Query(value = "select * from sds_user_type", nativeQuery = true)
    List<UserType> getAllUserType();

    UserType findByUserTypeLevel(Long level);
}

