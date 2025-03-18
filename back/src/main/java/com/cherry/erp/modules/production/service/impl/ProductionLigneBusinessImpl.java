package com.cherry.erp.modules.production.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import com.cherry.erp.modules.production.repository.ProductionLigneRepository;
import com.cherry.erp.modules.production.service.ProductionLigneBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductionLigneBusinessImpl implements ProductionLigneBusiness {
    private final ProductionLigneRepository productionLigneRepository;
    private final SpiUtils spiUtils;

    @Override
    public String generateReference() {
        Integer nextRefNumber = productionLigneRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.PRODUCTION_LINE_REFERENCE_PREFIX
                + String.format(ReferenceConstant.PRODUCTION_LINE_REFRENCE_FORMAT,nextRefNumber );

        return reference;
    }
    @Override
    public ProductionLigne Add(ProductionLigne productionLigne) {
        if (productionLigne.getId() != null && productionLigne.getId() != 0) {
            ProductionLigne oldLigneProduction = FindById(productionLigne.getId());
            spiUtils.setOldValues(productionLigne, oldLigneProduction);
        }
        if (productionLigne.getId() == null || productionLigne.getId() == 0) {
            ProductionLigne oldLigneProduction = productionLigneRepository.getByReference(productionLigne.getReference(),spiUtils.findCurrentCompany().getId());
            if(oldLigneProduction == null ){
                productionLigne.setCompany(spiUtils.findCurrentCompany());
                productionLigne.setReference(generateReference());
            }
        }
        if (productionLigne.getCompany() == null) {
            productionLigne.setCompany(spiUtils.findCurrentCompany());
        }
        spiUtils.setModifiedByAndCreatedByFields(productionLigne);
        productionLigne.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return productionLigneRepository.save(productionLigne);
    }

    @Override
    public Page<ProductionLigne> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return productionLigneRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public ProductionLigne FindById(Integer id) {
        return productionLigneRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public List<ProductionLigne> FindAll() {
        return productionLigneRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void DeleteBYId(Integer id) {
        productionLigneRepository.deleteById(FindById(id).getId());
    }
}
