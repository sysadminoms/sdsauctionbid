package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.City;
import com.oms.sdsauctionbid.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    List<City> findByState(State state);
}
