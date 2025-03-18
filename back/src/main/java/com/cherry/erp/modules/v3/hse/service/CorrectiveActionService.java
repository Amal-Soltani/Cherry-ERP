package com.cherry.erp.modules.v3.hse.service;

import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface CorrectiveActionService {

    @Transactional(propagation = REQUIRED)
    CorrectiveAction save(CorrectiveAction c);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);//??

    CorrectiveAction findOne(Integer id);

    List<CorrectiveAction> findAllByCompanyId();

    Page<CorrectiveAction> findByPage(Integer page, Integer pageSize);

}

