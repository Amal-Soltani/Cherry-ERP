package com.cherry.erp.administration.business.impl;

import com.cherry.erp.administration.business.ResponseRestMsgService;
import com.cherry.erp.administration.model.persistent.ResponseRestMessage;
import com.cherry.erp.administration.model.persistent.ResponseRestMsg;
import com.cherry.erp.administration.repository.ResponseRestMsgRepository;
import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.company.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.DELETE_KO_CODE;
import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.RESOURCE_NOT_FOUND;

@Service
@Slf4j
public class ResponseRestMsgServiceImpl implements ResponseRestMsgService {

    private final ResponseRestMsgRepository responseRestMsgRepository;
    private final SpiUtils spiUtils;
    private final CompanyRepository companyRepository;

    public ResponseRestMsgServiceImpl(ResponseRestMsgRepository responseRestMsgRepository, SpiUtils spiUtils, CompanyRepository companyRepository) {
        this.responseRestMsgRepository = responseRestMsgRepository;
        this.spiUtils = spiUtils;
        this.companyRepository = companyRepository;
    }


    @Override
    public List<ResponseRestMsg> findAll() {
        return responseRestMsgRepository.findAll();
    }

    @Override
    public Page<ResponseRestMsg> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return responseRestMsgRepository.findAll(pageParam);
    }

    @Override
    public ResponseRestMsg save(ResponseRestMsg responseRestMsg) {
        if (responseRestMsg.getId() != null && responseRestMsg.getId() != 0) {
            spiUtils.setOldValues(responseRestMsg, findOne(responseRestMsg.getId()));
        }
        responseRestMsg.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());
        spiUtils.setModifiedByAndCreatedByFields(responseRestMsg);
        return responseRestMsgRepository.save(responseRestMsg);
    }

    @Override
    public ResponseRestMsg findOne(Integer id) {
        return responseRestMsgRepository.findById(id)
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public ResponseRestMsg findByCode(String code) {
        return responseRestMsgRepository.findByCode(code).orElse(null);
    }

    @Override
    public String getMessageByCodeAndLocale(String code, List<String> info, String locale) {
        ResponseRestMsg responseRestMsg = findByCode(code);
        if (Objects.nonNull(responseRestMsg)) {
            List<ResponseRestMessage> messages = responseRestMsg.getMessages().getMessages();

            List<ResponseRestMessage> filterdMessages = messages.stream()
                    .filter(responseRestMessage -> responseRestMessage.getLocale().equals(locale))
                    .collect(Collectors.toList());
            if (!filterdMessages.isEmpty()) {
                String message = filterdMessages.get(0).getMessage();
                for (int i = 0; i < info.size(); i++) {
                    message = message.replace("{" + i + "}", info.get(i));
                }
                return message;
            }

            return getDefaultMessage(responseRestMsg, info);
        }
        return null;
    }

    @Override
    public String getMessageByCodeForCurrentCompany(String code, List<String> info) {
        String locale = spiUtils.findCurrentCompany().getLanguage();
        ResponseRestMsg responseRestMsg = findByCode(code);
        if (Objects.nonNull(responseRestMsg)) {
            List<ResponseRestMessage> messages = responseRestMsg.getMessages().getMessages();

            List<ResponseRestMessage> filterdMessages = messages.stream()
                    .filter(responseRestMessage -> responseRestMessage.getLocale().equals(locale))
                    .collect(Collectors.toList());
            if (!filterdMessages.isEmpty()) {
                return filterdMessages.get(0).getMessage();
            }
            return getDefaultMessage(responseRestMsg, info);
        }
        return null;
    }

    @Override
    public String getMessageByCodeForCurrentCompany(String code) {
        return getMessageByCodeForCurrentCompany(code, Collections.emptyList());
    }

    @Override
    public String getMessageByCodeAndCompanyId(String code, List<String> info, Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (Objects.nonNull(company)) {
            return getMessageByCodeAndLocale(code, info, company.getLanguage());
        }
        return null;
    }

    @Override
    public String getMessageByCodeAndCompanyId(String code, Integer companyId) {
        return getMessageByCodeAndCompanyId(code, Collections.emptyList(), companyId);
    }

    private String getDefaultMessage(ResponseRestMsg responseRestMsg, List<String> info) {
        List<ResponseRestMessage> defaultMessages = responseRestMsg.getMessages().getMessages().stream()
                .filter(responseRestMessage -> Boolean.TRUE.equals(responseRestMessage.getDefaultMsg()))
                .collect(Collectors.toList());

        if (!defaultMessages.isEmpty()) {
            String message = defaultMessages.get(0).getMessage();
            for (int i = 0; i < info.size(); i++) {
                message = message.replace("{" + i + "}", info.get(i));
            }
            return message;
        }
        return "";
    }

    @Override
    public void delete(Integer id) {
        try {
            responseRestMsgRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting responseRestMsg : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

}
