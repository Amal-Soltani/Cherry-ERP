package com.cherry.erp.modules.v3.hse.controller.mapper;


import com.cherry.erp.modules.v3.hse.model.dto.TrainingDto;
import com.cherry.erp.modules.v3.hse.model.dto.TrainingSessionDto;
import com.cherry.erp.modules.v3.hse.model.persistent.Training;
import com.cherry.erp.modules.v3.hse.model.persistent.TrainingSession;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class TrainingSessionMapper {

    public abstract TrainingSession toEntity(TrainingSessionDto trainingSessionDto);

    public abstract TrainingSessionDto toDto(TrainingSession trainingSession);

    @AfterMapping
    public void mapExtraAttributes(TrainingSession entity, @MappingTarget TrainingSessionDto dto) {

    }

}
