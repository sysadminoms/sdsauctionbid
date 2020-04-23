package com.oms.sdsauctionbid.repository;



import com.oms.sdsauctionbid.domain.TokenCacheDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenCacheDetailsRepository extends JpaRepository<TokenCacheDetails,Long> {

    @Query(value = "SELECT * FROM sds_token_cache_details where user_id= ?", nativeQuery = true)
    TokenCacheDetails getTokenCacheDetailsByUserId(String userId);

}
