package com.cherry.erp.modules.production.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.cherry.erp.common.utils.ReferenceConstant.PRODUCTION_LINE_REFERENCE_QUERY;

public interface ProductionLigneRepository extends GenericRepository<ProductionLigne> {
    @Query(value = PRODUCTION_LINE_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query("select l from ProductionLigne as l where l.company.id = :companyId order by l.creationDate desc")
    Page<ProductionLigne> findByCompany(Integer companyId, Pageable pageable);
    @Query("select l from ProductionLigne as l where l.reference = :reference and l.company.id = :companyId")
    ProductionLigne getByReference(String reference, Integer companyId);
}
