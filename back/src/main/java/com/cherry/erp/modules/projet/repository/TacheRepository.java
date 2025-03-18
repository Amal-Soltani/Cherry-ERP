package com.cherry.erp.modules.projet.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.projet.model.enums.StatutTacheEnum;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.cherry.erp.common.utils.ReferenceConstant.PRODUCTION_ORDER_REFERENCE_QUERY;


public interface TacheRepository extends GenericRepository<Tache> {

    @Query(value = PRODUCTION_ORDER_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query("select t from Tache as t where t.company.id = :companyId order by t.creationDate desc")
    Page<Tache> findByCompany(Integer companyId, Pageable pageable);

    @Query("select t from Tache as t where t.projet.id = :projectId order by t.creationDate desc")
    Page<Tache> findByPageAndProject(Integer projectId, Pageable pageable);

    @Query("select t from Tache as t where t.company.id = :companyId and t.status = :status order by t.creationDate desc")
    Page<Tache> findByPageAndStatus(StatutTacheEnum status, Integer companyId, Pageable pageable);

    @Query("select t from Tache as t where t.projet.id = :projectId and t.status = :status order by t.creationDate desc")
    Page<Tache> findByPageAndProjectAndStatus(Integer projectId, StatutTacheEnum status, Pageable pageable);
    @Query(value = "SELECT * FROM tache t " +
            "INNER JOIN employees e ON t.employee_id = e.id " +
            "WHERE t.projet_id = :idProjet ", nativeQuery = true)
    List<Tache> findAllTacheByProjet(@Param("idProjet") Integer idProjet);

    @Query(value="Select * from tache t " +
            "inner join projects p on t.projet_id = p.id " +
            "where t.id = :id " +
            "and p.id = :idProjet", nativeQuery = true)
    Tache findTacheByProjet(@Param("id") Integer id,@Param("idProjet") Integer idProjet);

    List<Tache> findByProjetId( Integer idProjet);

    @Query(value="select * from tache t " +
            "inner join projects p on t.projet_id = p.id  " +
            "inner join employees c on p.employee_id = c.id  " +
            "where c.id = :idClient", nativeQuery = true)
    List<Tache> findByClientId(@Param("idClient") Integer idClient);

    Tache findByReference(String reference);

    @Query("Select t from Tache t " +
            "where t.product.id = :idProduct")
    List<Tache> findAllTacheByProduct(@Param("idProduct") Integer idProduct);

    @Query(value = "select * from tache t where t.parent = :tacheId " +
            "and t.projet_id = :projetId " +
            "and t.company_id = :companyId", nativeQuery = true)
    List<Tache> findSousTache (@Param("tacheId") Integer tacheId,
                               @Param("projetId") Integer projetId,
                               @Param("companyId") Integer companyId );

    @Modifying
    void deleteByIdAndProjetId(Integer id, Integer projectId);

    @Query(value = "select sum (t.quantite) from tache t where t.projet_id = :id",nativeQuery = true)
    Integer SumQtTotalProjet(@Param("id") Integer id);
    @Query(value = "select sum (t.quantite) from tache t where t.company_id = :id",nativeQuery = true)
    Integer SumQtTotal(@Param("id") Integer id);


}
