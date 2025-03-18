package com.cherry.erp.modules.quality.service.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import com.cherry.erp.modules.quality.repository.FNCAnomaliesRepository;
import com.cherry.erp.modules.quality.repository.FNCRepository;
import com.cherry.erp.modules.quality.service.FNCAnomaliesBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class FNCAnomaliesBusinessImpl implements FNCAnomaliesBusiness {
    private final FNCAnomaliesRepository FNCAnomaliesRepository;
    private final FNCRepository FNCRepository;
    private final SpiUtils spiUtils;
    @Override
    public List<FNCAnomalies> FindAll(Integer fnc) {
        FNC fiche = FNCRepository.findByIdAndCompanyId(fnc,spiUtils.findCurrentCompany().getId()).orElse(null);
        return FNCAnomaliesRepository.findByFNC(fiche.getId());
    }

    @Override
    public FNCAnomalies Add(FNCAnomalies ficheAn, Integer fnc) {

        ficheAn.setFNC(FNCRepository.findByIdAndCompanyId(fnc,spiUtils.findCurrentCompany().getId()).orElse(null));

        return FNCAnomaliesRepository.save(ficheAn);
    }

    @Override
    public FNCAnomalies FindById(Integer id) {
        return FNCAnomaliesRepository.findById(id).orElse(null);
    }

    @Override
    public void Delete(Integer fiche, Integer id) {
        FNC fnc = FNCRepository.findByIdAndCompanyId(fiche,spiUtils.findCurrentCompany().getId()).orElse(null);
        FNCAnomaliesRepository.deleteByFNCAndId(fnc.getId(),id);
    }

    @Override
    public List<Object[]> GetFNCCountByYearAndMonth(Integer year) {
        return FNCAnomaliesRepository.findFNCCountByYearAndMonth(year, spiUtils.findCurrentCompany().getId());
    }

    @Override
    public List<Object[]> GetFNCCountByYearAndWeek(Integer year) {
        return FNCAnomaliesRepository.findFNCCountByYearAndWeek(year, spiUtils.findCurrentCompany().getId());
    }
}
