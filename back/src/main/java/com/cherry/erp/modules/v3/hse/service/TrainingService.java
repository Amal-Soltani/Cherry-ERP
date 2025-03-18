package com.cherry.erp.modules.v3.hse.service;


import com.cherry.erp.modules.v3.hse.model.persistent.Training;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface TrainingService {

    @Transactional(propagation = REQUIRED)
    Training save(Training training);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    Training findOne(Integer id);

    List<Training> findAllByCompanyId();

    Page<Training> findByPage(Integer page, Integer pageSize);

}

