package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.repository.RiskAssessmentRepository;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.cherry.erp.common.utils.SpiUtils;

import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;


@Service
@Slf4j
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final RiskAssessmentRepository riskAssessmentRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public RiskAssessmentServiceImpl(RiskAssessmentRepository riskAssessmentRepository,
                                     SpiUtils spiUtils) {
        this.riskAssessmentRepository = riskAssessmentRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public RiskAssessment save(RiskAssessment riskAssessment) {
        if (riskAssessment.getId() != null && riskAssessment.getId() != 0) {
            RiskAssessment oldRiskAssessment = findOne(riskAssessment.getId());
            spiUtils.setOldValues(riskAssessment, oldRiskAssessment);
            if (riskAssessment.getImage() == null) {
                riskAssessment.setImage(oldRiskAssessment.getImage());
            }
        }
        if (riskAssessment.getId() == null || riskAssessment.getId() == 0) {
            riskAssessment.setCompany(spiUtils.findCurrentCompany());
        }
        if (riskAssessment.getCompany() == null) {
            riskAssessment.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(riskAssessment);
        riskAssessment.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return riskAssessmentRepository.save(riskAssessment);
    }

    @Override
    public RiskAssessment findOne(Integer id) {
        return riskAssessmentRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<RiskAssessment> findAllByCompanyId() {
        return riskAssessmentRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void delete(Integer id) {
        try {
            riskAssessmentRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting risk Assessment : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public Page<RiskAssessment> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return riskAssessmentRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
