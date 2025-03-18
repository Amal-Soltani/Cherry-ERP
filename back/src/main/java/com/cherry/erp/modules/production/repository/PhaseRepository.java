package com.cherry.erp.modules.production.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import com.cherry.erp.modules.production.model.persistent.Phase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhaseRepository extends GenericRepository<Phase> {
    @Query(value = "select * from phase p " +
            "where p.name = :name " +
            "and p.company_id = :companyId ", nativeQuery = true)
    Phase findPhaseByName(@Param("name") String name,@Param("companyId")  Integer companyId);

    @Query("select p from Phase as p where p.company.id = :companyId order by p.creationDate desc")
    Page<Phase> findByCompany(Integer companyId, Pageable pageable);


    @Query(value = "select DISTINCT * from phase p " +
            "inner join gm_phase gp on gp.phase_id = p.id " +
            "inner join gamme g on gp.gamme_id = g.id " +
            "inner join product p2 on p2.gamme_id = g.id " +
            "inner join tache t on t.product_id = p2.id " +
            "where t.id = :tacheId and p.company_id = :companyId ", nativeQuery = true)
    List<Phase> getPhaseByTache (@Param("tacheId") Integer tacheId,@Param("companyId")  Integer companyId);

}

