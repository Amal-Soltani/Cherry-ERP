package com.cherry.erp.modules.documentaire.repository;

import com.cherry.erp.modules.documentaire.model.persistent.Document;
import com.cherry.erp.modules.production.model.persistent.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.cherry.erp.common.utils.ReferenceConstant.DOCUMENT_REFERENCE_QUERY;

public interface DocumentRepository extends JpaRepository<Document,Integer> {

    @Query(value = DOCUMENT_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query(value = "Select * from document d " +
            "join product p on d.product_id = p.id " +
            "where p.id = :idProduct " +
            "and p.company_id  = :companyId " +
            "order By d.creation_date DESC", nativeQuery = true)
    Page<Document> findByPageAndProduct(Integer idProduct, Integer companyId, Pageable pageable);
    @Query(value = "Select * from document d " +
            "join product p on d.product_id = p.id " +
            "where d.id = :id " +
            "and p.company_id  = :companyId ", nativeQuery = true)
    Document getDocumentByCompany(@Param("id") Integer id,
                                  @Param("companyId") Integer companyId);
    @Query(value = "Select * from document d " +
            "join product p on d.product_id = p.id " +
            "where p.id = :idProduct " +
            "and p.company_id  = :companyId " +
            "order by d.id desc ", nativeQuery = true)
    List<Document> findByProduct (@Param("idProduct") Integer idProduct,
                                  @Param("companyId") Integer companyId);
    @Query(value = "Select * from document d " +
            "join product p on d.product_id = p.id " +
            "where d.reference = :ref " +
            "and p.company_id  = :companyId ", nativeQuery = true)
    Document findOne(@Param("ref") String ref,
                                  @Param("companyId") Integer companyId);
    @Query(value = "Select * from document d " +
            "join product p on d.product_id = p.id " +
            "where d.id = :id " +
            "and p.company_id  = :companyId ", nativeQuery = true)
    Document getDocument(@Param("id") Integer id,@Param("companyId") Integer companyId);



}
