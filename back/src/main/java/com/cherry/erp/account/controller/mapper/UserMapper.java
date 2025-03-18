package com.cherry.erp.account.controller.mapper;

import com.cherry.erp.account.model.dto.BasicUserDto;
import com.cherry.erp.account.model.dto.UserDto;
import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.modules.v2.hr.controller.mapper.EmployeeMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SpiUtils spiUtils;

    public abstract UserDto entityToDto(SpiUser spiUser);

    public abstract BasicUserDto entityToBasicDto(SpiUser spiUser);

    @AfterMapping
    public void mapEmployee(SpiUser spiUser, @MappingTarget UserDto userDto) {
        userDto.setEmployee(employeeMapper.toDto(spiUser.getEmployee()));
    }

    @AfterMapping
    public void mapEmployee(SpiUser spiUser, @MappingTarget BasicUserDto basicUserDto) {
        basicUserDto.setEmployee(employeeMapper.toDto(spiUser.getEmployee()));

        Integer currentUserId = spiUtils.findCurrentUser().getId();
        basicUserDto.setNumberNotSeenMessages(0);
    }
}
