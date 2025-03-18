package com.cherry.erp.company.business;

import com.cherry.erp.account.model.persistent.SpiUser;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import freemarker.template.TemplateException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

public interface CompanyBusiness {

    List<Company> findAll();

    @Transactional(propagation = REQUIRED)
    void delete(Integer id);

    Company findOne(Integer id);

    @Transactional(propagation = REQUIRED)
    Company save(Company company);

    Page<Company> findByPage(Integer page, Integer pageSize);

    Boolean loginAsAdminCompany(Integer companyId);

    Company getCurrentCompany();

}
