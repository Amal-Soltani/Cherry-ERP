package com.cherry.erp.account.service;

import com.cherry.erp.account.model.persistent.SpiUser;
import freemarker.template.TemplateException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

    SpiUser create(SpiUser spiUser, boolean invitation) throws MessagingException, IOException, TemplateException;

    SpiUser save(SpiUser spiUser);

    List<SpiUser> findAll();

    List<SpiUser> findAllActive();

    Page<SpiUser> findByPage(Integer page, Integer pageSize);

    SpiUser findOne(Integer id);

    void delete(Integer id);

    String getCurrentUserName();

    SpiUser getCurrentUser();

    Boolean isConnectedUser();

    boolean isAdmin(Integer userId);

    Integer getConnectedUserId();

    boolean setEnable(Integer userId, Boolean enable);

    List<SpiUser> findAllByRole(String role);

    Optional<SpiUser> getUserByEmployeeId(Integer employeeId);

    Page<SpiUser> findByCompany(Integer companyId, Integer page, Integer pageSize);

    void loginAs(Integer id);

    void endLoginAs();

    List<SpiUser> findByIds(Integer[] ids);

    List<SpiUser> getUsersByEmployeeIds(Integer[] employeeIds);

    void logout();

    List<SpiUser> findAllInEquipe(Integer equipeId);
}
