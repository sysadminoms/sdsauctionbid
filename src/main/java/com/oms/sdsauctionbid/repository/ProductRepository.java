package com.oms.sdsauctionbid.repository;


import com.oms.sdsauctionbid.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from sds_manage_product  where productId = ?", nativeQuery = true)
    Product getProductById(Long id);

    @Query(value = "select * from sds_manage_product  where status = true", nativeQuery = true)
    Set<Product> getActiveProducts();
}
