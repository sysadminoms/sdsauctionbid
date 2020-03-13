package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.CompanyBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyBankAccountRepository extends JpaRepository<CompanyBankAccount,Long> {

    @Query(value = "select * from sds_company_bank cb where cb.id = ?",
            nativeQuery = true)
    CompanyBankAccount getCompanyBankAccountById(Long id);
}
