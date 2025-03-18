package com.cherry.erp.modules.projet.service;

import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface GmPhaseBusiness {
    @Transactional(propagation = REQUIRED)
    public GmPhase Add(GmPhase GmPhase, Integer gammeId);
    public List<GmPhase> FindAll(Integer gammeId);
    public GmPhase FindOne(Integer gammeId,Integer phaseId);
    public GmPhase FindById(Integer id);
    @Transactional(propagation = REQUIRED)
    public void delete(Integer gammeId,Integer id);



}
