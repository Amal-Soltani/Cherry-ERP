package com.cherry.erp.modules.stock.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.stock.model.persistent.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import static com.cherry.erp.common.utils.ReferenceConstant.ARTICLE_REFERENCE_QUERY;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Article> {

     @Query(value = ARTICLE_REFERENCE_QUERY, nativeQuery = true)
     Integer getNextReferenceNumber(@Param("companyId") Integer companyId);

     @Query("select a from Article as a where a.company.id = :companyId order by a.creationDate desc")
     Page<Article> findByCompany(Integer companyId, Pageable pageable);

     @Query(value = "SELECT * FROM article a " +
             "WHERE a.company_id   = :companyId " +
             "and a.libelle = :libelle ", nativeQuery = true)
     public Article findByLibelle(@Param("libelle")String libelle,
                                  @Param("companyId")Integer companyId);

     @Query(value = "SELECT * FROM article a " +
             "WHERE a.company_id  = :companyId " +
             "and a.id not in " +
             "(SELECT id FROM raw_material m " +
             "where m.article_id IS NOT null " +
             "and m.article_id = a.id " +
             "and m.product_id = :idProduct) ", nativeQuery = true)
     public List<Article> FindAllExecptUsed(@Param("idProduct")Integer idProduct,
                                            @Param("companyId")Integer companyId);

     @Query(value = "select * from article a , raw_material mp, product p " +
             "where a.id = mp.article_id " +
             "and p.id = mp.product_id " +
             "and p.id = :idProduct ", nativeQuery = true)
     public List<Article> findByProduct(@Param("idProduct")Integer idProduct);
}
