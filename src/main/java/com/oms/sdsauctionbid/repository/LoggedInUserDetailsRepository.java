package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.LoggedInUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggedInUserDetailsRepository extends JpaRepository<LoggedInUserDetails,Long> {

}
