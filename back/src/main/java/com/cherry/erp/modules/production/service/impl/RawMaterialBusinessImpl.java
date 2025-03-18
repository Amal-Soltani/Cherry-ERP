package com.cherry.erp.modules.production.service.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import com.cherry.erp.modules.production.service.RawMaterialBusiness;
import com.cherry.erp.modules.production.repository.RawMaterialRepository;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.stock.model.persistent.Article;
import com.cherry.erp.modules.stock.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RawMaterialBusinessImpl implements RawMaterialBusiness {

    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRepository productRepository;
    private final SpiUtils spiUtils;
    private final ArticleRepository articleRepository;


    @Override
    public RawMaterial Add(Integer idProduct, Integer idArticle, String dimensionBrut, double qteBrut) {
        RawMaterial rawMaterial = new RawMaterial();

        rawMaterial.setProduct(productRepository.findByIdAndCompanyId(idProduct,
                spiUtils.findCurrentCompany().getId()).orElse(null));
        rawMaterial.setArticle(articleRepository.findByIdAndCompanyId(idArticle,
                spiUtils.findCurrentCompany().getId()).orElse(null));

        rawMaterial.setRawDimension(dimensionBrut);
        rawMaterial.setGrossQuantity(qteBrut);

        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public List<RawMaterial> FindByProduct(Integer idProduct) {
        Product p = productRepository.findByIdAndCompanyId(idProduct,spiUtils.findCurrentCompany().getId()).orElse(null);
        return rawMaterialRepository.findByProduct(p.getId());
    }

    @Override
    public RawMaterial Update(Integer id,Integer idProduct,String dimensionBrut,double qteBrut) {
        Product p = productRepository.findByIdAndCompanyId(idProduct,spiUtils.findCurrentCompany().getId()).orElse(null);
        RawMaterial m = rawMaterialRepository.FindById(id,p.getId());
        m.setRawDimension(dimensionBrut);
        m.setGrossQuantity(qteBrut);
        return rawMaterialRepository.save(m);
    }

    @Override
    public void Delete(Integer idProduct,Integer idArticle) {
        Product p = productRepository.findByIdAndCompanyId(idProduct,spiUtils.findCurrentCompany().getId()).orElse(null);
        Article a = articleRepository.findByIdAndCompanyId(idArticle,spiUtils.findCurrentCompany().getId()).orElse(null);
        rawMaterialRepository.delete(p.getId(),a.getId());
    }

    @Override
    public void DeleteByProduct(Integer idProduct) {
        rawMaterialRepository.deleteByProduct(
                productRepository.findByIdAndCompanyId(idProduct,spiUtils.findCurrentCompany().getId())
                .orElse(null)
                .getId());
    }

}
