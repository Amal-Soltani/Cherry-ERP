package com.cherry.erp.common.business;

import com.cherry.erp.common.model.persistent.GenericParameter;

import java.util.List;

public interface GenericParameterService {

    GenericParameter save(GenericParameter genericParameter);

    GenericParameter update(Integer id, GenericParameter genericParameter);

    GenericParameter findByCompany();

}
