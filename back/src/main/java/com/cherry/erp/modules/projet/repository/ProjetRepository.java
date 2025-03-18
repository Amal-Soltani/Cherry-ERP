package com.cherry.erp.modules.projet.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.projet.model.enums.StatutProjetEnum;
import com.cherry.erp.modules.projet.model.persistent.Projects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.cherry.erp.common.utils.ReferenceConstant.PROJECT_REFERENCE_QUERY;

public interface ProjetRepository extends GenericRepository<Projects> {
    @Query(value = PROJECT_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query("select p from Projects as p where p.company.id = :companyId order by p.creationDate desc")
    Page<Projects> findByCompany(Integer companyId, Pageable pageable);

    @Query("select p from Projects as p where p.company.id = :companyId and p.statut = :status order by p.creationDate desc")
    Page<Projects> findByPageAndStatus(StatutProjetEnum status, Integer companyId, Pageable pageable);

}
