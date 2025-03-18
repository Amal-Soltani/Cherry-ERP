package com.cherry.erp.modules.v3.hse.service;

import com.cherry.erp.modules.v3.hse.model.persistent.Incident;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface IncidentService {

    @Transactional(propagation = REQUIRED)
    Incident save(Incident incident);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    Incident findOne(Integer id);

    List<Incident> findAllByCompanyId();

    Page<Incident> findByPage(Integer page, Integer pageSize);

}

