package com.oms.sdsauctionbid.repository;


import com.oms.sdsauctionbid.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "select * from sds_user_details u where u.user_id = ?",
            nativeQuery = true)
    User getUserByUserId(String userId);

}
