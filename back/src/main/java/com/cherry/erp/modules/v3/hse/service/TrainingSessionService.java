package com.cherry.erp.modules.v3.hse.service;



import com.cherry.erp.modules.v3.hse.model.persistent.TrainingSession;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface TrainingSessionService {

    @Transactional(propagation = REQUIRED)
    TrainingSession save(TrainingSession trainingSession);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    TrainingSession findOne(Integer id);

    List<TrainingSession> findAllByCompanyId();

    Page<TrainingSession> findByPage(Integer page, Integer pageSize);

}

