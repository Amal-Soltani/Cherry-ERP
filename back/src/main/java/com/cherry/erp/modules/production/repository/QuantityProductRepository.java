package com.cherry.erp.modules.production.repository;

import com.cherry.erp.modules.production.model.persistent.QuantityProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface QuantityProductRepository extends JpaRepository<QuantityProduct, Integer> {
    @Query(value = "select * from quantity_product qp where qp.parent_id = :parent ",nativeQuery = true)
    public List<QuantityProduct> findByParent(@Param("parent") Integer parent);


    @Modifying
    @Query("delete from QuantityProduct qp " +
            "where qp.parent.id = :parent " +
            "and qp.child.id  = :child")
    public void deleteByParentAndChild(@Param("parent") Integer parent, @Param("child") Integer child);

    @Modifying
    @Query("delete from QuantityProduct qp " +
            "where qp.parent.id = :parent ")
    public void deleteByParent(@Param("parent") Integer parent);


    @Modifying
    @Query("delete from QuantityProduct qp " +
            "where qp.child.id  = :child ")
    public void deleteByChild(@Param("child") Integer child);
}
