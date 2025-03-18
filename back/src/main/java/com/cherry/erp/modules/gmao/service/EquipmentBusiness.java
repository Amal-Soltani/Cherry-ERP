package com.cherry.erp.modules.gmao.service;

import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.gmao.model.persistent.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;


import java.io.IOException;
import java.util.List;

public interface EquipmentBusiness {
    @Transactional(propagation = REQUIRED)
    public Equipment add (Equipment equipment);

    public String generateReference();
    public List<Equipment> FindAll();
    public Equipment FindByID(Integer id);
    public Page<Equipment> findByPage(Integer page, Integer pageSize);
    @Transactional(propagation = REQUIRED)
    public void DeleteById(Integer id);

}
