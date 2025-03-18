package com.cherry.erp.common.repository;

import com.cherry.erp.common.model.persistent.GenericParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenericParameterRepository extends JpaRepository<GenericParameter, Integer> {

    @Query(value = "SELECT t.* FROM generic_parameters t WHERE t.company_id = :companyId limit 1", nativeQuery = true)
    GenericParameter findByCompany(Integer companyId);

}
