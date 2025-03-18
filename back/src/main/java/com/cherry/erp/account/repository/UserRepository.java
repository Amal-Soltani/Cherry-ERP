package com.cherry.erp.account.repository;

import com.cherry.erp.account.model.persistent.SpiUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SpiUser, Integer> {

    Optional<SpiUser> findByIdAndEmployeeCompanyId(Integer id, Integer companyId);

    List<SpiUser> findAllByOrderByModificationDateDesc();

    List<SpiUser> findAllByOrderByLastConnexionDesc();

    Optional<SpiUser> findByEmailIgnoreCaseAndEnabledTrue(String email);

    Optional<SpiUser> findByEmailIgnoreCase(String email);

    @Query("select u from SpiUser u where u.employee.company.id = :companyId and u.active=true ")
    List<SpiUser> findByEmployeeCompanyIdByOrderByModificationDateDesc(Integer companyId);

    @Query("select u from SpiUser u where u.employee.company.id = :companyId and u.enabled= :enabled and u.active=true ")
    List<SpiUser> findByEmployeeCompanyIdAndEnabledByOrderByModificationDateDesc(Integer companyId, boolean enabled);

    @Query(value = "select * " +
            "from users u inner join employees e on u.employee_id = e.id " +
            "where e.company_id = :companyId " +
            "and not :role = any (u.roles) and u.is_active=true ", nativeQuery = true)
    List<SpiUser> findByEmployeeCompanyIdAndRoleNotInByOrderByModificationDateDesc(Integer companyId, String role);

    @Query("select u from SpiUser u where u.employee.company.id = :companyId")
    Page<SpiUser> findByEmployeeCompanyIdByOrderByModificationDateDesc(Integer companyId, Pageable pageParam);

    @Query(value = "select * " +
            "from users u inner join employees e on u.employee_id = e.id " +
            "where e.company_id = :companyId " +
            "and not :role = any (u.roles) and u.is_active=true ", nativeQuery = true)
    Page<SpiUser> findByEmployeeCompanyIdAndRoleNotInAdminByOrderByModificationDateDesc(Integer companyId, String role, Pageable pageParam);

    Optional<SpiUser> findByEmployeeId(Integer employeeId);

    @Query(value = "SELECT *" +
            " FROM users u INNER JOIN employees e ON u.employee_id = e.id" +
            " WHERE :role = ANY (u.roles)" +
            " AND e.company_id = :companyId" +
            " AND u.is_active=true and u.enabled = true", nativeQuery = true)
    List<SpiUser> findAllByRole(String role, Integer companyId);

    @Query("select count(u) from SpiUser u where u.employee.company.id = :companyId and u.active = true")
    Integer countSpiUserByCompany(Integer companyId);

    @Query("select max(u.lastConnexion) from SpiUser u where u.employee.company.id = :companyId")
    Date lastActivity(Integer companyId);

    @Query(value = "SELECT *" +
            " FROM users u INNER JOIN employees e ON u.employee_id = e.id" +
            " WHERE e.company_id = :companyId" +
            " AND u.is_active=true", nativeQuery = true)
    Page<SpiUser> findByEmployeeCompanyId(Integer companyId, Pageable pageParam);

    @Query("SELECT u FROM SpiUser u WHERE u.id in ?1")
    List<SpiUser> findByIds(Integer[] ids);

    @Query(value = "SELECT *" +
            " FROM users u INNER JOIN employees e ON u.employee_id = e.id" +
            " WHERE e.company_id = :companyId" +
            " AND e_mail = :email " +
            " AND u.is_active=true" +
            " AND u.enabled=true", nativeQuery = true)
    List<SpiUser> findByEmailIgnoreCaseAndEnabledTrueAndCompanyId(String email, Integer companyId);

    @Query("select count(u) from SpiUser u where u.employee.company.id = :companyId and u.active = true and u.enabled = true")
    Integer countEnabledSpiUserByCompany(Integer companyId);

    Optional<SpiUser> findOneById(Integer reference);

    @Query(value = "select u.* from users u where :equipeId = any(u.equipes) and u.is_active=true",nativeQuery = true)
    List<SpiUser> findAllInEquipe(Integer equipeId);
}
