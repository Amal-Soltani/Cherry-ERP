package com.cherry.erp.modules.production.repository;

import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial,Integer> {

    @Query(value = "select * from raw_material m " +
            "where m.product_id = :idProduct ", nativeQuery = true)
    public List<RawMaterial> findByProduct(@Param("idProduct") Integer idProduct);

    @Query(value ="select * from raw_material m " +
            "Where m.id = :id " +
            "and m.product_id = :idProduct" , nativeQuery = true)
    public RawMaterial FindById(@Param("id") Integer id,@Param("idProduct") Integer idProduct);


    @Modifying
    @Query("Delete from RawMaterial m " +
            "Where m.product.id = :idProduct " +
            "and m.article.id = :idArticle " )
    void delete(@Param("idProduct") Integer idProduct,@Param("idArticle") Integer idArticle);


    @Transactional
    @Modifying
    @Query("Delete from RawMaterial m " +
            "Where m.product.id = :idProduct ")
    void deleteByProduct(@Param("idProduct") Integer idProduct);


    @Transactional
    @Modifying
    @Query("Delete from RawMaterial m " +
            "Where m.article.id = :idArticle ")
    void deleteByArticle(@Param("idArticle") Integer idArticle);




}
