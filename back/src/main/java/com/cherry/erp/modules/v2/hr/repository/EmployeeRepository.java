package com.cherry.erp.modules.v2.hr.repository;

import com.cherry.erp.common.repository.GenericRepository;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends GenericRepository<Employee> {

    Optional<Employee> findByEmployeeNumberAndCompanyId(String employeeNumber, Integer companyId);

    @Query(value = "select * from employees e where e.company_id = :companyId " +
            "except " +
            "select * from employees e2 where  e2.company_id = :companyId and e2.id in (select u.employee_id from users u where :role = any (u.roles)) ", nativeQuery = true)
    List<Employee> findByCompanyIdAndRoleNotIn(Integer companyId, String role);

    @Query(value = "select * from employees e where e.company_id = :companyId " +
            "except " +
            "select * from employees e2 where  e2.company_id = :companyId and e2.id in (select u.employee_id from users u where :role = any (u.roles)) ", nativeQuery = true)
    Page<Employee> findByCompanyIdAndRoleNotIn(Integer companyId, String role, Pageable pageParam);

    @Query("select u from Employee as u where u.company.id = :companyId and u.leavingDate is NULL order by u.creationDate desc")
    Page<Employee> findByCompanyIdInWork(Integer companyId, Pageable pageable);

    @Query("select u from Employee as u where u.company.id = :companyId and u.leavingDate is not NULL order by u.creationDate desc")
    Page<Employee> findByCompanyIdLeft(Integer companyId, Pageable pageable);

    @Query("select u from Employee as u where u.company.id = :companyId and u.leavingDate is not NULL and (u.external != true or u.external is null) order by u.creationDate desc")
    List<Employee> findActiveInternalByCompanyId(Integer companyId);

    @Query("select u from Employee as u where u.company.id = :companyId and u.leavingDate is NULL and (u.external != true or u.external is null) order by u.creationDate desc")
    List<Employee> findActiveByCompanyId(Integer companyId);




    @Query("select distinct e from Employee e inner join Planning p on p.employee.id = e.id where p.tache.id = :tacheId")
    List<Employee> findBytache(@Param("tacheId") Integer tacheId);


    @Query("select distinct e from Employee as e where e.external = true and e.externalType = 'CLIENT' and company_id = :companyId ")
    List<Employee> getClient(@Param("companyId") Integer companyId);

    @Query(value = "select * from employees c " +
            "inner join projects p on c.id = p.employee_id " +
            "inner join tache t on t.projet_id = p.id  " +
            "where t.id = :idTache", nativeQuery = true)
    Employee findTache(@Param("idTache") Integer idTache);


}
