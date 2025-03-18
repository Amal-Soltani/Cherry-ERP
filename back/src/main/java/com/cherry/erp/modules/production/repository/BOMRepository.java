package com.cherry.erp.modules.production.repository;

import com.cherry.erp.modules.production.model.persistent.BOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.cherry.erp.common.utils.ReferenceConstant.BOM_REFERENCE_QUERY;

public interface BOMRepository extends JpaRepository<BOM, Integer> {
    @Query(value = BOM_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

}
