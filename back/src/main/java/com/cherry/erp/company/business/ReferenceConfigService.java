package com.cherry.erp.company.business;

import com.cherry.erp.company.model.persistent.ReferenceConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface ReferenceConfigService {

    List<ReferenceConfig> getByYear(Integer year);

    List<ReferenceConfig> getWithoutYear();

    List<ReferenceConfig> findAll();

    ReferenceConfig findOne(Integer id);

    @Transactional(propagation = REQUIRED)
    ReferenceConfig save(ReferenceConfig project);

    @Transactional(propagation = REQUIRED)
    void delete(Integer id);

}
