package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;

import com.cherry.erp.modules.v3.hse.model.persistent.PlanAction;
import com.cherry.erp.modules.v3.hse.repository.CorrectiveActionRepository;

import com.cherry.erp.modules.v3.hse.repository.PlanActionRepository;
import com.cherry.erp.modules.v3.hse.service.CorrectiveActionService;
import com.cherry.erp.modules.v3.hse.service.PlanActionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.DELETE_KO_CODE;
import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.RESOURCE_NOT_FOUND;
@Service
@Slf4j
public class PlanActionImpl implements PlanActionService {

    private final PlanActionRepository planActionRepository;
    private final SpiUtils spiUtils;

    public PlanActionImpl(PlanActionRepository planActionRepository, SpiUtils spiUtils) {
        this.planActionRepository = planActionRepository;
        this.spiUtils = spiUtils;
    }


    @Override
    public PlanAction save(PlanAction p) {
        if (p.getId() != null && p.getId() != 0) {
            PlanAction oldPlanAction = findOne(p.getId());
            spiUtils.setOldValues(p, oldPlanAction);
        }

        spiUtils.setModifiedByAndCreatedByFields(p);
        p.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return planActionRepository.save(p);
    }

    @Override
    public void delete(Integer planActionNumber) {
        try {
            planActionRepository.deleteById(findOne(planActionNumber).getId());
        } catch (Exception e) {
            log.error("Error while deleting corrective action : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public PlanAction findOne(Integer id) {
        return planActionRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));

    }

    @Override
    public List<PlanAction> findAllByCompanyId() {
        return planActionRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }



    @Override
    public Page<PlanAction> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return planActionRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
