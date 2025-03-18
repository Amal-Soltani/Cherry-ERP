package com.cherry.erp.modules.gmao.service.impl;

import com.cherry.erp.common.utils.ReferenceConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.gmao.model.persistent.Equipment;
import com.cherry.erp.modules.gmao.repository.EquipmentRepository;
import com.cherry.erp.modules.gmao.service.EquipmentBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class EquipmentBusinessImpl implements EquipmentBusiness {

    private final EquipmentRepository equipmentRepository;
    private final SpiUtils spiUtils;

    @Override
    public String generateReference() {
        Integer nextRefNumber = equipmentRepository.getNextReferenceNumber(spiUtils.findCurrentCompany().getId());
        // Format the reference with the prefix and zero padding
        String reference = ReferenceConstant.EQUIPMENT_REFERENCE_PREFIX
                + String.format(ReferenceConstant.EQUIPMENT_REFRENCE_FORMAT,nextRefNumber );
        return reference;
    }
    @Override
    public Equipment add(Equipment equipment) {
        if (equipment.getId() != null && equipment.getId() != 0) {
            Equipment oldEquipment = FindByID(equipment.getId());
            spiUtils.setOldValues(equipment, oldEquipment);
        }
        if (equipment.getId() == null || equipment.getId() == 0) {
            equipment.setCompany(spiUtils.findCurrentCompany());
            equipment.setReference(generateReference());
        }
        if (equipment.getCompany() == null) {
            equipment.setCompany(spiUtils.findCurrentCompany());
        }
        spiUtils.setModifiedByAndCreatedByFields(equipment);
        equipment.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());

        return equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> FindAll() {
        return equipmentRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Equipment FindByID(Integer id) {
        return equipmentRepository.findByIdAndCompanyId(id,spiUtils.findCurrentCompany().getId()).orElse(null);
    }

    @Override
    public Page<Equipment> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return equipmentRepository.findByPage(spiUtils.findCurrentCompany().getId(),pageParam);
    }

    @Override
    public void DeleteById(Integer id) {
        equipmentRepository.deleteById(FindByID(id).getId());
    }
}
