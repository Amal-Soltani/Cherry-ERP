package com.cherry.erp.modules.production.service;

import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;


import java.util.List;
@Transactional
public interface RawMaterialBusiness {
    @Transactional(propagation = REQUIRED)
    public RawMaterial Add (Integer idProduct, Integer idArticle, String dimensionBrut, double qteBrut);
    public List <RawMaterial> FindByProduct (Integer idProduct);
    @Transactional(propagation = REQUIRED)
    public RawMaterial Update(Integer id,Integer idProduct,String dimensionBrut,double qteBrut);
    @Transactional(propagation = REQUIRED)
    public  void Delete(Integer idProduct,Integer idArticle);
    @Transactional(propagation = REQUIRED)
    public void DeleteByProduct( Integer idProduct);

}
