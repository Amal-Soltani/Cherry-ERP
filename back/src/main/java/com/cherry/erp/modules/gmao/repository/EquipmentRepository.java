package com.cherry.erp.modules.gmao.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.gmao.model.persistent.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.cherry.erp.common.utils.ReferenceConstant.EQUIPMENT_REFERENCE_QUERY;

public interface EquipmentRepository extends GenericRepository<Equipment> {
    @Query(value = EQUIPMENT_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query("select e from Equipment as e where e.company.id = :companyId order by e.creationDate desc")
    Page<Equipment> findByPage(Integer companyId, Pageable pageParam);



}
