package com.cherry.erp.company.business.impl;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.account.service.UserService;
import com.cherry.erp.administration.business.ResponseRestMsgService;
import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.common.utils.SpiUtils;
import com.cherry.erp.company.business.CompanyBusiness;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.company.repository.CompanyRepository;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v2.hr.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;

@Service
@Slf4j
public class CompanyBusinessImpl implements CompanyBusiness {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final SpiUtils spiUtils;

    private final ResponseRestMsgService responseRestMsgService;

    @Autowired
    public CompanyBusinessImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                               UserService userService,
                               SpiUtils spiUtils,
                               ResponseRestMsgService responseRestMsgService) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.spiUtils = spiUtils;
        this.responseRestMsgService = responseRestMsgService;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findOne(Integer id) {
        return companyRepository.findById(id).orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public Company save(Company company) {
        if (company.getId() != null && company.getId() != 0) {
            Company oldCompany = findOne(company.getId());
            spiUtils.setOldValues(company, oldCompany);
        }
        try {
            spiUtils.setModifiedByAndCreatedByFields(company);
            return companyRepository.save(company);
        } catch (Exception e) {
            log.error("Error while saving Company : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, ADD_KO_CODE);
        }
    }

    @Override
    public Page<Company> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        return companyRepository.findAllByOrderByCreationDateAsc(pageParam);
    }

    @Override
    public Boolean loginAsAdminCompany(Integer companyId) {

        SpiUser superAdmin = userService.getCurrentUser();

        if (superAdmin.hasRole(RolesConstant.ROLE_ERP_ADMIN)) {
            Employee emp = superAdmin.getEmployee();
            Optional<Company> optionalCompany = companyRepository.findById(companyId);
            if (optionalCompany.isPresent()) {
                emp.setCompany(optionalCompany.get());
                employeeRepository.save(emp);
                return true;
            }
        }

        return false;
    }

    @Override
    public Company getCurrentCompany() {
        return spiUtils.findCurrentUser().getEmployee().getCompany();
    }

    @Override
    public void delete(Integer id) {

        try {
            companyRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error while deleting Company : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }

}
