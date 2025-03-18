package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v3.hse.model.persistent.Training;
import com.cherry.erp.modules.v3.hse.model.persistent.TrainingSession;
import com.cherry.erp.modules.v3.hse.repository.TrainingRepository;
import com.cherry.erp.modules.v3.hse.repository.TrainingSessionRepository;
import com.cherry.erp.modules.v3.hse.service.TrainingService;
import com.cherry.erp.modules.v3.hse.service.TrainingSessionService;
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
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository,
                                      SpiUtils spiUtils) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public TrainingSession save(TrainingSession trainingSession) {
        if (trainingSession.getId() != null && trainingSession.getId() != 0) {
            TrainingSession oldTrainingSession = findOne(trainingSession.getId());
            spiUtils.setOldValues(trainingSession, oldTrainingSession);
           /* if (trainingSession.getImage() == null) {
                trainingSession.setImage(oldTrainingSession.getImage());
            }*/
        }
        if (trainingSession.getId() == null || trainingSession.getId() == 0) {
            trainingSession.setCompany(spiUtils.findCurrentCompany());
        }
        if (trainingSession.getCompany() == null) {
            trainingSession.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(trainingSession);
        trainingSession.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return trainingSessionRepository.save(trainingSession);
    }

    @Override
    public TrainingSession findOne(Integer id) {
        return trainingSessionRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<TrainingSession> findAllByCompanyId() {
        return trainingSessionRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void delete(Integer id) {
        try {
            trainingSessionRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting training session : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public Page<TrainingSession> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return trainingSessionRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
