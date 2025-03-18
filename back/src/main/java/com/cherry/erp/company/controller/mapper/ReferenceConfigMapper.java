package com.cherry.erp.company.controller.mapper;

import com.cherry.erp.company.model.dto.ReferenceConfigDto;
import com.cherry.erp.company.model.persistent.ReferenceConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReferenceConfigMapper {

    ReferenceConfig toEntity(ReferenceConfigDto dto);

    ReferenceConfigDto toDto(ReferenceConfig entity);

    List<ReferenceConfigDto> toDtoList(List<ReferenceConfig> list);

}
