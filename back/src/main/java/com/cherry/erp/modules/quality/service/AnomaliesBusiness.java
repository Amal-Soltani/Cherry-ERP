package com.cherry.erp.modules.quality.service;

import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface AnomaliesBusiness {
    @Transactional(propagation = REQUIRED)
    public Anomalies Add(Anomalies anomalie);
    public Page<Anomalies> findByPage(Integer page, Integer pageSize);
    public List<Anomalies> FindAll();
    public Anomalies FindById(Integer id);
    Anomalies FindByType(String type) ;
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);
}
