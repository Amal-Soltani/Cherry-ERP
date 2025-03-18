package com.cherry.erp.modules.v3.hse.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v3.hse.model.persistent.Incident;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.repository.IncidentRepository;
import com.cherry.erp.modules.v3.hse.repository.RiskAssessmentRepository;
import com.cherry.erp.modules.v3.hse.service.IncidentService;
import com.cherry.erp.modules.v3.hse.service.RiskAssessmentService;
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
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final SpiUtils spiUtils;

    @Autowired
    public IncidentServiceImpl(IncidentRepository incidentRepository,
                               SpiUtils spiUtils) {
        this.incidentRepository = incidentRepository;
        this.spiUtils = spiUtils;
    }

    @Override
    public Incident save(Incident incident) {
        if (incident.getId() != null && incident.getId() != 0) {
            Incident oldIncident = findOne(incident.getId());
            spiUtils.setOldValues(incident, oldIncident);
            if (incident.getImage() == null) {
                incident.setImage(oldIncident.getImage());
            }
        }
        if (incident.getId() == null || incident.getId() == 0) {
            incident.setCompany(spiUtils.findCurrentCompany());
        }
        if (incident.getCompany() == null) {
            incident.setCompany(spiUtils.findCurrentCompany());
        }

        spiUtils.setModifiedByAndCreatedByFields(incident);
        incident.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return incidentRepository.save(incident);
    }

    @Override
    public Incident findOne(Integer id) {
        return incidentRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public List<Incident> findAllByCompanyId() {
        return incidentRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }



    @Override
    public void delete(Integer id) {
        try {
            incidentRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting incident : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

    @Override
    public Page<Incident> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return incidentRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
    }
}
