package com.oms.sdsauctionbid.repository;



import com.oms.sdsauctionbid.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {

    State findByStateName(String stateName);
}
