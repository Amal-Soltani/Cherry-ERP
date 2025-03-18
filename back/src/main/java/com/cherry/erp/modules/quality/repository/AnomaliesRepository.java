package com.cherry.erp.modules.quality.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface AnomaliesRepository extends GenericRepository<Anomalies> {
    public Anomalies findByType(String type);

    @Query("select a from Anomalies as a where a.company.id = :companyId order by a.creationDate desc")
    Page<Anomalies> findByCompany(Integer companyId, Pageable pageable);
}
