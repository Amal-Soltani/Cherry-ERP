package com.cherry.erp.modules.projet.repository;

import com.cherry.erp.modules.projet.model.persistent.Planning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning,Integer> {

    List<Planning>findByProjectsId(Integer id);
    @Query("select p from Planning as p where p.projects.company.id = :companyId order by p.creationDate desc")
    Page<Planning> findByCompany(Integer companyId, Pageable pageable);

    @Query("select p from Planning as p where p.projects.id = :projectId order by p.creationDate desc")
    Page<Planning> findByPageAndProject(Integer projectId, Pageable pageable);
    @Query("select p from Planning as p where p.tache.id = :tacheId order by p.creationDate desc")
    Page<Planning> findByPageAndTache(Integer tacheId, Pageable pageable);

    Planning findByIdAndEmployeeId(Integer id, Integer employeeId);
    Planning findByIdAndTacheId(Integer id , Integer tacheId);
    List<Planning> findByTacheId(Integer tacheId);

    @Query(value ="WITH TRSByProject AS (" +
            "    SELECT " +
            "       SUM (p.qte_totale) as qte_totale_sum , " +
            "       SUM (p.qtenc) as qtenc_sum , " +
            "       SUM (p.qte_rebut) as qte_rebut_sum , " +
            "       SUM (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum , " +
            "       SUM (p.temps_arret) as temps_arret_sum, " +
            "       SUM(distinct t.quantite) AS theoretical_quantity " +
            "    FROM planning p " +
            "    JOIN tache t ON t.id = p.tache_id " +
            "    WHERE  p.projects_id = :id  " +
            "    AND t.company_id = :company_id " +
            "), " +
            "Taux_qualite as (" +
            "    SELECT " +
            "       ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite " +
            "    FROM TRSByProject t " +
            "), " +
            "Taux_performance as (" +
            "    SELECT " +
            "       ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance " +
            "    FROM TRSByProject t " +
            "), " +
            "Taux_disponibilite as (" +
            "    SELECT " +
            "       ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite " +
            "    FROM TRSByProject t " +
            ")  " +
            "SELECT  " +
            "    t.qte_totale_sum, " +
            "    t.qtenc_sum, " +
            "    t.qte_rebut_sum , " +
            "    t.temps_reel_sum, " +
            "    t.temps_arret_sum, " +
            "    t.theoretical_quantity , " +
            "    tq.taux_qualite , " +
            "    tp.taux_performance ,  " +
            "    td.taux_disponibilite , " +
            "    ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) * (CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as trs " +
            "FROM TRSByProject t, " +
            "Taux_qualite tq, " +
            "Taux_performance tp, " +
            "Taux_disponibilite td ",nativeQuery = true)
    List<Object[]> findTRSByProject(@Param("id") Integer id, @Param("company_id") Integer companyId);

    @Query(value ="WITH TRSByProject AS (" +
            "                       SELECT " +
            "                            SUM (p.qte_totale) as qte_totale_sum , " +
            "                            SUM (p.qtenc) as qtenc_sum , " +
            "                            SUM (p.qte_rebut) as qte_rebut_sum , " +
            "                            SUM (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum , " +
            "                            SUM (p.temps_arret) as temps_arret_sum, " +
            "                            SUM(distinct t.quantite) AS theoretical_quantity " +
            "                       FROM planning p " +
            "                       JOIN tache t ON t.id = p.tache_id " +
            "                       WHERE  t.company_id = :company_id " +
            "            ), " +
            "            Taux_qualite as (" +
            "                       SELECT  " +
            "                           ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite " +
            "                       FROM TRSByProject t " +
            "            ), " +
            "            Taux_performance as (" +
            "                       SELECT " +
            "                           ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance " +
            "                       FROM TRSByProject t " +
            "            ), " +
            "            Taux_disponibilite as (" +
            "                       SELECT " +
            "                           ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite " +
            "                       FROM TRSByProject t " +
            "            )  " +
            "            SELECT  " +
            "                t.qte_totale_sum, " +
            "                t.qtenc_sum,  " +
            "                t.qte_rebut_sum ,  " +
            "                t.temps_reel_sum,  " +
            "                t.temps_arret_sum,  " +
            "                t.theoretical_quantity , " +
            "                tq.taux_qualite ,  " +
            "                tp.taux_performance ,  " +
            "                td.taux_disponibilite ,  " +
            "                ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) * (CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as trs  " +
            "            FROM TRSByProject t,  " +
            "            Taux_qualite tq,  " +
            "            Taux_performance tp,  " +
            "            Taux_disponibilite td ",nativeQuery = true)
    List<Object[]> findTRSByAllProjects(@Param("company_id") Integer companyId);

    @Query(value = "select " +
            "    p.phase,p.employee_id, e.employee_number, e.first_name , e.last_name, " +
            "    SUM(p.qte_totale) AS qte_totale_sum " +
            "    FROM planning p " +
            "    JOIN tache t ON t.id = p.tache_id " +
            "    join employees e on e.id  = p.employee_id " +
            "    WHERE t.id = :id " +
            "    AND t.company_id = :company_id " +
            "    GROUP BY p.phase, p.employee_id, e.employee_number, e.first_name , e.last_name",nativeQuery = true)
    List<Object[]> QteProduiteGammeEmp(@Param("id") Integer id, @Param("company_id") Integer companyId);

    @Query(value="WITH OFStatic AS (" +
            "    SELECT  " +
            "        SUM(p.qte_totale) AS qte_totale_sum, " +
            "        SUM(p.qtenc) AS qtenc_sum, " +
            "        SUM(p.qte_rebut) AS qte_rebut_sum, " +
            "        ROUND(AVG(temps_machine), 2) AS tempsR, " +
            "        ROUND(MAX(gp.temps), 2) AS tempsEs , " +
            "        p.phase " +
            "    FROM planning p " +
            "    JOIN tache t ON t.id = p.tache_id " +
            "    JOIN product p2 ON p2.id = t.product_id " +
            "    JOIN gamme g on p2.gamme_id = g.id " +
            "    JOIN gm_phase gp ON gp.gamme_id = g.id " +
            "    WHERE t.id = :id " +
            "    AND t.company_id = :company_id " +
            "    GROUP BY p.phase " +
            "), " +
            "Quantity AS (" +
            "    SELECT " +
            "        t.quantite " +
            "    FROM tache t " +
            "    WHERE t.id = :id" +
            ") " +
            "SELECT " +
            "    o.phase, " +
            "    q.quantite, " +
            "    o.qte_totale_sum, " +
            "    o.qtenc_sum, " +
            "    o.qte_rebut_sum, " +
            "    ROUND(o.tempsR / 60 * o.qte_totale_sum, 2) AS temps_reel, " +
            "    ROUND(o.tempsEs / 60 * q.quantite, 2) AS temps_estime " +
            "    FROM OFStatic o " +
            "    JOIN Quantity q ON q.quantite IS NOT null" ,nativeQuery = true)
    List<Object[]> findStaticOF(@Param("id") Integer id, @Param("company_id") Integer companyId);

    //phase
    @Query(value = "WITH TacheQuantite AS (" +
            "    SELECT  " +
            "        SUM(DISTINCT t.quantite) AS theoretical_quantity, " +
            "        p.phase, " +
            "        t.projet_id, " +
            "        t.company_id " +
            "    FROM tache t  " +
            "    JOIN planning p ON p.tache_id = t.id " +
            "    WHERE t.company_id = :company_id  " +
            "    AND t.projet_id = :id " +
            "    GROUP BY p.phase, t.projet_id, t.company_id " +
            "), " +
            "TRSByProject AS ( " +
            "    SELECT  " +
            "        SUM(p.qte_totale) AS qte_totale_sum,  " +
            "        SUM(p.qtenc) AS qtenc_sum,  " +
            "        SUM(p.qte_rebut) AS qte_rebut_sum,  " +
            "        SUM(EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) AS temps_reel_sum, " +
            "        SUM(p.temps_arret) AS temps_arret_sum, " +
            "        p.phase, " +
            "        p.projects_id " +
            "    FROM planning p  " +
            "    WHERE p.projects_id = :id " +
            "    AND p.tache_id IN (SELECT id FROM tache WHERE company_id = :company_id) " +
            "    GROUP BY p.phase, p.projects_id " +
            "), " +
            "Taux_qualite AS (" +
            "    SELECT  " +
            "        phase, " +
            "        ROUND(CAST((SUM(qte_totale_sum) - SUM(qtenc_sum) - SUM(qte_rebut_sum)) AS numeric) / NULLIF(SUM(qte_totale_sum), 0) * 100, 2) AS taux_qualite, " +
            "        projects_id " +
            "    FROM TRSByProject " +
            "    GROUP BY phase, projects_id " +
            "), " +
            "Taux_performance AS (" +
            "    SELECT  " +
            "        r.phase, " +
            "        ROUND(SUM(q.theoretical_quantity) / NULLIF(SUM(r.qte_totale_sum), 0) * 100, 2) AS taux_performance, " +
            "        r.projects_id " +
            "    FROM TRSByProject r " +
            "    JOIN TacheQuantite q ON r.phase = q.phase AND r.projects_id = q.projet_id " +
            "    GROUP BY r.phase, r.projects_id " +
            "), " +
            "Taux_disponibilite AS (" +
            "    SELECT  " +
            "        phase, " +
            "        ROUND((SUM(temps_reel_sum) - SUM(temps_arret_sum)) / NULLIF(SUM(temps_reel_sum), 0) * 100, 2) AS taux_disponibilite, " +
            "        projects_id " +
            "    FROM TRSByProject " +
            "    GROUP BY phase, projects_id " +
            ") " +
            "SELECT  " +
            "    r.phase, " +
            "    r.qte_totale_sum,  " +
            "    r.qtenc_sum,  " +
            "    r.qte_rebut_sum,  " +
            "    r.temps_reel_sum,  " +
            "    r.temps_arret_sum, " +
            "    q.theoretical_quantity, " +
            "    tq.taux_qualite, " +
            "    tp.taux_performance, " +
            "    td.taux_disponibilite, " +
            "    ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) * (CAST(td.taux_disponibilite AS numeric) / 100)) * 100, 2) AS TRS " +
            "FROM TRSByProject r " +
            "JOIN Taux_qualite tq ON r.phase = tq.phase AND r.projects_id = tq.projects_id " +
            "JOIN Taux_performance tp ON r.phase = tp.phase AND r.projects_id = tp.projects_id " +
            "JOIN Taux_disponibilite td ON r.phase = td.phase AND r.projects_id = td.projects_id " +
            "JOIN TacheQuantite q ON r.phase = q.phase AND r.projects_id = q.projet_id " +
            "ORDER BY r.phase; ",nativeQuery = true)
    List<Object[]> findTRSByPhaseProject(@Param("id") Integer id, @Param("company_id") Integer companyId);
    @Query(value = "WITH TacheQuantite AS ( " +
            "                        SELECT  " +
            "                            SUM( distinct t.quantite) AS theoretical_quantity, " +
            "                            p.phase " +
            "                        FROM tache t " +
            "                        JOIN planning p ON p.tache_id = t.id " +
            "                        WHERE t.company_id = :company_id " +
            "                        GROUP BY p.phase, t.company_id " +
            "                        ), " +
            "            TRSByProject AS ( " +
            "                       SELECT  " +
            "                            SUM (p.qte_totale) as qte_totale_sum , " +
            "                            SUM (p.qtenc) as qtenc_sum , " +
            "                            SUM (p.qte_rebut) as qte_rebut_sum , " +
            "                            SUM (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum , " +
            "                            SUM (p.temps_arret) as temps_arret_sum, " +
            "                            p.phase " +
            "                       FROM planning p " +
            "                       JOIN tache t ON t.id = p.tache_id " +
            "                       WHERE  p.tache_id IN (SELECT id FROM tache WHERE company_id = :company_id) " +
            "                       GROUP BY p.phase, p.projects_id " +
            "            ), " +
            "            Taux_qualite as ( " +
            "                       SELECT " +
            "                          phase, " +
            "                          ROUND(CAST((SUM(qte_totale_sum) - SUM(qtenc_sum) - SUM(qte_rebut_sum)) AS numeric) / NULLIF(SUM(qte_totale_sum), 0) * 100, 2) AS taux_qualite " +
            "                       FROM TRSByProject " +
            "                       GROUP BY phase " +
            "            ), " +
            "            Taux_performance as (" +
            "                       SELECT " +
            "                          r.phase, " +
            "                          ROUND(SUM(q.theoretical_quantity) / NULLIF(SUM(r.qte_totale_sum), 0) * 100, 2) AS taux_performance " +
            "                       FROM TRSByProject r " +
            "                       JOIN TacheQuantite q ON r.phase = q.phase " +
            "                       GROUP BY r.phase " +
            "            ), " +
            "            Taux_disponibilite as (" +
            "                       select " +
            "                       phase, " +
            "                       ROUND((SUM(temps_reel_sum) - SUM(temps_arret_sum)) / NULLIF(SUM(temps_reel_sum), 0) * 100, 2) AS taux_disponibilite " +
            "                       FROM TRSByProject r " +
            "                       GROUP BY phase " +
            "            ) " +
            "            SELECT " +
            "                r.phase, " +
            "                r.qte_totale_sum, " +
            "                r.qtenc_sum,  " +
            "                r.qte_rebut_sum,  " +
            "                r.temps_reel_sum, " +
            "                r.temps_arret_sum, " +
            "                q.theoretical_quantity, " +
            "                tq.taux_qualite,  " +
            "                tp.taux_performance,  " +
            "                td.taux_disponibilite, " +
            "                ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) * (CAST(td.taux_disponibilite AS numeric) / 100)) * 100, 2) AS TRS " +
            "            FROM TRSByProject r " +
            "            JOIN Taux_qualite tq ON r.phase = tq.phase " +
            "            JOIN Taux_performance tp ON r.phase = tp.phase " +
            "            JOIN Taux_disponibilite td ON r.phase = td.phase " +
            "            JOIN TacheQuantite q ON r.phase = q.phase " +
            "            ORDER BY r.phase ",nativeQuery = true)
    List<Object[]> findTRSByPhase(@Param("company_id") Integer companyId);

    //EMP
    @Query(value = "WITH TRSByProject AS (" +
            "           SELECT  " +
            "               sum (p.qte_totale) as qte_totale_sum, " +
            "               sum (p.qtenc) as qtenc_sum, " +
            "               sum (p.qte_rebut) as qte_rebut_sum, " +
            "               sum (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum, " +
            "               sum (p.temps_arret) as temps_arret_sum, " +
            "               sum (t.quantite) as theoretical_quantity, " +
            "               p.employee_id, e.employee_number, e.first_name , e.last_name " +
            "           FROM planning p " +
            "           JOIN tache t on t.id = p.tache_id " +
            "           JOIN employees e on e.id  = p.employee_id " +
            "           WHERE p.projects_id = :id " +
            "           AND t.company_id = :company_id " +
            "           GROUP BY p.employee_id, e.employee_number, e.first_name , e.last_name " +
            "),  " +
            "Taux_qualite as (" +
            "           SELECT " +
            "               ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite, " +
            "               employee_id, employee_number, first_name , last_name " +
            "           FROM TRSByProject t " +
            "), " +
            "Taux_performance as (" +
            "            SELECT " +
            "                ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance, " +
            "                employee_id, employee_number, first_name , last_name " +
            "            FROM TRSByProject t " +
            "), " +
            "Taux_disponibilite as (" +
            "             SELECT " +
            "                 ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite, " +
            "                 employee_id, employee_number, first_name , last_name " +
            "FROM TRSByProject t " +
            ") " +
            "SELECT " +
            "    t.employee_id, t.employee_number, t.first_name , t.last_name, " +
            "    tq.taux_qualite, tp.taux_performance, td.taux_disponibilite, " +
            "    ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) *(CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as TRS " +
            "FROM TRSByProject t " +
            "JOIN Taux_qualite tq ON t.employee_id = tq.employee_id " +
            "                     AND t.employee_number = tq.employee_number " +
            "                     AND t.first_name = tq.first_name " +
            "                     AND t.last_name = tq.last_name " +
            "JOIN Taux_performance tp ON t.employee_id = tp.employee_id " +
            "                     AND t.employee_number = tp.employee_number " +
            "                     AND t.first_name = tp.first_name " +
            "                     AND t.last_name = tp.last_name " +
            "JOIN Taux_disponibilite td ON t.employee_id = td.employee_id " +
            "                     AND t.employee_number = td.employee_number " +
            "                     AND t.first_name = td.first_name " +
            "                     AND t.last_name = td.last_name ",nativeQuery = true)
    List<Object[]> findTRSByEmpProject(@Param("id") Integer id, @Param("company_id") Integer companyId);
    @Query(value = "WITH TRSByProject AS (" +
            "                    SELECT  sum (p.qte_totale) as qte_totale_sum, " +
            "                            sum (p.qtenc) as qtenc_sum, " +
            "                            sum (p.qte_rebut) as qte_rebut_sum, " +
            "                            sum (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum, " +
            "                            sum (p.temps_arret) as temps_arret_sum, " +
            "                            sum (distinct t.quantite) as theoretical_quantity, " +
            "                            p.employee_id, e.employee_number, e.first_name , e.last_name " +
            "                     FROM planning p " +
            "                     JOIN tache t on t.id = p.tache_id " +
            "                     JOIN employees e on e.id  = p.employee_id " +
            "                     WHERE t.company_id = :company_id " +
            "                     GROUP BY p.employee_id, e.employee_number, e.first_name , e.last_name " +
            "                    ),  " +
            "Taux_qualite as (" +
            "                     SELECT " +
            "                         ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite, " +
            "                         employee_id, employee_number, first_name , last_name " +
            "                    FROM TRSByProject t " +
            "                    ), " +
            "Taux_performance as (" +
            "                    SELECT " +
            "                         ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance, " +
            "                         employee_id, employee_number, first_name , last_name " +
            "                    FROM TRSByProject t " +
            "                    ), " +
            "Taux_disponibilite as (" +
            "                    SELECT " +
            "                        ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite, " +
            "                        employee_id, employee_number, first_name , last_name " +
            "                    FROM TRSByProject t " +
            "                    ) " +
            "SELECT " +
            "   t.employee_id, t.employee_number, t.first_name , t.last_name, " +
            "   tq.taux_qualite, tp.taux_performance, td.taux_disponibilite, " +
            "   ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) *(CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as TRS " +
            "FROM TRSByProject t " +
            "JOIN Taux_qualite tq ON t.employee_id = tq.employee_id " +
            "                     AND t.employee_number = tq.employee_number " +
            "                     AND t.first_name = tq.first_name " +
            "                     AND t.last_name = tq.last_name " +
            "JOIN Taux_performance tp ON t.employee_id = tp.employee_id " +
            "                     AND t.employee_number = tp.employee_number " +
            "                     AND t.first_name = tp.first_name " +
            "                     AND t.last_name = tp.last_name " +
            "JOIN Taux_disponibilite td ON t.employee_id = td.employee_id " +
            "                     AND t.employee_number = td.employee_number " +
            "                     AND t.first_name = td.first_name " +
            "                     AND t.last_name = td.last_name ",nativeQuery = true)
    List<Object[]> findTRSByEmp(@Param("company_id") Integer companyId);

    //Equipment
    @Query(value = "WITH TRSByProject AS (" +
            "                    select  sum (p.qte_totale) as qte_totale_sum, " +
            "                            sum (p.qtenc) as qtenc_sum, " +
            "                            sum (p.qte_rebut) as qte_rebut_sum, " +
            "                            sum (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum, " +
            "                            sum (p.temps_arret) as temps_arret_sum, " +
            "                            sum (distinct t.quantite) as theoretical_quantity, " +
            "                            p.equipment " +
            "                            from planning p " +
            "                            join tache t on t.id = p.tache_id " +
            "                            where p.projects_id = :id " +
            "                            and t.company_id = :company_id " +
            "                            GROUP BY p.equipment " +
            "                    ), " +
            "                    taux_qualite as (" +
            "                    select " +
            "                    ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite, " +
            "                    equipment " +
            "                    from TRSByProject t " +
            "                    ), " +
            "                    taux_performance as (" +
            "                    select " +
            "                    ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance, " +
            "                    equipment " +
            "                    from TRSByProject t " +
            "                    ), " +
            "                    taux_disponibilite as (" +
            "                    select " +
            "                    ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite, " +
            "                    equipment " +
            "                    from TRSByProject t " +
            "                    ) " +
            "                    select " +
            "                    t.equipment, tq.taux_qualite, tp.taux_performance, td.taux_disponibilite, " +
            "                    ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) *(CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as TRS " +
            "                    from TRSByProject t " +
            "                    JOIN taux_qualite tq ON t.equipment = tq.equipment " +
            "                    JOIN taux_performance tp ON t.equipment = tp.equipment " +
            "                    JOIN taux_disponibilite td ON t.equipment = td.equipment",nativeQuery = true)
    List<Object[]> findTRSByEquipProject(@Param("id") Integer id, @Param("company_id") Integer companyId);
    @Query(value = "WITH TRSByProject AS (" +
            "                    select  sum (p.qte_totale) as qte_totale_sum, " +
            "                            sum (p.qtenc) as qtenc_sum, " +
            "                            sum (p.qte_rebut) as qte_rebut_sum, " +
            "                            sum (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum, " +
            "                            sum (p.temps_arret) as temps_arret_sum, " +
            "                            sum (DISTINCT t.quantite) as theoretical_quantity, " +
            "                            p.equipment " +
            "                            from planning p " +
            "                            join tache t on t.id = p.tache_id " +
            "                            where t.company_id = :company_id " +
            "                            GROUP BY p.equipment " +
            "                    ), " +
            "                    taux_qualite as (" +
            "                    select " +
            "                    ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite, " +
            "                    equipment " +
            "                    from TRSByProject t " +
            "                    ), " +
            "                    taux_performance as (" +
            "                    select " +
            "                    ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance, " +
            "                    equipment " +
            "                    from TRSByProject t " +
            "                    ), " +
            "                    taux_disponibilite as (" +
            "                    select " +
            "                    ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite, " +
            "                    equipment " +
            "                    from TRSByProject t " +
            "                    ) " +
            "                    select " +
            "                    t.equipment, tq.taux_qualite, tp.taux_performance, td.taux_disponibilite, " +
            "                    ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) *(CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as TRS " +
            "                    from TRSByProject t " +
            "                    JOIN taux_qualite tq ON t.equipment = tq.equipment " +
            "                    JOIN taux_performance tp ON t.equipment = tp.equipment " +
            "                    JOIN taux_disponibilite td ON t.equipment = td.equipment",nativeQuery = true)
    List<Object[]> findTRSByEquip( @Param("company_id") Integer companyId);

    //Ligne de production
    @Query(value = "WITH TRSByProject AS ( " +
            "        SELECT  " +
            "            sum (p.qte_totale) as qte_totale_sum, " +
            "            sum (p.qtenc) as qtenc_sum, " +
            "            sum (p.qte_rebut) as qte_rebut_sum, " +
            "            sum (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum, " +
            "            sum (p.temps_arret) as temps_arret_sum, " +
            "            sum (DISTINCT t.quantite) as theoretical_quantity, " +
            "            p.production_ligne_id, l.name, l.reference " +
            "        FROM planning p " +
            "        JOIN tache t on t.id = p.tache_id " +
            "        JOIN production_ligne l on l.id = p.production_ligne_id " +
            "        WHERE p.projects_id = :id " +
            "        AND t.company_id = :company_id " +
            "        GROUP BY p.production_ligne_id, l.name, l.reference " +
            "), " +
            "Taux_qualite as ( " +
            "        SELECT " +
            "            ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite, " +
            "            production_ligne_id, name, reference  " +
            "        FROM TRSByProject t " +
            "), " +
            "Taux_performance as ( " +
            "        SELECT " +
            "           ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance, " +
            "           production_ligne_id, name, reference " +
            "        FROM TRSByProject t " +
            "), " +
            "Taux_disponibilite as ( " +
            "         SELECT " +
            "             ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite, " +
            "             production_ligne_id, name, reference " +
            "         FROM TRSByProject t " +
            ") " +
            "SELECT " +
            "   t.production_ligne_id, " +
            "   t.name, " +
            "   t.reference, " +
            "   tq.taux_qualite, " +
            "   tp.taux_performance, td.taux_disponibilite, " +
            "   ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) *(CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as TRS " +
            "FROM TRSByProject t " +
            "JOIN Taux_qualite tq ON t.production_ligne_id = tq.production_ligne_id " +
            "                     and t.name = tq.name and t.reference = tq.reference " +
            "JOIN Taux_performance tp ON t.production_ligne_id = tp.production_ligne_id " +
            "                     and t.name = tp.name and t.reference = tp.reference " +
            "JOIN Taux_disponibilite td ON t.production_ligne_id = td.production_ligne_id " +
            "                           and t.name = td.name and t.reference = td.reference",nativeQuery = true)
    List<Object[]> findTRSByLigneProject(@Param("id") Integer id, @Param("company_id") Integer companyId);
    @Query(value = "   WITH TRSByProject AS ( " +
            "             select  sum (p.qte_totale) as qte_totale_sum, " +
            "                sum (p.qtenc) as qtenc_sum, " +
            "                sum (p.qte_rebut) as qte_rebut_sum, " +
            "                sum (EXTRACT(EPOCH FROM p.date_fin - p.date_debut) / 60) as temps_reel_sum, " +
            "                sum (p.temps_arret) as temps_arret_sum, " +
            "                sum (DISTINCT t.quantite) as theoretical_quantity, " +
            "                p.production_ligne_id, l.name, l.reference " +
            "             from planning p " +
            "             join tache t on t.id = p.tache_id " +
            "             join production_ligne l on l.id = p.production_ligne_id " +
            "             where t.company_id = :company_id " +
            "             GROUP BY p.production_ligne_id, l.name, l.reference " +
            "), " +
            "taux_qualite as ( " +
            "             select " +
            "                ROUND(CAST((t.qte_totale_sum - t.qtenc_sum - t.qte_rebut_sum)AS numeric) / CAST(t.qte_totale_sum AS numeric) * 100, 2) AS taux_qualite, " +
            "                production_ligne_id, name, reference  " +
            "             from TRSByProject t " +
            "), " +
            "taux_performance as ( " +
            "             select " +
            "                ROUND(CAST(t.theoretical_quantity AS numeric) / cast(t.qte_totale_sum AS numeric)* 100, 2) AS taux_performance, " +
            "                production_ligne_id, name, reference " +
            "             from TRSByProject t " +
            "), " +
            "taux_disponibilite as ( " +
            "              select " +
            "                 ROUND((CAST(t.temps_reel_sum AS numeric) - CAST(t.temps_arret_sum AS numeric)) / cast(t.temps_reel_sum AS numeric)* 100, 2) AS taux_disponibilite, " +
            "                 production_ligne_id, name, reference " +
            "              from TRSByProject t " +
            ") " +
            "select " +
            "    t.production_ligne_id, " +
            "    t.name, " +
            "    t.reference, " +
            "    tq.taux_qualite, " +
            "    tp.taux_performance, " +
            "    td.taux_disponibilite, " +
            "    ROUND(((CAST(tq.taux_qualite AS numeric) / 100) * (CAST(tp.taux_performance AS numeric) / 100) *(CAST(td.taux_disponibilite AS numeric) / 100))*100,2) as TRS " +
            "from TRSByProject t " +
            "JOIN taux_qualite tq ON t.production_ligne_id = tq.production_ligne_id and t.name = tq.name " +
            "                     and t.reference = tq.reference " +
            "JOIN taux_performance tp ON t.production_ligne_id = tp.production_ligne_id " +
            "                     and t.name = tp.name and t.reference = tp.reference " +
            "JOIN taux_disponibilite td ON t.production_ligne_id = td.production_ligne_id " +
            "                     and t.name = td.name and t.reference = td.reference",nativeQuery = true)
    List<Object[]> findTRSByLigne(@Param("company_id") Integer companyId);


























}
