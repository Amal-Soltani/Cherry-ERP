package com.cherry.erp.modules.quality.service;

import com.cherry.erp.modules.quality.model.persistent.FNC;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface FNCAnomaliesBusiness {
    public List<FNCAnomalies> FindAll(Integer fnc);
    @Transactional(propagation = REQUIRED)
    public FNCAnomalies Add(FNCAnomalies ficheAn, Integer fnc) ;
    public FNCAnomalies FindById(Integer id);
    @Transactional(propagation = REQUIRED)
    public void Delete(Integer fiche, Integer id);

    public List<Object[]> GetFNCCountByYearAndMonth(Integer year);
    public List<Object[]> GetFNCCountByYearAndWeek(Integer year);
}
