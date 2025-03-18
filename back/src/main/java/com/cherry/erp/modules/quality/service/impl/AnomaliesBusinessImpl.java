package com.cherry.erp.modules.quality.service.impl;

import com.cherry.erp.common.utils.SpiUtils;

import com.cherry.erp.modules.production.model.persistent.Phase;
import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import com.cherry.erp.modules.quality.repository.AnomaliesRepository;
import com.cherry.erp.modules.quality.service.AnomaliesBusiness;
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
public class AnomaliesBusinessImpl implements AnomaliesBusiness {

    private final AnomaliesRepository anomaliesRepository;
    private final SpiUtils spiUtils;
    @Override
    public Anomalies Add(Anomalies anomalie) {
        if (anomalie.getId() != null && anomalie.getId() != 0) {
            Anomalies oldAnomalie = FindById(anomalie.getId());
            spiUtils.setOldValues(anomalie, oldAnomalie);
        }
        if (anomalie.getId() == null || anomalie.getId() == 0) {
            anomalie.setCompany(spiUtils.findCurrentCompany());
        }
        if (anomalie.getCompany() == null) {
            anomalie.setCompany(spiUtils.findCurrentCompany());
        }
        spiUtils.setModifiedByAndCreatedByFields(anomalie);
        anomalie.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return anomaliesRepository.save(anomalie);
    }

    @Override
    public Page<Anomalies> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return anomaliesRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }
    @Override
    public List<Anomalies> FindAll() {
        return anomaliesRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Anomalies FindById(Integer id) {
        return anomaliesRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public Anomalies FindByType(String type) {
        Anomalies anomalie = anomaliesRepository.findByType(type);
        return anomaliesRepository.findByIdAndCompanyId(anomalie.getId(),spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public void DeleteBYId(Integer id) {
        anomaliesRepository.deleteById(FindById(id).getId());
    }
}
