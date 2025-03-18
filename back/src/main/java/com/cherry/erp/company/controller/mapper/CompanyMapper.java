package com.cherry.erp.company.controller.mapper;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.repository.UserRepository;
import com.cherry.erp.company.model.dto.BasicCompanyDto;
import com.cherry.erp.company.model.dto.CompanyDto;
import com.cherry.erp.company.model.dto.MinimalCompanyDto;
import com.cherry.erp.company.model.dto.MinimalUserDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.dto.MinimalEmployeeDto;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {

    @Autowired
    private UserRepository userRepository;

    public abstract Company toEntity(CompanyDto companyDto);

    public abstract CompanyDto toDto(Company company);

    public abstract BasicCompanyDto toBasicDto(Company company);

    public abstract Company toEntity(MinimalCompanyDto minimalCompanyDto);

    public abstract Employee toEntity(MinimalEmployeeDto minimalEmployeeDto);

    public abstract SpiUser toEntity(MinimalUserDto minimalUserDto);

    @AfterMapping
    public void mapLicense(Company company, @MappingTarget CompanyDto companyDto) {
        companyDto.setNbUsers(userRepository.countSpiUserByCompany(company.getId()));
        companyDto.setLastActivity(userRepository.lastActivity(company.getId()));
        if (Objects.nonNull(company.getSupport())) {
            companyDto.setSla(company.getSupport().getSla());
        }
    }

}
