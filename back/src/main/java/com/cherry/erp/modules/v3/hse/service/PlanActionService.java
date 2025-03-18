package com.cherry.erp.modules.v3.hse.service;

import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import com.cherry.erp.modules.v3.hse.model.persistent.PlanAction;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

public interface PlanActionService {
    @Transactional(propagation = REQUIRED)
    PlanAction save(PlanAction p);

    @Transactional(propagation = REQUIRED)
    void delete(Integer planActionNumber);//??

    PlanAction findOne(Integer id);

    List<PlanAction> findAllByCompanyId();

    Page<PlanAction> findByPage(Integer page, Integer pageSize);
}
