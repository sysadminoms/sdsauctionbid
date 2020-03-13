package com.oms.sdsauctionbid.repository;

import com.oms.sdsauctionbid.domain.LegalDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalDocumentsRepository extends JpaRepository<LegalDocuments, Long> {

    @Query(value = "select * from sds_legal_documents ld where ld.id = ?", nativeQuery = true)
    LegalDocuments getCompanyBankAccountById(Long id);
}
