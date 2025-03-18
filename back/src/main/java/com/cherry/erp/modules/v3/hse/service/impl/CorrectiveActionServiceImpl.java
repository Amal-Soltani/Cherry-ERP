package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;

import com.cherry.erp.modules.v3.hse.repository.CorrectiveActionRepository;

import com.cherry.erp.modules.v3.hse.service.CorrectiveActionService;
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
public class CorrectiveActionServiceImpl implements CorrectiveActionService {

    private final CorrectiveActionRepository correctiveActionRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public CorrectiveActionServiceImpl(CorrectiveActionRepository correctiveActionRepository,
                                       SpiUtils spiUtils){
        this.correctiveActionRepository=correctiveActionRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public CorrectiveAction save(CorrectiveAction correctiveAction) {
        if (correctiveAction.getId() != null && correctiveAction.getId() != 0) {
            CorrectiveAction oldCorrectiveAction = findOne(correctiveAction.getId());
            spiUtils.setOldValues(correctiveAction, oldCorrectiveAction);
        }

        spiUtils.setModifiedByAndCreatedByFields(correctiveAction);
        correctiveAction.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return correctiveActionRepository.save(correctiveAction);
    }

    @Override
    public void delete(Integer id) {
        try {
            correctiveActionRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting corrective action : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public CorrectiveAction findOne(Integer id) {
        return correctiveActionRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
   public List<CorrectiveAction> findAllByCompanyId() {
        return correctiveActionRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Page<CorrectiveAction> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return correctiveActionRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
