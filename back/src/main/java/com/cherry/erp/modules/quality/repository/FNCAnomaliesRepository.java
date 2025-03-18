package com.cherry.erp.modules.quality.repository;

import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FNCAnomaliesRepository extends JpaRepository<FNCAnomalies,Integer> {
    @Query(value = "select * from fnc_anomalie fa where fa.fnc_id = :fnc ",nativeQuery = true)
    public List<FNCAnomalies> findByFNC(@Param("fnc") Integer fnc);
    @Transactional
    @Modifying
    @Query(value = "delete from fnc_anomalie fa " +
            "where fa.fnc_id = :fncId " +
            "and fa.id = :id",nativeQuery = true)
    void deleteByFNCAndId(@Param("fncId")Integer fncId,@Param("id")Integer id);


    @Query(value = "SELECT EXTRACT(MONTH FROM f.date) AS month, " +
            "SUM(fa.nc) AS nc, " +
            "SUM(fa.rebut) AS rebut, " +
            "SUM(fa.cout) AS cout, " +
            "COUNT(DISTINCT fa.fnc_id) AS fnc_count " +
            "FROM fnc_anomalie fa " +
            "JOIN fnc f on fa.fnc_id = f.id " +
            "WHERE EXTRACT(YEAR FROM f.date) = :year " +
            "AND f.company_id = :company_id " +
            "GROUP BY EXTRACT(MONTH FROM f.date) " +
            "ORDER BY month ",nativeQuery = true)
    public List<Object[]> findFNCCountByYearAndMonth(@Param("year")Integer year, @Param("company_id")Integer company_id);


    @Query(value = "SELECT EXTRACT(WEEK FROM f.date) AS week, " +
            "SUM(fa.nc) AS nc, " +
            "SUM(fa.rebut) AS rebut, " +
            "SUM(fa.cout) AS cout, " +
            "COUNT(DISTINCT fa.fnc_id) AS fnc_count " +
            "FROM fnc_anomalie fa " +
            "JOIN fnc f on fa.fnc_id = f.id " +
            "WHERE EXTRACT(YEAR FROM f.date) = :year " +
            "AND f.company_id = :company_id " +
            "GROUP BY EXTRACT(WEEK FROM f.date) " +
            "ORDER BY week ",nativeQuery = true)
    public List<Object[]> findFNCCountByYearAndWeek(@Param("year")Integer year, @Param("company_id")Integer company_id);



}
