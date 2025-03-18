package com.cherry.erp.company.business.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.company.business.ReferenceConfigService;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.company.model.persistent.ReferenceConfig;
import com.cherry.erp.company.repository.ReferenceConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;

@Service
@Slf4j
@AllArgsConstructor
public class ReferenceConfigServiceImpl implements ReferenceConfigService {

    private final ReferenceConfigRepository referenceConfigRepository;
    private final SpiUtils spiUtils;

    @Override
    public List<ReferenceConfig> getByYear(Integer year) {
        return referenceConfigRepository.findByCompanyIdAndYearOrderByObject(spiUtils.findCurrentCompany().getId(), year);
    }

    @Override
    public List<ReferenceConfig> getWithoutYear() {
        return referenceConfigRepository.findByCompanyIdAndYearIsNullOrderByObject(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<ReferenceConfig> findAll() {
        return referenceConfigRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public ReferenceConfig findOne(Integer id) {
        return referenceConfigRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public ReferenceConfig save(ReferenceConfig referenceConfig) {
        Company currentCompany = spiUtils.findCurrentCompany();
        if (referenceConfig.getId() != null && referenceConfig.getId() != 0) {
            spiUtils.setOldValues(referenceConfig, findOne(referenceConfig.getId()));
        } else {
            if (referenceConfigRepository.findByCompanyIdAndObjectAndYear(currentCompany.getId(), referenceConfig.getObject(), referenceConfig.getYear()).isPresent()) {
                throw new SpiException(HttpStatus.BAD_REQUEST, REFERENCE_ALREADY_EXIST);
            }
        }
        try {
            referenceConfig.setCompany(currentCompany);
            spiUtils.setModifiedByAndCreatedByFields(referenceConfig);
            return referenceConfigRepository.save(referenceConfig);
        } catch (Exception e) {
            log.error("Error while saving ReferenceConfig : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            referenceConfigRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting ReferenceConfig : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

}
