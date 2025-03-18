package com.cherry.erp.modules.gmao.controller.Mapper;
import com.cherry.erp.modules.gmao.model.dto.EquipmentDto;
import com.cherry.erp.modules.gmao.model.persistent.Equipment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EquipmentMapper {
    public abstract Equipment toEntity(EquipmentDto equipmentDto);
    public abstract EquipmentDto toDto(Equipment equipment);

    public abstract List<EquipmentDto> toDtoList(List<Equipment> equipmentList);
    @AfterMapping
    public void mapExtraAttributes(Equipment equipment, @MappingTarget EquipmentDto equipmentDto) {}
}
