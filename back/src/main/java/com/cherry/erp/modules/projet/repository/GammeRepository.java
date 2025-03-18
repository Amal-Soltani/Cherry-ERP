package com.cherry.erp.modules.projet.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GammeRepository extends GenericRepository<Gamme> {

    @Query("select g from Gamme g inner join Product p on g.id = p.gamme.id " +
            "where p.id = :id")
    Gamme findByProductId(@Param("id") Integer id);

}
