package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;

import com.cherry.erp.modules.v3.hse.model.persistent.Training;

import com.cherry.erp.modules.v3.hse.repository.TrainingRepository;

import com.cherry.erp.modules.v3.hse.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.DELETE_KO_CODE;
import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.RESOURCE_NOT_FOUND;


@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository,
                               SpiUtils spiUtils) {
        this.trainingRepository = trainingRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public Training save(Training training) {
        if (training.getId() != null && training.getId() != 0) {
            Training oldTraning = findOne(training.getId());
            spiUtils.setOldValues(training, oldTraning);
           /* if (training.getImage() == null) {
                training.setImage(oldTraning.getImage());
            }*/
        }
        if (training.getId() == null || training.getId() == 0) {
            training.setCompany(spiUtils.findCurrentCompany());
        }
        if (training.getCompany() == null) {
            training.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(training);
        training.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return trainingRepository.save(training);
    }

    @Override
    public Training findOne(Integer id) {
        return trainingRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<Training> findAllByCompanyId() {
        return trainingRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void delete(Integer id) {
        try {
            trainingRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting training module : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public Page<Training> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return trainingRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
