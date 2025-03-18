package com.cherry.erp.company.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.company.model.persistent.ReferenceConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface ReferenceConfigRepository extends GenericRepository<ReferenceConfig> {

    Optional<ReferenceConfig> findByCompanyIdAndObjectAndYear(Integer companyId, String object, @Nullable Integer year);

    @Query(value = "select * from cherry_ref_configs where company_id = :companyId and object = :object " +
            "and is_active = true order by modification_date desc limit 1", nativeQuery = true)
    Optional<ReferenceConfig> findByCompanyIdAndObject(Integer companyId, String object);

    @Query(value = "select * from cherry_ref_configs where company_id = :companyId and object = :object and year is null " +
            "and is_active = true order by modification_date desc limit 1", nativeQuery = true)
    Optional<ReferenceConfig> findByCompanyIdAndObjectAndYearNull(Integer companyId, String object);

    List<ReferenceConfig> findByCompanyIdAndYearOrderByObject(Integer companyId, Integer year);

    List<ReferenceConfig> findByCompanyIdAndYearIsNullOrderByObject(Integer companyId);

}
