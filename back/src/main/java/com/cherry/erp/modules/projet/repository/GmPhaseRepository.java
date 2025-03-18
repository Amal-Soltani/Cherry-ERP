package com.cherry.erp.modules.projet.repository;

import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GmPhaseRepository extends JpaRepository<GmPhase,Integer> {

    @Query(value = "select * from gm_phase gp where gp.gamme_id = :gammeId ", nativeQuery = true)
    List<GmPhase> findByGammeId(@Param("gammeId") Integer gammeId);

    GmPhase findByGammeIdAndPhaseId(Integer gammeId,Integer phaseId);

    @Modifying
    @Query(value = "delete from gm_phase gp where gp.id = :id " +
            "and gp.gamme_id = :gammeId ",nativeQuery = true)
    void deleteByIdAndGammeId(@Param("id")Integer id,@Param("gammeId")Integer gammeId);

    @Transactional
    @Modifying
    @Query(value = "delete from gm_phase gp " +
            "where gp.gamme_id = :gammeId " +
            "and gp.gamme_id.company_id = :companyId",nativeQuery = true)
    void deleteByGammeId(@Param("gammeId")Integer gammeId,@Param("companyId")Integer companyId);



}
