package com.cherry.erp.modules.quality.service;

import com.cherry.erp.modules.quality.model.dto.FNCPercentageDTO;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional

public interface FNCBusiness {
    @Transactional(propagation = REQUIRED)
    public FNC Add(FNC FNC);
    public String generateReference();
    public Page<FNC> findByPage(Integer page, Integer pageSize);
    public List<FNC> FindAll();
    public FNC FindById(Integer id);
    public List<FNC> FindByYear(Integer year);
    public FNC FindTheFirstDate();
    @Transactional(propagation = REQUIRED)
    public  void DeleteBYId(Integer id);

    List<Object[]> findFNCPercentageByYear(Integer year);
}
