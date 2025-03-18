package com.cherry.erp.modules.quality.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.cherry.erp.common.utils.ReferenceConstant.FNC_REFERENCE_QUERY;

public interface FNCRepository extends GenericRepository<FNC> {

    @Query(value = FNC_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query("select f from FNC as f where f.company.id = :companyId order by f.creationDate desc")
    Page<FNC> findByCompany(Integer companyId, Pageable pageable);
    @Query(value = "SELECT * FROM fnc f WHERE EXTRACT(YEAR FROM f.date) = :year AND f.company_id = :companyId ",nativeQuery = true)
    public List<FNC> findByYearAndCompanyId(@Param("year") Integer year, @Param("companyId") Integer companyId);
    @Query(value = "SELECT * FROM fnc " +
            "WHERE fnc.company_id = :id " +
            "ORDER BY fnc.date ASC LIMIT 1; ",nativeQuery = true)
    public FNC findTheFirstDate(@Param("id")Integer id);

    @Query(value = "WITH TotalFNC AS (" +
            "    SELECT COUNT(f.id) AS total_fnc " +
            "    FROM fnc f " +
            "    JOIN fnc_anomalie fa ON f.id = fa.fnc_id " +
            "    JOIN anomalie a ON fa.anomalies_id = a.id " +
            "    WHERE EXTRACT(YEAR FROM f.date) = :year " +
            "    AND f.company_id = :companyId" +
            "), " +
            "CategoryFNC AS (" +
            "    SELECT a.categorie, COUNT(f.id) AS fnc_count " +
            "    FROM fnc f " +
            "    JOIN fnc_anomalie fa ON f.id = fa.fnc_id " +
            "    JOIN anomalie a ON fa.anomalies_id = a.id " +
            "    WHERE EXTRACT(YEAR FROM f.date) = :year " +
            "    AND f.company_id = :companyId " +
            "    GROUP BY a.categorie" +
            ") " +
            "SELECT cf.categorie, cf.fnc_count, total_fnc, " +
            "ROUND((CAST(cf.fnc_count AS numeric) / tf.total_fnc) * 100, 2) AS percentage " +
            "FROM CategoryFNC cf, TotalFNC tf " +
            "ORDER BY cf.categorie",
            nativeQuery = true)
    List<Object[]> findFNCPercentageByYearAndCompany(@Param("year") Integer year, @Param("companyId") Integer companyId);


}
