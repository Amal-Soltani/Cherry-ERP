package com.cherry.erp.modules.v2.hr.controller.mapper;

import com.cherry.erp.modules.v2.hr.model.dto.BasicEmployeeDto;
import com.cherry.erp.modules.v2.hr.model.dto.EmployeeDto;
import com.cherry.erp.modules.v2.hr.model.dto.MinimalEmployeeDto;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v2.hr.repository.EmployeeRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

    @Value("${server.servlet.context-path}")
    private String fileServerUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    public abstract Employee toEntity(EmployeeDto employeeDto);

    public abstract EmployeeDto toDto(Employee employee);

    public abstract MinimalEmployeeDto toMinimalDto(Employee employee);

    public abstract List<EmployeeDto> toDtoList(List<Employee> employeeList);

    public abstract List<MinimalEmployeeDto> toMinimalDtoList(List<Employee> employeeList);

    public abstract BasicEmployeeDto toBasicDto(Employee employee);

    public abstract List<EmployeeDto> toBasicDtoList(List<Employee> employeeList);

    @AfterMapping
    public void mapExtraAttributes(Employee entity, @MappingTarget EmployeeDto dto) {

        //set image to null
        dto.setImage(null);

        //map imageUrl, add file server url
        if (Objects.nonNull(entity.getImageUrl())) {
            dto.setImageUrl(fileServerUrl + "/images/downloadFile" + entity.getImageUrl());
        }
    }

    @AfterMapping
    public void mapExtraAttributesBasic(Employee entity, @MappingTarget BasicEmployeeDto dto) {
        //map imageUrl, add file server url
        if (Objects.nonNull(entity.getImageUrl())) {
            dto.setImageUrl(fileServerUrl + "/images/downloadFile" + entity.getImageUrl());
        }
    }

}
