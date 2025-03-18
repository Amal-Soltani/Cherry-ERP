package com.cherry.erp.common.business;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.ReferenceConfig;

public interface ReferenceAwareService<T extends GenericEntity> {


    String generateReference(Class<T> clazz, Integer year);

    String generateReference(String clazz, Integer year);

    ReferenceConfig incrementReferenceConfigNumber(Class<T> clazz, Integer year);

    ReferenceConfig incrementReferenceConfigNumber(String clazz, Integer year);
}
