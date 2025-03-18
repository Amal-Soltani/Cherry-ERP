package com.cherry.erp.modules.v2.projetmanagement.service.impl;

import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.company.model.persistent.Company;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Disabled
class ProjectTaskServiceImplTest {

    @Mock
    private SpiUtils spiUtils;

    @Test
    void whenProjectTaskExistsFindOneShouldReturnTheProjectTask() {
        // when
        when(spiUtils.findCurrentCompany()).thenReturn(new Company());
        verify(spiUtils, times(1)).findCurrentCompany();
    }

    @Test
    void whenProjectTaskDoesNotExistFindOneShouldThrow() {
        // when
        when(spiUtils.findCurrentCompany()).thenReturn(new Company());

    }
}
