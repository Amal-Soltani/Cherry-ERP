package com.cherry.erp.modules.v2.hr.service.impl;

import com.cherry.erp.common.exception.SpiException;
import com.cherry.erp.common.exception.SpiNotFoundException;
import com.cherry.erp.common.utils.RolesConstant;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.projet.repository.TacheRepository;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v2.hr.repository.EmployeeRepository;
import com.cherry.erp.modules.v2.hr.service.EmployeeBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.cherry.erp.common.utils.SpiUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;


@Service
@Slf4j
public class EmployeeBusinessImpl implements EmployeeBusiness {

    @Value("${repository.base-url}")
    private String repositoryBaseUrl;

    private final EmployeeRepository employeeRepository;
    private final TacheRepository tacheRepository;
    private final SpiUtils spiUtils;


    @Autowired
    public EmployeeBusinessImpl(EmployeeRepository employeeRepository,
                                SpiUtils spiUtils, TacheRepository tacheRepository) {
        this.employeeRepository = employeeRepository;
        this.tacheRepository = tacheRepository;

        this.spiUtils = spiUtils;
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() != null && employee.getId() != 0) {
            Employee oldEmployee = findOne(employee.getId());
            spiUtils.setOldValues(employee, oldEmployee);
            if (employee.getImage() == null) {
                employee.setImage(oldEmployee.getImage());
            }
        }
        employee.setModifiedBy(spiUtils.findCurrentUser().getEmployee().getFullName());
        if (employee.getId() == null || employee.getId() == 0) {
            employee.setCompany(spiUtils.findCurrentCompany());
        }
        if (employee.getCompany() == null) {
            employee.setCompany(spiUtils.findCurrentCompany());
        }
        spiUtils.setModifiedByAndCreatedByFields(employee);

        Employee savedEmployee = employeeRepository.save(employee);

        // create drive space

        return employeeRepository.save(savedEmployee);
    }

    private String savePhoto(Employee employee) {
        Path path = Paths.get(repositoryBaseUrl, spiUtils.findCurrentCompany().getId().toString(), "employees",
                "photos");

        File file = this.spiUtils.convertAndSaveBase64ToImage(employee.getImage(), path.toString(), String.valueOf(employee.getId()));
        if (file.exists()) {
            return "/" + spiUtils.findCurrentCompany().getId().toString() + "/employees/photos/" + file.getName();
        }
        return null;
    }

    private void checkEmployeeNumber(Employee employee, Integer companyId) {
        Optional<Employee> opEmployee = employeeRepository.findByEmployeeNumberAndCompanyId(employee.getEmployeeNumber(), companyId);
        opEmployee.ifPresent(empl -> {
            if (!employee.getId().equals(empl.getId())) {
                throw new SpiException(HttpStatus.BAD_REQUEST, EMPLOYEE_NUMBER_ALREADY_EXISTS);
            }
        });
    }

    private boolean createDirectory(String dir) {
        Path path = Paths.get(repositoryBaseUrl, spiUtils.findCurrentCompany().getId().toString(), "employees", dir);

        if (!new File(repositoryBaseUrl).isDirectory()) {
            log.info("repositoryBaseUrl [" + repositoryBaseUrl + "] not found!");
            return false;
        }
        File file = path.toFile();
        if (!file.exists()) {
            // créer l'hiérarchie si elle n'existe pas
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.mkdir()) {
                log.info("Directory " + dir + " is created!");
            } else {
                log.error("Failed to create directory!");
            }
        }
        return true;
    }


    @Override
    public List<Employee> findAll() {
        // if current user is not ERP_ADMIN, do not return employees with role ERP_ADMIN
        if (spiUtils.findCurrentUser().hasRole(RolesConstant.ROLE_ERP_ADMIN)) {
            return employeeRepository.findByCompanyId(spiUtils.findCurrentCompany().getId());
        } else {
            //return employeeRepository.findByCompanyIdAndRoleNotIn(spiUtils.findCurrentCompany().getId(), RolesConstant.ROLE_ERP_ADMIN);
            return employeeRepository.findByCompanyId(spiUtils.findCurrentCompany().getId()); // todo
        }
    }

    @Override
    public List<Employee> findActiveByCompanyId() {
        return employeeRepository.findActiveByCompanyId(spiUtils.findCurrentCompany().getId());
    }


    @Override
    public Employee findOne(Integer id) {
        return employeeRepository.findByIdAndCompanyId(id, spiUtils.findCurrentCompany().getId())
                .orElseThrow(() -> new SpiNotFoundException(RESOURCE_NOT_FOUND.getCode()));
    }

    @Override
    public void delete(Integer id) {
        try {
            employeeRepository.deleteById(findOne(id).getId());
        } catch (Exception e) {
            log.error("Error while deleting employee : {}", e.getMessage());
            throw new SpiException(HttpStatus.BAD_REQUEST, DELETE_KO_CODE);
        }
    }


    @Override
    public Page<Employee> findByPage(Integer page, Integer pageSize) {
        Pageable pageParam = PageRequest.of(page, pageSize);
        // if current user is not ERP_ADMIN, do not return employees with role ERP_ADMIN
        if (spiUtils.findCurrentUser().hasRole(RolesConstant.ROLE_ERP_ADMIN)) {
            return employeeRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
        } else {
            //return employeeRepository.findByCompanyIdAndRoleNotIn(spiUtils.findCurrentCompany().getId(), RolesConstant.ROLE_ERP_ADMIN, pageParam);
            return employeeRepository.findByCompanyId(spiUtils.findCurrentCompany().getId(), pageParam);
        }
    }

    @Override
    public List<Employee> findByIds(Integer[] ids) {
        return employeeRepository.findAllById(Arrays.asList(ids));
    }

    @Override
    public List<Employee> findBytache(Integer tacheId) {
        Tache t = tacheRepository.findByIdAndCompanyId(tacheId,spiUtils.findCurrentCompany().getId()).orElse(null);
        System.out.println(t.getId());
        return employeeRepository.findBytache(t.getId());
    }

    @Override
    public List<Employee> getClient() {
        return employeeRepository.getClient(spiUtils.findCurrentCompany().getId());
    }

    @Override
    public Employee FindByTache(Integer idTache) {
        Tache t = tacheRepository.findByIdAndCompanyId(idTache, spiUtils.findCurrentCompany().getId()).orElse(null);
        return employeeRepository.findTache(t.getId());
    }

}
