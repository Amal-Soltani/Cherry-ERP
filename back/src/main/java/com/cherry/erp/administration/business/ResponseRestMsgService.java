package com.cherry.erp.administration.business;

import com.cherry.erp.administration.model.persistent.ResponseRestMsg;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface ResponseRestMsgService {

    @Transactional(propagation = REQUIRED)
    ResponseRestMsg save(ResponseRestMsg employee);

    ResponseRestMsg findByCode(String code);

    String getMessageByCodeAndLocale(String code, List<String> info, String locale);

    String getMessageByCodeForCurrentCompany(String code, List<String> info);

    String getMessageByCodeAndCompanyId(String code, List<String> info, Integer companyId);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    ResponseRestMsg findOne(Integer id);

    List<ResponseRestMsg> findAll();

    Page<ResponseRestMsg> findByPage(Integer page, Integer pageSize);

    String getMessageByCodeAndCompanyId(String code, Integer companyId);

    String getMessageByCodeForCurrentCompany(String code);
}

