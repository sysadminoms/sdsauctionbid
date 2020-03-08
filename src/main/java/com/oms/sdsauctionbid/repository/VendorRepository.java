package com.oms.sdsauctionbid.repository;


import com.oms.sdsauctionbid.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {


}
