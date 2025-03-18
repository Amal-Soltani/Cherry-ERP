package com.cherry.erp.modules.v3.hse.controller.mapper;


import com.cherry.erp.modules.v3.hse.model.dto.TrainingDto;

import com.cherry.erp.modules.v3.hse.model.persistent.Training;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class TrainingMapper {

    public abstract Training toEntity(TrainingDto trainingDto);

    public abstract TrainingDto toDto(Training training);

    @AfterMapping
    public void mapExtraAttributes(Training entity, @MappingTarget TrainingDto dto) {

    }

}
