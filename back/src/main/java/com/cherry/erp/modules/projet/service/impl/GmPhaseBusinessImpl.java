package com.cherry.erp.modules.projet.service.impl;

import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import com.cherry.erp.modules.projet.repository.GammeRepository;
import com.cherry.erp.modules.projet.repository.GmPhaseRepository;
import com.cherry.erp.modules.production.repository.PhaseRepository;
import com.cherry.erp.modules.projet.repository.TacheRepository;
import com.cherry.erp.modules.projet.service.GmPhaseBusiness;
import com.cherry.erp.modules.production.model.persistent.Phase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GmPhaseBusinessImpl implements GmPhaseBusiness {
    private final GmPhaseRepository gmPhaseRepository;
    private final GammeRepository gammeRepository;
    private final PhaseRepository phaseRepository;
    private final TacheRepository tacheRepository;
    private final SpiUtils spiUtils;

    @Override
    public GmPhase Add(GmPhase gmPhase, Integer gammeId) {
        Gamme gamme = gammeRepository.findByIdAndCompanyId(gammeId, spiUtils.findCurrentCompany().getId()).orElse(null);
        gmPhase.setGamme(gamme);
        return gmPhaseRepository.save(gmPhase);
    }

    @Override
    public GmPhase FindById(Integer id) {
        return gmPhaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<GmPhase> FindAll(Integer gammeId) {
        Gamme g = gammeRepository.findByIdAndCompanyId(gammeId,spiUtils.findCurrentCompany().getId()).orElse(null);
        return gmPhaseRepository.findByGammeId(g.getId());
    }

    @Override
    public GmPhase FindOne(Integer gammeId, Integer phaseId) {
        Gamme g = gammeRepository.findByIdAndCompanyId(gammeId,spiUtils.findCurrentCompany().getId()).orElse(null);
        Phase p = phaseRepository.findByIdAndCompanyId(phaseId,spiUtils.findCurrentCompany().getId()).orElse(null);
        return gmPhaseRepository.findByGammeIdAndPhaseId(g.getId(),p.getId());
    }

    @Override
    public void delete(Integer id,Integer gammeId) {
        gmPhaseRepository.deleteByIdAndGammeId(id,gammeId);
    }


}
