package com.cherry.erp.modules.production.service.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.production.repository.PhaseRepository;
import com.cherry.erp.modules.production.service.PhaseBusiness;
import com.cherry.erp.modules.production.model.persistent.Phase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PhaseBusinessImpl implements PhaseBusiness {
    private final PhaseRepository phaseRepository;
    private final SpiUtils spiUtils;

    @Override
    public Phase Add(Phase phase) {
        if (phase.getId() != null && phase.getId() != 0) {
            Phase oldPhase = FindById(phase.getId());
            spiUtils.setOldValues(phase, oldPhase);
        }
        if (phase.getId() == null || phase.getId() == 0) {
            phase.setCompany(spiUtils.findCurrentCompany());
        }
        if (phase.getCompany() == null) {
            phase.setCompany(spiUtils.findCurrentCompany());
        }
        spiUtils.setModifiedByAndCreatedByFields(phase);
        phase.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return phaseRepository.save(phase);
    }

    @Override
    public Page<Phase> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return phaseRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }


    @Override
    public Phase FindById(Integer id) {
        return phaseRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public Phase FindByName(String name) {
        return phaseRepository.findPhaseByName(name,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Phase> FindAll() {
        return phaseRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Phase> getPhaseByTache(Integer tacheId) {
        return  phaseRepository.getPhaseByTache(tacheId,spiUtils.findCurrentCompany().getId());
    }


    @Override
    public void DeleteBYId(Integer id) {
        phaseRepository.deleteById(FindById(id).getId());
    }
}
