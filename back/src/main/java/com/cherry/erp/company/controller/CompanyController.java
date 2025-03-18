package com.cherry.erp.company.controller;

import com.cherry.erp.common.mail.MailModel;
import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.utils.ResponseFactory;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.company.business.CompanyBusiness;
import com.cherry.erp.company.controller.mapper.CompanyMapper;
import com.cherry.erp.company.model.dto.*;
import com.cherry.erp.company.model.enums.BusinessPlanEnum;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.dto.MinimalEmployeeDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;
import static com.cherry.erp.common.utils.ResponseFactory.success;

@RestController
@RequestMapping("/companies")
@Slf4j
public class CompanyController {

    private final CompanyBusiness companyBusiness;
    private final CompanyMapper companyMapper;
    private final Configuration emailConfig;

    @Autowired
    public CompanyController(CompanyBusiness companyBusiness, CompanyMapper companyMapper,

                             @Qualifier("emailConfigBean") Configuration emailConfig) {
        this.companyBusiness = companyBusiness;
        this.companyMapper = companyMapper;
        this.emailConfig = emailConfig;
    }

    @GetMapping("loginAsAdminCompany")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    public ResponseEntity<Boolean> loginAsAdminCompany(@RequestParam("companyId") Integer companyId) {
        return ResponseEntity.ok().body(companyBusiness.loginAsAdminCompany(companyId));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    public ResponseEntity<Page<CompanyDto>> getAllPage(@RequestParam("page") Integer page,
                                                       @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(companyBusiness.findByPage(page, pageSize).map(companyMapper::toDto));
    }

    @PostMapping("/register")
    //@CrossOrigin(origins = {"http://cherry-erp.com", "https://cherry-erp.com"})
    public ResponseEntity<CompanyDto> register(@RequestBody RegisterCompanyDto registerCompanyDto) throws MessagingException, IOException, TemplateException {

        MinimalCompanyDto minimalCompanyDto = MinimalCompanyDto.builder()
                .name(registerCompanyDto.getName())
                .adresse(registerCompanyDto.getAdresse())
                .tel(registerCompanyDto.getTel())
                .build();

        MinimalEmployeeDto minimalEmployeeDto = MinimalEmployeeDto.builder()
                .firstName(registerCompanyDto.getFirstName())
                .lastName(registerCompanyDto.getLastName())
                .build();

        MinimalUserDto minimalUserDto = MinimalUserDto.builder()
                .email(registerCompanyDto.getEmail())
                .build();

        return ResponseEntity.ok().body(null);
    }

    @PostMapping
    //@CrossOrigin(origins = "http://cherry-erp.com")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    public GenericResponse create(@RequestBody CreateCompanyDto createCompanyDto) throws MessagingException, IOException, TemplateException {
        MinimalCompanyDto minimalCompanyDto = MinimalCompanyDto.builder()
                .name(createCompanyDto.getName())
                .adresse(createCompanyDto.getAdresse())
                .tel(createCompanyDto.getTel())
                .build();
        MinimalEmployeeDto minimalEmployeeDto = MinimalEmployeeDto.builder()
                .firstName(createCompanyDto.getFirstName())
                .lastName(createCompanyDto.getLastName())
                .build();
        MinimalUserDto minimalUserDto = MinimalUserDto.builder().email(createCompanyDto.getEmail()).build();

        return ResponseFactory.success(ADD_OK_CODE, null);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_GLOBAL_PARAM_ADMIN + "')")
    public GenericResponse update(@RequestBody CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        return ResponseFactory.success(ADD_OK_CODE, companyMapper.toDto(companyBusiness.save(company)));
    }

    @GetMapping("/current-company")
    public GenericResponse currentCompanyDto() {
        Company currentCompany = companyBusiness.getCurrentCompany();
        return ResponseFactory.success(FIND_ONE_OK_CODE, companyMapper.toDto(currentCompany));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + RolesConstant.ROLE_ERP_ADMIN + "')")
    public GenericResponse findOne(@PathVariable("id") Integer id) {
        return ResponseFactory.success(FIND_ONE_OK_CODE, companyMapper.toDto(companyBusiness.findOne(id)));
    }

    // delete company
    @DeleteMapping("/{id}")
    public GenericResponse delete(@PathVariable("id") Integer id) {
        // delete user
        // delete employee
        // delete taxes
        // delete licenses
        // delete currencies
        // delete company
        return success(DELETE_OK_CODE, id);
    }

    @GetMapping("/resend/{id}")
    public GenericResponse generateId(@PathVariable("id") Integer id) {
        return ResponseFactory.success(FIND_ONE_OK_CODE, "OK");
    }
}
