package com.cherry.erp.modules.production.service.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.model.persistent.QuantityProduct;
import com.cherry.erp.modules.production.repository.ProductRepository;
import com.cherry.erp.modules.production.repository.QuantityProductRepository;
import com.cherry.erp.modules.production.service.QuantityProductBusniss;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class QuantityProductBusinessImpl implements QuantityProductBusniss {

    private final QuantityProductRepository quantityProductRepository;
    private final ProductRepository productRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public QuantityProductBusinessImpl(QuantityProductRepository quantityProductRepository,
                                       ProductRepository productRepository,
                                       SpiUtils spiUtils) {
        this.quantityProductRepository = quantityProductRepository;
        this.productRepository = productRepository;
        this.spiUtils = spiUtils ;
    }

    @Override
    public QuantityProduct Add(Integer idParent, Integer idChild, int qte) {
        QuantityProduct quantityProduct = new QuantityProduct();
        spiUtils.setModifiedByAndCreatedByFields(quantityProduct);
        quantityProduct.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        quantityProduct.setParent(productRepository.findByIdAndCompanyId(idParent,
                spiUtils.findCurrentCompany().getId()).orElse(null));
        quantityProduct.setChild(productRepository.findByIdAndCompanyId(idChild,
                spiUtils.findCurrentCompany().getId()).orElse(null));

        quantityProduct.setQteParAssemblage(qte);

        return quantityProductRepository.save(quantityProduct);

    }

    @Override
    public List<QuantityProduct> FindByParent(Integer idParent) {
        Product p = productRepository.findByIdAndCompanyId(idParent,spiUtils.findCurrentCompany().getId()).orElse(null);
        return quantityProductRepository.findByParent(p.getId());
    }

    @Override
    public QuantityProduct Update(Integer id,Integer child, int qte) {
        QuantityProduct q = quantityProductRepository.findById(id).orElse(null);
        Product product = productRepository.findByIdAndCompanyId(child, spiUtils.findCurrentCompany().getId()).orElse(null);
        q.setQteParAssemblage(qte);
        q.setChild(product);
        return quantityProductRepository.save(q);
    }


    @Override
    public void Delete(Integer idParent, Integer idChild) {
        Product parent = productRepository.findByIdAndCompanyId(idParent,spiUtils.findCurrentCompany().getId()).orElse(null);
        Product child = productRepository.findByIdAndCompanyId(idChild,spiUtils.findCurrentCompany().getId()).orElse(null);

        quantityProductRepository.deleteByParentAndChild(parent.getId(),child.getId());
    }

    @Override
    public void DeleteByParent(Integer idParent) {
        Product parent = productRepository.findByIdAndCompanyId(idParent,spiUtils.findCurrentCompany().getId()).orElse(null);
        quantityProductRepository.deleteByParent(parent.getId());
    }
}
