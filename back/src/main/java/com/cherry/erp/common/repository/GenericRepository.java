package com.cherry.erp.common.repository;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.stock.model.persistent.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Integer> {

    List<T> findByCompanyId(Integer companyId);

    @Query("select u from #{#entityName} as u where u.company.id = :companyId order by u.modificationDate desc")
    List<T> findByCompanyIdByOrderByModificationDateDesc(Integer companyId);

    @Query("select u from #{#entityName} as u where u.company.id = :companyId order by u.creationDate desc")
    Page<T> findByCompanyId(Integer companyId, Pageable pageable);

    @Query("select u from #{#entityName} as u where u.company.id = :companyId order by u.modificationDate desc")
    Page<T> findByCompanyIdByOrderByModificationDateDesc(Integer companyId, Pageable pageable);

    Optional<T> findByIdAndCompanyId(Integer id, Integer companyId);



}
