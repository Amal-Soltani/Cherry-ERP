package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskIdentification;
import com.cherry.erp.modules.v3.hse.repository.RiskIdentificationRepository;
import com.cherry.erp.modules.v3.hse.service.RiskIdentificationService;
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
public class RiskIdentificationServiceImpl implements RiskIdentificationService {

    private final RiskIdentificationRepository riskIdentificationRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public RiskIdentificationServiceImpl(RiskIdentificationRepository riskIdentificationRepository,
                                         SpiUtils spiUtils) {
        this.riskIdentificationRepository = riskIdentificationRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public RiskIdentification save(RiskIdentification riskIdentification) {
        if (riskIdentification.getId() != null && riskIdentification.getId() != 0) {
            RiskIdentification oldRiskIdentification = findOne(riskIdentification.getId());
            spiUtils.setOldValues(riskIdentification, oldRiskIdentification);
            if (riskIdentification.getImage() == null) {
                riskIdentification.setImage(oldRiskIdentification.getImage());
            }
        }
        if (riskIdentification.getId() == null || riskIdentification.getId() == 0) {
            riskIdentification.setCompany(spiUtils.findCurrentCompany());
        }
        if (riskIdentification.getCompany() == null) {
            riskIdentification.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(riskIdentification);
        riskIdentification.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return riskIdentificationRepository.save(riskIdentification);
    }

    @Override
    public RiskIdentification findOne(Integer id) {
        return riskIdentificationRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<RiskIdentification> findAllByCompanyId() {
        return riskIdentificationRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void delete(Integer id) {
        try {
            riskIdentificationRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting risk Identification : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public Page<RiskIdentification> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return riskIdentificationRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
