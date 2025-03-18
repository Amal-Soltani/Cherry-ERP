package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v3.hse.model.persistent.PermitWork;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.repository.PermitWorkRepository;

import com.cherry.erp.modules.v3.hse.service.PermitWorkService;

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
public class PermitWorkServiceImpl implements PermitWorkService {

    private final PermitWorkRepository permitWorkRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public PermitWorkServiceImpl(PermitWorkRepository permitWorkRepository,
                                 SpiUtils spiUtils) {
        this.permitWorkRepository = permitWorkRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public PermitWork  save(PermitWork permitWork) {
        if (permitWork.getId() != null && permitWork.getId() != 0) {
            PermitWork oldPermitWork = findOne(permitWork.getId());
            spiUtils.setOldValues(permitWork, oldPermitWork);
        }
        if (permitWork.getId() == null || permitWork.getId() == 0) {
            permitWork.setCompany(spiUtils.findCurrentCompany());
        }
        if (permitWork.getCompany() == null) {
            permitWork.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(permitWork);
        permitWork.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return permitWorkRepository.save(permitWork);
    }

    @Override
    public PermitWork findOne(Integer id) {
        return permitWorkRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<PermitWork> findAllByCompanyId() {
        return permitWorkRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void delete(Integer id) {
        try {
            permitWorkRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting permit to work : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public Page<PermitWork> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return permitWorkRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
