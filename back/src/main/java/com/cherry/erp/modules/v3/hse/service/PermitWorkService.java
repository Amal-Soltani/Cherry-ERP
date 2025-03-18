package com.cherry.erp.modules.v3.hse.service;

import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import com.cherry.erp.modules.v3.hse.model.persistent.PermitWork;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

public interface PermitWorkService {
    @Transactional(propagation = REQUIRED)
    PermitWork save(PermitWork pw);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);//??

    PermitWork findOne(Integer id);

    List<PermitWork> findAllByCompanyId();

    Page<PermitWork> findByPage(Integer page, Integer pageSize);
}
