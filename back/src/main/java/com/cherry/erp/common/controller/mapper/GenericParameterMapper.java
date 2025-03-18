package com.cherry.erp.common.controller.mapper;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.repository.UserRepository;
import com.cherry.erp.common.model.dto.GenericParameterDto;
import com.cherry.erp.common.model.persistent.GenericParameter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class GenericParameterMapper {


    public abstract GenericParameter toEntity(GenericParameterDto genericParameterDto);

    public abstract GenericParameterDto toDto(GenericParameter genericParameter);

    public abstract List<GenericParameterDto> toDtoList(List<GenericParameter> genericParameter);

    @AfterMapping
    public void mapLicense(GenericParameter genericParameter, @MappingTarget GenericParameterDto genericParameterDto) {

    }

}
