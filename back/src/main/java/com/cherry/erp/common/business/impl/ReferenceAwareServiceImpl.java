package com.cherry.erp.common.business.impl;

import com.cherry.erp.common.business.ReferenceAwareService;
import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.business.ReferenceGenerationService;
import com.cherry.erp.company.model.persistent.ReferenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferenceAwareServiceImpl<T extends GenericEntity> implements ReferenceAwareService<T> {

    @Autowired
    ReferenceGenerationService referenceGenerationService;

    @Override
    public String generateReference(Class<T> clazz, Integer year) {
        return referenceGenerationService.generateReference(clazz.getSimpleName(), year);
    }

    @Override
    public String generateReference(String clazz, Integer year) {
        return referenceGenerationService.generateReference(clazz, year);
    }

    @Override
    public ReferenceConfig incrementReferenceConfigNumber(Class<T> clazz, Integer year) {
        return referenceGenerationService.incrementReferenceConfigNumber(clazz.getSimpleName(), year);
    }

    @Override
    public ReferenceConfig incrementReferenceConfigNumber(String clazz, Integer year) {
        return referenceGenerationService.incrementReferenceConfigNumber(clazz, year);
    }

}
