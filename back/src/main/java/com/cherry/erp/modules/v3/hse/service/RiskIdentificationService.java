package com.cherry.erp.modules.v3.hse.service;


import com.cherry.erp.modules.v3.hse.model.persistent.RiskIdentification;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface RiskIdentificationService {

    @Transactional(propagation = REQUIRED)
    RiskIdentification save(RiskIdentification riskIdentification);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    RiskIdentification findOne(Integer id);

    List<RiskIdentification> findAllByCompanyId();

    Page<RiskIdentification> findByPage(Integer page, Integer pageSize);

}

