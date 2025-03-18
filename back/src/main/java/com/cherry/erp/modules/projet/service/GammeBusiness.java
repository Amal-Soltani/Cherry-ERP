package com.cherry.erp.modules.projet.service;

import com.cherry.erp.modules.projet.model.persistent.Gamme;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface GammeBusiness {
    @Transactional(propagation = REQUIRED)
    public Gamme Add (Integer productId);
    public Gamme FindByProduct(Integer id);
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);
}
