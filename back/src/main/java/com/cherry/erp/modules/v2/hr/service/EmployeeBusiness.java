package com.cherry.erp.modules.v2.hr.service;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Transactional
public interface EmployeeBusiness {

    @Transactional(propagation = REQUIRED)
    Employee save(Employee employee);

    @Transactional(propagation = REQUIRED)
    void delete(Integer employeeNumber);

    Employee findOne(Integer id);

    List<Employee> findAll();

    List<Employee> findActiveByCompanyId();

    Page<Employee> findByPage(Integer page, Integer pageSize);

    List<Employee> findByIds(Integer[] ids);

    List<Employee> findBytache(Integer tacheId);
    List<Employee> getClient();
    public Employee FindByTache(Integer idTache);
}

