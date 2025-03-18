package com.cherry.erp.modules.production.service;

import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
@Transactional
public interface ProductionLigneBusiness {
    @Transactional(propagation = REQUIRED)
    public ProductionLigne Add (ProductionLigne productionLigne);
    public String generateReference();
    public Page<ProductionLigne> findByPage(Integer page, Integer pageSize);

    public ProductionLigne FindById(Integer id);
    public List<ProductionLigne> FindAll();
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);
}
