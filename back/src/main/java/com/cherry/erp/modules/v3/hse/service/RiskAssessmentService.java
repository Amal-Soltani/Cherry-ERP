package com.cherry.erp.modules.v3.hse.service;

import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface RiskAssessmentService {

    @Transactional(propagation = REQUIRED)
    RiskAssessment save(RiskAssessment employee);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    RiskAssessment findOne(Integer id);

    List<RiskAssessment> findAllByCompanyId();

    Page<RiskAssessment> findByPage(Integer page, Integer pageSize);

}

