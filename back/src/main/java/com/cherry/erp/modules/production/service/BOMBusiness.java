package com.cherry.erp.modules.production.service;

import com.cherry.erp.modules.production.model.persistent.BOM;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface BOMBusiness {
    @Transactional(propagation = REQUIRED)
    public BOM Add (BOM nomenclature, Integer idProduct);
    public String generateReference();
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);
}
