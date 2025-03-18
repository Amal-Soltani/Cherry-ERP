package com.cherry.erp.modules.production.service;

import com.cherry.erp.modules.production.model.persistent.Phase;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface PhaseBusiness {
    @Transactional(propagation = REQUIRED)
    public Phase Add (Phase phase);
    public Page<Phase> findByPage(Integer page, Integer pageSize);
    public Phase FindById(Integer id);
    public Phase FindByName(String name);
    public List<Phase> FindAll();
    List<Phase> getPhaseByTache (Integer tacheId);

    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);

}
