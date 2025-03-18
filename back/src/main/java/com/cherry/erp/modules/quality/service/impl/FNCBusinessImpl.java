package com.cherry.erp.modules.quality.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import com.cherry.erp.modules.quality.repository.FNCAnomaliesRepository;
import com.cherry.erp.modules.quality.repository.FNCRepository;
import com.cherry.erp.modules.quality.service.FNCBusiness;
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
public class FNCBusinessImpl implements FNCBusiness {

    private final FNCRepository FNCRepository;
    private final FNCAnomaliesRepository FNCAnomaliesRepository;
    private final SpiUtils spiUtils;

    @Override
    public String generateReference() {
        Integer nextRefNumber = FNCRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.FNC_REFERENCE_PREFIX
                + String.format(ReferenceConstant.FNC_REFRENCE_FORMAT,nextRefNumber );
        return reference;
    }

    @Override
    public FNC Add(FNC FNC) {
        if (FNC.getId() != null && FNC.getId() != 0) {
            FNC oldFNC = FindById(FNC.getId());
            spiUtils.setOldValues(FNC, oldFNC);
        }
        if (FNC.getId() == null || FNC.getId() == 0) {
            FNC.setCompany(spiUtils.findCurrentCompany());
            FNC.setReference(generateReference());
        }
        if (FNC.getCompany() == null) {
            FNC.setCompany(spiUtils.findCurrentCompany());

        }
        spiUtils.setModifiedByAndCreatedByFields(FNC);
        FNC.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return FNCRepository.save(FNC);
    }

    @Override
    public Page<FNC> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return FNCRepository.findByCompany(spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public List<FNC> FindAll() {
        return FNCRepository.findByCompanyIdByOrderByModificationDateDesc(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public FNC FindById(Integer id) {
        return FNCRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public List<FNC> FindByYear(Integer year) {
        return FNCRepository.findByYearAndCompanyId(year,spiUtils.findCurrentCompany().getId());
    }

    @Override
    public FNC FindTheFirstDate() {
        return FNCRepository.findTheFirstDate(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public void DeleteBYId(Integer id) {
        List<FNCAnomalies> anomalies = FNCAnomaliesRepository.findByFNC(id);
        for (FNCAnomalies anomalie: anomalies) {
            anomalie.setFNC(null);
            anomalie.setAnomalies(null);
            FNCAnomaliesRepository.delete(anomalie);
        }
        FNCRepository.deleteById(FindById(id).getId());
    }

    @Override
    public List<Object[]> findFNCPercentageByYear(Integer year) {
        return FNCRepository.findFNCPercentageByYearAndCompany(year, spiUtils.findCurrentCompany().getId());
    }
}
