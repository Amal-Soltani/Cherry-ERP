package com.cherry.erp.modules.production.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.production.model.enums.TypeProductEnum;
import com.cherry.erp.modules.production.model.persistent.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.cherry.erp.common.utils.ReferenceConstant.PRODUCT_REFERENCE_QUERY;


public interface ProductRepository extends GenericRepository<Product> {

    @Query(value = PRODUCT_REFERENCE_QUERY, nativeQuery = true)
    Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

    @Query(value = "Select p from product p " +
            "inner join tache t on p.id = t.id " +
            "Where t.id = :idTache " +
            "and p.company_id = :companyId ",  nativeQuery = true)
    public Product FindProductByTache(@Param("idTache") Integer idTache,
                                      @Param("companyId") Integer companyId);

    @Query("select p from Product as p where p.company.id = :companyId order by p.creationDate desc")
    Page<Product> findByCompany(Integer companyId, Pageable pageable);

    @Query("select p from Product as p where p.company.id = :companyId and p.typeProduct = :type order by p.creationDate desc")
    Page<Product> findByPageAndType(TypeProductEnum type, Integer companyId, Pageable pageable);

    @Query(value = "select * from product p " +
            "inner join tache t on p.id = t.product_id " +
            "inner join projects pt on t.projet_id = pt.id " +
            "where pt.id = :projectId " +
            "and p.bom_id IS NOT NULL " +
            "order By p.creation_date DESC",  nativeQuery = true)
    Page<Product> findByPageAndProject(Integer projectId, Pageable pageable);


    @Query(value ="select * from product p " +
            "inner join bom n " +
            "on p.bom_id = n.id " +
            "where p.id = :id " +
            "and p.company_id = :companyId " , nativeQuery = true)
    public Product FindNomenclature(@Param("id") Integer id,
                                    @Param("companyId") Integer companyId);

    @Query(value = "Select * from Product p " +
            "Where p.bom_id is null " +
            "and p.company_id = :companyId ",  nativeQuery = true)
    public List<Product> FindAvailableProducts(@Param("companyId") Integer companyId);

    @Query(value = "select * from product p " +
            "inner join tache t on p.id = t.product_id " +
            "inner join projects pt on t.projet_id = pt.id " +
            "where pt.id = :projectId " +
            "and p.company_id = :companyId " +
            "and p.bom_id is null " +
            "order By p.id DESC",nativeQuery = true)
    public List<Product> FindAvailableProductsByProject(@Param("projectId")Integer projectId,
                                                        @Param("companyId") Integer companyId);

    public Product findByReference(String refrence);
    public Product findByLibelle(String libelle);

    @Query(value = "select * " +
            "from  product p inner join bom n " +
            "on n.id = p.bom_id " +
            "where p.company_id = :companyId " +
            "order By n.id DESC",nativeQuery = true)
    public List<Product> FindAllDesc(@Param("companyId") Integer companyId);

    @Query(value = "select * from product p " +
            "inner join tache t on p.id = t.product_id " +
            "inner join projects pt on t.projet_id = pt.id " +
            "where pt.id = :projectId " +
            "and p.company_id = :companyId " +
            "and p.bom_id IS NOT NULL " +
            "order By p.bom_id DESC",nativeQuery = true)
    public List<Product> FindByProject(@Param("projectId") Integer projectId,
                                       @Param("companyId") Integer companyId);

    @Query(value = "select * from product p " +
            "where p.bom_id = :idNomenclature " +
            "and p.company_id = :companyId ", nativeQuery = true)
    public Product FindByNomenclature(@Param("idNomenclature") Integer idNomenclature,
                                      @Param("companyId") Integer companyId);


    @Query(value ="SELECT * FROM product p " +
            "WHERE p.id <> :id " +
            "and p.company_id = :companyId " +
            "and p.id not in " +
            "(SELECT child_id FROM quantity_product qp " +
            "where qp.child_id IS NOT null " +
            "and qp.parent_id = :id ) ", nativeQuery = true)
    public List<Product> FindAllExceptParent(@Param("id")Integer id, @Param("companyId") Integer companyId);


    @Query(value = "select * from product p, quantity_product qp, product p2 " +
            "where p.id = qp.parent_id " +
            "and p2.id = qp.child_id " +
            "and p.id = :idProduct ", nativeQuery = true)
    public List<Product> findByParent(@Param("idProduct")Integer idProduct);


}
