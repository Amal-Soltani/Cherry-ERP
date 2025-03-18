package com.cherry.erp.common.business.impl;

import com.cherry.erp.common.business.GenericParameterService;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.model.persistent.GenericParameter;
import com.cherry.erp.common.repository.GenericParameterRepository;
import com.cherry.erp.common.utils.SpiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.RESOURCE_NOT_FOUND;

@Service
@Slf4j
public class GenericParameterServiceImpl implements GenericParameterService {


    @Value("${platform.cron.check-license}")
    private String checkLicenseCron;

    private final GenericParameterRepository genericParameterRepository;
    private final SpiUtils spiUtils;

    public GenericParameterServiceImpl(GenericParameterRepository genericParameterRepository, SpiUtils spiUtils) {
        this.genericParameterRepository = genericParameterRepository;
        this.spiUtils = spiUtils;
    }


    @Override
    public GenericParameter save(GenericParameter genericParameter) {
        genericParameter.setCompany(spiUtils.findCurrentCompany());
        return genericParameterRepository.save(genericParameter);
    }

    @Override
    public GenericParameter update(Integer id, GenericParameter genericParameter) {
        genericParameter.setCompany(spiUtils.findCurrentCompany());
        genericParameter.setId(id);
        return genericParameterRepository.save(genericParameter);
    }

    @Override
    public GenericParameter findByCompany() {
        return genericParameterRepository.findByCompany(spiUtils.findCurrentCompany().getId());
    }

}
