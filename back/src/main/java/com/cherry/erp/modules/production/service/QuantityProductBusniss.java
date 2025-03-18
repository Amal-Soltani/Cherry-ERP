package com.cherry.erp.modules.production.service;
import com.cherry.erp.modules.production.model.persistent.QuantityProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface QuantityProductBusniss {
    @Transactional(propagation = REQUIRED)
    public QuantityProduct Add (Integer parent, Integer child, int qte);
    public List<QuantityProduct> FindByParent(Integer parent);
    @Transactional(propagation = REQUIRED)

    public QuantityProduct Update(Integer id,Integer child, int qte);
    @Transactional(propagation = REQUIRED)
    public void Delete(Integer parent,Integer child);
    @Transactional(propagation = REQUIRED)
    public void DeleteByParent(Integer parent);
}
